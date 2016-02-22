/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    var accountPanel = $("#accountPanel"),rolePanel=$("#rolePanel");

    Dolphin.form.parse();

    var list = new Dolphin.LIST({
        panel: "#list",
        title: '角色列表',
        columns: [{
            code: 'code',
            title: '角色编码'
        }, {
            code: 'name',
            title: '角色名称'
        }, {
            code: 'permits',
            title: '权限'
        }],
        multiple: false,
        ajaxType: 'post',
        url: '/data/sym/role/list',
        dataFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                data.rows[i].arguments = Dolphin.string2json(data.rows[i].arguments || '{}');
            }
            return data;
        },
        onCheck: function (data) {
            Dolphin.form.setValue(data, '#editForm');
        }
    });

    var roleAccntList = new Dolphin.LIST({
        panel : '#accountList',
        url : '/data/sym/account/role/{id}',
        ajaxType:'post',
        mockPathData : ['id'],
        data : {rows : [], total : 0},
        pagination : true,
        columns : [{
            code: 'code',
            title: '账号编码'
        }, {
            code: 'name',
            title: '账号名称'
        }]
    });

    var selectedList = new Dolphin.LIST({
        panel : '#selectedList',
        data : {rows : [], total : 0},
        title : '已选择列表',
        pagination : true,
        rowIndex : false,
        columns : [{
            code : 'code',
            title : '账号编码'
        }, {
            code : 'name',
            title : '账号名称'
        }, {
            code : 'status',
            title : '状态'
        }]
    });

    var unselectedList = new Dolphin.LIST({
        panel : '#unselectedList',
        data : {rows : [], total : 0},
        title : '未选择列表',
        panelType : 'panel-info',
        ajaxType:'post',
        url : '/data/sym/account/excludeRole/'+selectNode.id,
        pagination : true,
        rowIndex : false,
        columns : [{
            code : 'code',
            title : '账号编码'
        }, {
            code : 'name',
            title : '账号名称'
        }, {
            code : 'status',
            title : '状态'
        }]
    });

    //============================================================== event
    function checkEditFormHidden(){
        return $('#formPanel').is(':hidden');
    }
    $('#insert').click(function () {
        if(checkEditFormHidden()){
            $('#listPanel').toggleClass('dolphin-col-24').toggleClass('dolphin-col-18');
            $('#formPanel').toggle();
        }
        Dolphin.form.empty("#editForm");
    });
    $('#update').click(function () {
        if(checkEditFormHidden()) {
            $('#listPanel').toggleClass('dolphin-col-24').toggleClass('dolphin-col-18');
            $('#formPanel').toggle();
        }
        Dolphin.form.setValue(list.getChecked()[0], '#editForm');
    });

    $('#save').click(function () {
        if (Dolphin.form.validate('#editForm')) {
            var data = Dolphin.form.getValue('editForm', '"');
            data.arguments = Dolphin.json2string(data.arguments);
            Dolphin.ajax({
                url: '/data/sym/role/',
                type: Dolphin.requestMethod.PUT,
                data: Dolphin.json2string(data),
                onSuccess: function (reData) {
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback: function () {
                            list.reload();
                            Dolphin.form.empty('#editForm');
                        }
                    })
                }
            })
        }
    });
    $("#query").click(function () {
        list.query(Dolphin.form2json("queryForm"));
    });
    $("#conditionReset").click(function () {
        Dolphin.form.empty("#queryForm")
    });
    $("#cancel").click(function(){

    });

    $('#multipleUpdateAttr').click(function () {
        rolePanel.slideToggle(300, function () {
            accountPanel.slideToggle(300);
            unselectedList.load('/data/sym/account/excludeRole/'+selectNode.id);
            selectedList.loadData(roleAccntList.data);
        });
    });

    $('[change]').click(function () {
        var thisButton = $(this), allFlag = !!thisButton.attr('all'),
            sourceList, targetList,
            checkedData,
            i;
        if(thisButton.attr("change") == "select"){
            sourceList = unselectedList;
            targetList = selectedList;
        }else{
            sourceList = selectedList;
            targetList = unselectedList;
        }
        if(allFlag){
            checkedData = [].concat(sourceList.data.rows);
        }else{
            checkedData = sourceList.getChecked();
        }
        for(i = 0; i < checkedData.length; i++){
            if(!checkedData[i].inheritFlag){
                sourceList.removeRow(checkedData[i].__id__);
                targetList.addRowWithData(checkedData[i]);
            }
        }
    });

    $('#confirm').click(function () {
        var data = {},selectNode=list.getChecked()[0];
        data.id = selectNode.id;
        data.code = selectNode.code;
        data.accounts = [];
        var selData = selectedList.data.rows;
        for(var i = 0; i < selData.length; i++){
            data.accounts.push(selData[i].id);
        }
        console.log(selectNode);
        Dolphin.ajax({
            url : '/data/sym/role/acntRel',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '保存成功', {
                    callback : function () {
                        roleAccntList.reload();
                    }
                })
            }
        });
    });

    $('#cancelSel').click(function () {
        accountPanel.slideToggle(300, function () {
            rolePanel.slideToggle(300);
        });
    });
});