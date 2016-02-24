var request = require('request');

var route = {};

route.restaurantList = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/restaurant/',
        mockData : '/restaurant/restaurantList',
        form : queryData
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