Dolphin.defaults.mockFlag = true;
$(function () {
    $('.restaurantLink').click(function () {
        var restId = $(this).closest('.restaurantId').data('id');
        var orderType = REQUEST_MAP.data.orderType;

        Dolphin.cookie("restId", restId, {path:'/'});
        Dolphin.cookie("orderType", orderType, {path:'/'});
        Dolphin.goUrl('/order/orderFood?restId='+restId);
    });
});