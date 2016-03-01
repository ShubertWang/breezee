Dolphin.defaults.mockFlag = false;
$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('form');
        Dolphin.ajax({
            url : '/data/crm/user/shippingAddress',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '提交成功', {
                    callback : function () {
                        Dolphin.goUrl("/account/address?pickFlag=" + !!REQUEST_MAP.data.pickFlag);
                    }
                });
            }
        });
    });
});