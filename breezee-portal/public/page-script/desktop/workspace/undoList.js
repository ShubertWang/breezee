Dolphin.defaults.mockFlag = false;
$(function () {
    menu.select('undoList');
    var page = {},prcsDef;

    page.connect = {
        undoList: {
            url: '/data/bpm/bpmTask/findUndoTasks'
        }
    };

    page.orderStatus = {
        orderConfirm: 2,
        orderReject: 5,
        orderMake: 3,
        orderComplete: 4,

        orderAssign:3
    };

    page.orderStatusName = {
        orderConfirm: '确认',
        orderReject: '拒绝',
        orderMake: '已制作',
        orderComplete: "完成",
        orderCancel: '已取消',
        orderAssign:'分配'
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.column = {
        orderProcess: [{
            code: 'properties.orderInfo.code',
            title: '订单号',
            formatter: function (val, row, index) {
                var link = $('<a>').html(val);
                link.attr('href', 'taskInfo?type=product&taskId=' + row.id + '&processInstanceId=' + row['processInstanceId'] + '&processDefinitionId=' + row['processDefinitionId'] + '&businessKey=' + row['businessKey'] + '&formKey=' + (row['formKey'] || ''));
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
            code: 'properties.orderInfo.issueDate',
            title: '下单时间',
            formatter: function (val) {
                return Dolphin.longDate2string(val, 'yyyy-MM-dd hh:mm:ss');
            }
        }, {
            code: 'properties.orderInfo.subTotal.value',
            textAlign: 'right',
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
        }],
        seatProcess: [{
            code: 'properties.seatInfo.code',
            title: '订单号',
            formatter: function (val, row, index) {
                var link = $('<a>').html(val);
                link.attr('href', 'taskInfo?type=seat&taskId=' + row.id + '&processInstanceId=' + row['processInstanceId'] + '&processDefinitionId=' + row['processDefinitionId'] + '&businessKey=' + row['businessKey'] + '&formKey=' + (row['formKey'] || ''));
                return link;
            }
        }, {
            code: 'name',
            title: '当前节点'
        }, {
            code: 'properties.seatInfo.contactPerson',
            title: '联系人'
        }, {
            code: 'properties.seatInfo.sex',
            title: '性别'
        }, {
            code: 'properties.seatInfo.phone',
            title: '联系手机'
        }, {
            code: 'properties.seatInfo.issueDate',
            title: '下单时间',
            formatter: function (val) {
                return Dolphin.longDate2string(val, 'yyyy-MM-dd hh:mm:ss');
            }
        }, {
            code: 'properties.seatInfo.reservedTime',
            title: '预计时间'
        }, {
            code: 'properties.seatInfo.personNum',
            title: '人数'
        }, {
            code: 'properties.seatInfo.statusName',
            title: '订单状态'
        }, {
            code: 'properties.seatInfo.acceptShare',
            title: '接受拼桌'
        }],
        requestProcess: [{
            code: 'properties.seatInfo.code',
            title: '订单号'
        }, {
            code: 'name',
            title: '当前节点'
        }, {
            code: 'properties.requestInfo.userName',
            title: '请求人'
        }, {
            code: 'properties.requestInfo.userMobile',
            title: '联系手机'
        }, {
            code: 'properties.requestInfo.issueDate',
            title: '下单时间',
            formatter: function (val) {
                return Dolphin.longDate2string(val, 'yyyy-MM-dd hh:mm:ss');
            }
        }, {
            code: 'properties.requestInfo.name',
            title: '请求类型'
        }, {
            code: 'properties.requestInfo.remark',
            title: '请求内容'
        }]
    };

    page.initPage = function () {
        var _this = this;
        var button = {
            code: 'formKey',
            title: '操作',
            width: '150px',
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
        };
        Dolphin.ajax({
            url: _this.connect.undoList.url,
            type: 'post',
            data: Dolphin.json2string(Dolphin.form.getValue('queryForm')),
            onSuccess: function (data) {
                $("#list").empty();
                if (data.rows.length > 0) {
                    prcsDef = data.rows[0].processDefinitionKey;
                    _this.column[prcsDef].push(button);
                    _this.undoList = new Dolphin.LIST({
                        panel: '#list',
                        url: _this.connect.undoList.url,
                        queryParams : Dolphin.form.getValue('queryForm'),
                        ajaxType: 'post',
                        title: '待办列表',
                        data: data,
                        columns: _this.column[data.rows[0].processDefinitionKey]
                    });
                } else {
                    $("#list").html("暂无待办数据。");
                }
            }
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
        var data = {
            prcsDef:prcsDef,
            orderCancel: 'N',
            taskOwner: REQUEST_MAP.userData.userId,
            orderId: orderId,
            orderStatus: page.orderStatus[el.id],
            complete: true
        };
        if (el.id == 'orderMake') {
            data.complete = false;
        }
        if (el.id == 'orderReject') {
            data.orderCancel = 'Y';
        }
        if(el.id=="orderAssign"){
            Dolphin.prompt('请输入分配的座位号',{
                callback : function(string){
                    if(string){
                        data.complete = false;
                        data.seatNo = string;
                        _taskComplete(data,taskId);
                    }
                }
            });
        } else {
            _taskComplete(data, taskId);
        }

    }

    function _taskComplete(data, taskId){
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