Dolphin.defaults.mockFlag = false;
$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('editForm');
        console.log(data);
        Dolphin.ajax({
            url : '/data/crm/user/',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '绑定成功', {
                    callback : function () {
                        Dolphin.goUrl('/index');
                    }
                });
            }
        });
    });
});