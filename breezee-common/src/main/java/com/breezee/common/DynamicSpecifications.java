/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 查询条件的获取
 *
 * @author Silence
 */
public class DynamicSpecifications {

    /**
     * 生成查询条件
     *
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Specification<T> createSpecification(Class<T> cla, final Map<String, Object> map) {
        map.remove("pageNumber");
        map.remove("pageSize");
        return (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            map.forEach((key, val) -> {
                if ((!key.startsWith("_")) && val != null && StringUtils.hasText(val.toString())) {
                    if (key.endsWith("_between")) {
                        String[] ss = val.toString().split(",");
                        predicate.add(cb.between(root.get(key.substring(0, (key.length() - 8))), ss[0], ss[1]));
                    } else if (key.endsWith("_or")) {
                        Predicate[] ps = new Predicate[val.toString().split(",").length];
                        int i = 0;
                        for (String s : val.toString().split(",")) {
                            ps[i] = cb.equal(root.get(key.substring(0, (key.length() - 3))), s);
                            i++;
                        }
                        predicate.add(cb.or(ps));
                    } else if (key.endsWith("_gt")) {
                        if (val instanceof Timestamp)
                            predicate.add(cb.greaterThanOrEqualTo(root.get(key.substring(0, (key.length() - 3))).as(Timestamp.class), (Timestamp) val));
                        else if (val instanceof Date)
                            predicate.add(cb.greaterThanOrEqualTo(root.get(key.substring(0, (key.length() - 3))).as(Date.class), (Date) val));
                        else if (val instanceof Number)
                            predicate.add(cb.greaterThanOrEqualTo(root.get(key.substring(0, (key.length() - 3))).as(Float.class), Float.parseFloat(val.toString())));
                    } else if (key.endsWith("_le")) {
                        if (val instanceof Timestamp)
                            predicate.add(cb.lessThanOrEqualTo(root.get(key.substring(0, (key.length() - 3))).as(Timestamp.class), (Timestamp) val));
                        else if (val instanceof Date)
                            predicate.add(cb.lessThanOrEqualTo(root.get(key.substring(0, (key.length() - 3))).as(Date.class), (Date) val));
                        else if (val instanceof Number)
                            predicate.add(cb.lessThanOrEqualTo(root.get(key.substring(0, (key.length() - 3))).as(Float.class), Float.parseFloat(val.toString())));
                    } else if (key.endsWith("_like")) {
                        if ("level".equals(val.toString())) {//进行中需要包含uploaded  特殊处理
                            cb.or(cb.like(root.get(key.substring(0, (key.length() - 5))).as(String.class), "%" + val.toString() + "%"), cb.like(root.get(key.substring(0, (key.length() - 5))).as(String.class), "uploaded"));
                        } else {
                            predicate.add(cb.like(root.get(key.substring(0, (key.length() - 5))).as(String.class), "%" + val.toString() + "%"));
                        }
                    } else if (key.endsWith("_notin")) {
                        if (val instanceof List) {
                            ((List) val).forEach(v -> {
                                predicate.add(cb.notEqual(root.get(key.substring(0, (key.length() - 6))).as(v.getClass()), v));
                            });
                        }
                    } else if (key.endsWith("_obj")) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String k = key.substring(0, (key.length() - 4));
                            Object v = objectMapper.readValue("{\"id\":" + val.toString() + "}", root.getModel().getAttribute(k).getJavaType());
                            predicate.add(cb.equal(root.get(k).as(v.getClass()), v));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        predicate.add(cb.equal(root.get(key).as(val.getClass()), val));
                    }
                }
            });
            Predicate[] pre = new Predicate[predicate.size()];
            return query.where(predicate.toArray(pre)).getRestriction();
        };
    }

    /**
     * Map转为查询条件
     *
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Specification<T> createSpecification(final Map<String, Object> map) {
        return createSpecification(null, map);
    }

}
