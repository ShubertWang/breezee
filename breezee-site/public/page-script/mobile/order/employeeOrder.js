$(function () {
    $('.wddd_ddlist').click(function () {
        location.href = '/view/order/employeeOrderDetail?id=1';
    });
});

function moreOrder(thisObj, pageNumber){
    $(thisObj).closest('.orderListPanel').load('/view/order/myOrderByPage?employeeFlag=true&pageNumber='+pageNumber);
}