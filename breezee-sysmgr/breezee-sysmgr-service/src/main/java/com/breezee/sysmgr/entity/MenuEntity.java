/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.sysmgr.entity;

import com.breezee.common.BaseInfo;

import java.util.Set;

/**
 * 对于菜单，我们不在需要，直接配置在模板文件中，然后进行权限配置即可
 * Created by Silence on 2016/2/11.
 */
@Deprecated
public class MenuEntity extends BaseInfo {

    private String url;

    private String script;

    private boolean leaf;

    private boolean collapsed;

    private String accessRule;

    private int orderNo;

    private String icon;

    /**
     * 使用范围，用来表示是在desktop中，还是在mobile中，还是在所有中
     */
    private String scope;

    private MediaEntity parent;

    private Set<MenuEntity> children;

}
