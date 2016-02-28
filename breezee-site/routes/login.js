var express = require('express');
var request = require('request');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    url = req.originalUrl;

    if(url.indexOf("?") >= 0){
        url = url.substring(0, url.indexOf("?")).replace('/view','');
    }
    endType = /mobile/.test(req.headers['user-agent'])?"mobile":"desktop";

    res.render('login', {
        path : url,
        endType : "",
        title : '走在nodeJs的路上',
        redirect : "",
        userData:{}
    });
});

router.post('/', function(req, res, next){
    var bodyData = req.body,
        redirect, url;

    console.log(global.config.service['crm']);

    var customerInfo = function(openId){
        request({
            method: 'get',
            uri: global.config.service['crm']+'/user/code/'+openId,
            json: {},
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        }, function (error, response, body) {
            req.session.userData = {};
            if(error)
                throw error;
            //判断body
            if(body && body.id > 0){
                //根据openId获取其site
                req.session.userData.userType = body.type;
                req.session.userData.siteId = body.company;
                req.session.userData.userId = body.id;
                req.session.userData.userCode = openId;
                req.session.userData.userName = body.name;
                req.session.userData.addressCount = body.addressCount;
                if(body.defaultAddress) {
                    req.session.userData.defaultAddress = body.defaultAddress;
                }
                //req.session.dn =
                res.send({success : true});
            }
        });
    };

    if(bodyData.id){
        req.session.openId=bodyData.id;
        customerInfo(bodyData.id);

    }else{
        res.send({success : false, msg : "username不能为空"});
    }
});


module.exports = router;
