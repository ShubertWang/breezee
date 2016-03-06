$(function () {
    $('.wddd_ddlist').click(function () {
        Dolphin.goUrl('/order/orderDetail?id='+$(this).attr("data-order-id"));
    });
});

function moreOrder(thisObj, pageNumber){
    $(thisObj).closest('.orderListPanel').load('/order/myOrderByPage?pageNumber='+pageNumber);
}