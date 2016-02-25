/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    Dolphin.form.parse();

    var list = new Dolphin.LIST({
        panel: "#list",
        title: '客户列表',
        columns: [{
            code: 'code',
            title: '客户编码'
        }, {
            code: 'name',
            title: '客户名称'
        }, {
            code: 'type',
            title: '客户类型'
        }, {
            code: 'sex',
            title: '性别'
        }, {
            code: 'star',
            title: '星级'
        }, {
            code: 'mobile',
            title: '手机'
        }, {
            code: 'company',
            title: '公司'
        }, {
            code: 'wechat',
            title: '微信号'
        }, {
            code: 'status',
            title: '状态',
            formatter:function(val){
                return '<div class="slideThree"><input type="checkbox" value="None" id="slideThree" name="check" checked /><label for="slideThree"></label></div>';
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
        $('#listPanel').toggleClass('dolphin-col-18').toggleClass('dolphin-col-24');
        $('#formPanel').toggle();
        Dolphin.form.empty("#editForm");
    });
});