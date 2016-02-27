var express = require('express');
var http = require('http');
var request = require('request');
var extend = require('extend');
var router = express.Router();

/* GET users listing. */
router.use('*', function (req, res, next) {
    try {
        var service, queryData, bodyData,
            uri, method;

        service = global.config.service;
        var parseUrl = function (url) {
            var m = url.substr(6, 3);
            return global.config.service[m] + url.substring(9);
        }
        if (service.mockFlag) {
            uri = req.originalUrl.replace('/data', service.mockPath);
        } else {
            uri = service.hostname + req.originalUrl.replace('/data', service.contextPath);
            uri = parseUrl(req.originalUrl);
        }

        queryData = req.query;
        bodyData = req.body;

        console.log('=====================start data ====================');
        console.log(queryData);
        console.log(bodyData);
        console.log('=====================end data ====================');

        /**这个方法有很多问题，舒毅需要改善一下。主要是因为我后台不在返回了JsonResponse的结构了**/
        var toJson = function (body) {
            if (!body)
                body = {};
            //var ret = eval("(" + body + ")");
            var ret = body;
            var reponse = {
                "total": 0,
                "success": true,
                "value": null,
                "rows": []
            };
            if (toString.apply(ret) === '[object Array]') {
                reponse.total = ret.length;
                reponse.rows = ret;
            } else if (ret.content) {
                reponse.total = ret.total;
                reponse.rows = ret.content;
            } else {
                if(ret.id < 0){
                    reponse.success = false;
                    reponse.msg = ret.remark;
                }
                reponse.total = 1;
                reponse.value = ret;
            }
            return reponse;
        }

        request({
            method: req.method,
            uri: uri,
            json: bodyData || {},
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        }, function (error, response, body) {
            res.send(toJson(body));
        });
    } catch (e) {
        console.error(e);
        throw e;
    }
});

module.exports = router;
