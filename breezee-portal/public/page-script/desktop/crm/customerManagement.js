/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    menu.select('customerManagement');
    Dolphin.form.parse();

    var list = new Dolphin.LIST({
        panel: "#list",
        title: '客户列表',
        columns: [{
            code: 'code',
            width:'75px',
            title: '客户编码',
            formatter:function(val,data){
                return val.substr(7,20)+"...";
            }
        }, {
            code: 'name',
            title: '客户名称'
        }, {
            code: 'type',
            title: '客户类型'
        }, {
            code: 'company',
            title: '网点编码'
        }, {
            code: 'userJob',
            title: '内部人员'
        }, {
            code:'addressCount',
            title:'收货地址',
            formatter:function(val,data){
                if(val>0)
                    return "<a href='shippingAddress?userId="+data.id+"'>"+val+"</a>";
                return val;

            }
        }],
        multiple: false,
        ajaxType: 'post',
        url: '/data/crm/user/page',
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
    $("#delete").click(function(){
        var checked = list.getChecked();
        if(checked.length==0){
            Dolphin.alert("请选择一行");
            return;
        }
        Dolphin.ajax({
            url: '/data/crm/user/'+checked[0].id,
            type: Dolphin.requestMethod.DELETE,
            onSuccess: function (reData) {
                list.reload();
            }
        });
    });
    $('#save').click(function () {
        if (Dolphin.form.validate('#editForm')) {
            var data = Dolphin.form.getValue('editForm', '"');
            data.arguments = Dolphin.json2string(data.arguments);
            Dolphin.ajax({
                url: '/data/crm/user/',
                type: Dolphin.requestMethod.PUT,
                data: Dolphin.json2string(data),
                onSuccess: function (reData) {
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback: function () {
                            list.reload();
                            Dolphin.form.empty('#editForm');
                        }
                    });
                }
            });
        }
    });
    $("#query").click(function () {
        list.query(Dolphin.form2json("queryForm"));
    });
    $("#conditionReset").click(function () {
        Dolphin.form.empty("#queryForm")
    });
    $("#cancel").click(function(){
        $('#listPanel').toggleClass('dolphin-col-18').toggleClass('dolphin-col-24');
        $('#formPanel').toggle();
        Dolphin.form.empty("#editForm");
    });
});