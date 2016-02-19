/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.impl;

import com.breezee.common.PageInfo;
import com.breezee.common.PageResult;
import com.breezee.common.SuccessInfo;
import com.breezee.sysmgr.api.domain.MediaInfo;
import com.breezee.sysmgr.api.service.IMediaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("mediaService")
public class MediaServiceImpl implements IMediaService {
    @Override
    public MediaInfo saveInfo(MediaInfo mediaInfo) {
        return SuccessInfo.build(MediaInfo.class);
    }

    @Override
    public MediaInfo findInfoById(Long id) {
        return null;
    }

    @Override
    public List<MediaInfo> listAll(Map<String, Object> m) {
        return null;
    }

    @Override
    public PageResult<MediaInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }
}
