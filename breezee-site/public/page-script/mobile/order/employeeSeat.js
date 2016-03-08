$(function () {
    $('.wddd_ddlist').click(function () {
        var param = $(this).data();
        Dolphin.goUrl('/order/employeeSeatDetail?id=' + param.id + "&taskId=" + param.taskId);
    });
});

var orderStatus = {
    orderConfirm: 2,
    orderAssign: 3,
    orderComplete: 4,
    orderReject: 5
};

function moreOrder(thisObj, pageNumber) {
    $(thisObj).closest('.orderListPanel').load('/view/order/myEmployeeSeatByPage?employeeFlag=true&pageNumber=' + pageNumber);
}

function taskComplete(el, id, orderId) {
    var data = {
        prcsDef: 'seatProcess',
        orderCancel: 'N',
        taskOwner: REQUEST_MAP.userData.userId,
        orderId: orderId,
        orderStatus: orderStatus[el.id],
        complete: true
    };
    if (el.id == 'orderReject') {
        data.orderCancel = 'Y';
    }
    if (el.id == "orderAssign") {
        var string = window.prompt("请输入分配的座位号");
        if (string) {
            data.complete = false;
            data.seatNo = string;
            _taskComplete(data, id);
        }
    } else {
        _taskComplete(data, id);
    }
}

function _taskComplete(data, taskId) {
    Dolphin.ajax({
        url: '/data/bpm/bpmTask/' + taskId,
        type: Dolphin.requestMethod.POST,
        data: Dolphin.json2string(data),
        onSuccess: function (reData) {
            location.reload();
        }
    });
}