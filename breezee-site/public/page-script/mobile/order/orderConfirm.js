$(function () {
    $('#remark').keyup(function () {
        $("#remarkLength").html($(this).val().length);
    });
});