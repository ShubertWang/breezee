$(function () {
    menu.select('articleManagement');
    Dolphin.form.parse();

    var infoPanel = $('#infoPanel'),
        categoryPanel = $('#categoryPanel'),selectNode,curSelect;

    //categoryTree
    var categoryTree = new Dolphin.TREE({
        panel : '#categoryTree',
        url : '/data/sdx/model/p/-1',
        mockPathData : ['id'],
        multiple : false,
        onChecked : function (data) {
            selectNode = data;
            Dolphin.form.setValue(data, '#baseInfo');
            if(!data.parent){
                $("#parentName").html("");
            }
            attrList.query(null, {
                id : data.id
            });
            infoPanel.show();
            Dolphin.toggleEnable($('.page-header .operationButton button'), true);
        }
    });

    var attrList = new Dolphin.LIST({
        panel : '#productList',
        url : '/data/sdx/article/modelId/{id}',
        ajaxType:'post',
        mockPathData : ['id'],
        data : {rows : [], total : 0},
        pagination : true,
        columns: [{
            code: 'name',
            title: '标题'
        }, {
            code: 'subtitle',
            title: '副标题'
        }, {
            code: 'lang',
            title: '语言',
            formatter: function (value, record) {
                return value;
            }
        } , {
            code: 'updator',
            title: '更新人'
        }, {
            code: 'updateTime',
            title: '更新时间',
            formatter:function(val){
                return Dolphin.longDate2string(val);
            }
        }, {
            code: 'content',
            title: '详情',
            formatter: function (value, record) {
                return '<a href="articleDetail?id='+record.id+'" target="_blank">详情</a>';
            }
        }],
        onLoadSuccess : function (data) {
        },
        onCheck : function (data, row, thisInput) {
        },
        onChecked: function (data, row, thisCheckbox) {
            curSelect = data;
        },
        onUnchecked : function (data) {
        }
    });

    //============================================================== event
    $('#insertRoot').click(function () {
        Dolphin.form.setValue({
            parent : {
                id : -1
            }
        }, '#categoryForm');

        categoryWin.modal('show');
    });
    //$('#insert').click(function () {
    //    var checkedData = categoryTree.getChecked();
    //    if(checkedData.length != 1){
    //        Dolphin.alert('请选择一个品类节点。');
    //    }else{
    //        Dolphin.form.setValue({
    //            parent : {
    //                id : checkedData[0].id
    //            }
    //        }, '#categoryForm');
    //        categoryWin.modal('show');
    //    }
    //});
    $('#update').click(function () {
        var checkedData = categoryTree.getChecked();
        if(checkedData.length != 1){
            Dolphin.alert('请选择一个品类节点。');
        }else{
            Dolphin.form.setValue(checkedData[0], '#categoryForm');
            categoryWin.modal('show');
        }
    });
    //$('#delete').click(function () {
    //    var checkedData = categoryTree.getChecked();
    //    if(checkedData.length != 1){
    //        Dolphin.alert('请选择一个品类节点。');
    //    }else{
    //        Dolphin.confirm('确定要删除吗？', {
    //            callback : function (flag) {
    //                if(flag){
    //                    Dolphin.ajax({
    //                        url : '/data/model/{id}',
    //                        pathData : {
    //                            id : checkedData[0].id
    //                        },
    //                        mockPathData : ['category'],
    //                        type : Dolphin.requestMethod.DELETE,
    //                        onSuccess : function (reData) {
    //                            Dolphin.alert('删除成功', {
    //                                callback : function () {
    //                                    categoryTree.reload();
    //                                    attrList.empty();
    //                                    Dolphin.form.empty('#baseInfo');
    //                                }
    //                            })
    //                        }
    //                    });
    //                }
    //            }
    //        });
    //    }
    //});

    $("#articleAdd").bind('click',function(){
        location.href = "articleEdit?modelId="+selectNode['id'];
    });

    $('#articleUpdate').bind('click', function () {
        location.href = "articleEdit?id=" + curSelect.id+"&modelId="+curSelect['id'];
    });

    $('#articleDel').bind('click', function () {
        Dolphin.ajax({
            url: "/data/sdx/article/"+curSelect.id,
            type: Dolphin.requestMethod.DELETE,
            onSuccess : function (reData) {
                Dolphin.alert('删除成功', {
                    callback : function () {
                        attrList.reload();
                    }
                })
            }
        });
    });

    //============================================================== modal
    var categoryWin;
    (function () {
        var footer = $('<div>'), confirmButton, cancelButton;
        confirmButton = $('<button type="button" class="btn btn-primary btn-small">').html('确定').appendTo(footer);
        confirmButton.click(function () {
            var data = Dolphin.form.getValue('categoryForm', '"');
            Dolphin.ajax({
                url : '/data/sdx/model/',
                type : Dolphin.requestMethod.PUT,
                data : Dolphin.json2string(data),
                onSuccess : function (reData) {
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback : function () {
                            categoryTree.reload();
                            categoryWin.modal('hide');
                        }
                    })
                }
            });
        });
        cancelButton = $('<button type="button" class="btn btn-default btn-small" >').html('取消').appendTo(footer);
        cancelButton.click(function () {
            categoryWin.modal('hide');
        });
        categoryWin = Dolphin.modalWin({
            title : '编辑类别基本信息',
            content : $('#categoryModalWin').show(),
            footer : footer,
            defaultHidden : true,
            init : function () {

            },
            show : function () {

            },
            hidden : function () {
                Dolphin.form.empty('#categoryForm');
            }
        });
    })();
});