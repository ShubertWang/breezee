var config = {
    service : {
        hostname: "http://127.0.0.1:18080",
        contextPath : "",
        pcm:'http://127.0.0.1:10246/services',
        sym:'http://127.0.0.1:10245/services',
        sdx:'http://127.0.0.1:10250/services',
        bpm:'http://127.0.0.1:10249/services',
        crm:'http://127.0.0.1:10248/services',
        oms:'http://127.0.0.1:10247/services'
    },
    session : {
        secret: "12345",
        name: "sID",                                      //这里的name值得是cookie的name，默认cookie的name是：connect.sid
        cookie: {"maxAge": 1800000 }                     //设置maxAge是1800000ms，即30min后session和相应的cookie失效过期
    },
    upload : {
        uploadDir : 'D:\\mywork\\workspace0\\breezee\\breezee-portal\\public\\uploadFiles\\images\\'
    },
    publicPath : "",
    nginxProxy : ""
};

module.exports = config;