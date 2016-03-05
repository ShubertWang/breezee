Dolphin.defaults.mockFlag = false;
$(function () {
    menu.select('undoList');
    var page = {};

    page.connect = {
        undoList : {
            url : '/data/bpm/bpmTask/findUndoTasks'
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

        this.undoList.query(Dolphin.form.getValue("#queryForm"));
        return this;
    };

    page.initPage = function () {
        var _this = this;
        this.undoList = new Dolphin.LIST({
            panel : '#list',
            url : _this.connect.undoList.url,
            ajaxType:'post',
            title : '待办列表',
            data:{rows:[]},
            columns : [{
                code: 'properties.orderInfo.code',
                title: '订单号',
                formatter : function(val, row, index){
                    var link = $('<a>').html(val);
                    link.attr('href', 'taskInfo');
                    return link;
                }
            }, {
                code:'processDefinitionName',
                title:'流程名称'
            },{
                code:'name',
                title:'节点名称'
            }, {
                code:'properties.orderInfo.consigneeName',
                title:'收货人'
            },{
                code:'properties.orderInfo.consigneeMobile',
                title:'收货手机'
            },{
                code: 'createTime',
                title: '下单时间',
                formatter:function(val){
                    return Dolphin.longDate2string(val,'yyyy-MM-dd hh:mm:ss');
                }
            },{
                code: 'properties.orderInfo.subTotal.value',
                title: '订单金额'
            },{
                code: 'properties.orderInfo.paymentType',
                title: '支付方式'
            },{
                code: 'properties.orderInfo.statusName',
                title: '订单状态'
            },{
                code: 'properties.orderInfo.shippingMethod',
                title: '服务类型'
            },{
                code: 'properties.orderInfo.needTime',
                title: '送餐时间'
            },{
                code: 'formKey',
                title: '操作',
                formatter:function(val,data){
                    if(val) {
                        var render = [];
                        var tmp = val.split('|');
                        for(var i=0;i<tmp.length;i++){
                            render.push("<button onclick='taskComplete(this,"+data.id+","+data.businessKey+")' id='"+tmp[i]+"'>"+tmp[i]+"</button>");
                        }
                        console.log(render);
                        return render.join(" ");
                    }
                    console.log(val);
                    return val;
                }
            }]
        });
    };

    page.initEvent = function () {
        var _this = this;
        $("#query").click(function () {
            _this.undoList.query(Dolphin.form.getValue("#queryForm"));
        });
        $("#conditionReset").click(function () {
            Dolphin.form.empty("#queryForm")
        });
    };

    window.page = page.init();

    window.taskComplete = function(el,id,orderId){
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
            url:'/data/bpm/bpmTask/'+id,
            type: Dolphin.requestMethod.POST,
            data: Dolphin.json2string(data),
            onSuccess: function (reData) {
                page.undoList.reload();
            }
        });
    }
});