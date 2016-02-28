$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('editForm', '"');
        Dolphin.ajax({
            url: '/data/crm/user/registerSite',
            type: Dolphin.requestMethod.POST,
            data: Dolphin.json2string(data),
            onSuccess: function (reData) {
                Dolphin.alert(reData.msg || '保存成功', {
                    callback: function () {
                        Dolphin.goHistory();
                    }
                })
            }
        })
    });
});