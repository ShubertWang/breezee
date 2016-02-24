var request = require('request');

var myUtil = {};

myUtil.renderStar = function (point, total) {
    var html = "", j;
    for(j = 0; j < parseInt(point); j++){
        html += '<img src="/custom/wx_style/images/icon_24_1.png">';
    }
    if(point != parseInt(point)) {
        html += '<img src="/custom/wx_style/images/icon_24_3.png">';
    }
    for(j = 0; j < parseInt(total - point); j++){
        html += '<img src="/custom/wx_style/images/icon_24_2.png">';
    }

    return html;
};

module.exports = myUtil;