/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.sysmgr.api.domain.RoleInfo;
import com.breezee.sysmgr.api.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

    @Override
    public RoleInfo saveInfo(RoleInfo roleInfo) {
        return SuccessInfo.build(RoleInfo.class);
    }

    @Override
    public RoleInfo findInfoById(Long id) {
        return null;
    }

    @Override
    public List<RoleInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<RoleInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }
}
