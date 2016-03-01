var request = require('request');

var route = {};

route.restaurantList = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'post',
        uri : 'http://127.0.0.1:10250/services/messhall/list',
        mockData : '/restaurant/restaurantList',
        json:{},
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function(error, response, body){
        if(error){
            throw error;
        }
        callback(body);
    });
};
route.restaurantDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/restaurant/{id}',
        mockData : '/restaurant/restaurantDetail',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        callback(body);
    });
};

module.exports = route;