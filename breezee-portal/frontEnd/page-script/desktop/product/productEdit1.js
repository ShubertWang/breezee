Dolphin.defaults.mockFlag = false;
$(function () {
    Dolphin.form.parse();

    var page = {
        categoryAttr : null,
        productData : null,

        baseInfo : $('#baseInfo'),
        categoryAttrPanel : $('#extendAttrPanel'),
        categoryAttrPanelBody : $('#categoryAttrPanelBody'),
        save : $('#save')
    };

    page.connect = {
        productDetail : {
            url : '/data/pcm/product/'
        },
        category : {
            url : '/data/pcm/category/cateAttrs/{id}'
        },
        categoryTree: {
            url: '/data/pcm/category/p/{id}'
        },
        productInfo : {
            url : '/data/pcm/product/{id}'
        },
        productSave : {
            url : '/data/pcm/product/',
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
        _this.curPage = 1;
        if(REQUEST_MAP.data.id){
            Dolphin.ajax({
                url : _this.connect.productInfo.url,
                pathData : {
                    id : REQUEST_MAP.data.id
                },
                mockPathData : ['id'],
                loading : true,
                onSuccess : function (reData) {
                    _this.initValue(reData.value);
                    _this.selCat = {id:_this.productInfo.cateId,name:_this.productInfo.cateName};
                    _this.curPage = 2;
                    _this._buttonInit();
                }
            });
        } else {
            this.categoryTree();
        }
    };

    page._buttonInit = function(){
        var _this = this;
        //这块逻辑写的太差了，先这样吧--anjing
        var previousPage = _this.curPage;
        _this.save.html('保存');

        $(".page-info").hide();
        $(".page-" + _this.curPage).show();

        $("#categoryId").val(_this.selCat.id);
        $("#categoryName").val(_this.selCat.name);
    }

    page.initEvent = function () {
        var _this = this;
        _this.save.click(function (){
            _this.curPage++;
            _this._buttonInit();
            _this.renderForm(_this.selCat.id);
            if(_this.curPage == 3) {
                var submitData = $.extend({}, _this.productInfo, Dolphin.form.getValue(_this.baseInfo), {
                    productData: Dolphin.form.getValue(_this.categoryAttrPanel)
                });

                Dolphin.ajax($.extend({}, _this.connect.productSave, {
                    loading: true,
                    data: Dolphin.json2string(submitData),
                    onSuccess: function (reData) {
                        Dolphin.alert(reData.msg || '保存成功', {
                            callback: function () {
                                Dolphin.goUrl('/product/productList');
                            }
                        })
                    }
                }))
            }
        });
    };

    page.initValue = function(data){
        var _this = this;
        _this.productInfo = data;
        Dolphin.form.setValue(data, _this.baseInfo);
        _this.renderForm(data.cateId, function (reData) {
            Dolphin.form.setValue(data.productData, _this.categoryAttrPanel);
        });
    };

    page.categoryTree  = function () {
        var _this = this;
        new Dolphin.HORIZONTAL_TREE({
            panel: "#categoryTree",
            url: _this.connect.categoryTree.url,
            mockPathData: ["hid"],
            loadingFlag : true,
            itemType: "folder",
            idField:'id',
            click: function (node, event) {
                _this.selCat = node;
            },
            buttons: [],
            itemButtons: []
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

                _this.categoryAttrPanelBody.empty();
                _this.categoryAttrPanel.show();
                $.each(_this.categoryAttr, function (i, attr) {
                    _this.renderField(attr, _this.categoryAttrPanelBody);
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

        if(!attr.display){
            $('<input type="hidden" name="'+attr.id+'" />').prependTo(panel);
        }else{
            col = $('<div class="dolphin-col-12">').appendTo(panel);
            control = $('<div class="form-group">').attr({
                'code': attr.code,
                'id': attr.id
            }).appendTo(col);

            inputLabel = $('<label class="col-sm-2 control-label">').html(attr.name).appendTo(control);
            inputPanel = $('<div class="col-sm-10">').appendTo(control);

            switch (attr.attrType){
                case "integer":
                case "numberic":
                    input = $('<div class="input-group">');
                    $('<input type="text" class="form-control">').attr({
                        name : attr.id
                    }).appendTo(input);
                    $('<span class="input-group-addon">').html(attr.unitCode || '&nbsp;').appendTo(input);
                    break;
                case "dict":
                    input = $('<select class="form-control">');
                    input.attr($.extend({}, {options:attr.enumCode}, attr.param));
                    input.attr('name', attr.id);
                    Dolphin.form.parseSelect(input);
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