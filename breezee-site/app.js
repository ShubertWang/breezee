var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var session = require('express-session');
var extend = require('extend');
var i18n = require('i18n');

var routes = require('./routes/index');
var view = require('./routes/view');
var data = require('./routes/data');
var login = require('./routes/login');
var verifyToken = require('./routes/verifyToken');

//config
global.config = require('./config/config.js');
console.log("/-------------------------系统配置信息-------------------------/");
console.log(global.config);
console.log("///////////////////////////////////////////////////////////////");

//common util
global.myUtil = require('./module/util/util.js');
global.weChatUtil = require('./module/util/wechat.js');
global.htmlRender = require('./module/util/htmlRender.js');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(session(extend(true, {}, {
    secret: '12345',
    name: 'testApp',   //这里的name值得是cookie的name，默认cookie的name是：connect.sid
    cookie: {maxAge: 1800000 },  //设置maxAge是1800000ms，即30min后session和相应的cookie失效过期
    resave: false,
    saveUninitialized: true
}, global.config.session)));

app.use(express.static(path.join(__dirname, 'public')));

//i18n
i18n.configure({
    // setup some locales - other locales default to en silently
    locales: ['en', 'zh'],
    directory: __dirname + global.config.i18nPath,
    defaultLocale : 'zh'
});
app.use(i18n.init);

app.use('/', routes);
app.use('/view', view);
app.use('/data', data);
app.use('/login', login);
app.use('/verifyToken',verifyToken);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});
module.exports = app;
