var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {

   var queryData = req.query;
   console.log("==========");
   console.log(queryData);
   console.log("==========");
   res.send(queryData.echostr);

   /* url = req.originalUrl;

    if(url.indexOf("?") >= 0){
        url = url.substring(0, url.indexOf("?")).replace('/verifyToken','');
    }
    endType = /mobile/.test(req.headers['user-agent'])?"mobile":"desktop";

    res.render('login', {
        path : url,
        endType : "",
        title : '走在nodeJs的路上',
        redirect : ""
    });*/
});

router.post('/', function(req, res, next) {

   var queryData = req.query;
   console.log("==========");
   console.log(queryData);
   console.log("==========");
   res.send(queryData.echostr);

   /* url = req.originalUrl;

    if(url.indexOf("?") >= 0){
    url = url.substring(0, url.indexOf("?")).replace('/verifyToken','');
    }
    endType = /mobile/.test(req.headers['user-agent'])?"mobile":"desktop";

    res.render('login', {
    path : url,
    endType : "",
    title : '走在nodeJs的路上',
    redirect : ""
    });*/
});

module.exports = router;
