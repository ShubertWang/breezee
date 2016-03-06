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
			optionParam : null,
			ajaxType:'get'
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
						ajaxType = $(this).attr('ajaxType') || opts.ajaxType,
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
						options = thisTool.ajax({url : optionUrl, async : false, type:ajaxType, mockPathData: mockPathData});
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