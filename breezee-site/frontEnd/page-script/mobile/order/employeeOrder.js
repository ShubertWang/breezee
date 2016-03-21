$(function () {
    $('.wddd_ddlist').click(function () {
        var param = $(this).data();
        Dolphin.goUrl('/order/employeeOrderDetail?id='+param.id+"&taskId="+param.taskId);
    });
});

var orderStatus = {
    orderConfirm : 2,
    orderReject:5,
    orderMake:3,
    orderComplete:4
};

function moreOrder(thisObj, pageNumber){
    $(thisObj).closest('.orderListPanel').load('/view/order/myEmployeeOrderByPage?employeeFlag=true&pageNumber='+pageNumber);
}

function taskComplete(el,id,orderId){
    var data = {
        prcsDef:'orderProcess',
        orderCancel:'N',
        taskOwner:REQUEST_MAP.userData.userId,
        orderId:orderId,
        orderStatus:orderStatus[el.id],
        complete:true
    };
    if(el.id=='orderMake'){
        data.complete = false;
    }
    if (el.id == 'orderReject') {
        data.orderCancel = 'Y';
        var string = window.prompt("请输入拒绝原因");
        if(string==null){
            return;
        }
        if(!string){
            alert("拒绝原因不能为空!");
            return;
        }
        data.rejectReason = string;
    }
    Dolphin.ajax({
        url:'/data/bpm/bpmTask/'+id,
        type: Dolphin.requestMethod.POST,
        data: Dolphin.json2string(data),
        onSuccess: function (reData) {
            location.reload();
        }
    });
}