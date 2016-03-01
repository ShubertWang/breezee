var request = require('request');
var extend = require('extend');
var weChatUtil = {
    open_id_url:'https://api.weixin.qq.com/sns/oauth2/access_token',
    token_url:'https://api.weixin.qq.com/cgi-bin/token',
    user_info_url:'https://api.weixin.qq.com/cgi-bin/user/info',
    message_url:'https://api.weixin.qq.com/cgi-bin/message/template/send',
    config:{
        appId:'wx5fb7696767080dbb',
        secret:'9837eb6f6b6ca07dedd08db4c4531b5a'
    }
};

/**
 * 根据code获取openid
 * @param code
 * @param callback
 */
weChatUtil.getOpenId = function(code,callback){
    var _this = this;
    request({
        method: 'get',
        uri: _this.access_token_url,
        form: extend({code:code,grant_type:'authorization_code'},_this.config)
    }, function (error, response, body) {
        if (error)
            throw error;
        console.log("weChatUtil.getOpenId:@" + body+"@");
        var retData = JSON.parse(body);
        if (retData.openid) {
            callback(retData.openid);
        } else {
            console.log("fetch openId error!");
        }
    });
};

/**
 * 获取token
 * @param callback
 */
weChatUtil.getToken = function(callback){
    var _this = this;
    request({
        method: 'get',
        uri: _this.token_url,
        form: extend({grant_type:'client_credential'},_this.config)
    }, function (error, response, body) {
        if (error)
            throw error;
        console.log("weChatUtil.getToken:@" + body+"@");
        var retData = JSON.parse(body);
        if (retData.access_token) {
            callback(retData.access_token);
        } else {
            console.log("fetch token error!");
        }
    });
};

/**
 * 获取微信用户的基本信息
 * @param openId
 * @param calback
 */
weChatUtil.getUserInfo = function (openId, calback) {
    var _this = this;
    _this.getToken(function(tokenId){
        request({
            method: 'get',
            uri: _this.user_info_url,
            form: {access_token:tokenId,openid:openId,lang:'zh_CN'}
        }, function (error, response, body) {
            if (error)
                throw error;
            console.log("weChatUtil.getUserInfo:@" + body+"@");
            var retData = JSON.parse(body);
            calback(retData);
            //req.session.userData.headimgurl = retData.headimgurl;
            //req.session.userData.nickname = retData.nickname;
        });
    });
};

/**
 * 发送微信消息
 * @param message
 */
weChatUtil.templateMessage = function(message){
    var _this = this;
    _this.getToken(function(token){
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
        request({
            method: 'post',
            uri: _this.message_url+"?access_token=" + token,
            //form: message
            body: JSON.stringify(message),
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        }, function (error, response, body) {
            if (error) {
                console.log(error);
            }
            console.log("weChatUtil.templateMessage:@" + body+"@");
        });
    });
};