var request = require('request');
var extend = require('extend');
var md5 = require('md5');
var js2xmlparser = require("js2xmlparser");
var xml2json = require('basic-xml2json');

var weChatUtil = {
    open_id_url:'https://api.weixin.qq.com/sns/oauth2/access_token',
    token_url:'https://api.weixin.qq.com/cgi-bin/token',
    user_info_url:'https://api.weixin.qq.com/cgi-bin/user/info',
    message_url:'https://api.weixin.qq.com/cgi-bin/message/template/send',
    preorder_url:'https://api.mch.weixin.qq.com/pay/unifiedorder',

    menu_url:'https://api.weixin.qq.com/cgi-bin/menu',
    config:{
        appid:'wx5fb7696767080dbb',
        mch_id:'1316675801',
        secret:'9837eb6f6b6ca07dedd08db4c4531b5a',
        key:'vc8fhbrmUaE9Z1EMuI48usyLeyNJ0201'
    },
    tokenTime:7000000, //jsapi_ticket的有效期为7200秒，我们在这里提前2s
    tokenCache : {
        tokenId:'tokenId',
        tokenTime:0
    }
};

/**
 * 验证token
 */
weChatUtil.validateToken = function(){
    var _this = this;
    var n = new Date().getTime();
    if(n - _this.tokenCache.tokenTime > _this.tokenTime){
        _this.getToken(function(token){
            _this.tokenCache.tokenId = token;
            _this.tokenCache.tokenTime = n;
        })
    }
}

/**
 * 根据code获取openid
 * @param code
 * @param callback
 */
weChatUtil.getOpenId = function(code,callback){
    var _this = this;
    request({
        method: 'get',
        uri: _this.open_id_url+"?appid="+_this.config.appid+"&secret="+_this.config.secret+"&code="+code+"&grant_type=authorization_code"
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
        uri: _this.token_url+"?appid="+_this.config.appid+"&secret="+_this.config.secret+"&grant_type=client_credential"
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
            uri: _this.user_info_url+"?access_token="+tokenId+"&openid="+openId+"&lang=zh_CN"
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

weChatUtil.preOrder = function(obj, callback){
    var _this = this;
    var params = {};
    params['appid'] = this.config.appid;
    params['mch_id'] = this.config.mch_id;
    params['device_info']='WEB';
    params['nonce_str'] = obj.nonce_str;
    params['body'] = 'Nobody';
    params['out_trade_no'] = obj.nonce_str;
    params['total_fee'] = obj.amount;
    params['spbill_create_ip'] = obj.remoteIp;
    params['notify_url'] = 'http://weixin.sodexo-cn.com/verifyToken';
    params['trade_type'] = 'JSAPI';
    params['openid'] = obj.openid;

    var key=[];
    key.push("appid="+params['appid']);
    key.push("body="+params['body']);
    key.push("device_info="+params['device_info']);
    key.push("mch_id="+params['mch_id']);
    key.push("nonce_str="+params['nonce_str']);
    key.push("notify_url="+params['notify_url']);
    key.push("openid="+params['openid']);
    key.push("out_trade_no="+params['out_trade_no']);
    key.push("spbill_create_ip="+params['spbill_create_ip']);
    key.push("total_fee="+params['total_fee']);
    key.push("trade_type="+params['trade_type']);
    var tmp = key.join("&")+"&key="+_this.config.key;
    var sign = md5(tmp);
    sign = sign.toUpperCase();
    params['sign'] = sign;
    var xml = js2xmlparser("xml",params);
    console.log(xml);
    request({
        method: 'post',
        uri: _this.preorder_url,
        body: xml,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }, function (error, response, body) {
        if (error) {
            throw error;
        }
        var json = xml2json.parse(body);
        var children = json.root.children,ret={};
        for(var i=0;i<children.length;i++){
            ret[children[i].name]=children[i].content;
        }
        callback(ret);
        console.log("weChatUtil.unifiedorder:@", ret, "@");
    });
};

/**
 * 获取菜单信息
 * @param token
 * @param callback
 */
weChatUtil.getMenu = function(callback){
    var _this = this;
    _this.getToken(function (token) {
        request({
            method: 'get',
            uri: _this.menu_url+"/get?access_token=" + token,
            json : {}
        }, function (error, response, body) {
            if (error)
                throw error;
            callback(body);
        });
    });
};
/**
 * 创建或修改菜单信息
 * @param token
 * @param callback
 */
weChatUtil.updateMenu = function(menuData, callback){
    var _this = this;
    _this.getToken(function (token) {
        request({
            method: 'post',
            uri: _this.menu_url+"/create?access_token=" + token,
            json : menuData
        }, function (error, response, body) {
            if (error)
                throw error;
            callback(body);
        });
    });
};





module.exports = weChatUtil;