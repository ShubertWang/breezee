Dolphin.defaults.mockFlag = true;
$(function () {
    var page = {
        restaurantTree : null,
        serviceLineList : null,
        tableList : null,
        restaurantModal : null,
        serviceLineModal : null,
        tableModal : null,

        infoPanel : $('#infoPanel'),
        baseInfoPanel : $('#baseInfoPanel'),
        restaurantTreePanel : $('#restaurantTreePanel'),
        serviceLinePanel : $('#serviceLinePanel'),
        tablePanel : $('#tablePanel'),
        restaurantModalWin : $('#restaurantModalWin'),
        serviceLineModalWin : $('#serviceLineModalWin'),
        tableModalWin : $('#tableModalWin'),

        curEditData : null
    };

    page.connect = {
        restaurantTree : {
            url : '/data/restaurant/tree/{id}'
        },
        restaurantSave : {
            url : '/data/restaurant/',
            type : Dolphin.requestMethod.PUT
        },
        restaurantDelete : {
            url : '/data/restaurant/{id}',
            type : Dolphin.requestMethod.DELETE
        },
        serviceLine : {
            url : '/data/restaurant/serviceLine/{id}'
        },
        serviceLineSave : {
            url : '/data/restaurant/serviceLine/',
            type : Dolphin.requestMethod.PUT
        },
        serviceLineDelete : {
            url : '/data/restaurant/serviceLine/{id}',
            type : Dolphin.requestMethod.DELETE
        },
        table : {
            url : '/data/restaurant/table/{id}'
        },
        tableSave : {
            url : '/data/restaurant/table/',
            type : Dolphin.requestMethod.PUT
        },
        tableDelete : {
            url : '/data/restaurant/table/{id}',
            type : Dolphin.requestMethod.DELETE
        }
    };

    page.init = function () {
        this.initPage();
        this.initModalWin();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;

        _this.restaurantTree = new Dolphin.TREE({
            url : _this.connect.restaurantTree.url,
            panel : _this.restaurantTreePanel,
            mockPathData : ['id'],
            multiple : false,
            onCheck : function (data) {
                _this.infoPanel.show();
                Dolphin.form.empty(_this.baseInfoPanel);
                Dolphin.form.setValue(data, _this.baseInfoPanel);
                _this.serviceLineList.query(null, {
                    id : data.id
                });
                _this.tableList.query(null, {
                    id : data.id
                })
            },
            onLoad : function () {
                _this.infoPanel.hide();
            }
        });

        _this.serviceLineList = new Dolphin.LIST({
            panel : _this.serviceLinePanel,
            url : _this.connect.serviceLine.url,
            mockPathData : ['id'],
            data : {rows : [], total : 0},
            pagination : false,
            checkbox : false,
            columns : [{
                code : 'name',
                title : '服务线名称'
            }, {
                code : 'mealType',
                title : '餐次'
            }, {
                code : 'deliverType',
                title : '送餐方式'
            }, {
                code : 'time',
                title : '翻台时间'
            }, {
                code : '__func__',
                title : '<button class="btn btn-success btn-xs" onclick="page.serviceLineModal.modal(\'show\');">添加</button>',
                formatter : function (val, row, index) {
                    var div = $(' <div>');
                    $('<button class="btn btn-primary btn-xs">').html('修改').click(function () {
                        Dolphin.form.setValue(row, _this.serviceLineModalWin);
                        _this.curEditData = row;
                        _this.serviceLineModal.modal('show');
                    }).appendTo(div);
                    $('<button class="btn btn-danger btn-xs">').html('删除').click(function () {
                        Dolphin.confirm('确定要删除该记录吗？', {
                            callback : function (flag) {
                                if(flag){
                                    Dolphin.ajax($.extend({}, _this.connect.serviceLineDelete, {
                                        pathData : {
                                            id : row.id
                                        },
                                        mockPathData : ['id'],
                                        onSuccess : function (reData) {
                                            Dolphin.alert(reData.msg || '删除成功', {
                                                callback : function () {
                                                    _this.serviceLineList.reload();
                                                }
                                            })
                                        }
                                    }))
                                }
                            }
                        })
                    }).appendTo(div);
                    return div;
                }
            }]
        });

        _this.tableList = new Dolphin.LIST({
            panel : _this.tablePanel,
            url : _this.connect.table.url,
            mockPathData : ['id'],
            data : {rows : [], total : 0},
            pagination : false,
            checkbox : false,
            columns : [{
                code : 'code',
                title : '餐桌编码'
            }, {
                code : 'name',
                title : '餐桌名称'
            }, {
                code : 'number',
                title : '标准座位数'
            }, {
                code : 'location',
                title : '位置'
            }, {
                code : 'status',
                title : '状态'
            }, {
                code : 'remark',
                title : '备注'
            }, {
                code : '__func__',
                title : '<button class="btn btn-success btn-xs" onclick="page.tableModal.modal(\'show\');">添加</button>',
                formatter : function (val, row, index) {
                    var div = $(' <div>');
                    $('<button class="btn btn-primary btn-xs">').html('修改').click(function () {
                        Dolphin.form.setValue(row, _this.tableModalWin);
                        _this.curEditData = row;
                        _this.tableModal.modal('show');
                    }).appendTo(div);
                    $('<button class="btn btn-danger btn-xs">').html('删除').click(function () {
                        Dolphin.confirm('确定要删除该记录吗？', {
                            callback : function (flag) {
                                if(flag){
                                    Dolphin.ajax($.extend({}, _this.connect.tableDelete, {
                                        pathData : {
                                            id : row.id
                                        },
                                        mockPathData : ['id'],
                                        onSuccess : function (reData) {
                                            Dolphin.alert(reData.msg || '删除成功', {
                                                callback : function () {
                                                    _this.tableList.reload();
                                                }
                                            })
                                        }
                                    }))
                                }
                            }
                        })
                    }).appendTo(div);
                    return div;
                }
            }]
        })
    };

    page.initEvent = function () {
        var _this = this;

        $('#insert').click(function () {
            _this.restaurantModal.modal('show');
        });
        $('#update').click(function () {
            var checkedData = _this.restaurantTree.getChecked();
            if(checkedData.length != 1){
                Dolphin.alert('请选中一条记录');
            }else{
                Dolphin.form.setValue(checkedData[0], _this.restaurantModalWin);
                _this.curEditData = checkedData[0];
                _this.restaurantModal.modal('show');
            }
        });
        $('#delete').click(function () {
            var checkedData = _this.restaurantTree.getChecked();
            if(checkedData.length != 1){
                Dolphin.alert('请选中一条记录');
            }else{
                Dolphin.confirm('确定要删除该节点及其子节点吗？', {
                    callback : function (flag) {
                        if(flag){
                            Dolphin.ajax($.extend({}, _this.connect.restaurantDelete, {
                                pathData : {
                                    id : checkedData[0].id
                                },
                                mockPathData : ["id"],
                                onSuccess : function (reData) {
                                    Dolphin.alert(reData.msg || "删除成功", {
                                        callback : function () {
                                            _this.restaurantTree.reload();
                                        }
                                    });
                                }
                            }));
                        }
                    }
                });
            }
        });
    };

    page.initModalWin = function () {
        var _this = this;
        (function () {
            var footer = $('<div>'), confirmButton, cancelButton;

            confirmButton = $('<button type="button" class="btn btn-primary btn-small">').html('确定').appendTo(footer);
            confirmButton.click(function () {
                var _data = $.extend({}, _this.curEditData, Dolphin.form.getValue(_this.restaurantModalWin));
                Dolphin.ajax($.extend({}, _this.connect.restaurantSave, {
                    data : Dolphin.json2string(_data),
                    loading : true,
                    onSuccess : function (reData) {
                        _this.restaurantModal.modal('hide');
                        Dolphin.alert(reData.msg || '保存成功', {
                            callback : function () {
                                _this.restaurantTree.reload();
                            }
                        })
                    }
                }));
            });
            cancelButton = $('<button type="button" class="btn btn-default btn-small" >').html('取消').appendTo(footer);
            cancelButton.click(function () {
                _this.restaurantModal.modal('hide');
            });
            _this.restaurantModal = Dolphin.modalWin({
                title : '编辑餐厅基本信息',
                content : _this.restaurantModalWin.show(),
                footer : footer,
                defaultHidden : true,
                width : '800px',
                init : function () {

                },
                show : function () {

                },
                hidden : function () {
                    _this.curEditData = null;
                    Dolphin.form.empty(_this.restaurantModalWin);
                }
            });
        })();

        (function () {
            var footer = $('<div>'), confirmButton, cancelButton;

            confirmButton = $('<button type="button" class="btn btn-primary btn-small">').html('确定').appendTo(footer);
            confirmButton.click(function () {
                var _data;
                if(_this.curEditData){
                    _data = $.extend({}, _this.curEditData, Dolphin.form.getValue(_this.serviceLineModalWin));
                }else{
                    _data = $.extend({}, {
                        restaurantId : _this.restaurantTree.getChecked()[0].id
                    }, Dolphin.form.getValue(_this.serviceLineModalWin));
                }
                Dolphin.ajax($.extend({}, _this.connect.serviceLineSave, {
                    data : Dolphin.json2string(_data),
                    loading : true,
                    onSuccess : function (reData) {
                        _this.serviceLineModal.modal('hide');
                        Dolphin.alert(reData.msg || '保存成功', {
                            callback : function () {
                                _this.serviceLineList.reload();
                            }
                        })
                    }
                }));
            });
            cancelButton = $('<button type="button" class="btn btn-default btn-small" >').html('取消').appendTo(footer);
            cancelButton.click(function () {
                _this.serviceLineModal.modal('hide');
            });
            _this.serviceLineModal = Dolphin.modalWin({
                title : '编辑服务线信息',
                content : _this.serviceLineModalWin.show(),
                footer : footer,
                defaultHidden : true,
                width : '800px',
                init : function () {

                },
                show : function () {

                },
                hidden : function () {
                    _this.curEditData = null;
                    Dolphin.form.empty(_this.serviceLineModalWin);
                }
            });
        })();

        (function () {
            var footer = $('<div>'), confirmButton, cancelButton;

            confirmButton = $('<button type="button" class="btn btn-primary btn-small">').html('确定').appendTo(footer);
            confirmButton.click(function () {
                var _data;
                if(_this.curEditData){
                    _data = $.extend({}, _this.curEditData, Dolphin.form.getValue(_this.tableModalWin));
                }else{
                    _data = $.extend({}, {
                        restaurantId : _this.restaurantTree.getChecked()[0].id
                    }, Dolphin.form.getValue(_this.tableModalWin));
                }
                Dolphin.ajax($.extend({}, _this.connect.tableSave, {
                    data : Dolphin.json2string(_data),
                    loading : true,
                    onSuccess : function (reData) {
                        _this.tableModal.modal('hide');
                        Dolphin.alert(reData.msg || '保存成功', {
                            callback : function () {
                                _this.tableList.reload();
                            }
                        })
                    }
                }));
            });
            cancelButton = $('<button type="button" class="btn btn-default btn-small" >').html('取消').appendTo(footer);
            cancelButton.click(function () {
                _this.tableModal.modal('hide');
            });
            _this.tableModal = Dolphin.modalWin({
                title : '编辑餐桌信息',
                content : _this.tableModalWin.show(),
                footer : footer,
                defaultHidden : true,
                width : '800px',
                init : function () {

                },
                show : function () {

                },
                hidden : function () {
                    _this.curEditData = null;
                    Dolphin.form.empty(_this.tableModalWin);
                }
            });
        })();
    };

    window.page = page.init();
});