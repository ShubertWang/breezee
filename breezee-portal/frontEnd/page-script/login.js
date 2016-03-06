/**
 * Created by Shubert.Wang on 2016/1/22.
 */
$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('form');
        var redirectUrl =$(this).data('redirect') || '/index';
        Dolphin.ajax({
            url : nginxProxy+'/login/',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.goUrl(redirectUrl);
            }
        });
    });
});