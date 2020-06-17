/**
  * @Description:  管理员管理
  * 
  * @Author MING
  * @Email  lmm_work@163.com
  * @Date   2019/1/22 10:06
　*/

layui.define(['table','form','configs'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form
  ,configs = layui.configs
  ,admin = layui.admin;



    // 用户信息
    var userInfo = layui.data('userInfo');
    $("#userId").val(userInfo.data[0].userId);
    $("#username").val(userInfo.data[0].username);
    $("#mobile").val(userInfo.data[0].mobile);
    $("#email").val(userInfo.data[0].email);


    // 修改用户信息
    form.on('submit(lay-user-submit)', function(data){

        var userId = $("#userId").val();
        data.field.userId = userId;


        $.ajax({
            type: "POST",
            url : configs.base_server + "sys/user/updateInfo",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function(data){
                if(data.code == 0){
                    layer.msg("修改成功",{icon: 1});

                }else{
                    layer.msg(data.msg,{icon: 2});
                }

            }
        });

    });



  exports('userinfo', {})
});