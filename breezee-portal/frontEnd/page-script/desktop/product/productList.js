Dolphin.defaults.mockFlag = true;
$(function () {
    var page = {};

    page.connect = {
        productList : {
            url : '/data/product/'
        },
        productDelete : {
            url : '/data/product/',
            type : Dolphin.requestMethod.DELETE
        }
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;
        this.productList = new Dolphin.LIST({
            panel : '#list',
            url : _this.connect.productList.url,
            queryParams : Dolphin.form.getValue('queryForm'),
            title : '菜品列表',
            columns : [{
                code : 'code',
                title : '菜品编码'
            }, {
                code : 'name',
                title : '菜品名称'
            }, {
                code : 'basePrice',
                title : '基本价格'
            }, {
                code : 'category.name',
                title : '所属品类'
            }]
        });
    };

    page.initEvent = function () {
        var _this = this;
        $('#insert').click(function () {
            Dolphin.goUrl('/product/productEdit');
        });
        $('#update').click(function () {
            var checkedData = _this.productList.getChecked();
            if(checkedData.length != 1){
                Dolphin.alert('请选中一条记录');
            }else{
                Dolphin.goUrl('/product/productEdit?id='+checkedData[0].id);
            }
        });
        $('#delete').click(function () {
            var checkedData = _this.productList.getChecked(),
                ids = [], i;
            if(checkedData.length == 0){
                Dolphin.alert('请选中一条记录');
            }else{
                Dolphin.confirm('确定要删除吗？', {
                    callback : function (flag) {
                        if(flag){
                            for(i = 0; i < checkedData.length; i++){
                                ids.push(checkedData.id);
                            }
                            Dolphin.ajax($.extend({}, _this.connect.productDelete, {
                                loading : true,
                                data : Dolphin.json2string(ids),
                                onSuccess : function (reData) {
                                    Dolphin.alert(reData.msg || '删除成功', {
                                        callback : function () {
                                            _this.productList.reload();
                                        }
                                    })
                                }
                            }));
                        }
                    }
                })
            }
        });

        $("#query").click(function () {
            this.productList.query(Dolphin.form.getValue("#queryForm"));
        });
        $("#conditionReset").click(function () {
            Dolphin.form.empty("#queryForm")
        });
    };

    window.page = page.init();
});