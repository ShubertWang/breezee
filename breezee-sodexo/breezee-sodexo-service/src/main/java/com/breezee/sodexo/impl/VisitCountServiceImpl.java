package com.breezee.sodexo.impl;

import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.sodexo.api.domain.VisitCountInfo;
import com.breezee.sodexo.api.service.IVisitCountService;
import com.breezee.sodexo.entity.VisitCountEntity;
import com.breezee.sodexo.repository.VisitCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/3/7.
 */
@Service("visitCountService")
public class VisitCountServiceImpl implements IVisitCountService {

    @Autowired
    private VisitCountRepository visitCountRepository;

    @Override
    public VisitCountInfo saveInfo(VisitCountInfo visitCountInfo) {
        String code = visitCountInfo.getPageId()+"+"+visitCountInfo.getReader();
        visitCountInfo.setCode(code);
        VisitCountEntity entity = visitCountRepository.findOne(code);
        if(entity!=null){
            visitCountInfo.setReadCount(entity.getReadCount()+1);
        } else {
            entity = new VisitCountEntity();
            visitCountInfo.setReadCount(1);
        }
        entity.parse(visitCountInfo);
        visitCountRepository.save(entity);
        return SuccessInfo.build(VisitCountInfo.class);
    }

    @Override
    public VisitCountInfo deleteById(Long id) {
        return null;
    }

    @Override
    public VisitCountInfo findInfoById(Long id) {
        return null;
    }

    @Override
    public List<VisitCountInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<VisitCountInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }
}
