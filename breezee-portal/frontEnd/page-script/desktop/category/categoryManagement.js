/**
 * Created by Shubert.Wang on 2016/1/27.
 */
Dolphin.defaults.mockFlag = true;
$(function () {
    Dolphin.form.parse();

    var infoPanel = $('#infoPanel'),
        categoryPanel = $('#categoryPanel'),
        attributePanel = $('#attributePanel'),
        categoryAttrSave = $('#categoryAttrSave'),
        attributeLibrary;

    //categoryTree
    var categoryTree = new Dolphin.TREE({
        panel : '#categoryTree',
        url : '/data/model/tree/{id}',
        mockPathData : ['id'],
        multiple : false,
        onChecked : function (data) {
            Dolphin.form.setValue(data, '#baseInfo');
            attrList.query(null, {
                id : data.id
            });
            infoPanel.show();
            Dolphin.toggleEnable($('.page-header .operationButton button'), true);
        }
    });

    var attrList = new Dolphin.LIST({
        panel : '#productList',
        url : '/data/model/categoryAttr/{id}',
        mockPathData : ['id'],
        data : {rows : [], total : 0},
        pagination : false,
        columns : [{
            code: 'attributeDefine.code',
            title: '属性编码'
        }, {
            code: 'attributeDefine.name',
            title: '属性名称'
        }, {
            code : 'attributeDefine.fieldType',
            title : '字段类型'
        }, {
            code : 'inheritFlag',
            title : '是否可继承',
            width : '100px'
        }, {
            code : 'attributeDefine.remark',
            title : '备注'
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
        pagination : false,
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
        var selectedData = [], ids = ',',
            i, j;
        if(!attributeLibrary){
            Dolphin.ajax({
                url : '/data/model/attribute',
                loading : true,
                onSuccess : function (reData) {
                    attributeLibrary = reData.rows;
                    showAttributePanel();
                }
            })
        }else{
            showAttributePanel();
        }

        function showAttributePanel(){
            var _attributeLibrary = $.extend(true, [], attributeLibrary);
            for(i = 0; i < attrList.data.rows.length; i++){
                selectedData.push($.extend({}, attrList.data.rows[i].attributeDefine, {
                    relId : attrList.data.rows[i].id,
                    inheritFlag : attrList.data.rows[i].inheritFlag
                }));
                for(j = 0; j < _attributeLibrary.length;){
                    if(selectedData[i].id == _attributeLibrary[j].id){
                        _attributeLibrary.splice(j,1);
                    }else{
                        j++;
                    }
                }
            }
            selectedList.loadData({rows : selectedData, total:selectedData.length});
            unselectedList.loadData({rows : _attributeLibrary, total: _attributeLibrary.length});
            categoryPanel.slideToggle(300, function () {
                attributePanel.slideToggle(300);
            });
        }
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
                targetList.addRowWithData(checkedData[i]);
            }
        }
    });
    $('#confirm').click(function () {
        var data = [], attrData,
            i;

        for(i = 0; i < selectedList.data.rows.length; i++){
            attrData = {
                attributeDefine : {
                    id : selectedList.data.rows[i].id
                },
                inheritFlag : selectedList.data.rows[i].inheritFlag==null?true:selectedList.data.rows[i].inheritFlag
            }
        }

        Dolphin.ajax({
            url : '/data/pcm/categoryAttr',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.string2json(data),
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
            categoryWin.modal('hide');
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