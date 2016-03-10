$(function () {
    $('.wddd_ddlist').click(function () {
        Dolphin.goUrl('/order/orderDetail?id='+$(this).attr("data-order-id"));
    });
});

function moreOrder(thisObj, pageNumber){
    $(thisObj).closest('.orderListPanel').load(Dolphin.path.contextPath+Dolphin.defaults.url.viewPrefix+'/order/myOrderByPage?pageNumber='+pageNumber);
}