/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.crm.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.common.util.ContextUtil;
import com.breezee.crm.api.domain.ShippingAddressInfo;
import com.breezee.crm.api.domain.UserInfo;
import com.breezee.crm.entity.EncodeEntity;
import com.breezee.crm.entity.ShippingAddressEntity;
import com.breezee.crm.entity.UserEntity;
import com.breezee.crm.repository.EncodeRepository;
import com.breezee.crm.repository.ShippingAddressRepository;
import com.breezee.crm.repository.UserRepository;
import com.breezee.crm.api.service.IUserService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/11.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private EncodeRepository encodeRepository;

    @Override
    public void saveShippingAddress(ShippingAddressInfo addressInfo) {
        UserEntity u = userRepository.findOne(addressInfo.getUserId());
        //TODO:throw user not found exception
        ShippingAddressEntity se = new ShippingAddressEntity().parse(addressInfo);
        se.setUser(u);
        shippingAddressRepository.save(se);
    }

    @Override
    public UserInfo findByCode(String code) {
        UserEntity ue = userRepository.findByCode(code);
        if(ue == null)
            return ErrorInfo.build(UserInfo.class);
        return ue.toInfo();
    }

    @Override
    public List<ShippingAddressInfo> findShippingAddress(Long userId) {
        UserEntity u = userRepository.findOne(userId);
        if(u != null){
            UserInfo ui = u.toInfo();
            return ui.getShippingAddressInfos();
        }
        return new ArrayList<>(0);
    }

    @Override
    public UserInfo employeeValidate(UserInfo info) {
        String key = Md5Crypt.md5Crypt((info.getDn()+info.getName()+info.getIdCard()).getBytes());
        EncodeEntity en = encodeRepository.findBySiteAndCode(info.getCompany(),key);
        if(en == null)
            return ErrorInfo.build(UserInfo.class);
        return SuccessInfo.build(info);
    }

    @Override
    public UserInfo registerSite(UserInfo info) {
        UserEntity entity = userRepository.findOne(info.getId());
        if(entity!=null){
            entity.setCompany(info.getCompany());
            entity.setType("site");
            userRepository.save(entity);
            return SuccessInfo.build(info);
        }
        return ErrorInfo.build(UserInfo.class);
    }

    @Override
    public UserInfo saveInfo(UserInfo userInfo) {
        UserEntity entity = userRepository.findByCode(userInfo.getCode());
        //如果新增的账号已经存在，则返回错误信息
        if (userInfo.getId() == null && entity != null) {
            return ErrorInfo.build(userInfo, ContextUtil.getMessage("duplicate.key", new String[]{userInfo.getCode()}));
        }
        if (entity == null)
            entity = new UserEntity();
        entity.parse(userInfo);
        userRepository.save(new UserEntity().parse(userInfo));
        return SuccessInfo.build(UserInfo.class);
    }

    @Override
    public UserInfo deleteById(Long id) {
        return null;
    }

    @Override
    public UserInfo findInfoById(Long id) {
        UserEntity ue = userRepository.findOne(id);
        if(ue == null)
            return ErrorInfo.build(UserInfo.class);
        return ue.toInfo();
    }

    @Override
    public List<UserInfo> listAll(Map<String, Object> m) {
        List<UserEntity> l = userRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<UserEntity, UserInfo>) (userEntity, userInfo) -> userEntity.toInfo());
    }

    @Override
    public PageResult<UserInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<UserEntity> page = userRepository.findAll(DynamicSpecifications.createSpecification(m),pageInfo);
        return new PageResult<>(page, UserInfo.class, (userEntity, userInfo) -> userEntity.toInfo());
    }

    @Override
    public void updateStatus(Long id, int status) {

    }
}
