$(function () {
    $('#submit').click(function () {
        $(this).disabled();
        $("#msg").html("邮件发送中，请稍候...");
        var data = Dolphin.form.getValue('editForm');
        Dolphin.ajax({
            url : '/data/crm/user/registerSite',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                alert("请查看邮件，并进行验证操作。");
                location.href = REQUEST_MAP.contextPath+"/login/logout";
            }
        });
    });
});