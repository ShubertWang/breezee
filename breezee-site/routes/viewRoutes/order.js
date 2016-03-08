var sha1=require('sha1');
var md5 = require('md5');
var route = {};

route.restaurantList = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10250/services/foodLine/site/' + queryData.siteId + '/' + queryData.orderType,
        mockData: '/restaurant/restaurantList'
    }, function (error, response, body) {
        body = body || [];
        callback(body, global.config.permission.employee);
    });
};

route.restaurantDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10250/services/foodLine/code/' + queryData.code,
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
        route.restaurantDetail({code: queryData.restId}, null, function (foodLine) {
            var ret = {};
            ret.data = body || [];
            ret.messhall = foodLine.messhallInfo || {};
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
        body = body || {content:[]};
        body.content = body.content || [];
        callback(body.content, global.config.permission.employee);
    });
};

route.mySeat = function(queryData, res, callback){
    global.myUtil.request({
        method: 'post',
        uri: global.config.service['sdx']+'/seatOrder/mySeat/' + queryData.userId,
        mockData: '/order/myOrder_dfd'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        body = body || {content:[]};
        body.content = body.content || [];
        callback(body.content, global.config.permission.employee);
    });
}

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
    queryData.pageNumber = queryData.pageNumber || 0;
    queryData.pageSize = queryData.pageSize || 20;
    global.myUtil.request({
        method: 'post',
        uri: 'http://127.0.0.1:10249/services/bpmTask/findUndoTasks',
        mockData: '/order/employeeOrderList',
        json:{userJob : queryData.userJob,pageNumber:queryData.pageNumber,pageSize:queryData.pageSize}
        //form: queryData
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        body = body || {content:[]};
        body.content = body.content || [];
        callback(body.content);
    });
};

route.employeeOrderByPage = function (queryData, res, callback) {
    route.employeeOrder(queryData, res, callback);
};

route.employeeOrderDetail = function (queryData, res, callback) {
    route.orderDetail(queryData,res,callback);
};

route.employeeSeat = function (queryData, res, callback) {
    route.employeeOrder(queryData,res,callback);
};

route.employeeSeatDetail = function(queryData, res, callback){
    route.seatDetail(queryData,res,callback);
}

route.seatDetail = function(queryData, res, callback){
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10250/services/seatOrder/'+queryData.id,
        mockData: '/order/otherService'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
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
    //根据site获取组织
    var body={};
    body.serviceType = [];
    body.serviceType.push({code:"birthdayParty",value:"生日会"})
    body.serviceType.push({code:"teamBuilding",value:"部门会议订餐"})
    body.serviceType.push({code:"selfBuilding",value:"自助餐"})
    body.serviceType.push({code:"coffeeBreak",value:"茶歇"})
    callback(body, global.config.permission.public);
};

route.bookSite = function(queryData, res, callback){
    //获取餐厅列表
    global.myUtil.request({
        method: 'post',
        uri: global.config.service['sdx']+'/foodLine/list',
        json:{shipping:'seat'},
        mockData: '/order/otherService'
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        body = body || [];
        callback(body, global.config.permission.employee);
    });
};

route.orderMessage = function(queryData, res, callback){
    global.myUtil.request({
        method: 'post',
        uri: global.config.service['crm']+'/user/list',
        json:{userJob : queryData.storeName}
    },function(error, response, body){
        body = body || [];
        for(var i=0;i<body.length;i++){
            global.weChatUtil.templateMessage({
                touser:body[i].code,
                template_id: "UdFYzwb7GdGH25-kx69vkbz4wOBwHuWWjocmJF34HYM",
                url:"http://weixin.sodexo-cn.com/site/view/order/employeeOrderDetail?id="+queryData.orderId+"&taskId="+queryData.taskId,
                topcolor: "#FF0000",
                data:{
                    title: {value: "有新的订单来临，请及时处理", color: "#173177"},
                    keyword1: {value: "订单号"+queryData.orderCode, color: "#173177"},
                    keyword2: {value: global.myUtil.dateFormatter(new Date(), "yyyy-MM-dd hh:mm:ss"), color: "#173177"},
                    remark: {value: "详情请点击查看。", color: "#173177"}
                }
            }, function(data){
            });
        }
        callback({});
    });
};

route.wepay = function(queryData, res, callback){
    global.myUtil.request({
        method: 'get',
        uri: 'http://127.0.0.1:10247/services/order/'+queryData.id,
        mockData: '/restaurant/restaurantList'
    }, function (error, response, body) {
        body = body || {};
        global.weChatUtil.preOrder({
            nonce_str:body.code,
            amount:body.subTotal.value,
            remoteIp:queryData.remoteIp,
            openid:queryData.userCode
        },function(ret){
            if(ret.return_code=='SUCCESS'){
                global.weChatUtil.getTicket(function(ticket){
                    var key = [],n = body.issueDate;
                    key.push("jsapi_ticket="+ticket);
                    key.push("noncestr="+body.code);
                    key.push("timestamp="+n);
                    key.push("url=http://weixin.sodexo-cn.com/view/order/wepay?id="+body.id);
                    var str = sha1(key.join("&"));
                    var paySign = [];
                    paySign.push("appId="+ret.appid);
                    paySign.push("nonceStr="+body.code);
                    paySign.push("package=prepay_id="+ret.prepay_id);
                    paySign.push("signType=MD5");
                    paySign.push("timeStamp="+n);
                    paySign.push("key="+global.weChatUtil.config.key);
                    var _paySign = md5(paySign.join("&")).toUpperCase();
                    callback({
                        appId:ret.appid,
                        timestamp:n,
                        nonceStr: body.code,
                        signature: str,
                        signType:'MD5',
                        paySign:_paySign,
                        package:"prepay_id="+ret.prepay_id,
                        orderId:queryData.id,
                        storeName:body.storeName
                    });

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