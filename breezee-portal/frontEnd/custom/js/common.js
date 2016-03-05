$(function () {
   $('.panel.togglePanel').each(function () {
        var thisPanel = this;
        $(thisPanel).find('.panel-heading').click(function (e) {
            if ($(e.target).closest('.panel-operation').length == 0) {
                $(thisPanel).find('.panel-operation').toggle();
                $(thisPanel).find('.panel-body').slideToggle(500);
            }
        });
    });

    //提示
    $('[data-toggle="tooltip"]').tooltip();

    //通用按钮
    $('.dol-goBack').click(function () {
        history.go(-1);
    });
});

function parseCheckbox(name, checked){
    var div = $('<div class="slideThree">');
    var id = Dolphin.random(8);
    var input = $('<input type="checkbox">').attr({
        id : id,
        name : name
    }).appendTo(div);
    Dolphin.toggleCheck(input, checked);
    var label = $('<label>').attr('for', id).appendTo(div);
    return div;
}

function passwordChecker(password){
    var result = {
        success : true,
        msg : ""
    };

    if(/\d/.test(password) && /[a-zA-Z]/.test(password && password.length >= 8)){
        error('密码中必须含有字母和数字，且长度至少为8位');
    }

    return result;

    function error(msg){
        result.success = false;
        result.msg = msg;
    }
}