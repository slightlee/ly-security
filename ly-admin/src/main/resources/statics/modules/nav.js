/**
 @Name： nav 导航菜单
 @Author：MING
 @Eamil lmm_work@163.com
 @Date: 2018/11/22 15:42
 */

layui.define(function (exports) {
    var admin = layui.admin
        ,element = layui.element
        ,$ = layui.$,
        configs = layui.configs,

      nav ={

          getNav : function(menuType){

              $.ajax({
                  url: configs.base_server + 'sys/menu/nav'
                  ,type:'get'
                  ,data:{ menuType : menuType}
                  ,success:function (data) {
                      console.log(data);
                      // 获取菜单列表
                      var menuList = data.menuList;
                      // 遍历菜单
                      var navlist = "";

                      for (var i = 0;i < menuList.length;i++){

                          navlist += '<li class="layui-nav-item">';
                          navlist += '<a href="javascript:;">';
                          navlist += ' <svg class="icon" aria-hidden="true">'+
                              ' <use xlink:href="'+ menuList[i].icon +'"></use></svg>  <span>'+menuList[i].name+'</span></a>';

                          // 判断是否有二级菜单
                          var ccc = menuList[i].list;
                          if(menuList[i].list != undefined && menuList[i].list != null && menuList[i].list.length > 0){
                              navlist += '<dl class="layui-nav-child">';

                              for(var j = 0;j < menuList[i].list.length ; j++){
                                  navlist += '<dd><a href="javascript:;"  lay-id="'+ menuList[i].list[j].menuId +'" lay-title ="'+menuList[i].list[j].name+'" lay-url="'+menuList[i].list[j].url+'">'+
                                      '<svg class="icon" aria-hidden="true"><use xlink:href="'+ menuList[i].list[j].icon +'"></use></svg>  '+ menuList[i].list[j].name+'</a></dd>';
                              }

                              navlist += '</dl>';
                          }
                          navlist += '</li>';
                      }

                    // $("#leftMenu").html(navlist);
                      $("#leftMenu").append(navlist);
                      element.init();  //初始化页面元素
                  }
              });

          }
      }

     // 获取当前登录用户信息
     $.ajax({
         url: configs.base_server + 'sys/user/info'
         , type: 'get'
         , data: {}
         , success: function (res) {
             $("#user").html(res.user.username);
             layui.data('userInfo', {
                 key: 'data',value: [
                     {userId: res.user.userId,username: res.user.username,mobile: res.user.mobile,email:res.user.email}
                 ]
             });
         }

     });


    //对外暴露的接口
    exports('nav',nav);
});


