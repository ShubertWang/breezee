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
        redirect : ""
    });
});

router.post('/', function(req, res, next){
    var bodyData = req.body,
        redirect, url;
    var data = {};
    data.code = bodyData.code;
    data.password = bodyData.password;
    request({
        method: 'post',
        uri: global.config.service['sym']+'/account/checkPassword',
        json: data,
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function (error, response, body) {
        if(error)
            throw error;
        //判断body
        if(body && body.id > 0){
            req.session.userCode=bodyData.code;
            req.session.userId=body.id;
            req.session.userName=body.name;
            req.session.userOrg=body.orgId;
            req.session.userRoles=body.roles;
            res.send({success : true});
        }else{
            res.send({success : false, msg : body.remark});
        }
    });

});

router.get('/logout', function(req, res, next) {
    delete req.session.userId;
    res.render('login', {
        path : '/login',
        endType : "",
        title : '登录',
        redirect : '',
        data:{}
    });
});

module.exports = router;
