$(function () {
    var page = {};

    page.init = function () {
        this.event();
    };

    page.event = function () {
        $('#headTab li').click(function (e) {
            var _this = $(this);
            $('#headTab .li1').removeClass('li1');
            _this.addClass('li1');

            $('.pagePanel').hide();
            $('#'+_this.data('tab')).show();
        });
    };

    page.init();
    window.page = page;
});