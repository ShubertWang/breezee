;/*!/dolphin/js/core.js*/
(function($) {
	var DOLPHIN = {};
	var thisTool = DOLPHIN;

	// 基础配置
	DOLPHIN.defaults =  {
		ajax : {
			param : {
				type : 'get',
				dataType : "json",
				data : {},
				contentType : "application/json; charset=UTF-8",
				async : true,
				cache : false,
				loading : false,
				mockPathData : null
			},
			requestHeader : {},
			formatterRequestData : null,
            originalPath : "/data",
			mockPath : "/mockData"
		},
		compare : function(a, b){
			return a == b;
		},

		array : {
			separator : ',',
			formatter : null
		},
		date : {
			dateFormat : 'yyyy-MM-dd',
			dateTimeFormat : 'yyyy-MM-dd hh:mm:ss'
		},

		modalWin : {
			title : '系统提示',
			content : null,
			footer : null,

			defaultHidden : false,

			//event
			init : null,
			hide : null,
			hidden : null
		},
		alert : {
			width : '300px',
			title : '系统提示',
			countDownFlag : true,
			countDownTime : 3
		},
		confirm : {
			width : '300px',
			title : '系统提示'
		},
		prompt : {
			width : '300px',
			title : '系统提示',
			mustFlag : false,

			type : 'text',

			placeholder : '',
			defaultValue : '',

			items : null,															//type = radio, checkbox, select,
			idField : 'code',
			textField : 'name'
		},
		img : {
			mockPath : "/public/mockImg",
			param : {
				prefixPath : "/public/mockImg",
				suffixPath : ".png",
				style : {}
			}
		},
		url : {
			viewPrefix : ""
		},
		mockFlag : false
	};
	DOLPHIN.template = {};

	// 浏览器信息
	DOLPHIN.browser = function() {
		var browser = {
			appName : 'unknown',
			version : 0,
			isMobile : false,
			msIe : false,
			firefox : false,
			opera : false,
			safari : false,
			chrome : false,
			netscape : false
		};
		var userAgent = window.navigator.userAgent;
		if (/(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test(userAgent.toLowerCase())) {
			browser[RegExp.$1] = true;
			browser.appname = RegExp.$1;
			browser.version = RegExp.$2;
		} else if (/version\D+(\d[\d.]*).*safari/.test(userAgent.toLowerCase())) { // safari
			browser.safari = true;
			browser.appName = 'safari';
			browser.version = RegExp.$2;
		}

		browser.isMobile = /mobile|Mobile/.test(userAgent);
		browser.language = navigator.language;

		return browser;
	}();

	// 常用路径
	DOLPHIN.path = (function(){
		var obj = {},key;
		for(key in location){
			if(typeof location[key] !== 'function'){
				obj[key] = location[key];
			}
		}
		var pathname = obj.pathname.split('/');
		obj.contextPath = "/" + pathname[1];

		return obj;
	})();

	//常用变量
	DOLPHIN.requestMethod = {
		GET : "get",
		POST : "post",
		PUT : "put",
		DELETE : "delete"
	};

	//数据操作
	DOLPHIN.emptyObj = function (data) {
		for(var key in data){
			delete data[key];
		}
		return data;
	};
	DOLPHIN.isPrime = function(i) {
		var ones = "";
		while(--i >= 0) ones += "1";
		return !/^1?$|^(11+?)\1+$/.test(ones);
	};
	DOLPHIN.isInt = function(num){
		return num == parseInt(num);
	};
	DOLPHIN.isNumber = function(num){
		return num == parseFloat(num);
	};
	DOLPHIN.isPositiveNumber = function(num, flag){
		return (num > 0 || (!flag && num == 0));
	};
	DOLPHIN.compareDate = function(date1, date2){
		return date1.getTime() - date2.getTime();
	};
	DOLPHIN.urlAddParam = function(url, params){
		var newUrl = url,key;
		switch (this.typeof(params)){
			case "array":
				for(var i = 0; i < params.length; i++){
					newUrl = this.urlAddParam(newUrl, params[i]);
				}
				break;
			case "object":
				for(key in params){
					newUrl += (newUrl.indexOf('?') > 0 ? "&" : "?");
					newUrl += key + "=" + params[key] ;
				}
				break;
			default :
		}
		return newUrl;
	};
	DOLPHIN.typeof = function(obj){
		if($.isArray(obj)){
			return "array";
		}else{
			return typeof obj;
		}
	};
	DOLPHIN.objInArray = function(o, a, func){
		var check = func || this.defaults.compare;

		for(var i = 0; i < a.length; i++){
			if(check(o, a[i])){
				return true;
			}
		}
		return false;
	};
	DOLPHIN.objIndexOfArray = function(o, a, func){
		var check = func || this.defaults.compare;

		for(var i = 0; i < a.length; i++){
			if(check(o, a[i])){
				return i;
			}
		}
		return -1;
	};
	DOLPHIN.random = function(length){
		var _Length = length || 6;
		var randomNumber = Math.round(Math.random() * Math.pow(10, _Length));
		var randomStr = randomNumber + "";
		if(randomStr.length < _Length){
			for(var i = 0; i < _Length - randomStr.length;){
				randomStr = "0" + randomStr;
			}
		}
		return randomStr;
	};
	DOLPHIN.dateDifference = function (date1, date2) {
		var difference = [], timeDifference, differenceText,
			differenceUnit = ['毫秒', '秒', '分钟', '小时', '天'],
			divisorArr = [1000, 60, 60, 24],
			level = 0, i;
		try{
			timeDifference = Math.abs(date1.getTime() - date2.getTime());

			if(timeDifference == 0){
				differenceText = 0;
			}else{
				differenceText = "";
				while(timeDifference > 0 && level < divisorArr.length){
					difference.push(timeDifference % divisorArr[level]);
					timeDifference = Math.floor(timeDifference / divisorArr[level]);
					level++;
				}
				if(timeDifference > 0){
					difference.push(timeDifference);
				}

				for(i = 0; i < difference.length; i++){
					differenceText = difference[i] + differenceUnit[i] + differenceText;
				}
			}
		}catch(e){
			console.log(e);
			return differenceText;
		}

		return differenceText;
	};

	//数据间转换
	//string <--> array
	DOLPHIN.splitString = function(string, param){
		var opts = $.extend({}, this.defaults.array, param);
		var data = string.split(opts.separator);
		if(typeof opts.formatter == 'function'){
			for(var i = 0; i < data.length; i++){
				data[i] = opts.formatter(data[i], data);
			}
		}

		return data;
	};
	DOLPHIN.joinArray = function(array, param){
		var opts = $.extend({}, this.defaults.array, param);
		var string = "";
		if(typeof opts.formatter == 'function'){
			for(var i = 0; i < array.length; i++){
				if(i != 0){
					string += opts.separator;
				}
				string += opts.formatter(array[i]);
			}
		}else{
			string = array.join(opts.separator);
		}
		return string;
	};
	//json <--> string
	DOLPHIN.string2json = function(str){
		var o = null;
		try{
			o = jQuery.parseJSON(str);
		}catch(e){
			console.warn(e);
			o = str;
		}
		return o;
	};
	DOLPHIN.json2string = function(json, quote){
		try{
			if(quote == "single"){
				return JSON.stringify(json).replace(/\"/g,"\\\'");
			}else if(quote == "double"){
				return JSON.stringify(json).replace(/\"/g,"\\\"");
			}else{
				return JSON.stringify(json);
			}
		}catch(e){
			console.warn(e);
			return json;
		}
	};
	//form --> json
	DOLPHIN.form2json = function(formId){
		var _form;
		if(typeof formId === 'string'){
			_form = $("#"+formId);
		}else{
			_form = formId;
		}
		var obj = {}, control,
			nameTree, namePointer,
			i, j, k;

		//select,input,textarea,checkbox,radio
		var item = _form.find('select[name], input[name][type!="checkbox"][type!="radio"], textarea[name], input[name][type="checkbox"]:checked, input[name][type="radio"]:checked');
		for(i = 0; i < item.length; i++){
			control = item.eq(i);
			if(control.closest('.table-edit').length > 0 || control.attr('type') == 'file'){
				continue;
			}
			if(control.attr('name').indexOf('.') > 0){
				nameTree = control.attr('name').split('.');
				namePointer = obj;
				for(j = 0; j < nameTree.length; j++){
					if(j != (nameTree.length -1)){
						if(!namePointer[nameTree[j]]){
							namePointer[nameTree[j]] = {};
						}
						namePointer = namePointer[nameTree[j]];
					}else{
						namePointer[nameTree[j]] = control.val();
					}
				}
			}else{
				obj[control.attr('name')] = control.val();
			}
		}

		//list
		var editList = _form.find('.table-edit');
		for(i = 0; i < editList.length; i++){
			obj[editList.eq(i).attr('tableName')] = editList.data('dolphin').data.rows;

			//TODO i18n 处理多语言问题 待优化
			var i18n_box = editList.eq(i).find('.list_body').children('tr').eq(0).find('.dolphin_i18n_box');
			for(j = 0; j < i18n_box.length; j++){
				var field_name = i18n_box.eq(j).attr('controlName');
				for(k = 0; k < obj[editList.eq(i).attr('tableName')].length; k++){
					obj[editList.eq(i).attr('tableName')][k][field_name] = translateI18n(obj[editList.eq(i).attr('tableName')][k][field_name]);
				}
			}
		}

		//TODO i18n 处理多语言问题 待优化
		_form.find('.dolphin_i18n_box').each(function (i) {
			var control = $(this);
			if(control.closest('.table-edit').length > 0 || control.attr('type') == 'file'){
				return true;
			}else{
				var name = control.attr('controlName');
				obj[name] = translateI18n(obj[name]);
			}
		});
		function translateI18n(data){
			var i18nData = "";
			for(var key in data){
				if(i18nData != ""){
					i18nData += ","
				}
				i18nData += "\"" + key + "\"" + ":" + (data[key]?"\"" + data[key] + "\"":"\"\"");
			}

			return i18nData;
		}


		return obj;
	};
	//date
	DOLPHIN.string2date = function(string, format){
		format = format || thisTool.defaults.date.dateFormat;
		var y = string.substring(format.indexOf('y'),format.lastIndexOf('y')+1);//年
		var M = string.substring(format.indexOf('M'),format.lastIndexOf('M')+1);//月
		var d = string.substring(format.indexOf('d'),format.lastIndexOf('d')+1);//日
		var h = string.substring(format.indexOf('h'),format.lastIndexOf('h')+1);//时
		var m = string.substring(format.indexOf('m'),format.lastIndexOf('m')+1);//分
		var s = string.substring(format.indexOf('s'),format.lastIndexOf('s')+1);//秒

		if(s == null ||s == "" || isNaN(s)) {s = new Date().getSeconds();}
		if(m == null ||m == "" || isNaN(m)) {m = new Date().getMinutes();}
		if(h == null ||h == "" || isNaN(h)) {h = new Date().getHours();}
		if(d == null ||d == "" || isNaN(d)) {d = new Date().getDate();}
		if(M == null ||M == "" || isNaN(M)) {M = new Date().getMonth()+1;}
		if(y == null ||y == "" || isNaN(y)) {y = new Date().getFullYear();}
		var dt = null ;
		eval ("dt = new Date('"+ y+"', '"+(M-1)+"','"+ d+"','"+ h+"','"+ m+"','"+ s +"')");
		return dt;
	};
	DOLPHIN.date2string = function(date, format){
		format = format || thisTool.defaults.date.dateFormat;
		var o = {
			"M+" : date.getMonth() + 1, //month
			"d+" : date.getDate(),      //day
			"h+" : date.getHours(),     //hour
			"m+" : date.getMinutes(),   //minute
			"s+" : date.getSeconds(),   //second
			"w+" : "天一二三四五六".charAt(date.getDay()),   //week
			"q+" : Math.floor((date.getMonth() + 3) / 3),  //quarter
			"S"  : date.getMilliseconds() //millisecond
		};
		if(/(y+)/.test(format)) {
			format = format.replace(RegExp.$1,
				(date.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for(var k in o){
			if(new RegExp("("+ k +")").test(format)){
				format = format.replace(RegExp.$1,
					RegExp.$1.length == 1 ? o[k] :
						("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
	DOLPHIN.longDate2string = function(long, format){
		if(long) {
			var date = new Date(long);
			return this.date2string(date, format);
		} else {
			return "-";
		}
	};
	DOLPHIN.jsonDate2string = function(json, format){
		var jsonObj = null;
		var jsonStr = null;
		var date = null;

		if(typeof json == "string"){
			jsonStr = json;
			jsonObj = this.string2json(json);
		}else{
			jsonStr = this.json2string(json);
			jsonObj = json;
		}
		if(jsonObj && jsonObj.time){
			date = new Date(jsonObj.time);
			return this.date2string(date, format);
		}else{
			return this.i18n.get('core_jsonDate2string_error', jsonStr);
		}
	};

	//require
	DOLPHIN.require = function (url, pageFlag) {
		var _u = Dolphin.path.contextPath + "/public" + Dolphin.systemConfig.pageScript +(Dolphin.browser.ismobile?"/mobile":"/desktop") + url + ".js";
		document.write('<script src="'+_u+'"></'+ 'script>');
	};

	//ajax
	DOLPHIN.ajax = function(param){//初始化数据
		var return_data = null, opts, key;
		var defaultFunction = {
			success : function(reData, textStatus){
				return_data = reData;
				if(reData.success){
					if(typeof param.onSuccess === 'function'){
						param.onSuccess(reData, textStatus);
					}
				}else{
					thisTool.alert(reData.msg || (this.i18n && this.i18n.get('core_ajax_error')), {
						countDownFlag:false,
						callback : function(){
							if(typeof param.onError === 'function'){
								param.onError(reData);
							}
						}
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				if(textStatus == "parsererror" && XMLHttpRequest.status == 200){
					return_data = XMLHttpRequest.responseText;
				}else if(XMLHttpRequest.status == 500){
					thisTool.alert(textStatus + "<br/>" + XMLHttpRequest.status + "<br/>" + XMLHttpRequest.responseText, {
						countDownFlag:false
					});
				}else if(XMLHttpRequest.status == 403){
					thisTool.alert(this.i18n.get('core_login_timeout') + '<br/>' + '<a href=".">'+this.i18n.get('core_reLogin')+'</a>', {
						countDownFlag:false
					});
				}else{
					thisTool.alert(textStatus + "<br/>" + XMLHttpRequest.status, {
						countDownFlag:false
					});
					return_data = textStatus;
					if(typeof param.onError === 'function'){
						param.onError(textStatus);
					}
				}
			},
			beforeSend : function(XMLHttpRequest){
				if(typeof param.onBeforeSend === 'function'){
					param.onBeforeSend.call(thisTOOL, reData);
				}
				if(param.loading){
					$('body > #loading').show();
				}

				var requestHeaderParam = $.extend({}, thisTool.defaults.ajax.requestHeader, param.requestHeader);
				for(var key in requestHeaderParam){
					XMLHttpRequest.setRequestHeader(key, requestHeaderParam[key]);
				}
			},
			complete : function (XMLHttpRequest, textStatus) {
				// this; 调用本次AJAX请求时传递的options参数
				if(typeof param.onComplete === 'function'){
					param.onComplete(XMLHttpRequest, textStatus);
				}

				if(param.loading){
					$('body > #loading').hide();
				}
			}
		};

		opts = $.extend({}, thisTool.defaults.ajax.param, defaultFunction, param);

		if(typeof thisTool.defaults.ajax.formatterRequestData === 'function'){
			opts.data = thisTool.defaults.ajax.formatterRequestData.call(thisTool, opts.data, opts);
		}

		if(typeof opts.formatterRequestData === 'function'){
			opts.data = opts.formatterRequestData.call(thisTool, opts.data, opts);
		}

		if(opts.pathData){
			for(key in opts.pathData){
				opts.url = opts.url.replace('{' + key + '}', opts.pathData[key]);
			}
		}

		if(thisTool.defaults.mockFlag && opts.url.indexOf(thisTool.defaults.ajax.originalPath+'/') >= 0){
			var mockType = "",urlArray, paramFlag = (opts.url.indexOf("?") > 0)?true:false,paramArray,i;
			opts.url = opts.url.replace(thisTool.defaults.ajax.originalPath, thisTool.defaults.ajax.mockPath);
			if(opts.mockPathData){
				urlArray = opts.url.split("/");
				if(paramFlag){
					paramArray = urlArray[urlArray.length -1].split("?");
					urlArray[urlArray.length -1] = paramArray[0];
				}
				for(i = 1; i <= opts.mockPathData.length; i++){
					urlArray[urlArray.length - i] = opts.mockPathData[opts.mockPathData.length - i];
				}
				opts.url = urlArray.join("/");
				if(paramFlag){
					opts.url += "?" + paramArray[1];
				}
			}

			mockType = "_"+opts.type;

			if(paramFlag){
				opts.url = opts.url.replace("?", mockType+".json?");
			}else{
				opts.url += mockType+".json";
			}

			opts.type = "get";
		}

		if(!opts.forceUrl){
            var contextPathRegexp;
            if(thisTool.path.contextPath == "/"){
                contextPathRegexp = new RegExp("^/");
            }else{
                contextPathRegexp = new RegExp("^" + thisTool.path.contextPath + "/");
            }
			if(!contextPathRegexp.test(opts.url) && !/^http:\/\//.test(opts.url)){
				opts.url = thisTool.path.contextPath + opts.url;
			}
		}

		$.ajax(opts);
		return return_data;
	};

	//DOM
	DOLPHIN.modalWin = function(param){
		var opts = $.extend({}, thisTool.defaults.modalWin, param);
		var modalWindow, modalDialog, modalContent, modalHeader, modalBody, modalFooter;
		modalWindow = $('<div class="modal fade">').appendTo('body');

		modalDialog = $('<div class="modal-dialog">').css({
			width : opts.width
		}).appendTo(modalWindow);
		modalContent = $('<div class="modal-content">').appendTo(modalDialog);

		modalHeader = $('<div class="modal-header">').appendTo(modalContent);
		$('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>').appendTo(modalHeader);
		$('<h4 class="modal-title">').html(opts.title).appendTo(modalHeader);

		modalBody = $('<div class="modal-body">').appendTo(modalContent);
		modalBody.append(opts.content);

		if(opts.footer){
			modalFooter = $('<div class="modal-footer">').appendTo(modalContent);
			modalFooter.append(opts.footer);
		}
		if(typeof opts.init === 'function'){
			opts.init.call(modalWindow);
		}

		if(typeof opts.show === 'function'){
			modalWindow.on('show.bs.modal', function (e) {
				opts.show.call(modalWindow);
			})
		}
		if(typeof opts.shown === 'function'){
			modalWindow.on('shown.bs.modal', function(){
				opts.shown.call(modalWindow);
			});
		}
		if(typeof opts.hide === 'function'){
			modalWindow.on('hide.bs.modal', function (e) {
				opts.hide.call(modalWindow);
			})
		}
		if(typeof opts.hidden === 'function'){
			modalWindow.on('hidden.bs.modal', function(){
				opts.hidden.call(modalWindow);
			});
		}
		if(opts.defaultHidden){
			modalWindow.modal('hide');
		}else{
			modalWindow.modal('show');
		}

		return modalWindow;
	};
	DOLPHIN.alert = function(info, param){
		var _this = this;
		var opts = $.extend({}, thisTool.defaults.alert, param), countDownSpan;

		if(this.browser.isMobile){
			alert(info);
			if(typeof opts.callback == 'function'){
				opts.callback.call(this);
			}
			return false;
		}else{
			opts.content = info;

			if(opts.countDownFlag !== false){
				opts.footer = $('<div>');
				countDownSpan = $('<span class="countDown">').appendTo(opts.footer);
				countDownSpan.html(_this.i18n.get('core_alert_countDown', opts.countDownTime));

				opts.init = function(){
					var countDownNum = opts.countDownTime,
						modalWindow = this;
					function countDown(){
						var callee = arguments.callee;
						if(countDownNum != 0){
							countDownSpan.html(_this.i18n.get('core_alert_countDown', countDownNum));
							countDownNum--;
							setTimeout(function(){
								callee();
							}, 1000);
						}else{
							modalWindow.modal('hide');
						}
					}
					countDown();
				}
			}

			if(typeof opts.callback == 'function'){
				opts.hide = function(){
					opts.callback.call(this);
				};
			}

			opts.hidden = function () {
				this.remove();
			};

			return this.modalWin(opts);
		}
	};
	DOLPHIN.confirm = function(info, param){
		var opts = $.extend({}, thisTool.defaults.confirm, param),
			flag = false, confirmButton, cancelButton, callback = opts.callback;

		opts.content = info;
		opts.footer = $('<div>');
		confirmButton = $('<button type="button" class="btn btn-primary btn-small">'+thisTool.i18n.get('core_confirm_yes')+'</button>').appendTo(opts.footer);
		cancelButton = $('<button type="button" class="btn btn-default btn-small" >'+thisTool.i18n.get('core_confirm_no')+'</button>').appendTo(opts.footer);
		opts.init = function () {
			var thisWin = this;
			confirmButton.click(function () {
				flag = true;
				thisWin.modal('hide');
			});
			cancelButton.click(function () {
				thisWin.modal('hide');
			});
		};

		if(typeof callback == 'function'){
			opts.hide = function(){
				callback.call(this, flag);
			};
		}
		opts.hidden = function () {
			this.remove();
		};

		return this.modalWin(opts);
	};
	/*
	 param:{width:'300px', title:'系统提示', callback:function(){}, type:'input', mustFlag:false
	 placeholder:'', defaultValue:'',
	 items:[{code:'', name:''},{code:'', name:''}]																//radio, checkbox, select
	 }
	 */
	DOLPHIN.prompt = function(info, param){
		var opts = $.extend({}, thisTool.defaults.prompt, param),
			string = null, confirmButton, cancelButton, inputPanel, input, callback = opts.callback;

		opts.content = $('<div>');
		$('<div>').append(info).appendTo(opts.content);
		inputPanel = $('<div>').appendTo(opts.content);
		switch(opts.type){
			default :
				input = $('<input type="text" class="form-control" placeholder="'+opts.placeholder+'" value="'+opts.defaultValue+'">');
		}
		input.appendTo(inputPanel);
		function getResultData(){
			switch(opts.type){
				default :
					string = input.val();
			}
		}

		opts.footer = $('<div>');
		confirmButton = $('<button type="button" class="btn btn-primary btn-small">确定</button>').appendTo(opts.footer);
		cancelButton = $('<button type="button" class="btn btn-default btn-small" >取消</button>').appendTo(opts.footer);

		opts.init = function () {
			var thisWin = this;
			input.focus();
			confirmButton.click(function () {
				getResultData();
				thisWin.modal('hide');
			});
			cancelButton.click(function () {
				thisWin.modal('hide');
			});
		};
		if(typeof callback == 'function'){
			opts.hide = function () {
				callback.call(this, string);
			}
		}
		opts.hidden = function () {
			this.remove();
		};

		return this.modalWin(opts);
	};
	DOLPHIN.toggleCheck = function(selecter, flag){
		selecter.each(function(){
			if(typeof flag === 'boolean'){
				this.checked = flag;
			}else{
				this.checked = !this.checked;
			}
			$(this).change();
		});
	};
	DOLPHIN.toggleEnable = function(selecter, flag){
		var _flag;
		selecter.each(function(){
			_flag = flag==null?!!$(this).attr('disabled'):flag;
			if(_flag){
				$(this).removeAttr('disabled');
			}else{
				$(this).attr('disabled', 'disabled');
			}
		});
	};

	//location
	DOLPHIN.goHistory = function(){
		history.go(-1);
	};
	DOLPHIN.goUrl = function(url){
		if(url.indexOf("http://") != 0){
			url = this.path.contextPath + this.defaults.url.viewPrefix + url;
		}
		location.href = url;
	};

	//console
	DOLPHIN.console = null;

	//cookie
	/**
	 * Cookie plugin
	 *
	 * Copyright (c) 2006 Klaus Hartl (stilbuero.de)
	 * Dual licensed under the MIT and GPL licenses:
	 * http://www.opensource.org/licenses/mit-license.php
	 * http://www.gnu.org/licenses/gpl.html
	 *
	 */

	/**
	 * Create a cookie with the given name and value and other optional parameters.
	 *
	 * @example $.cookie('the_cookie', 'the_value');
	 * @desc Set the value of a cookie.
	 * @example $.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain: 'jquery.com', secure: true });
	 * @desc Create a cookie with all available options.
	 * @example $.cookie('the_cookie', 'the_value');
	 * @desc Create a session cookie.
	 * @example $.cookie('the_cookie', null);
	 * @desc Delete a cookie by passing null as value. Keep in mind that you have to use the same path and domain
	 *       used when the cookie was set.
	 *
	 * @param String name The name of the cookie.
	 * @param String value The value of the cookie.
	 * @param Object options An object literal containing key/value pairs to provide optional cookie attributes.
	 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object.
	 *                             If a negative value is specified (e.g. a date in the past), the cookie will be deleted.
	 *                             If set to null or omitted, the cookie will be a session cookie and will not be retained
	 *                             when the the browser exits.
	 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
	 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
	 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will
	 *                        require a secure protocol (like HTTPS).
	 * @type undefined
	 *
	 * @name $.cookie
	 * @cat Plugins/Cookie
	 * @author Klaus Hartl/klaus.hartl@stilbuero.de
	 */

	/**
	 * Get the value of a cookie with the given name.
	 *
	 * @example $.cookie('the_cookie');
	 * @desc Get the value of a cookie.
	 *
	 * @param String name The name of the cookie.
	 * @return The value of the cookie.
	 * @type String
	 *
	 * @name $.cookie
	 * @cat Plugins/Cookie
	 * @author Klaus Hartl/klaus.hartl@stilbuero.de
	 */
	DOLPHIN.cookie = function(name, value, options) {
		if (typeof value != 'undefined') { // name and value given, set cookie
			options = options || {};
			if (value === null) {
				value = '';
				options.expires = -1;
			}
			var expires = '';
			if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
				var date;
				if (typeof options.expires == 'number') {
					date = new Date();
					date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
				} else {
					date = options.expires;
				}
				expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
			}
			// CAUTION: Needed to parenthesize options.path and options.domain
			// in the following expressions, otherwise they evaluate to undefined
			// in the packed version for some reason...
			var path = options.path ? '; path=' + (options.path) : '';
			var domain = options.domain ? '; domain=' + (options.domain) : '';
			var secure = options.secure ? '; secure' : '';
			document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
		} else { // only name given, get cookie
			var cookieValue = null;
			if (document.cookie && document.cookie != '') {
				var cookies = document.cookie.split(';');
				for (var i = 0; i < cookies.length; i++) {
					var cookie = jQuery.trim(cookies[i]);
					// Does this cookie string begin with the name we want?
					if (cookie.substring(0, name.length + 1) == (name + '=')) {
						cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
						break;
					}
				}
			}
			return cookieValue;
		}
	};

	DOLPHIN.createImg = function (param) {
		var img = $('<img>'), src, opts = $.extend({}, this.defaults.img.param, param);
		if(this.defaults.mockFlag){
			src = this.path.contextPath + this.defaults.img.mockPath + (opts.url || (opts.id?("/" + opts.id):false) || "/null") + opts.suffixPath;
		}else{
			src = this.path.contextPath + opts.prefixPath + (opts.url || (opts.id?("/" + opts.id):false) || "/null.png");
		}
		img.attr("src", src);

		img.addClass(opts.css);
		img.css(opts.style);
		return img;
	};

	DOLPHIN.initLoadingPanel = function (panel) {
		var loading, progressPanel, progress, bar;
		loading = $('<div class="loading" id="loading">').appendTo(panel);
		progressPanel = $('<div class="progressPanel">').appendTo(loading);
		progress = $('<div class="progress">').appendTo(progressPanel);
		bar = $('<div class="progress-bar progress-bar-striped active"></div>').css('width', '100%').appendTo(progress);
		return loading;
	};
	$(function () {
		DOLPHIN.initLoadingPanel('body');
	});

	window.Dolphin = DOLPHIN;
	window.TOOL = DOLPHIN;
	$.Dolphin = DOLPHIN;
})(jQuery);
;/*!/dolphin/js/validate.js*/
(function($) {
	var thisTool = Dolphin;

	var validate = function(selector, param){
		var thisValidate = arguments.callee;
		var flag = true, thisFlag;
		selector.each(function(){
			var _this = $(this), method, i;
			method = param || _this.attr(thisValidate.defaults.attr);

			switch (thisTool.typeof(method)){
				case "array":
					for(i = 0; i < method.length; i++){
						if(!thisValidate(_this, method[i])){
							flag = false;
							break;
						}
					}
					break;
				case "string":
					var methodArray = method.split(","), funcName, funcArguments;
					for(i = 0; i < methodArray.length; i++){
						funcArguments = methodArray[i].match(/\[\S*\]/g);
						if(funcArguments){
							funcArguments = thisTool.string2json(funcArguments[0]);
						}else{
							funcArguments = [];
						}
						funcArguments.unshift(_this);
						funcName = $.trim(methodArray[i].replace(/\[\S*\]/g, ""));
						if(!thisValidate.check.call(thisValidate, _this, thisValidate.method[funcName], funcArguments)){
							flag = false;
							break;
						}
					}
					break;
				case "function":
					if(!method.call(thisValidate, _this)){
						flag = false;
					}
					break;
				case "object":
					if(!thisValidate.check.call(thisValidate, _this, method, [_this])){
						flag = false;
					}
					break;
			}
		});

		return flag;
	};

	validate.defaults = {
		attr : "dol-validate"
	};

	validate.check = function(selector, checkMethod, params){
		var content = null, label, i, flag;
		if(checkMethod.validator.apply(this, params)){
			this.hide(selector);
			flag = true;
		}else{
			label = selector.attr('dol-label') || selector.closest('.form-group').find('label').html() || '';
			switch (typeof checkMethod.message){
				case "function":
					content = checkMethod.message.apply(this, [label].concat(params));
					break;
				case "string":
					content = checkMethod.message.replace("{label}", label);
					for(i = 1; i < params.length; i++){
						content = content.replace("{"+i+"}", params[i]);
					}
					break;
			}
			this.show(selector, content);
			flag = false;
		}

		return flag;
	};
	validate.show = function(_this, content){
		var group = _this.closest('.form-group');
		if(group.length == 0){
			group = _this.closest('.input-group');
		}
		group.addClass('has-error');
		_this.popover('destroy');
		setTimeout(function(){
			_this.popover({
				content : content,
				trigger : 'hover',
				placement : 'top'
			});
		}, 200);
	};

	validate.hide = function(_this){
		var group = _this.closest('.form-group');
		if(group.length == 0){
			group = _this.closest('.input-group');
		}
		group.removeClass('has-error');
		_this.popover('destroy');
	};

	validate.monitor = function(selector, param){
		var thisValidate = this, _selector = selector || $('['+thisValidate.defaults.attr+']');
		_selector.bind('blur', function(){
			thisValidate($(this), param);
		}).bind('keyup', function(){
			thisValidate($(this), param);
		});
	};

	validate.method = {};
	validate.method.required = {
		validator : function(selector){
			if(selector.val()){
				return true;
			}else{
				return false;
			}
		},
		message : "{label}不能为空"
	};

	validate.method.later = {
		validator : function(selector, otherDateId){
			var otherDate = thisTool.string2date($(otherDateId).val());
			var thisDate = thisTool.string2date(selector.val());
			if(thisTool.compareDate(thisDate, otherDate) < 0){
				return false;
			}else{
				return true;
			}
		},
		message : "结束时间不能早于开始时间"
	};
	validate.method.before = {
		validator : function(selector, otherDateId){
			var otherDate = thisTool.string2date($(otherDateId).val());
			var thisDate = thisTool.string2date(selector.val());
			if(thisTool.compareDate(thisDate, otherDate) > 0){
				return false;
			}else{
				return true;
			}
		},
		message : "开始时间不能晚于结束时间"
	};

	validate.method.maxLength = {
		validator : function(selector, maxLength){
			if(selector.val() && selector.val().length > maxLength){
				return false;
			}else{
				return true;
			}
		},
		message : "{label}长度不能超过{1}"
	};
	validate.method.minLength = {
		validator : function(selector, minLength){
			if(selector.val() && selector.val().length < minLength){
				return false;
			}else{
				return true;
			}
		},
		message : function(label, selector, length){
			return label + "长度不能低于" + length;
		}
	};
    validate.method.email = {
        validator : function(selector, minLength){
            if(selector.val() && !/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(selector.val())){
                return false;
            }else{
                return true;
            }
        },
        message : function(label, selector){
            return label + "格式不正确";
        }
    };
	validate.method.number = {
		validator : function(selector, minLength){
			if(selector.val() && !/^(0|[1-9][0-9]*)$/.test(selector.val())){
				return false;
			}else{
				return true;
			}
		},
		message : function(label, selector){
			return label + "必须为数字";
		}
	};

	thisTool.validate = validate;
})(jQuery);
;/*!/dolphin/js/form.js*/
(function($){
	var thisTool = Dolphin;
	function FORM(param){
		this.init(param);
	}
	FORM.defaults = {
		panel : 'body',
		ajax : thisTool.ajax,
		formatter : null,

		select : {
			emptyOption : true,
            codeField : 'code',
            nameField : 'name',
			optionUrl : null,
			optionParam : null
		}
	};


	FORM.prototype = {
		/* ==================== property ================= */
		constructor : FORM,
		data : null,

		/* ===================== method ================== */
		init : function(param){
			this.opts = $.extend({}, FORM.defaults, param);
		},
		parse : function(panel, param){
			var _panel = panel || this.opts.panel;
			//select
			this.parseSelect($(_panel).find('select[options]'));

			//date
			$(_panel).find('.input-group.dolphin_date_picker').datepicker({
				format: "yyyy-mm-dd",
				language: navigator.language,
				autoclose: true,
				orientation: "bottom left"
			});

			//datetime
			$(_panel).find('.input-group.dolphin_datetime_picker').datetimepicker({
				format: "yyyy-mm-dd hh:ii",
				autoclose: true,
				pickerPosition: "bottom-left"
			});

			//i18n
			$(_panel).find('.dolphin_i18n_box').each(function () {
				$(this).i18nBox();
			});

			//ref-tree
			$(_panel).find('.form-control-ref').each(function(){
				var thisControl = $(this);
				var url = thisTool.path.contextPath + thisControl.attr('data-ref-url');
				var idField = thisControl.attr('idField');
				var nameField = thisControl.attr('nameField');

				var refTree = new REFWIN({
					type : thisControl.attr('data-ref-type'),
					url : url,
					mockPathData : thisControl.attr('mockPathData').split(","),

					idField : idField || 'code',
					textField : nameField || 'name',

					multiple : thisControl.attr('data-ref-multiple') === "true"?true:false,
					checkbox : thisControl.attr('data-ref-checkbox') === "false"?false:true,
					cascadeCheck : thisControl.attr('data-ref-cascadeCheck') === "true"?true:false,
					onlyLeafCheck : thisControl.attr('data-ref-onlyLeafCheck') === "true"?true:false,
					onShow : function(){
						var selected = thisControl.find('.form-control-hidden').val().split(',');

						for(var i = 0; i < selected.length; i++){
							this.refObj.check(this.refObj.findById(selected[i]));
						}
					},
					onSubmit : function(data){
						var selectNode = '';
						var selectId = '';
						for(var i = 0; i < data.length; i++){
							if(i != 0){
								selectNode += ', ';
								selectId += ', ';
							}
							selectNode += data[i][this.opts.textField];
							selectId += data[i][this.opts.idField];
						}
						thisControl.find('.form-control').val(selectNode);
						thisControl.find('.form-control-hidden').val(selectId);
					}
				});
				thisControl.find('.input-group-addon').bind('click', function(){
					//console.log(tree.getChecked());
					refTree.show();
				});
			});

			//file
			$(_panel).find('.dolphin_file_box').each(function(){
				$(this).fileBox();
			});

			//validate
			thisTool.validate.monitor($(this.opts.panel).find('['+thisTool.validate.defaults.attr+']'));

			return this;
		},
		empty : function(panel, param){
			var thisPanel = panel || this.opts.panel;
			var opts = param || this.opts;
			var control;

			$(thisPanel).find('[name]').each(function () {
				control = $(this);
				if(control[0].tagName.toLowerCase() == 'input'){
					if(control.attr('type') == 'radio' || control.attr('type') == 'checkbox'){
						control[0].checked = false;
					}else{
						control.val("");
					}
				}else if(control[0].tagName.toLowerCase() == 'select' || control[0].tagName.toLowerCase() == 'textarea'){
					control.val("");
				}else if(control[0].tagName.toLowerCase() == 'p' || control[0].tagName.toLowerCase() == 'span' || control[0].tagName.toLowerCase() == 'div'){
					control.html("");
				}
			});
			$(thisPanel).find('div.dolphin_i18n_box').each(function () {
				$(this).find('[__i18n_flag]').removeAttr('__i18n_flag');
				$(this).find('._lang_items').hide();
			});
		},
		//form --> json
		getValue : function(formId){
			var _form;
			if(typeof formId === 'string'){
				if($(formId).length > 0){
					_form = $(formId);
				}else{
					_form = $("#"+formId);
				}
			}else{
				_form = formId;
			}
			var obj = {}, control,
				nameTree, namePointer,
				i, j, k;

			//select,input,textarea,checkbox,radio
			var item = _form.find('select[name], input[name][type!="checkbox"][type!="radio"], textarea[name], input[name][type="checkbox"]:checked, input[name][type="radio"]:checked');
			for(i = 0; i < item.length; i++){
				control = item.eq(i);
				if(control.closest('.table-edit').length > 0 || control.attr('type') == 'file'){
					continue;
				}
				if(control.attr('name').indexOf('.') > 0){
					nameTree = control.attr('name').split('.');
					namePointer = obj;
					for(j = 0; j < nameTree.length; j++){
						if(j != (nameTree.length -1)){
							if(!namePointer[nameTree[j]]){
								namePointer[nameTree[j]] = {};
							}
							namePointer = namePointer[nameTree[j]];
						}else{
							namePointer[nameTree[j]] = control.val();
						}
					}
				}else{
					obj[control.attr('name')] = control.val();
				}
			}

			//list
			var editList = _form.find('.table-edit');
			for(i = 0; i < editList.length; i++){
				obj[editList.eq(i).attr('tableName')] = editList.data('dolphin').data.rows;

				//TODO i18n 处理多语言问题 待优化
				var i18n_box = editList.eq(i).find('.list_body').children('tr').eq(0).find('.dolphin_i18n_box');
				for(j = 0; j < i18n_box.length; j++){
					var field_name = i18n_box.eq(j).attr('controlName');
					for(k = 0; k < obj[editList.eq(i).attr('tableName')].length; k++){
						obj[editList.eq(i).attr('tableName')][k][field_name] = translateI18n(obj[editList.eq(i).attr('tableName')][k][field_name]);
					}
				}
			}

			//TODO i18n 处理多语言问题 待优化
			_form.find('.dolphin_i18n_box').each(function (i) {
				var control = $(this);
				if(control.closest('.table-edit').length > 0 || control.attr('type') == 'file'){
					return true;
				}else{
					var name = control.attr('controlName');
					obj[name] = translateI18n(obj[name]);
				}
			});
			function translateI18n(data){
				var i18nData = "";
				for(var key in data){
					if(i18nData != ""){
						i18nData += ","
					}
					i18nData += "\"" + key + "\"" + ":" + (data[key]?"\"" + data[key] + "\"":"\"\"");
				}

				return i18nData;
			}


			return obj;
		},
		setValue : function(data, panel, param){
			var thisPanel = $(panel || this.opts.panel);
			var opts = param || this.opts,
				key, _key, value, control;

			//TODO i18n
			if(data.lang){
				for(key in data.lang){
					data[key + "_i18n_"] = {};
					data[key + "_i18n_"]['code'] = data.lang[key];
					data[key + "_i18n_"][Dolphin.I18N_BOX.defaults.defaultLang] = data[key];
				}
			}

			for(key in data){
				if(typeof data[key] != 'object'){
					control = thisPanel.find('[name="'+key+'"]');
					this.setControlValue(control, data[key]);
				}else{
					for(_key in data[key]){
						control = thisPanel.find('[name="'+key+'.'+_key+'"]');
						this.setControlValue(control, data[key][_key]);
					}
				}
			}

			//TODO file
			thisPanel.find('.dolphin_file_box').each(function(){
				$(this).data('dolphin').resetFiles();
			});

			return this;
		},
		setControlValue : function (control, value, param) {
			var opts = param || this.opts;
			if(control.length > 0){
				if(control[0].tagName.toLowerCase() == 'input'){
					if(control.attr('type') == 'radio' || control.attr('type') == 'checkbox'){
						if(control.length > 1){
							for(var i = 0; i < control.length; i++){
								if(control.eq(i).val() == value){
									control[i].checked = true;
								}
							}
						}else{
							if(value === true || value === "true" || value === "1"){
								control[0].checked = true;
							}
						}
					}else{
						control.val(value);
					}
				}else if(control[0].tagName.toLowerCase() == 'select'){
					control.val(value + "");
					control.attr('selectedOption', value + "");
				}else if(control[0].tagName.toLowerCase() == 'textarea'){
					control.val(value + "");
				}else if(control[0].tagName.toLowerCase() == 'p' || control[0].tagName.toLowerCase() == 'span' || control[0].tagName.toLowerCase() == 'div'){
					if(control.attr('options')){
						control.html(thisTool.enum.getEnumText(control.attr('options'),value));
					}else{
						if(opts.formatter && typeof opts.formatter[key] === 'function'){
							control.html(opts.formatter[key].call(this, value, data));
						}else{
							control.html(value);
						}
					}
				}
			}
		},
		loadData : function(param, panel, funcParam){
			var thisForm = this;
			param.onSuccess = function(data){
				if(typeof funcParam.dataFilter == "function"){
					data = funcParam.dataFilter.call(thisForm, data);
				}

				thisForm.setValue(data.value, panel, param);

				if(typeof funcParam.callback == "function"){
					funcParam.callback.call(thisForm, data);
				}
			};
			thisTool.ajax(param);
		},
		validate : function(panel){
			var _panel = panel || this.opts.panel;

			return thisTool.validate($(_panel).find('['+thisTool.validate.defaults.attr+']'));
		},

		/*
		 功能：通过json创建表单
		 参数说明：
		 param : {attr: [name:"", title:"", controlType:"", placeholder:""], colNum : 1, labelCol : 3}
		 */
		renderForm : function(param, panel){
			var thisPanel = panel || this.opts.panel;

			var form = "",attrObj = null;
			var row = $('<div class="row">').appendTo($(thisPanel)), col;

			for(var i = 0; i < param.attr.length; i++){
				attrObj = this.renderControl(param.attr[i]);
				if(attrObj){
					col = $('<div>').addClass('col-md-'+12/(param.colNum || 1)).appendTo(row);
					col.append(attrObj);
				}
			}
		},
		renderControl : function(param){
			var control = null;

			switch(param.controlType){
				case 'text':
					control = this.renderText(param);
					break;
				default :
					control = this.renderStatic(param);
				//console.log(param.name + '控件未找到');
			}

			return control;
		},
		renderText : function(param){
			var control = $('<div>').addClass('form-group');
			var label = $('<label>').addClass('col-sm-'+(param.labelCol || 3)+' control-label').html(param.title + '：').appendTo(control);
			var input = $('<div>').addClass('col-sm-'+(12-(param.labelCol || 3))).appendTo(control);
			$('<input type="text" class="form-control"/>').val(param.defautValue || "").attr({
				'name' : param.code,
				'placeholder' : param.placeholder || ''
			}).appendTo(input);

			return control;
		},
		renderStatic : function(param){
			var control = $('<div>').addClass('form-group');
			var label = $('<label>').addClass('control-label').html(param.title + '：').appendTo(control);
			var input = $('<div>').addClass('control-label control-value').appendTo(control);
			input.attr('name',param.code).html(param.defautValue || " ");

			return control;
		},
		submitForm : function(param){
			var result = thisTool.ajax({
				url: param.url,
				data: param.data || {},
				type: param.type
			});
			if(result.success){
				thisTool.alert(result.msg || "操作成功");
				if(param.callback){
					param.callback();
				}
			}else{
				thisTool.alert(result.msg);
			}
		},
		parseSelect : function(selectors, param){
			var thisForm = this;
			selectors.each(
				function() {
					var thisSelect = this,opts = $.extend({}, thisForm.opts.select, param);
					var options = null,
						optionUrl = $(this).attr('optionUrl') || opts.optionUrl,
						optionParam=$(this).attr('optionParam') || opts.optionParam,
						codeField = $(this).attr('codeField') || opts.codeField,
						nameField = $(this).attr('nameField') || opts.nameField,
						nameFormatter = $(this).attr('nameFormatter') || opts.nameFormatter,
						emptyOption = ($(this).attr('emptyOption') === false || $(this).attr('emptyOption') === "false")?false:opts.emptyOption,
						selectedOption = $(this).attr('selectedOption') || opts.selectedOption,
						mockPathData = $(this).attr('mockPathData')?$(this).attr('mockPathData').split(","):opts.mockPathData,
						dataFilter = $(this).attr('dataFilter') || opts.dataFilter,
						optionName;

					if(optionUrl){
						if(optionParam){
							//urgent, so just like this
							optionUrl = optionUrl+"?"+optionParam;
						}
						options = thisTool.ajax({url : optionUrl, async : false, mockPathData: mockPathData});
						if(dataFilter){
							switch(typeof dataFilter){
								case "string" :
									options = window[dataFilter].call(thisSelect, options);
									break;
								case "function":
									options = dataFilter.call(thisSelect, options);
									break;
								default:
									break;
							}
						}else{
							options = options.rows;
						}
					}else{
						options = thisTool.enum.getEnum($(this).attr('options'));
					}
					if(options){
						if(emptyOption){
							$(this).append(
								'<option value="">'
								+ '--请选择--' + '</option>');
						}
						for (var i = 0; i < options.length; i++) {
							switch(typeof nameFormatter){
								case "string" :
									optionName = window[nameFormatter].call(thisSelect, options[i][nameField]);
									break;
								case "function":
									optionName = nameFormatter.call(thisSelect, options[i][nameField]);
									break;
								default:
									optionName = options[i][nameField];
									break;
							}
							$(this).append(
								'<option value="' + options[i][codeField] + '">'
								+ optionName + '</option>');
						}
						if(selectedOption){
							$(this).val(selectedOption);
						}
					}
					if($(this).attr('callback')){
						window[$(this).attr('callback')].call(this,$(this).val(), options);
						if($(this).attr('noChange')){

						}else{
							$(this).bind('change', function(){
								window[$(thisSelect).attr('callback')].call(this,$(thisSelect).val(), options);
							})
						}
					}
				}
			);
		},
		setOptions : function(param){
			$.extend(true, this.opts, param);
			return this;
		}
	};

	thisTool.FORM = FORM;
	thisTool.form = new FORM();
})(jQuery);
;/*!/dolphin/js/enum.js*/
/**
 * jquery Main Data
 * Description: 目前主要用于处理json格式的enum
 * Author: wangsy
 * Date  : 2015-04-15
 * Update: 2015-04-15
 *===============================================================================
 * 一、功能说明：
 * 1. 管理前台枚举值的数据
 *
 * 二、使用参考：
 * 1. 依赖 jQuery
 * 2. 引入插件js: main-data.js
 * 3. 初始化插件
 *        var main_data = new MAINDATA({
 * 			ajaxFlag : true,
 * 			enumUrl : '/view/demo/maindata/mockData.jsp'
 * 		});
 * 4. 方法参考：
 *    添加enum : main_data.addEnum('test', [{value : 'v1', text : 't1'}]);
 *    查询enum : main_data.getEnum('test');
 *    查询text : main_data.getEnumText('test', 'v1');
 *
 *===============================================================================
 *
 ********************************************************************************/
(function ($) {
    var thisTool = Dolphin;
    function ENUM(param) {
        this.init(param);
    }
    ENUM.defaults = {
        //enum
        valueField: "code",									  //枚举code label
              textField: "name",									  //枚举值 label
        otherField: "other",								   	  //枚举附加属性 label

        //cookie
        cookieFlag : false,                                     //是否支持cookie

        //ajax
        ajaxFlag: false,							   		       //是否支持远程
        enumUrl: null,						     				   //远程url
        enumTextUrl: null,									   //远程取值url
        async: false,					 				           //是否默认异步
        type: "get",					                 		   //默认请求方法
        dataType: "json",									       //默认数据类型
        //contentType: "application/json; charset=UTF-8",	   //默认contentType
        cache: false,									           //默认ajax是否缓存
        enumKey: "id",								               //默认提交参数enumId名称
        enumNameKey: "enumOptionId",						   //默认提交参数enumOptionId名称
        ajax: thisTool.ajax,							           //默认ajax方法
        enumCache: true,										   //ajax请求结果是否缓存到前台
        dataFilter : null                                       //数据处理
    };

    ENUM.prototype = {
        /* ==================== property ================= */
        constructor: ENUM,
        enumData: {},															//前台缓存枚举数据
        enumType: {															//前台创建枚举类型
            lowerCase: "abcdefghijklmnopqrstuvwxyz",
            upperCase: "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        },

        /* ===================== method ================== */
        init: function (param) {
            this.opts = $.extend({}, ENUM.defaults, param);

            if (this.opts.ajaxFlag) {
                if (this.opts.enumTextUrl == null) {
                    this.opts.enumTextUrl = this.opts.enumUrl;
                }
            }
        },
        addEnumType: function (name, data) {
            this.enumType[name] = data;
        },

        //add enum
        addEnum: function (name, data) {
            this.enumData[name] = data;
        },
        createEnum: function (name, data, type, start) {
            //init param
            if (typeof data == 'string') {
                data = data.split(',');
            }
            type = type || 'number';
            start = start || 0;

            //init start index
            var indexValue = start;
            if (type != 'number') {
                indexValue = this.enumType[type].indexOf(start);
                if (indexValue < 0) {
                    indexValue = 0;
                }
            }

            //enumData
            var enumData = [];
            var value = null;
            var enumOption = null;
            for (var i = 0; i < data.length; i++) {
                if (type == 'number') {
                    value = indexValue + i;
                } else {
                    value = this.enumType[type].charAt(indexValue + i);
                }

                enumOption = {};
                enumOption[this.opts.valueField] = value;
                enumOption[this.opts.textField] = data[i];

                enumData.push(enumOption);
            }

            this.addEnum.call(this, name, enumData);
            return enumData;
        },

        //cookie enum
        setCookieEnum : function(name, enumObj){
            if(!Dolphin.cookie(name)){
                thisTool.cookie(name, thisTool.json2string(enumObj), {
                    expires: 365,
                    path : thisTool.path.basePath
                });

                if (this.opts.enumCache === true) {
                    this.addEnum(name, enumObj);
                }
            }
        },
        setCookieEnumByAjax : function(name){
            if(!thisTool.cookie(name)){
                var enumObj = this.loadEnum(name);
                if(enumObj){
                    var cookieEnum = [], cookieEnumItem = null;
                    for(var i = 0; i < enumObj.length; i++){
                        cookieEnumItem = {};
                        cookieEnumItem[this.opts.valueField] = enumObj[i][this.opts.valueField];
                        cookieEnumItem[this.opts.textField] = enumObj[i][this.opts.textField];
                        cookieEnum.push(cookieEnumItem);
                    }
                    this.setCookieEnum(name, cookieEnum);
                }
            }
        },
        setCookieEnumsByAjax : function(nameList){
            for(var i = 0; i < nameList.length; i++){
                this.setCookieEnumByAjax(nameList[i]);
            }
        },
        getCookieEnum : function(name){
            var enumStr = thisTool.cookie(name);
            return thisTool.string2json(enumStr);
        },

        //load enum
        loadEnum: function (name) {
            var _this = this;
            var data = {}, url;
            data[this.opts.enumKey] = name;
            url = this.opts.enumUrl.replace("{"+this.opts.enumKey+"}", name);
            var returnData = this.opts.ajax.call(this, {
                url: url,
                data: data,
                async : false
            });

            var enumData = null;
            if(returnData.success){
                if(typeof _this.opts.dataFilter == 'function'){
                    returnData = _this.opts.dataFilter.call(_this, returnData);
                }
                enumData = returnData.rows;
                if (this.opts.enumCache === true) {
                    this.addEnum.call(this, name, enumData);
                }
            }

            return enumData;
        },
        loadEnumText: function (name, value) {
            var text = null;
            var data = {};
            data[this.opts.enumKey] = name;
            data[this.opts.enumNameKey] = value;

            this.opts.ajax.call(this, {
                url: this.opts.enumTextUrl,
                data: data,
                async : false
            });

            return text;
        },

        //get enum
        getEnum: function (name) {
            var enumData = this.enumData[name];

            if(enumData == null && this.opts.cookieFlag == true){
                enumData = this.getCookieEnum.call(this, name);
            }

            if (enumData == null && this.opts.ajaxFlag == true) {
                enumData = this.loadEnum.call(this, name);
            }

            return enumData;
        },
        getEnumText: function (name, value) {
            var enumData = this.getEnum(name);
            var text = value;

            if (enumData) {
                for (var i = 0; i < enumData.length; i++) {
                    if (enumData[i][this.opts.valueField] == value) {
                        text = enumData[i][this.opts.textField];
                        break;
                    }
                }
            } else {
                if (console && console.log) {
                    console.log(thisTool.i18n.get('enum_cannot_found', name));
                }
            }

            return text;
        },
        getEnumOption: function (name, value) {
            var enumData = this.getEnum(name);
            var option = null;

            if (enumData) {
                for (var i = 0; i < enumData.length; i++) {
                    if (enumData[i][this.opts.valueField] == value) {
                        option = enumData[i];
                        break;
                    }
                }
            } else {
                if (console && console.log) {
                    console.log(thisTool.i18n.get('enum_cannot_found', name));
                }
            }

            return option;
        },
        setOptions : function(param){
            $.extend(true, this.opts, param);
            return this;
        }
    };

    thisTool.ENUM = ENUM;
    thisTool.enum = new ENUM();
})(jQuery);