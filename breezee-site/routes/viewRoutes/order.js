var request = require('request');
var extend = require('extend');

var route = {};

route.orderFood = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/restaurant/',
        mockData : '/restaurant/restaurantDetail',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};
route.myOrder = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/order/myOrder_dfd',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        callback(body);
    });
};
route.myOrderByPage = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/order/myOrder_dfd',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        callback(body);
    });
};
route.orderDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/order/orderDetail_'+queryData.id,
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};
route.employeeOrder = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/order/myOrder_dfd',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        callback(body);
    });
};
route.employeeOrderDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/order/orderDetail_1',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};
route.orderConfirm = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/account/myAccount',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        var foodList = JSON.parse(res.req.cookies.foodList);
        callback(extend({"foodList":foodList}, body));
    });
};
route.otherService = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/order/',
        mockData : '/order/otherService',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        callback(body);
    });
};
module.exports = route;