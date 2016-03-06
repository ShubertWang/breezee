var express = require('express');
var request = require('request');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {
    url = req.originalUrl;

    if (url.indexOf("?") >= 0) {
        url = url.substring(0, url.indexOf("?")).replace('/view', '');
    }
    endType = /mobile/.test(req.headers['user-agent']) ? "mobile" : "desktop";

    res.render('login', {
        path: url,
        endType: "",
        title: '走在nodeJs的路上',
        redirect: "",
        userData: {}
    });
});

router.post('/', function (req, res, next) {
    var bodyData = req.body,
        redirect, url;
    if (bodyData.id) {
        req.session.openId = bodyData.id;
        console.log(global.config.service['crm']+'/user/code/'+bodyData.id);
        myUtil.customerInfo(global.config.service['crm']+'/user/code/'+bodyData.id, function (data) {
            req.session.userData = data;
            if(data.userId>0)
                res.send({success : true});
            else
                res.send({success : false});
        });
    } else {
        res.send({success: false, msg: "openId不能为空"});
    }
});

module.exports = router;
