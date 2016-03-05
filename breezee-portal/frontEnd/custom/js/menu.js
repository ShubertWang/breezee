var menu = {};

menu.select = function (code) {
    var curItem = $('[menuCode="'+code+'"]');
    curItem.addClass('active').closest('.dropdown').addClass('active');

    if(/^workspace-/.test(code)){
        $('#pageHeader [menuCode="workspace"]').addClass('active');
    }
};