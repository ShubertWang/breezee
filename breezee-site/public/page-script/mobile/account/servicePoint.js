$(function () {
    $('#submit').click(function () {
        var data = Dolphin.form.getValue('editForm', '"');
        Dolphin.ajax({
            url: '/data/crm/user/',
            type: Dolphin.requestMethod.PUT,
            data: Dolphin.json2string(data),
            onSuccess: function (reData) {
                if(reData.value && reData.value.id){
                    alert("注册成功。");
                    location.href = REQUEST_MAP.contextPath+"/login/logout";
                }else {
                    alert("注册失败。可能是网点编号不存在。");
                }
            }
        })
    });
});