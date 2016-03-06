Dolphin.defaults.mockFlag = false;
$(function () {
    menu.select('undoList');
    var page = {};

    page.connect = {
        undoList: {
            url: '/data/bpm/bpmTask/findUndoTasks'
        }
    };

    page.orderStatus = {
        orderConfirm: 2,
        orderReject: 5,
        orderMake: 3,
        orderComplete: 4
    };

    page.orderStatusName = {
        orderConfirm: '确认',
        orderReject: '拒绝',
        orderMake: '已制作',
        orderComplete: "完成",
        orderCancel:'已取消'
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
            panel: '#list',
            url: _this.connect.undoList.url,
            ajaxType: 'post',
            title: '待办列表',
            data: {rows: []},
            columns: [{
                code: 'properties.orderInfo.code',
                title: '订单号',
                formatter: function (val, row, index) {
                    var link = $('<a>').html(val);
                    link.attr('href', 'taskInfo?taskId=' + row.id + '&processInstanceId=' + row['processInstanceId'] + '&processDefinitionId=' + row['processDefinitionId'] + '&businessKey=' + row['businessKey'] + '&formKey=' + (row['formKey'] || ''));
                    return link;
                }
            }, {
                code: 'name',
                title: '当前节点'
            }, {
                code: 'properties.orderInfo.consigneeName',
                title: '收货人'
            }, {
                code: 'properties.orderInfo.consigneeMobile',
                title: '收货手机'
            }, {
                code: 'createTime',
                title: '下单时间',
                formatter: function (val) {
                    return Dolphin.longDate2string(val, 'yyyy-MM-dd hh:mm:ss');
                }
            }, {
                code: 'properties.orderInfo.subTotal.value',
                textAlign:'right',
                title: '订单金额'
            }, {
                code: 'properties.orderInfo.statusName',
                title: '订单状态'
            }, {
                code: 'properties.orderInfo.shippingMethod',
                title: '服务类型'
            }, {
                code: 'properties.orderInfo.needTime',
                title: '送餐时间'
            }, {
                code: 'formKey',
                title: '操作',
                width:'150px',
                formatter: function (val, data) {
                    if (val) {
                        var render = [];
                        var tmp = val.split('|');
                        for (var i = 0; i < tmp.length; i++) {
                            if (tmp[i]) {
                                render.push("<button type='button' class='btn btn-default btn-sm' onclick='taskComplete(this," + data.id + "," + data.businessKey + ")' id='" + tmp[i] + "'>" + _this.orderStatusName[tmp[i]] + "</button>");
                            }
                        }
                        return render.join(" ");
                    }
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

    window.taskComplete = function (el, taskId, orderId) {
        console.log(el.id);
        var data = {
            orderCancel: 'N',
            taskOwner: REQUEST_MAP.userData.userId,
            orderId: orderId,
            orderStatus: page.orderStatus[el.id],
            complete: true
        };
        if (el.id == 'orderMake') {
            data.complete = false;
        }
        if(el.id == 'orderReject') {
            data.orderCancel = 'Y';
        }
        Dolphin.ajax({
            url: '/data/bpm/bpmTask/' + taskId,
            type: Dolphin.requestMethod.POST,
            data: Dolphin.json2string(data),
            onSuccess: function (reData) {
                page.undoList.reload();
            }
        });
    }
});