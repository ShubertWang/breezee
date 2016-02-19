Dolphin.defaults.mockFlag = true;
$(function () {
    Dolphin.form.parse();

    var page = {
        categoryAttr : null,
        productData : null,

        baseInfo : $('#baseInfo'),
        categoryId : $('#categoryId'),
        categoryAttrPanel : $('#extendAttrPanel'),
        categoryAttrPanelBody : $('#categoryAttrPanelBody'),
        save : $('#save')
    };

    page.connect = {
        productDetail : {
            url : '/data/product/'
        },
        category : {
            url : '/data/model/categoryAttr/{id}'
        },
        productData : {
            url : '/data/product/{id}'
        },
        productSave : {
            url : '/data/product/',
            type : Dolphin.requestMethod.PUT
        }
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;

        if(REQUEST_MAP.data.id){
            _this.productData = Dolphin.ajax({
                url : _this.connect.productData.url,
                pathData : {
                    id : REQUEST_MAP.data.id
                },
                mockPathData : ['id'],
                loading : true,
                onSuccess : function (reData) {
                    _this.initValue(reData.value);
                }
            });
        }

    };

    page.initEvent = function () {
        var _this = this;
        _this.categoryId.change(function () {
            if(this.value){
                _this.renderForm(this.value);
            }else{
                _this.categoryAttrPanel.hide();
            }
        });

        _this.save.click(function (){
            var productDataArr = [],
                productDataMap,submitData,
                key;

            productDataMap = Dolphin.form.getValue(_this.categoryAttrPanel);
            for(key in productDataMap){
                productDataArr.push({
                    code : key,
                    value : productDataMap[key]
                });
            }

            submitData = $.extend({}, _this.productData, Dolphin.form.getValue(_this.baseInfo), {
                productData : productDataArr
            });

            Dolphin.ajax($.extend({}, _this.connect.productSave, {
                loading : true,
                data : Dolphin.json2string(submitData),
                onSuccess : function (reData) {
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback : function () {
                            Dolphin.goUrl('/product/productList');
                        }
                    })
                }
            }))
        })
    };

    page.initValue = function(data){
        var _this = this, productDataMap = {},
            i;
        _this.productData = data;
        Dolphin.form.setValue(data, _this.baseInfo);
        for(i = 0; i < data.productData.length; i++){
            productDataMap[data.productData[i].code] = data.productData[i].value;
        }
        _this.renderForm(data.category.id, function (reData) {
            Dolphin.form.setValue(productDataMap, _this.categoryAttrPanel);
        });
    };

    page.renderForm = function (id, callback) {
        var _this = this;
        Dolphin.ajax({
            url : _this.connect.category.url,
            mockPathData : ['id'],
            pathData : {
                id : id
            },
            loading : true,
            onSuccess : function (reData) {
                _this.categoryAttr = reData.rows;

                _this.categoryAttrPanel.show();
                _this.categoryAttrPanelBody.empty();
                $.each(_this.categoryAttr, function (i, attr) {
                    _this.renderField(attr.attributeDefine, _this.categoryAttrPanelBody);
                });
                if(typeof callback == 'function'){
                    callback.call(_this, reData);
                }
            }
        });
    };
    page.renderField = function (attr, panel, param) {
        var col, control, inputLabel, inputPanel, input,
            _this = this,
            opts = $.extend({}, param);

        if(typeof opts.eachField === 'function'){
            if(opts.eachField.call(_this, attr) === false){
                return false;
            }
        }

        if(attr.hidden){
            $('<input type="hidden" name="'+attr.id+'" />').prependTo(panel);
        }else{
            col = $('<div class="dolphin-col-12">').appendTo(panel);
            control = $('<div class="form-group">').attr({
                'code': attr.code,
                'id': attr.id
            }).appendTo(col);

            inputLabel = $('<label class="col-sm-2 control-label">').html(attr.name).appendTo(control);
            inputPanel = $('<div class="col-sm-10">').appendTo(control);

            switch (attr.fieldType){
                case "enum":
                    input = $('<select class="form-control">');
                    input.attr($.extend({}, {options:attr.subType}, attr.param));
                    input.attr('name', attr.id);
                    break;
                case "image":
                    col.attr('class', 'dolphin-col-24');
                    inputLabel.removeClass('col-sm-2').addClass('col-sm-1');
                    inputPanel.removeClass('col-sm-10').addClass('col-sm-11');

                    input = $('<div class="dolphin_file_box">').html('<input type="file" name="'+attr.id+'">');
                    input.fileBox();
                    break;
                default :
                    input = $('<input type="text" class="form-control" />');
                    input.attr('name', attr.id);
            }
            input.appendTo(inputPanel)
        }
    };

    window.page = page.init();
});