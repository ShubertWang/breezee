/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    Dolphin.form.parse();

    var list = new Dolphin.LIST({
        panel : "#list",
        title : '属性列表',
        columns: [{
            code: 'code',
            title: '属性编码'
        }, {
            code: 'name',
            title: '属性名称'
        }, {
            code: 'fieldType',
            title: '字段类型'
        }, {
            code: 'unitCode',
            title: '单位'
        }, {
            code:'arguments',
            title:'参数',
            formatter : function (val) {
                return Dolphin.json2string(val);
            }
        },{
            code:'orderNo',
            title:'排序'
        },{
            code: 'remark',
            title: '备注'
        }],
        multiple : false,
        ajaxType:'post',
        url : '/data/pcm/attribute/page',
        dataFilter : function (data) {
            for(var i = 0; i < data.rows.length; i++){
                data.rows[i].arguments = Dolphin.string2json(data.rows[i].arguments || '{}');
            }
            return data;
        },
        onCheck : function (data) {
            Dolphin.form.setValue(data, '#editForm');
        }
    });

    //============================================================== event
    function checkEditFormHidden(){
        return $('#formPanel').is(':hidden');
    }
    $('#insert').click(function () {
        if(checkEditFormHidden()) {
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
        $("#field-Type").change();
        Dolphin.form.setValue(list.getChecked()[0], '#editForm');
    });
    $('#save').click(function () {
        if(Dolphin.form.validate('#editForm')){
            var data = Dolphin.form.getValue('editForm', '"');
            data.arguments = Dolphin.json2string(data.arguments);
            Dolphin.ajax({
                url : '/data/pcm/attribute',
                type : Dolphin.requestMethod.PUT,
                data : Dolphin.json2string(data),
                onSuccess : function (reData) {
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback : function () {
                            list.reload();
                            Dolphin.form.empty('#editForm');
                        }
                    })
                }
            })
        }
    });
    $("#cancel").click(function(){
        $('#listPanel').toggleClass('dolphin-col-18').toggleClass('dolphin-col-24');
        $('#formPanel').toggle();
        Dolphin.form.empty("#editForm");
    });
    $("#query").click(function () {
        list.query(Dolphin.form2json("queryForm"));
    });
    $("#conditionReset").click(function () {
        Dolphin.form.empty("#queryForm")
    });
    $("#field-Type").change(function(){
        var selVal = $(this).children('option:selected').val();
        switch (selVal){
            case 'dict':
                $("#extendInfo").show();
                $("#extendInfo").empty();
                $('<hr/><div class="form-group"><label>枚举编码</label><input type="text" class="form-control"  name="arguments.enumCode" dol-validate="required"></div>').appendTo($("#extendInfo"));
                break;
            case 'integer':
            case 'numberic':
                $("#extendInfo").show();
                $("#extendInfo").empty();
                $('<hr/><div class="form-group"><label>单位</label><input type="text" class="form-control"  name="unitCode"></div>').appendTo($("#extendInfo"));
                break;
            default:
                $("#extendInfo").empty();
                $("#extendInfo").hide();
                break;
        }
    });

});