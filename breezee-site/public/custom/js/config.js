(function($){
	Dolphin.systemConfig = {
		pageScript : "/page-script"
	};
    Dolphin.defaults.mockFlag = false;
    Dolphin.path.contextPath = REQUEST_MAP.contextPath;
    Dolphin.defaults.url.viewPrefix = REQUEST_MAP.viewPrefix;
    Dolphin.enum.setOptions({
        ajaxFlag :	true,
        enumUrl : '/data/dict/{id}',
        cookieFlag : false
    });

})(jQuery);