var request = require('request');
var extend = require('extend');

var route = {};

route.orderFood = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        url : 'http://127.0.0.1:10246/services/category/pCode/'+queryData.restId,
        mockData : '/restaurant/restaurantDetail',
        json:{},
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function(error, response, body){
        var ret = {};
        ret.data = body || [];
        ret.messhall={};
        ret.foodTypeName =
        ret.createTime = global.myUtil.dateFormatter(new Date(), "yyyy-MM-dd hh:mm");
        ret.time = global.myUtil.dateFormatter(new Date(), "yyyy-MM-dd hh:mm");
        callback(ret);
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
        uri : 'http://127.0.0.1:10250/services/foodLine/code/'+queryData.restId,
        mockData : '/account/myAccount',
        json:{},
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function(error, response, body){
        if(error){
            throw error;
        }
        var foodList = JSON.parse(res.req.cookies.foodList);
        var data = {"foodList":foodList,"payType":{}};
        var payType = (body.payType || "").split(",");
        for(var i=0;i<payType.length;i++){
            if(payType[i]){
                data['payType'][payType[i]]=true;
            }
        }
        global.myUtil.request({
            method : 'get',
            uri : 'http://127.0.0.1:10248/services/user/shippingAddress/user/'+queryData.userId,
            mockData : '/account/myAccount',
            json:{},
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        },function(error, response, body){
            if(error){
                throw error;
            }
            data.address = body;
            callback(data);
        });
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