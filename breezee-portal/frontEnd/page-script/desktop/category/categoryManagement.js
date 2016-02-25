/**
 * Created by Shubert.Wang on 2016/1/27.
 */
Dolphin.defaults.mockFlag = false;
$(function () {
    Dolphin.form.parse();

    var infoPanel = $('#infoPanel'),
        categoryPanel = $('#categoryPanel'),
        attributePanel = $('#attributePanel'),
        categoryAttrSave = $('#categoryAttrSave'),
        attributeLibrary,selectNode;

    //categoryTree
    var categoryTree = new Dolphin.TREE({
        panel : '#categoryTree',
        url : '/data/pcm/category/p/{id}',
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
        url : '/data/pcm/category/cateAttrs/{id}',
        mockPathData : ['id'],
        data : {rows : [], total : 0},
        pagination : false,
        columns : [{
            code: 'code',
            title: '属性编码'
        }, {
            code: 'name',
            title: '属性名称'
        }, {
            code : 'attrType',
            title : '字段类型'
        }, {
            code : 'inheritable',
            title : '是否可继承',
            width : '100px'
        }, {
            code:'sourceCateId',
            title:'来源',
            formatter:function(val,dat){
                console.log(val);
                if(val==selectNode.id){
                    return "自身";
                } else {
                    return "父类"+val;
                }
            }
        }],
        onLoadSuccess : function (data) {
            Dolphin.toggleEnable(categoryAttrSave, false);
        },
        onCheck : function (data, row, thisInput) {
            if(this.getChecked().length > 0){
                Dolphin.toggleEnable(categoryAttrSave, true);
            }else{
                Dolphin.toggleEnable(categoryAttrSave, false);
            }
        },
        onChecked : function (data) {
            var row = $('tr[__id__="'+data.__id__+'"]');
            var col = row.children('[columnCode="inheritFlag"]');
            var input = $('<input type="checkbox" name="inheritFlag">');
            Dolphin.toggleCheck(input, data.inheritFlag);
            col.html(input);
        },
        onUnchecked : function (data) {
            var row = $('tr[__id__="'+data.__id__+'"]');
            var col = row.children('[columnCode="inheritFlag"]');
            col.html(data.inheritFlag + "");
        }
    });

    var selectedList = new Dolphin.LIST({
        panel : '#selectedList',
        data : {rows : [], total : 0},
        title : '已选择列表',
        pagination : false,
        idField:'attrId',
        rowIndex : false,
        columns : [{
            code : 'code',
            title : '属性编码'
        }, {
            code : 'name',
            title : '属性名称'
        }, {
            code : 'remark',
            title : '备注'
        }]
    });

    var unselectedList = new Dolphin.LIST({
        panel : '#unselectedList',
        data : {rows : [], total : 0},
        title : '未选择列表',
        panelType : 'panel-info',
        ajaxType:'post',
        pagination : true,
        rowIndex : false,
        columns : [{
            code : 'code',
            title : '属性编码'
        }, {
            code : 'name',
            title : '属性名称'
        }, {
            code : 'remark',
            title : '备注'
        }]
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
    $('#insert').click(function () {
        var checkedData = categoryTree.getChecked();
        if(checkedData.length != 1){
            Dolphin.alert('请选择一个品类节点。');
        }else{
            Dolphin.form.setValue({
                parent : {
                    id : checkedData[0].id
                }
            }, '#categoryForm');

            categoryWin.modal('show');
        }
    });
    $('#update').click(function () {
        var checkedData = categoryTree.getChecked();
        if(checkedData.length != 1){
            Dolphin.alert('请选择一个品类节点。');
        }else{
            Dolphin.form.setValue(checkedData[0], '#categoryForm');

            categoryWin.modal('show');
        }
    });
    $('#delete').click(function () {
        var checkedData = categoryTree.getChecked();
        if(checkedData.length != 1){
            Dolphin.alert('请选择一个品类节点。');
        }else{
            Dolphin.confirm('确定要删除吗？', {
                callback : function (flag) {
                    if(flag){
                        Dolphin.ajax({
                            url : '/data/model/{id}',
                            pathData : {
                                id : checkedData[0].id
                            },
                            mockPathData : ['category'],
                            type : Dolphin.requestMethod.DELETE,
                            onSuccess : function (reData) {
                                Dolphin.alert('删除成功', {
                                    callback : function () {
                                        categoryTree.reload();
                                        attrList.empty();
                                        Dolphin.form.empty('#baseInfo');
                                    }
                                })
                            }
                        });
                    }
                }
            });
        }
    });

    // list button
    $('#multipleUpdateAttr').click(function () {
        categoryPanel.slideToggle(300, function () {
            attributePanel.slideToggle(300);
            unselectedList.load('/data/pcm/attribute/excludeCate/'+selectNode.id);
            selectedList.loadData(attrList.data);
        });
    });
    categoryAttrSave.click(function () {
        var data = attrList.getChecked(),
            i;
        for(i = 0; i < data.length; i++){
            data[i].inheritFlag = $('tr[__id__="'+data[i].__id__+'"]').find('[name="inheritFlag"]')[0].checked;
        }

        Dolphin.ajax({
            url : '/data/pcm/categoryAttr',
            type : Dolphin.requestMethod.POST,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || "保存成功", {
                    callback : function () {
                        attrList.reload();
                    }
                })
            }
        });
    });

    // panel button
    $('[change]').click(function () {
        var thisButton = $(this), allFlag = !!thisButton.attr('all'),
            sourceList, targetList,
            checkedData,
            i;
        if(thisButton.attr("change") == "select"){
            sourceList = unselectedList;
            targetList = selectedList;
        }else{
            sourceList = selectedList;
            targetList = unselectedList;
        }

        if(allFlag){
            checkedData = [].concat(sourceList.data.rows);
        }else{
            checkedData = sourceList.getChecked();
        }

        for(i = 0; i < checkedData.length; i++){
            if(!checkedData[i].inheritFlag){
                sourceList.removeRow(checkedData[i].__id__);
                targetList.addRowWithData($.extend({attrId:checkedData[i].id}, checkedData[i]));
            }
        }
        console.log('------------');
        console.log(targetList.data.rows);
    });
    $('#confirm').click(function () {
        var data = {id:selectNode.id,cateAttrInfos:[]}, attrData,
            i;
        for(i = 0; i < selectedList.data.rows.length; i++){
            if(!selectedList.data.rows[i].sourceCateId || selectedList.data.rows[i].sourceCateId==selectNode.id) {
                data.cateAttrInfos.push({
                    attrId: selectedList.data.rows[i].attrId,
                    inheritable: selectedList.data.rows[i].inheritable == null ? true : selectedList.data.rows[i].inheritable
                });
            }
        }
        console.log(data);
        Dolphin.ajax({
            url : '/data/pcm/category/categoryAttr',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '保存成功', {
                    callback : function () {
                        attrList.reload();
                        attributePanel.slideToggle(300, function () {
                            categoryPanel.slideToggle(300);
                        });
                    }
                })
            }
        });
    });
    $('#cancel').click(function () {
        attributePanel.slideToggle(300, function () {
            categoryPanel.slideToggle(300);
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
                url : '/data/pcm/category/',
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
            title : '编辑品类基本信息',
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