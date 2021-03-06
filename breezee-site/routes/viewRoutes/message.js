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
route.news = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'post',
        uri : 'http://127.0.0.1:10250/services/article/modelCode/'+queryData.modelCode,
        mockData : '/message/news'
    }, function(error, response, body){
        body = body || {content:[]};
        body.content = body.content || [];
        for(var i = 0; i < body.content.length; i++){
            body.content[i]._date = global.myUtil.dateFormatter(new Date(body.content[i].updateTime), "MM月dd日");
        }
        if(queryData.modelCode=='event'|| queryData.modelCode=='menu'){
            callback(body.content, global.config.permission.employee);
        } else {
            callback(body.content);
        }
    });
};
route.newsByPage = function (queryData, res, callback) {
    route.news(queryData, res, callback);
};

route.newsDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : 'http://127.0.0.1:10250/services/article/'+queryData.id,
        mockData : '/message/newsDetail'
    }, function(error, response, body){
        body = body || {};
        //发送阅读统计
        global.myUtil.request({
            method : 'put',
            uri : global.config.service['sdx']+'/visitCount/',
            json : {
                pageId:queryData.id,
                pageName:body.name,
                reader:queryData.openId,
                readerType:(queryData.userType || 1)
            },
            mockData : '/message/newsDetail'
        }, function(){});
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

route.warningMessage = function(queryData, res, callback){
    callback({});
}
module.exports = route;