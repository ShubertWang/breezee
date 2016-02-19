/**
 * Created by Shubert.Wang on 2016/1/27.
 */
$(function () {
    Dolphin.form.parse();

    var enableOptions = $('#enableOptions'),
        disableOptions = $('#disableOptions'),
        deleteOptions = $('#deleteOptions'),
        infoPanel = $('#infoPanel'),
        updateDict = $('#updateDict'),
        deleteDict = $('#deleteDict');

    var list = new Dolphin.LIST({
        panel : "#list",
        title : '枚举列表',
        columns: [{
            code: 'code',
            title: '枚举编码'
        }, {
            code: 'name',
            title: '枚举名称'
        }],
        multiple : false,
        ajaxType:'post',
        url : '/data/sym/dict/page',
        paginationSimpleFlag : true,
        onLoadSuccess : function (data) {
            Dolphin.toggleEnable(updateDict, false);
            Dolphin.toggleEnable(deleteDict, false);
        },
        onChecked : function (data) {
            Dolphin.form.setValue(data, '#formPanel');
            optionList.loadData({rows : data.detailInfos, total : data.detailInfos.length});

            infoPanel.show();

            Dolphin.toggleEnable(updateDict, true);
            Dolphin.toggleEnable(deleteDict, true);
        }
    });

    var optionList = new Dolphin.LIST({
        panel : "#optionList",
        columns: [{
            code: 'code',
            title: '枚举项编码'
        }, {
            code: 'name',
            title: '枚举项名称'
        }, {
            code : 'status',
            title : '状态'
        }],
        data : {rows : [], total : 0},
        pagination : false,
        onCheck : function (data) {
            var checkData = this.getChecked();
            if(checkData.length > 0){
                var enableFlag = true, disableFlag = true,
                    i;
                for(i = 0; i < checkData.length; i++){
                    if(checkData[i].status == 1){
                        enableFlag = false;
                    }else{
                        disableFlag = false;
                    }

                    if(!enableFlag && !disableFlag){
                        break;
                    }
                }
                Dolphin.toggleEnable(enableOptions, enableFlag);
                Dolphin.toggleEnable(disableOptions, disableFlag);
                Dolphin.toggleEnable(deleteOptions, true);
            }else{
                Dolphin.toggleEnable(enableOptions, false);
                Dolphin.toggleEnable(disableOptions, false);
                Dolphin.toggleEnable(deleteOptions, false);
            }
        }
    });

    var editOptionList = new Dolphin.LIST({
        panel : "#editOptionList",
        columns: [{
            code: 'code',
            title: '枚举项编码'
        }, {
            code: 'name',
            title: '枚举项名称'
        }],
        editFlag : true,
        data : {rows : [], total : 0},
        pagination : false,
        rowIndex : false,
        checkbox : false,
        onCheck : function (data) {
            var checkData = this.getChecked();
            if(checkData.length > 0){
                Dolphin.toggleEnable(deleteOptions, true);
            }else{
                Dolphin.toggleEnable(enableOptions, false);
            }
        }
    });

    //============================================================== event
    $('#insert').click(function () {
        Dolphin.form.empty('#editForm');
        editOptionList.loadData({rows : [], total : 0});
        editWin.modal('show');
    });
    updateDict.click(function () {
        var data = list.getChecked();
        Dolphin.form.empty('#editForm');
        Dolphin.form.setValue(data[0], '#editForm');
        editOptionList.loadData({rows : data[0].children, total : data[0].children.length});

        editWin.modal('show');
    });

    //==================================================================== modal win
    var editWin;
    (function () {
        var footer = $('<div>'), confirmButton, cancelButton;

        confirmButton = $('<button type="button" class="btn btn-primary btn-small">').html('保存').appendTo(footer);
        confirmButton.click(function () {
            var formData = Dolphin.form.getValue(editWin);
            formData.creator = 'admin';
            formData.updator = 'admin';
            var detail = formData.tableName;
            formData.detailInfos = detail;
            delete formData.tableName;
            Dolphin.ajax({
                url : '/data/sym/dict/',
                type : Dolphin.requestMethod.PUT,
                data : Dolphin.json2string(formData),
                loading : true,
                onSuccess : function (reData) {
                    editWin.modal('hide');
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback : function () {
                            infoPanel.hide();
                            list.reload();
                        }
                    })
                }
            });
        });
        cancelButton = $('<button type="button" class="btn btn-default btn-small" >').html('取消').appendTo(footer);
        cancelButton.click(function () {
            editWin.modal('hide');
        });
        editWin = Dolphin.modalWin({
            title : '枚举维护',
            content : $('#editWin').show(),
            footer : footer,
            defaultHidden : true,
            init : function () {

            },
            show : function () {

            }
        });
    })();
    $("#query").click(function () {
        list.query(Dolphin.form2json("queryForm"));
    });
    $("#conditionReset").click(function () {
        Dolphin.form.empty("#queryForm")
    });
});