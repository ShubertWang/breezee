package com.breezee.crm.impl;

import com.breezee.common.InfoList;
import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.util.Callback;
import com.breezee.crm.api.domain.EncodeInfo;
import com.breezee.crm.api.service.IEncodeService;
import com.breezee.crm.entity.EncodeEntity;
import com.breezee.crm.repository.EncodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/28.
 */
@Service("encodeService")
public class EncodeServiceImpl implements IEncodeService {

    @Autowired
    private EncodeRepository encodeRepository;

    @Override
    public List<EncodeInfo> findBySite(String site) {
        return new InfoList<>(encodeRepository.findBySite(site), (Callback<EncodeEntity, EncodeInfo>) (encodeEntity, info) -> encodeEntity.toInfo());
    }

    @Override
    public EncodeInfo findBySiteAndCode(String site, String code) {
        EncodeEntity encodeEntity = encodeRepository.findBySiteAndCode(site,code);
        if(encodeEntity!=null)
            return encodeEntity.toInfo();
        return null;
    }

    @Override
    public EncodeInfo saveInfo(EncodeInfo encodeInfo) {
        return encodeRepository.save(new EncodeEntity().parse(encodeInfo));
    }

    @Override
    public EncodeInfo deleteById(Long id) {
        encodeRepository.delete(id);
        return null;
    }

    @Override
    public EncodeInfo findInfoById(Long id) {
        return null;
    }

    @Override
    public List<EncodeInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<EncodeInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }

    @Override
    public void updateStatus(Long id, int status) {

    }
}
