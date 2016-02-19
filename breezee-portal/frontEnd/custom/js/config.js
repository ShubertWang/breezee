(function($){
	Dolphin.systemConfig = {
		pageScript : "/page-script"
	};
    Dolphin.defaults.mockFlag = false;
    Dolphin.path.contextPath = "/";
    Dolphin.defaults.url.viewPrefix = "view";
    Dolphin.enum.setOptions({
        ajaxFlag :	true,
        enumUrl : '/data/sym/dict/code/{id}',
        cookieFlag : false
    });

})(jQuery);