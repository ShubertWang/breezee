$(function () {
    $('.wddd_ddlist').click(function () {
        Dolphin.goUrl('/order/seatDetail?id='+$(this).attr("data-order-id"));
    });
});

function moreOrder(thisObj, pageNumber){
    $(thisObj).closest('.orderListPanel').load('/order/mySeatByPage?pageNumber='+pageNumber);
}