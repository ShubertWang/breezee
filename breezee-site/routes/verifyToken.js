var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {

   res.send(true);

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
