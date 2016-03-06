$(function () {
    $('.wddd_ddlist').click(function () {
        Dolphin.goUrl('/view/order/orderDetail?id='+$(this).attr("data-order-id"));
    });
});

function moreOrder(thisObj, pageNumber){
    $(thisObj).closest('.orderListPanel').load('/view/order/myOrderByPage?pageNumber='+pageNumber);
}