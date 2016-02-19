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
        title: '账号列表',
        columns: [{
            code: 'code',
            title: '登录名称'
        }, {
            code: 'name',
            title: '账号名称'
        }, {
            code: 'type',
            title: '类型'
        }, {
            code: 'sex',
            title: '性别'
        }, {
            code: 'job',
            title: '岗位'
        }, {
            code: 'mobile',
            title: '手机'
        }, {
            code: 'orgName',
            title: '所属组织'
        }, {
            code: 'roles',
            title: '角色'
        }],
        multiple: false,
        ajaxType: 'post',
        url: '/data/sym/account/page',
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
    $('#insert').click(function () {
        $('#listPanel').toggleClass('dolphin-col-24').toggleClass('dolphin-col-18');
        $('#formPanel').toggle();
    });
    $('#save').click(function () {
        if (Dolphin.form.validate('#editForm')) {
            var data = Dolphin.form.getValue('editForm', '"');
            data.arguments = Dolphin.json2string(data.arguments);
            Dolphin.ajax({
                url: '/data/sym/account/',
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
});