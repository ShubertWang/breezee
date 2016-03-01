package com.breezee.prj.sodexo.impl;

import com.breezee.common.ErrorInfo;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.common.util.ContextUtil;
import com.breezee.prj.sodexo.domain.ModelInfo;
import com.breezee.prj.sodexo.entity.ModelEntity;
import com.breezee.prj.sodexo.repository.ModelRepository;
import com.breezee.prj.sodexo.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/27.
 */
@Service("modelService")
public class ModelServiceImpl implements IModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public List<ModelInfo> findByParentId(Long parentId) {
        List<ModelEntity> l = new ArrayList<>();
        if (parentId == -1) {
            l = modelRepository.findTop();
        } else {
            ModelEntity en = modelRepository.findOne(parentId);
            if (en != null)
                l.addAll(en.getChildren());
        }
        List<ModelInfo> _l = new ArrayList<>(l.size());
        l.forEach(a -> {
            _l.add(a.toInfo(true));
        });
        return _l;
    }

    @Override
    public ModelInfo saveInfo(ModelInfo categoryInfo) {
        ModelEntity entity = modelRepository.findByCode(categoryInfo.getCode());
        if (categoryInfo.getId() == null && entity != null) {
            return ErrorInfo.build(categoryInfo, ContextUtil.getMessage("duplicate.key", new String[]{categoryInfo.getCode()}));
        }
        if (entity == null)
            entity = new ModelEntity();
        entity.parse(categoryInfo);
        if (categoryInfo.getParent() != null && categoryInfo.getParent().getId() != null) {
            entity.setParent(modelRepository.findOne(categoryInfo.getParent().getId()));
        }
        modelRepository.save(entity);
        return SuccessInfo.build(ModelInfo.class);
    }

    @Override
    public ModelInfo deleteById(Long id) {
        return null;
    }

    @Override
    public ModelInfo findInfoById(Long id) {
        return null;
    }

    @Override
    public List<ModelInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<ModelInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }

    @Override
    public void updateStatus(@PathParam("id") Long id, @PathParam("status") int status){}
}
