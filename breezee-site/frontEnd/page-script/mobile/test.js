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
        display: 'modal'
    };

    $('.demo-test').scroller(opt);

});