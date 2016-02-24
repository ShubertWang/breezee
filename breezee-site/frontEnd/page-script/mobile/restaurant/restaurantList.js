Dolphin.defaults.mockFlag = true;
$(function () {
    $('.nr_list .bh.li_ct').click(function () {
        Dolphin.goUrl('/restaurant/restaurantDetail?id='+$(this).data('id'));
    });
});