var express = require('express');
var request = require('request');
var router = express.Router();

/* GET users listing. */
router.use('*', function(req, res, next) {
    try{
        var service, queryData, bodyData,
            uri, method;


        var parseUrl = function(url){
            var m = url.substr(6,3);
            return global.config.service[m]+url.substring(9);
        };

        service = global.config.service;

        uri = service.hostname + req.originalUrl.replace('/data', service.contextPath);
        uri = parseUrl(req.originalUrl);

        queryData = req.query;
        bodyData = req.body;

        console.log('===================== data ====================');
        console.log(queryData);
        console.log(bodyData);
        console.log('===================== data ====================');

        //这个方法有很多问题，舒毅需要改善一下。主要是因为我后台不在返回了JsonResponse的结构了
        var toJson = function(body){
            var ret = eval("("+body+")");
            var reponse = {
                "total":0,
                "success":true,
                "value":null,
                "rows":[]
            };
            if(toString.apply(ret) === '[object Array]'){
                reponse.total = ret.length;
                reponse.rows.concat(ret);
            } else {
                reponse.total = 1;
                reponse.value = ret;
            }
            return reponse;
        };

        request({
            method : req.method,
            uri : uri,
            form : bodyData
        }, function(error, response, body){
            res.send(toJson(body));
        })
    }catch (e){
        console.error(e);
        throw e;
    }
});

module.exports = router;
