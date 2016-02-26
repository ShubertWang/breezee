/**
 * Created by Shubert.Wang on 2016/1/27.
 */
var url = window.location.hostname === '/file/';
$('#fileupload').fileupload({
    url: url,
    dataType: 'json',
    done: function (e, data) {
        $.each(data.result.files, function (index, file) {
            $('<p/>').text(file.name).appendTo('#files');
        });
    },
    progressall: function (e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .progress-bar').css(
            'width',
            progress + '%'
        );
    }
})