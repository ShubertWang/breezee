/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    Dolphin.form.parse();
    var list = new Dolphin.LIST({
        panel : "#list",
        title : '服务列表',
        columns: [{
            code: 'code',
            title: '服务线编码',
            width:'90px'
        }, {
            code: 'name',
            title: '服务线名称'
        }, {
            code: 'shipping',
            title: '服务类型'
        }
        //    ,{
        //    code: 'startTime',
        //    title: '营业时间',
        //    width:'150px',
        //    formatter:function(val,data){
        //        return data.startTime+"-"+data.endTime;
        //    }
        //}
            ,{
            code: 'closeTime',
            title: '预订截止',
            width:'90px'
        },{
            code: 'shiftNum',
            width:'75px',
            title: '提前量'
        }, {
            code: 'startTime',
            title: '开始时间'
        },{
            code: 'payType',
            title: '支付方式'
        },{
            code: 'shiftNum',
            width:'100px',
            title: '翻台时间'
        }, {
                code:'status',
                title:'状态',
                formatter:function(val,data){
                    return val?"启用":"禁用";
                }
            }],
        multiple : false,
        url : '/data/sdx/foodLine/messhallId/'+REQUEST_MAP.data.id,
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
        if(list.getChecked().length==0) {
            Dolphin.alert( '无选择项');
            return;
        }
        if(checkEditFormHidden()) {
            $('#listPanel').toggleClass('dolphin-col-24').toggleClass('dolphin-col-18');
            $('#formPanel').toggle();
        }
        Dolphin.form.setValue(list.getChecked()[0], '#editForm');
        if(list.getChecked()[0].payType) {
            var tmp = list.getChecked()[0].payType.split(",");
            for (var i = 0; i < tmp.length; i++) {
                if(tmp[i] && document.getElementById(tmp[i]))
                    document.getElementById(tmp[i]).checked=true;
            }
        }
    });

    $("#delete").click(function(){
        if(list.getChecked().length>0) {
            Dolphin.ajax({
                url: '/data/sdx/foodLine/'+list.getChecked()[0].id,
                type: Dolphin.requestMethod.DELETE,
                onSuccess: function (reData) {
                    Dolphin.alert(reData.msg || '删除成功', {
                        callback: function () {
                            list.reload();
                        }
                    })
                }
            });
        } else {
            Dolphin.alert( '无选择项');
        }
    });
    $('#save').click(function () {
        if(Dolphin.form.validate('#editForm')){
            var data = Dolphin.form.getValue('editForm', '"');
            data.messhallId = REQUEST_MAP.data.id;
            var tmp="";
            $("input[name='payType']").each(function () {
                if ($(this).is(':checked')) {
                    tmp = tmp+$(this).val()+",";
                }
            });
            data.payType = tmp;
            Dolphin.ajax({
                url : '/data/sdx/foodLine/',
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
            });
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
    $("#cancelSel").click(function(){
        Dolphin.goHistory();
    });

    $("#enableBtn").click(function(){
        if(list.getChecked().length==0) {
            Dolphin.alert( '无选择项');
            return;
        }
        Dolphin.ajax({
            url : '/data/sdx/foodLine/status/'+list.getChecked()[0].id+'/1',
            type : Dolphin.requestMethod.GET,
            onSuccess : function (reData) {
                list.reload();
            }
        });
    });
    $("#disableBtn").click(function(){
        if(list.getChecked().length==0) {
            Dolphin.alert( '无选择项');
            return;
        }
        Dolphin.ajax({
            url : '/data/sdx/foodLine/status/'+list.getChecked()[0].id+'/0',
            type : Dolphin.requestMethod.GET,
            onSuccess : function (reData) {
                list.reload();
            }
        });
    });
});