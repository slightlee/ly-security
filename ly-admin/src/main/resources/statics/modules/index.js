
layui.extend({
    configs: 'config',
    admin : 'admin',
    mTab : 'mTab',
    nav : 'nav'
}).define(['configs','mTab','layer','element','admin','nav'],function(exports){

    var configs = layui.configs,
        mTab =layui.mTab,
        element =layui.element,
        admin = layui.admin,
        $ = layui.jquery,
        nav = layui.nav,
        layer = layui.layer;


    // 页面加载 显示菜单
    nav.getNav(0);

/*    $("#openConsole").on('click',function () {
        nav.getNav(0);
        admin.closeAllPage;
    });*/

    // $("#openArt").on('click',function () {
    //     nav.getNav(1);
    //     admin.closeAllPage;
    // });



    /* 点击清除缓存 */
    $("#clearCache").on('mouseenter',function () {
       var title = $(this).attr('title');
        this.index =  layer.tips(title,this,{
           tips:[3, '#000000']
       })
    }).on('mouseleave', function () {
        layer.close(this.index);
    }).on("click", function () {
        window.sessionStorage.removeItem("");   // ?????  key 值
        layer.msg("缓存清除成功！");
    });

    /* 开启导航栏B，展示B的子导航栏，然后自动关闭导航栏A */
    $('.layui-nav-item').click(function(){
        $(this).siblings('li').attr('class','layui-nav-item');
    })


   /* $.ajax({
        url: 'http://127.0.0.1:8866/app/login'
        , type: 'post'
        ,contentType: "application/json"
        ,data: JSON.stringify({"mobile": "test","password": "test"})
        , success: function (data) {

            alert(data);
        }
    });*/


    exports('index', {});
});

