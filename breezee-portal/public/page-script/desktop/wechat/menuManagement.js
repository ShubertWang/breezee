$(function () {
    menu.select('menuManagement');

    Dolphin.ajax({
        url : '/wechat/menu',
        onSuccess : function (reData) {
            if(reData.value.menu && reData.value.menu.button){
                setValue(reData.value.menu.button)
            }
        }
    });

    $('#save').click(function () {
        var menu = getValue();
        Dolphin.ajax({
            url : '/wechat/menu',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(menu),
            onSuccess : function (reData) {
                Dolphin.alert('保存成功', {
                    callback : function () {
                        location.reload();
                    }
                })
            }
        })
    });
});

function setValue(data){
    var i, j;
    for(i = 0; i < data.length; i++){
        $('input[menu_level_1="'+i+'"][name="_name"]').val(data[i].name);
        for(j = 0; j < data[i].sub_button.length; j++){
            $('input[menu_level_1="'+i+'"][menu_level_2="'+j+'"][name="name"]').val(data[i].sub_button[j].name);
            $('select[menu_level_1="'+i+'"][menu_level_2="'+j+'"][name="type"]').val(data[i].sub_button[j].type);
            $('input[menu_level_1="'+i+'"][menu_level_2="'+j+'"][name="content"]').val(data[i].sub_button[j].url);
        }
    }
}

function getValue(){
    var data = [], _data, __data;
    for(i = 0; i < 3; i++){
        _data = {};
        _data.name=$('input[menu_level_1="'+i+'"][name="_name"]').val();
        if(_data.name){
            _data.sub_button = [];
            for(j = 0; j < 5; j++){
                __data = {};
                __data.name = $('input[menu_level_1="'+i+'"][menu_level_2="'+j+'"][name="name"]').val();
                __data.type = $('select[menu_level_1="'+i+'"][menu_level_2="'+j+'"][name="type"]').val();
                __data.url = $('input[menu_level_1="'+i+'"][menu_level_2="'+j+'"][name="content"]').val();
                if(__data.name && __data.type && __data.url){
                    _data.sub_button.push(__data);
                }
            }
            if(_data.sub_button.length > 0){
                data.push(_data);
            }
        }
    }
    return data;
}