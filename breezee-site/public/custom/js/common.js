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