/**
  * @Description:  login 页面 js
  *
  * @Author MING
  * @Email  lmm_work@163.com
  * @Date   2019/1/18 15:11
　*/
layui.extend({
    configs: 'config'
}).define(['form','configs'], function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,configs = layui.configs
  ,admin = layui.admin
  ,form = layui.form,
   $body = $('body');
  
  //自定义验证
  form.verify({
      username: function(value, item){ //value：表单的值、item：表单的DOM对象
      if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
        return '用户名不能有特殊字符';
      }
      if(/(^\_)|(\__)|(\_+$)/.test(value)){
        return '用户名首尾不能出现下划线\'_\'';
      }
      if(/^\d+\d+\d$/.test(value)){
        return '用户名不能全为数字';
      }
    }
    
    //我们既支持上述函数式的方式，也支持下述数组的形式
    //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
    ,password: [
      /^[\S]{6,12}$/
      ,'密码必须6到12位，且不能出现空格'
    ] 
  });
  
  
  //发送短信验证码
/*  admin.sendAuthCode({
    elem: '#LAY-user-getsmscode'
    ,elemPhone: '#LAY-user-login-cellphone'
    ,elemVercode: '#LAY-user-login-vercode'
    ,ajax: {
      url: layui.setter.base + 'json/user/sms.js' //实际使用请改成服务端真实接口
    }
  });*/

  
  //更换图形验证码
  $body.on('click', '#lay-vercode', function(){
    var othis = $(this);
    this.src = 'sys/captcha.jpg?t='+ new Date().getTime();
  });

  refresh();

  function refresh(){
      $("#lay-vercode").attr("src",'sys/captcha.jpg?t='+ new Date().getTime())
  }


    //提交
    form.on('submit(login-submit)', function(obj){

        var c = obj;
        //请求登入接口
        $.ajax({
            url: configs.base_server +'sys/login' //实际使用请改成服务端真实接口
            ,type:'post'
            ,data: obj.field
            ,success: function(data){
                // window.sessionStorage.username = obj.field.username;
                if(data.code == 0){
                    //登入成功的提示与跳转
                    layer.msg('登入成功', {
                        icon: 1
                        ,time: 1000
                    }, function(){
                        location.href = 'index.html'; //后台主页
                    });
                }else {
                    layer.msg(data.msg,{icon: 2});
                    refresh();
                }

            }
        });

    });

    $(document).keydown(function (e) {
        if (e.keyCode === 13) {
            $("#login-submit").trigger("click");
        }
    });

    //对外暴露的接口
  exports('login', {});
});