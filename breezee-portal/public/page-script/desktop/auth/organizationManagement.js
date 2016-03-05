$(function () {
    menu.select('organizationManagement');
    Dolphin.form.parse();

    var infoPanel = $('#infoPanel'),
        categoryPanel = $('#categoryPanel'),
        attributePanel = $('#attributePanel'),
        attributeLibrary,selectNode;

    //categoryTree
    var categoryTree = new Dolphin.TREE({
        panel : '#categoryTree',
        url : '/data/sym/organization/p/{id}',
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
        url : '/data/sym/account/org/{id}',
        ajaxType:'post',
        mockPathData : ['id'],
        data : {rows : [], total : 0},
        pagination : true,
        columns : [{
            code: 'code',
            title: '账号编码'
        }, {
            code: 'name',
            title: '账号名称'
        }, {
            code : 'type',
            title : '账号类型'
        }, {
            code : 'set',
            title : '性别',
            width : '60px'
        }, {
            code : 'mobile',
            title : '联系手机'
        }],
        onLoadSuccess : function (data) {
        },
        onCheck : function (data, row, thisInput) {
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
        pagination : true,
        rowIndex : false,
        columns : [{
            code : 'code',
            title : '账号编码'
        }, {
            code : 'name',
            title : '账号名称'
        }, {
            code : 'status',
            title : '状态'
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
            title : '账号编码'
        }, {
            code : 'name',
            title : '账号名称'
        }, {
            code : 'status',
            title : '状态'
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
            //unselectedList.load('/data/sym/account/excludeOrg/'+selectNode.id);
            unselectedList.load('/data/sym/account/page');
            selectedList.loadData(attrList.data);
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
        var data = {};
        data.id = selectNode.id;
        data.code = selectNode.code;
        data.accounts = [];
        var selData = selectedList.data.rows;
        for(var i = 0; i < selData.length; i++){
            data.accounts.push(selData[i].id);
        }
        Dolphin.ajax({
            url : '/data/sym/organization/acntRel',
            type : Dolphin.requestMethod.PUT,
            data : Dolphin.json2string(data),
            onSuccess : function (reData) {
                Dolphin.alert(reData.msg || '保存成功', {
                    callback : function () {
                        //categoryTree.reload();
                        //categoryWin.modal('hide');
                        attributePanel.slideToggle(300, function () {
                            categoryPanel.slideToggle(300);
                        });
                        attrList.reload();
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
                url : '/data/sym/organization/',
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
            title : '编辑组织基本信息',
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