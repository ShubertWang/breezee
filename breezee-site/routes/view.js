var express = require('express');
var router = express.Router();
var viewRoutes = {};
viewRoutes.account = require('./viewRoutes/account.js');
viewRoutes.message = require('./viewRoutes/message.js');

/* GET users listing. */
router.get('*', function(req, res, next) {
    try{
        var url, endType, userInfo,
            queryData;

        userInfo = true;
//        userInfo = req.session.username;

        if(userInfo == null){
            res.render('login', {
                path : '/login',
                endType : "",
                title : '登录',
                redirect : req.url
            });
        }else{
            endType = /mobile|Mobile/.test(req.headers['user-agent'])?"/mobile":"/desktop";
            url = endType + req.url;
            queryData = req.query;

            if(url.indexOf("?") >= 0){
                url = url.substring(0, url.indexOf("?"));
            }

            var fun = viewRoutes;
            var routerPath = req.url.split('?')[0].split('/');
            for(var i = 1; i < routerPath.length; i++){
                fun = fun[routerPath[i]];
                if(fun == null){
                    break;
                }
            }
            if(fun == null){
                res.render(url.substring(1), {
                    path : url,
                    data : queryData,
                    body : {}
                });
            }else{
                fun(queryData, res, function(body){
                    res.render(url.substring(1), {
                        path : url,
                        data : queryData,
                        body : body
                    });
                })
            }
        }
    }catch(e){
        console.error('请求' + url + '报错');
        console.error(e);
        throw e;
    }
});

module.exports = router;
