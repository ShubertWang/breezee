$(function () {
    $('.__remark').keyup(function () {
        var value = $(this).val();
        if(value.length > 200){
            value = value.substr(0, 200);
            $(this).val(value);
        }
        $(this).next().children('span').html(value.length);
    }).change(function () {
        var value = $(this).val();
        if(value.length > 200){
            value = value.substr(0, 200);
            $(this).val(value);
        }
        $(this).next().children('span').html(value.length);
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

function __evaluat(thisObj, flag){
    var param, _this = $(thisObj);

    var param_panel = _this.closest(".evaluate_param");
    if(param_panel.length > 0){
        param = param_panel.data();
    }else{
        param = {
            id : REQUEST_MAP.body.id
        };
    }

    console.log(flag);
    console.log(param);
    /*ajax*/
    //onSuccess TODO 后台需对评价的次数做校验限制
    var countDOM = _this.find('.__count');
    countDOM.html(countDOM.html()-0+1);
}