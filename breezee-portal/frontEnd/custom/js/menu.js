var menu = {};

menu.select = function (code) {
    var curItem = $('#pageHeader [menuCode="'+code+'"]');
    curItem.addClass('active').closest('.dropdown').addClass('active');
};