/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    menu.select('index');
});

var url = nginxProxy+'/file/';
$('#fileupload').fileupload({
    url: url,
    dataType: 'json',
    done: function (e, data) {
        console.log(data);
    },
    progressall: function (e, data) {
    }
});