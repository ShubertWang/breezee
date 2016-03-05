/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

$(function () {
    menu.select('undoList');

    var procsInsId = REQUEST_MAP.data.procsInsId;
    $("#traceChart").attr("src","http://127.0.0.1:6789/services/workflowTrace/chart/order_process");
});