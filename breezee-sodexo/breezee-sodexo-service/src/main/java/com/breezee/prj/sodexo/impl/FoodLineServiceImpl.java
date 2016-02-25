/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.prj.sodexo.domain.FoodLineInfo;
import com.breezee.prj.sodexo.entity.FoodLineEntity;
import com.breezee.prj.sodexo.repository.FoodLineRepository;
import com.breezee.prj.sodexo.service.IFoodLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("foodLineService")
public class FoodLineServiceImpl implements IFoodLineService {

    @Autowired
    private FoodLineRepository foodLineRepository;

    @Override
    public FoodLineInfo saveInfo(FoodLineInfo foodLineInfo) {
        foodLineRepository.save(new FoodLineEntity().parse(foodLineInfo));
        return SuccessInfo.build(FoodLineInfo.class);
    }

    @Override
    public FoodLineInfo deleteById(Long id) {
        return null;
    }

    @Override
    public FoodLineInfo findInfoById(Long id) {
        FoodLineEntity entity = foodLineRepository.findOne(id);
        if(entity==null)
            return ErrorInfo.build(FoodLineInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<FoodLineInfo> listAll(Map<String, Object> m) {
        List<FoodLineEntity> l = foodLineRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l,(Callback<FoodLineEntity,FoodLineInfo>)(FoodLineEntity,FoodLineInfo)->FoodLineEntity.toInfo());
    }

    @Override
    public PageResult<FoodLineInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<FoodLineEntity> page = foodLineRepository.findAll(DynamicSpecifications.createSpecification(m),pageInfo);
        return new PageResult<>(page, FoodLineInfo.class, (foodLineEntity, foodLineInfo) -> foodLineEntity.toInfo());
    }
}
