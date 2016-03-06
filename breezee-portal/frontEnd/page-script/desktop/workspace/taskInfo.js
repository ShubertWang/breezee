/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

$(function () {
    menu.select('undoList');

    var page = {};

    page.connect = {
        orderInfo : {
            url : nginxProxy+'/data/oms/order/'+REQUEST_MAP.data.businessKey
        }
    };

    page.orderStatus = {
        orderConfirm : 2,
        orderReject:5,
        orderMake:3,
        orderComplete:4
    };

    page.init = function () {
        this.initPage();
        this.initEvent();
        this.initData();

        return this;
    };

    page.initPage = function () {
        var _this = this;
        this.orderLineList = new Dolphin.LIST({
            panel : '#list',
            data:{rows:[]},
            ajaxType:'post',
            pagination:false,
            columns : [{
                code: 'skuId',
                title: '菜品编码'
            },{
                code: 'note',
                title: '菜品名称'
            },{
                code: 'unitPrice.value',
                title: '单价'
            },{
                code: 'quantity.value',
                title: '数量'
            }]
        });
    };

    page.initEvent = function () {
    };

    page.initData = function(){
        var _this = this;
        Dolphin.ajax({
            url : _this.connect.orderInfo.url,
            onSuccess : function(retData){
                if(retData.value) {
                    Dolphin.form.setValue(retData.value, '#baseInfo');
                    _this.orderLineList.loadData({rows:retData.value.orderLines});
                }
            }
        });
    }

    window.page = page.init();

    window.taskComplete = function(el,taskId,orderId){
        console.log(el.id);
        var data = {
            orderCancel:'N',
            taskOwner:REQUEST_MAP.userData.userId,
            orderId:orderId,
            orderStatus:page.orderStatus[el.id],
            complete:true
        };
        if(el.id=='orderMake'){
            data.complete = false;
        }
        Dolphin.ajax({
            url:nginxProxy+'/data/bpm/bpmTask/'+taskId,
            type: Dolphin.requestMethod.POST,
            data: Dolphin.json2string(data),
            onSuccess: function (reData) {
                Dolphin.goHistory();
            }
        });
    }
});