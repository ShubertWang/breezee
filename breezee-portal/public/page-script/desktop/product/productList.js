Dolphin.defaults.mockFlag = false;
$(function () {
    var page = {};

    page.connect = {
        productList : {
            url : '/data/pcm/product/page'
        },
        productDelete : {
            url : '/data/pcm/product/',
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
            rowIndex:false,
            url : _this.connect.productList.url,
            //queryParams : Dolphin.form.getValue('queryForm'),
            ajaxType: 'post',
            title : '菜品列表',
            columns : [{
                code : 'code',
                title : '菜品编码'
            }, {
                code : 'recommend',
                title : '推荐菜品',
                width:'75px',
                formatter:function(val,data){
                    return '<input type="checkbox" value="'+val+'" ' +
                        'id="recoslideThree" onchange="updateSome(this,'+data.id+',0)" name="check" '+ (val?'checked':'')+' />';
                }
            },{
                code : 'name',
                title : '菜品名称'
            }, {
                code : 'basePrice.value',
                width:'90px',
                title : '基本价格'
            }, {
                code : 'cateName',
                title : '所属品类'
            }, {
                code : 'quantity.value',
                width:'120px',
                title:'库存',
                formatter:function(val,data){
                    //return '<div class="text-center">'+val+' &nbsp;&nbsp;&nbsp;<a href="productStock?skuId='+data.code+'">明细</a> </div>';
                    return val;
                }
            },{
                code : 'status',
                title : '是否上架',
                width:'75px',
                formatter:function(val,data){
                    return '<input type="checkbox" value="'+val+'" ' +
                    'onchange="updateStatus(this,'+data.id+')" name="check'+data.id+'" '+ (val?'checked':'')+' />';
                }
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
            _this.productList.query(Dolphin.form.getValue("#queryForm"));
        });
        $("#conditionReset").click(function () {
            Dolphin.form.empty("#queryForm")
        });
    };

    window.updateStatus = function(el,id){
        var tmp = ['recommend','status'];
        console.log(el.checked);
        return;
        Dolphin.ajax({
            url : '/data/pcm/product/status/'+id+'/'+el.checked?1:0
        });
    }

    window.page = page.init();
});