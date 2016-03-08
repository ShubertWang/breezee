$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('editForm');
        if(data.email.toLowerCase().indexOf('@microsoft.com')<0){
            alert('请提供微软企业邮箱！')
            return;
        }
        $(this).hide();
        $("#msg").html("邮件发送中，请稍候...");
        Dolphin.ajax({
            url : '/data/crm/user/registerSite',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                alert("请查看邮件，并进行验证操作。");
                //location.href = REQUEST_MAP.contextPath+REQUEST_MAP.viewPrefix+"/login/logout";
            }
        });
    });
});