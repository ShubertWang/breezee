/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    var opt = {
        preset: 'datetime',
        dateFormat : 'yyyy-mm-dd',
        timeFormat : 'HH:ii',
        mode: 'scroller',
        lang: 'zh',
        dateOrder : 'yymmdd', //面板中日期排列格式
        display: 'modal'
    };

    $('.demo-test').scroller(opt);

});