function graphTrace(options, pid, pdid, panelId) {
    var _defaults = {
        srcEle: null,
        pid: pid,
        pdid: pdid
    };
    var opts = $.extend(true, _defaults, options);
    // 处理使用js跟踪当前节点坐标错乱问题 TODO jQuery已不支持live方法，暂改为bind，不过changeImg好像没有地方用到，测试后可以删除
    $('#changeImg').bind('click', function () {
        $('#workflowTraceDialog').dialog('close');
        if ($('#imgDialog').length > 0) {
            $('#imgDialog').remove();
        }
        $('<div/>', {
            'id': 'imgDialog',
            html: "<img src='" + REQUEST_MAP.contextPath + '/workflow/process/trace/auto/' + opts.pid + "' />"  //TODO 待确认图片内容
        }).appendTo('body').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95
        });
    });
    var groupOffset = {
        supervisal: {left: -102, top: -41, percent: 0.93, offset: -96},
        process_pool1: {left: -82, top: -62},
        prcs_register: {left: -71, top: -81},
        prcs_supervision: {left: -34, top: -33}
    };

    // 获取图片资源
    var imageUrl = "http://127.0.0.1:10249/services/workflowTrace/processGraph/" + opts.pid;     //TODO 获取图片路径
    Dolphin.ajax({
        url: '/data/bpm/workflowTrace/traceProcess/' + opts.pid,
        type: Dolphin.requestMethod.GET,
        onSuccess: function (infos) {
            var positionHtml = "";
            var workflowTraceDialog = null;
            if ($('#workflowTraceDialog').length == 0) {
                workflowTraceDialog = $('<div/>', {
                    id: 'workflowTraceDialog',
                    html: "<div><img src='" + imageUrl + "' style='position:absolute;' onload='workflowTraceResetHeight(this,\"" + panelId + "\")' />" +
                    "<div id='processImageBorder'>" +
                        //positionHtml +
                    "</div>" +
                    "</div>"
                }).appendTo('#' + panelId);
            } else {
                $('#workflowTraceDialog img').attr('src', imageUrl);
                $('#workflowTraceDialog #processImageBorder').html(positionHtml);
            }
            // 生成图片
            var workflowTraceBodrder = workflowTraceDialog.find("#processImageBorder");
            var varsArray = new Array();
            $.each(infos, function (i, v) {
                if (v) {
                    var $positionDiv = $('<div/>', {
                        'class': 'activity-attr'
                    }).css({
                        position: 'absolute',
                        left: (v.x + (groupOffset[pdid.split(":")[0]] ? groupOffset[pdid.split(":")[0]].left : -34)),
                        top: (v.y + (groupOffset[pdid.split(":")[0]] ? groupOffset[pdid.split(":")[0]].top : -33)),
                        width: (v.width - 2),
                        height: (v.height - 2),
                        backgroundColor: 'black',
                        opacity: 0,
                        zIndex: 800
                    });

                    // 节点边框
                    var $border = $('<div/>', {
                        'class': 'activity-attr-border'
                    }).css({
                        position: 'absolute',
                        left: (v.x + (groupOffset[pdid.split(":")[0]] ? groupOffset[pdid.split(":")[0]].left : -34)),
                        top: (v.y + (groupOffset[pdid.split(":")[0]] ? groupOffset[pdid.split(":")[0]].top : -33)),
                        width: (v.width - 4),
                        height: (v.height - 3),
                        zIndex: 799
                    });

                    // 子流程点击事件
                    if (v.subProSize && v.subProSize > 0) {
                        $positionDiv.css('cursor', 'pointer');
                        $positionDiv.attr('onclick', 'showSubPro(' + v.processInstanceId + ', "' + panelId + '", "' + pdid.split(":")[0] + '")');
                    }
                    workflowTraceBodrder.append($positionDiv).append($border);
                    //positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
                    varsArray[varsArray.length] = v.vars;
                }
            });
            // 设置每个节点的data
            $('#workflowTraceDialog .activity-attr').each(function (i, v) {
                $(this).data('vars', varsArray[i]);
            });
            $('#workflowTraceDialog').css('padding', '0.2em');
            $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);
        }
    });
}

function showSubPro(processInstanceId, parentId, prcsName) {
    var url = REQUEST_MAP.contextPath + '/content/workflow/subTaskInfo.jsp?processInstanceId=' + processInstanceId + '&parentId=' + parentId;
    //if(prcsName == 'prcs_register'){
    if (true) {
        url += "&buttonFlag=true";
    }
    var subProId = processInstanceId + "sub";
    //if(prcsName == 'prcs_register'){
    if (true) {
        if ($("#" + subProId).length == 0) {
            var subPro = $('<div id="' + subProId + '">').css({
                display: "none",
                position: "relative"
            }).addClass("content").load(url);
            $("#" + parentId).after(subPro);
        } else {
            //$("#"+subProId).load(url);
        }

        $("#" + parentId).hide();
        $("#" + subProId).show();
    } else {
        if ($("#" + subProId).length == 0) {
            $('<div id="' + subProId + '" class="modal hide fade" >').css({
                width: "900px",
                height: "500px",
                marginLeft: "-450px"
            }).load(url).modal();
        } else {
            $("#" + subProId).load(url).modal();
        }
    }
}

function workflowTraceResetHeight(thisObj, panelId) {
    $("#" + panelId).height(thisObj.height || 465);
}
