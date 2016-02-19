var express = require('express');
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

    if(bodyData.username){
        req.session.username=bodyData.username;
        console.log(req.session.username);

        res.send({success : true});
    }else{
        res.send({success : false, msg : "username不能为空"});
    }
});


module.exports = router;
