var express = require('express');
var router = express.Router();

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
            endType = /mobile/.test(req.headers['user-agent'])?"/mobile":"/desktop";
            url = endType + req.url;
            queryData = req.query;

            if(url.indexOf("?") >= 0){
                url = url.substring(0, url.indexOf("?"));
            }
            console.log(url);

            res.render(url.substring(1), {
                path : url,
                data : queryData
            });
        }
    }catch(e){
        console.error('请求' + url + '报错');
        console.error(e);
        throw e;
    }
});

module.exports = router;
