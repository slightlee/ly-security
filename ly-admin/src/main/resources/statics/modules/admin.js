layui.define(function (exports) {

   var $ = layui.jquery,
       element = layui.element,
        device = layui.device,
        $body = $('body'),
        configs = layui.configs,
        mTab =layui.mTab,

       LEFT_MENU_TAB = 'leftMenuTab',
       TAB_TABS = '#tab_tabs>li',

    /*  方法 */
    admin = {

       //记录最近一次点击的页面标签数据
       tabPage: {},

        //全屏
       fullScreen: function(){
            var elem = document.documentElement
                ,reqFullScreen = elem.requestFullScreen || elem.webkitRequestFullScreen
                || elem.mozRequestFullScreen || elem.msRequestFullscreen;
            if(typeof reqFullScreen !== 'undefined' && reqFullScreen) {
                reqFullScreen.call(elem);
            };
       },
        //退出全屏
       exitScreen: function(){
            var elem = document.documentElement
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            }
        }

    }


   var cur_event = admin.event ={

       //刷新
       refresh: function(){

           if($(this).hasClass("refreshThis")){
               $(this).removeClass("refreshThis");
               $(".layui-tab-content .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload();
               setTimeout(function(){
                   $(".refresh").addClass("refreshThis");
               },2000)
           }else{
               layer.msg("您的点击速度过快，还稍等！！！");
           }

       },

       //关闭当前标签页
       closeCurrentPage: function(){
		
		 if(admin.tabPage.index != 111) {
             mTab.tabDelete(admin.tabPage.index);
         }
       },

       //关闭其它标签页
       closeOtherPage: function(){

           $.each($(".layui-tab-title li[lay-id]"), function () {
               //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
               if ($(this).attr("lay-id") != admin.tabPage.index && $(this).attr("lay-id") != 111) {
                   console.log("即将删除lay-id" + $(this).attr("lay-id"));
                   mTab.tabDelete($(this).attr("lay-id"));
               }
           });

       },

       //关闭全部标签页
     closeAllPage: function(){

         var lay_id = $(".layui-tab-title li"),
         ids = new Array();
         $.each(lay_id, function (i) {
             var ccc = $(this).attr("lay-id");
             if($(this).attr("lay-id") != 111){
                 ids[i] = $(this).attr("lay-id");
             }
         });
         mTab.tabDeleteAll(ids);

     },


     // 侧边伸缩
     flexible : function(cur_this){

           var   NAV_MINI = 'left-nav-mini',
               SPREAD_LEFT = 'layui-icon-spread-left',
               SPREAD_RIGHT = 'layui-icon-shrink-right',
                iconElem = cur_this.children("i"),
               leftNavExpand = $body.hasClass(NAV_MINI);

           if (! leftNavExpand ) {
               $body.addClass(NAV_MINI);
               iconElem.addClass(SPREAD_LEFT).removeClass(SPREAD_RIGHT);
           } else {
               $body.removeClass(NAV_MINI);
               iconElem.addClass(SPREAD_RIGHT).removeClass(SPREAD_LEFT);
           }
           $('.left-nav-mini .layui-side .layui-nav .layui-nav-item').hover(function () {
               var that = $(this),
                   text = that.children("a:eq(0)").find("span").text();
               if ($body.hasClass(NAV_MINI) && document.body.clientWidth > 750) {
                   layer.tips(text, this);
               }
           }, function () {
               layer.closeAll('tips');
           });
     },

       //全屏
       fullscreen: function(cur_this){
        var SCREEN_FULL = 'layui-icon-screen-full'
            ,SCREEN_RESTORE = 'layui-icon-screen-restore'
            ,iconElem = cur_this.children("i");

        if(iconElem.hasClass(SCREEN_FULL)){
            admin.fullScreen();
            iconElem.addClass(SCREEN_RESTORE).removeClass(SCREEN_FULL);
        } else {
            admin.exitScreen();
            iconElem.addClass(SCREEN_FULL).removeClass(SCREEN_RESTORE);
        }
    }

   }

    //监听 tab 组件切换，同步 index
    element.on('tab('+ LEFT_MENU_TAB +')', function(data){
        admin.tabPage.index = $(this).attr("lay-id");
    });


    /* 左侧导航点击 */
    element.on('nav(leftMenu)', function(elem){
        openTab(elem);
    });

    /* 头部导航点击 */
    element.on('nav(headMenu)', function(elem){
        openTab(elem);
    });

   function openTab(elem){
        var title = elem.attr('lay-title'),
            url =  elem.attr('lay-url'),
            id =  elem.attr('lay-id'),
            lay_id = $("li[lay-id="+id+"]").length;;

        if (lay_id > 0) {
            mTab.tabChange(id);
        } else if(id == "" || id == undefined) {
            return;
        }else {
            //打开新的tab项
            mTab.tabAdd(title,url,id);
            mTab.tabChange(id);
        }
    }


    /* layui-event 点击事件 */
    $body.on('click', '*[layui-event]', function(){
        var cur_this = $(this)
            ,attrEvent = cur_this.attr('layui-event');
        cur_event[attrEvent] && cur_event[attrEvent].call(this, cur_this);
    });


    /* 侧边栏 点击事件 */
    $(".layui-side-scroll").on("click", function () {
        if ($body.hasClass('left-nav-mini')) {
            $body.removeClass('left-nav-mini');
        }
    });


    exports('admin',admin);
});