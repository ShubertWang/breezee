var request = require('request');

var accountRoute = {};

accountRoute.myAccount = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/myAccount',
        mockData : '/account/myAccount',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.account = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/myAccount',
        mockData : '/account/myAccount',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.address = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/address',
        mockData : '/account/address',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.addAddress = function (queryData, res, callback) {
    if(queryData.id){
        global.myUtil.request({
            method : 'get',
            uri : '/account/address',
            mockData : '/account/addAddress',
            form : queryData
        }, function(error, response, body){
            callback(body);
        });
    }else{
        callback({});
    }

};
accountRoute.accountBalance = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/balance',
        mockData : '/account/balance',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.accountDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/detail',
        mockData : '/account/detail',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};

module.exports = accountRoute;