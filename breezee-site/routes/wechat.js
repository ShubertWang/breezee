var express = require('express');
var request = require('request');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {

    if(req.session.openId){
        return;
    }

    var WX_AppId = "wx5fb7696767080dbb";
    var WX_Secret = "9837eb6f6b6ca07dedd08db4c4531b5a";
    var code = req.query.code;

    var queryData = req.query;
    var bodyData = req.body;

    console.log('===================== wechat ====================');
    console.log(queryData);
    console.log(bodyData);
    console.log('===================== wechat ====================');

    var customerInfo = function(openId){
        request({
            method: 'post',
            uri: global.config.service['crm']+'/user/code/'+openId,
            json: {},
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        }, function (error, response, body) {
            if(error)
                throw error;
            //判断body
            if(body && body.id > 0){
                req.session.userData = {};
                //根据openId获取其site
                req.session.userData.userType = body.type;
                 req.session.userData.siteId = body.company;
                req.session.userData.userId = body.id;
                req.session.userData.userCode = openId;
                req.session.userData.userName = body.name;
                req.session.userData.addressCount = body.addressCount;

                //req.session.dn =
            }
        });
    }

    var getUserInfo = function () {
        request({
            method: 'get',
            uri: "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + req.session.tokenId + "&openid=" + req.session.openId + "&lang=zh_CN",
            form: {}
        }, function (error, response, body) {
            if (error)
                throw error;
            console.log("^^^^" + body);
            var retData = JSON.parse(body);
            req.session.userData.headimgurl = retData.headimgurl;
            req.session.userData.nickname = retData.nickname;
            customerInfo(req.session.openId);

        });
    }

    var getOpenId = function () {
        request({
            method: 'get',
            uri: "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WX_AppId + "&secret=" + WX_Secret + "&code=" + code + "&grant_type=authorization_code",
            form: {}
        }, function (error, response, body) {
            if (error)
                throw error;
            var retData = JSON.parse(body);
            if (retData.openid) {
                req.session.openId = retData.openid;
            } else {
                console.log("fetch openid error");
            }
            console.log("+++" + body);
        });
    }

    var getToken = function () {
        request({
            method: 'get',
            uri: "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WX_AppId + "&secret=" + WX_Secret,
            form: {}
        }, function (error, response, body) {
            if (error)
                throw error;
            var retData = JSON.parse(body);
            if (retData.access_token) {
                req.session.tokenId = retData.access_token;
                //getUserInfo();
                pushMessage();
            } else {
                console.log("fetch token error!");
            }
            console.log("&&&&&&&&" + body);
        });
    };

    var pushMessage = function () {
        //var message = {
        //    touser: "opkN1t-njzuumf7t2d3Xlw8sUg2U",//openid
        //    template_id: "UdFYzwb7GdGH25-kx69vkbz4wOBwHuWWjocmJF34HYM",//
        //    url: "www.baidu.com",
        //    topcolor: "#FF0000"
        //};
        //message.data = {
        //    title: {value: "aa", color: "#173177"},
        //    keyword1: {value: "aa", color: "#173177"},
        //    keyword2: {value: "aa", color: "#173177"},
        //    remark: {value: "aa", color: "#173177"}
        //}
        //
        //request({
        //    method: 'post',
        //    uri: "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + req.session.tokenId,
        //    //form: message
        //    body: JSON.stringify(message),
        //    headers: {
        //        "Content-Type": "application/x-www-form-urlencoded"
        //    }
        //}, function (error, response, body) {
        //    if (error) {
        //        console.log(error);
        //    }
        //    console.log("############" + body);
        //    console.log(body);
        //});
    }
    getOpenId();
    getToken();
});

module.exports = router;
