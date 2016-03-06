$(function () {
    $('.wddd_ddlist').click(function () {
        var param = $(this).data();
        location.href = '/view/order/employeeOrderDetail?id='+param.id+"&taskId="+param.taskId;
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
    console.log(el.id);
    var data = {
        orderCancel:'N',
        taskOwner:REQUEST_MAP.userData.userId,
        orderId:orderId,
        orderStatus:orderStatus[el.id],
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
            location.reload();
        }
    });
}