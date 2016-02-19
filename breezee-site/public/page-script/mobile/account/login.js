Dolphin.defaults.mockFlag = true;
$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('form');
        Dolphin.ajax({
            url : '/data/login/',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '登录成功', {
                    callback : function () {
                        Dolphin.goUrl('/index');
                    }
                });
            }
        });
    });
});