$(function () {
    /*Dolphin.ajax({
        url : '/data/account/myAccount',
        async : false,
        onSuccess : function(reData){
            var data = reData.value;
            Dolphin.form.setValue(reData.value, '#myAccount');
        }
    })*/
});
function moreMessage(thisObj, weekNumber){
    $(thisObj).closest('.listPanel').load('/view/message/newsByPage?weekNumber='+weekNumber);
}