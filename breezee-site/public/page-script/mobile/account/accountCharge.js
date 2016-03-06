$(function () {
    $('#chargeAmount .add_mk').click(function (e) {
        $('#chargeAmount .add_xz').removeClass('add_xz');
        $(this).addClass('add_xz');
    });
    $('#submit').click(function () {
        var data = {
            amount : $('#chargeAmount .add_xz').data('amount')
        };
        alert(Dolphin.json2string(data));
        //TODO 充值如何请求
        /*Dolphin.ajax({
            url : '/data/account/bindCard',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '绑定成功', {
                    callback : function () {
                        Dolphin.goUrl('/index');
                    }
                });
            }
        });*/
    });
});