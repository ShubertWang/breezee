var request = require('request');

var route = {};

route.message = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/message/message',
        mockData : '/message/message',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
route.messageLuckyMoney = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/message/messageLuckyMoney',
        mockData : '/message/messageLuckyMoney',
        form : queryData
    }, function(error, response, body){
        for(var i = 0; i < body.length; i++){
            body[i].activeDate = global.myUtil.dateFormatter(new Date(body[i].activeDate), "yyyy-MM-dd");
            body[i].expireDate = global.myUtil.dateFormatter(new Date(body[i].expireDate), "yyyy-MM-dd");
        }
        callback(body);
    });
};

module.exports = route;