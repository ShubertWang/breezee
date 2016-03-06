Dolphin.defaults.mockFlag = false;
$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('editForm');
        console.log(data);
        Dolphin.ajax({
            url : '/data/crm/user/registerSite',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert('请查看邮件，并进行验证操作', function(){

                });
                window.close();
            }
        });
    });
});