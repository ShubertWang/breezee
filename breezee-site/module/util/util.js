var request = require('request');
var extend = require('extend');

var myUtil = {};

myUtil.request = function (param, callback) {
    if(global.config.mockFlag){
        try{
            var data = require("../mockService" + param.mockData + ".js");
            callback(null, null, data);
        }catch(e){
            callback(e, null, data);
        }
    }else{
        defaultParam = {
            json : {},
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        };
        var _param = extend(true, {}, defaultParam, param);
        request(_param, callback);
    }
};

myUtil.isToday = function (date) {
    var today = new Date();
    var todayTime = today.getTime() - today.getTime()%(1000*60*60*24);
    var dateTime = date.getTime();
    if(dateTime > todayTime && dateTime < (todayTime+(1000*60*60*24))){
        return true;
    }else{
        return false;
    }
};

myUtil.addLength = function(str, length){
    var newStr = str+"";
    while (newStr.length < length){
        newStr = "0"+newStr;
    }
    return newStr;
};

myUtil.dateFormatter = function (date, format) {
    var o = {
        "M+" : date.getMonth() + 1, //month
        "d+" : date.getDate(),      //day
        "h+" : date.getHours(),     //hour
        "m+" : date.getMinutes(),   //minute
        "s+" : date.getSeconds(),   //second
        "w+" : "天一二三四五六".charAt(date.getDay()),   //week
        "q+" : Math.floor((date.getMonth() + 3) / 3),  //quarter
        "S"  : date.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1,
            (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(format)){
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/**
 * 客户信息的获取
 * @param url
 * @param callback
 */
myUtil.customerInfo = function(url, callback) {
    request({
        method: 'get',
        //uri: global.config.service['crm']+'/user/code/'+openId,
        uri: url,
        json: {},
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function (error, response, body) {
        if(error)
            throw error;
        //判断body
        var userData = {};
        if(body && body.id > 0){
            userData.userType = body.type;
            userData.siteId = body.company;
            userData.userId = body.id;
            userData.userCode = body.code;
            userData.userName = body.name;
            userData.addressCount = body.addressCount;
            if(body.defaultAddress) {
                userData.defaultAddress = body.defaultAddress;
            }
        }
        callback(userData);
    });
};

module.exports = myUtil;