var express = require('express');
var router = express.Router();
var request = require('request');

router.get('/', function(req, res, next) {
    var queryData = req.query;
    request({
        method: 'get',
        uri: global.config.service['crm']+'/user/status/'+queryData.id+"/"+queryData.status
    }, function (error, response, body) {
        res.send("<html><body><h1>验证成功。索迪斯竭诚为您服务！</h1></body>");
    });

});

module.exports = router;