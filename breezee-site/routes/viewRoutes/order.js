var sha1=require('sha1');
var route = {};

route.restaurantList = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10250/services/foodLine/site/' + queryData.siteId + '/' + queryData.orderType,
        mockData: '/restaurant/restaurantList'
    }, function (error, response, body) {
        body = body || [];
        callback(body,3);
    });
};

route.restaurantDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10250/services/messhall/code/' + queryData.code,
        mockData: '/restaurant/restaurantDetail'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        callback(body,'site');
    });
};

route.orderFood = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        url: 'http://127.0.0.1:10246/services/category/pCode/' + queryData.restId,
        mockData: '/restaurant/restaurantDetail'
    }, function (error, response, body) {
        route.restaurantDetail({code: queryData.restId}, null, function (messhall) {
            var ret = {};
            ret.data = body || [];
            ret.messhall = messhall;
            callback(ret,'site');
        });
    });
};

route.myOrder = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'post',
        uri: 'http://127.0.0.1:10247/services/order/myOrder/' + queryData.userId,
        mockData: '/order/myOrder_dfd'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        callback(body.content);
    });
};

route.myOrderByPage = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10247/services/order/' + queryData.id,
        mockData: '/order/myOrder_dfd'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        body.type = 'DineIn';
        callback(body);
    });
};

route.orderDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10247/services/order/' + queryData.id,
        mockData: '/order/orderDetail_' + queryData.id
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        body.type = 'DineIn';
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};

route.employeeOrder = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'post',
        uri: 'http://127.0.0.1:10249/services/bpmTask/findUndoTasks',
        mockData: '/order/myOrder_dfd',
        json:{userId:queryData.userId}
        //form: queryData
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        callback(body);
    });
};

route.employeeOrderDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: '/order/',
        mockData: '/order/orderDetail_1',
        form: queryData
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};

route.orderConfirm = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10250/services/foodLine/code/' + queryData.restId,
        mockData: '/account/myAccount'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        var foodList = JSON.parse(res.req.cookies.foodList);
        var data = {"foodList": foodList, "payType": {}};
        var payType = (body.payType || "").split(",");
        for (var i = 0; i < payType.length; i++) {
            if (payType[i]) {
                data['payType'][payType[i]] = true;
            }
        }
        global.myUtil.request({
            method: 'get',
            uri: 'http://127.0.0.1:10248/services/user/shippingAddress/user/' + queryData.userId,
            mockData: '/account/myAccount'
        }, function (error, response, body) {
            if (error) {
                throw error;
            }
            data.address = body;
            callback(data);
        });
    });
};

route.otherService = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10245/services/organization/'+queryData.siteId,
        mockData: '/order/otherService',
        form: queryData
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        if(!body){
            body = {serviceType:[]};
        }
        body.serviceType = body.serviceType || [];
        callback(body);
    });
};

route.wepay = function(queryData, res, callback){
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10247/services/order/4',
        mockData: '/restaurant/restaurantList'
    }, function (error, response, body) {
        body = body || {};
        global.weChatUtil.preOrder({
            nonce_str:body.code,
            amount:1,
            remoteIp:queryData.remoteIp,
            openid:queryData.userCode
        },function(ret){
            if(ret.return_code=='SUCCESS'){
                var key = [],n = body.issueDate;
                key.push("jsapi_ticket="+global.weChatUtil.tokenCache.tokenId);
                key.push("noncestr="+ret.nonce_str);
                key.push("timestamp="+n);
                key.push("url=http://weixin.sodexo-cn.com/wepay?id="+body.id);
                var str = sha1(key.join("&"));
                console.log("str:",key.join("&"),",signature:",str);
                callback({
                    appId:ret.appid,
                    timestamp:n,
                    nonceStr: ret.nonce_str,
                    signature: str,
                    signType:'MD5',
                    paySign:ret.sign,
                    package:ret.prepay_id
                });
            } else {
                callback({
                    code:ret.return_msg
                });
            }

        });
    });
};

module.exports = route;