/**
 * Created by Shubert.Wang on 2016/1/19.
 */
$(function () {
    $('#resource').bind('keypress', function (e) {
        if(e.keyCode == 13){
            var html = this.value;
            $('#content').html(html);
        }
    });
    $('#resourceButton').bind('click', function (e) {
        var html = $(this).closest('.input-group-btn').prev().val();
        $('#content').html(html);
    })
});

function escape2Html(str) {
    var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
    return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
}
function goYouDaoPage(pageNumber) {
    $('#frame').attr('src', 'http://dg.youdao.com/index.php?app=group&ac=show&id=181&order=addTime&page='+pageNumber);
}