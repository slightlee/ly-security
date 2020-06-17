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

  //用户管理
  table.render({
    elem: '#LAY-table-manage'
    ,url: configs.base_server + '/sys/user/list'
    ,toolbar: true
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'userId', width: 100, title: '用户ID', sort: true}
      ,{field: 'username', title: '用户名', minWidth: 80, width: 100}
      ,{field: 'mobile', title: '手机号', minWidth: 80}
      ,{field: 'email', title: '邮箱', minWidth: 120}
      ,{field: 'createTime',title: '创建时间',sort: true}
      ,{title: '状态',toolbar: '#table-user-status', minWidth: 80, width: 100}
      ,{title: '操作', width: 160, align:'center', fixed: 'right', toolbar: '#table-user-admin'}
    ]]
    ,page: true
    ,limit: 10
    ,height: 'full-80'
    ,text: {
        none : '暂无数据'
      }
  });


    //监听搜索
    form.on('submit(LAY-table-search)', function(data){
        var field = data.field;

        //执行重载
        table.reload('LAY-table-manage', {
            where: field
        });
    });

    //  按钮开关状态修改
    form.on('switch(status_filter)',function (obj) {

        var field = {};
        field.userId = obj.elem.value;
        field.status = obj.elem.checked ? 1 : 0;

        if(field.userId == 1){
            layer.msg("管理员不能禁用",{icon: 2});
            table.reload('LAY-table-manage');
        }else{

            $.ajax({
                url : configs.base_server + "sys/user/update",
                type : "post",
                contentType: "application/json",
                data: JSON.stringify(field),
                success:function (data) {
                    console.log(data);
                    if(data.code == 0){
                        layer.msg("操作成功",{icon: 1});
                    }else{
                        layer.msg("操作失败",{icon: 2});
                    }
                    table.reload('LAY-table-manage');
                }
            });

        }


    });


    /* 角色列表 */
    $.ajax({
        url : configs.base_server + "sys/role/rolelist",
        type:'post',
        success:function (data) {
            var $html = "";
            if(data.roleList != null){
                $.each(data.roleList, function (index, item) {
                        $html += "<option value='" + item.roleId + "'>" + item.roleName + "</option>";
                });
                $("select[name='roleId']").append($html);

                //append后必须从新渲染
                form.render('select');
            }
        }
    })


    //事件
    var active = {
        del: function(){
            var checkStatus = table.checkStatus('LAY-table-manage')
                ,checkData = checkStatus.data //得到选中的数据
                ,userIds= [];   //  定义空数组 用来存放 多个id   var userId = new Array();
            for (var index in checkData){
                console.log(checkData[index].userId);
                userIds.push(checkData[index].userId)
            }
            var  aa = JSON.stringify(userIds);

            if(checkData.length === 0){
                return layer.msg('请选择数据');
            }
            layer.confirm('确定删除吗？', function() {

                $.ajax({
                    type: "POST",
                    url : configs.base_server + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(data){
                        if(data.code == 0){
                            layer.msg("删除成功",{icon: 1});

                        }else{
                            layer.msg(data.msg,{icon: 2});
                        }
                        table.reload('LAY-table-manage');
                    }
                });

            });
        }
        ,add: function(){
            layer.open({
                type: 2
                ,title: '添加用户'
                ,content: 'userform.html'
                ,maxmin: true
                ,area: ['600px', '500px']
                ,btn: ['确定', '取消']
                ,yes: function(index, layero){
                    var iframeWindow = window['layui-layer-iframe'+ index]
                        ,submitID = 'LAY-user-front-submit'
                        ,submit = layero.find('iframe').contents().find('#'+ submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit('+ submitID +')', function(data){

                        var field = data.field; //获取提交的字段
                        var status;
                        if('switch' in field){  // 包含该元素
                            status = 1 ;
                        }else{
                            status = 0;
                        }
                        field.status = status;
                        console.log(field);
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url :  configs.base_server + "sys/user/save"
                            ,contentType: "application/json"
                            ,type : 'post'
                            ,data : JSON.stringify(field)
                            ,success :function (data) {
                                if(data.code == 0){
                                    layer.msg("添加成功",{icon: 1});

                                }else{
                                    layer.msg(data.msg,{icon: 2});
                                }
                                table.reload('LAY-table-manage');
                            }
                        });
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
            });
        }
    };

    $('.layui-btn.ud-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


  //监听工具条
  table.on('tool(LAY-table-manage)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){

      var userIds = [];
      userIds.push(data.userId);

        $.ajax({
            type: "POST",
            url : configs.base_server + "sys/user/delete",
            contentType: "application/json",
            data: JSON.stringify(userIds),
            success: function(data){
                if(data.code == 0){
                    layer.msg("删除成功",{icon: 1});

                }else{
                    layer.msg(data.msg,{icon: 2});
                }
                table.reload('LAY-table-manage');
            }
        });

    } else if(obj.event === 'edit'){

       layer.open({
        type: 2
        ,title: '编辑用户'
        ,content: 'userform.html'
        ,maxmin: true
        ,area: ['600px', '500px']
        ,btn: ['确定', '取消']
        ,yes: function(index, layero){
          var iframeWindow = window['layui-layer-iframe'+ index]
          ,submitID = 'LAY-user-front-submit'
          ,submit = layero.find('iframe').contents().find('#'+ submitID);

          //监听提交
          iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
            var field = data.field;
            field.userId = obj.data.userId;
              var status;
              if('switch' in field){  // 包含该元素
                  status = 1 ;
              }else{
                  status = 0;
              }
              field.status = status;
              console.log(field);
            // 修改
            $.ajax({
                type: "POST",
                url : configs.base_server + "sys/user/update",
                contentType: "application/json",
                data: JSON.stringify(field),
                success: function(data){
                    if(data.code == 0){
                        layer.msg("修改成功",{icon: 1});

                    }else{
                        layer.msg(data.msg,{icon: 2});
                    }
                    table.reload('LAY-table-manage');
                }
            });
            layer.close(index); //关闭弹层
          });  
          
          submit.trigger('click');
        }
        ,success: function(layero, index){

        /*      二者等价
        var iframeWindow = window['layui-layer-iframe'+ index];
        var iframeWin = window[layero.find('iframe')[0]['name']];

         var div = layero.find('iframe').contents().find('#layuiadmin-form-useradmin');
         var body = layer.getChildFrame('body', index);
        */
          var div = layero.find('iframe').contents().find('#layuiadmin-form-useradmin');

            var userdata = obj.data;

           for(var key in userdata){
               var ac = userdata[key]
               div.find('#'+key +'').val(userdata[key]);
               if (key == 'status'&& userdata[key] == 1 ){
                   div.find('#'+key +'').attr("checked",true);
               }
           }

           $.ajax({
               url : configs.base_server + "sys/role/rolelistByUserId",
               type:'post',
               data:{userId:userdata.userId},
               success:function (data) {
                   selectrender(layero,data.roleIds[0]);
               }
           });


           }
      });
    }
  });

    function selectrender(layero,data_value){

        $.ajax({
            url : configs.base_server + "sys/role/rolelist",
            type:'post',
            success:function (data) {
                var $html = "";
                if(data.roleList != null){
                    $.each(data.roleList, function (index, item) {
                        $html += "<option value='" + item.roleId + "'>" + item.roleName + "</option>";
                    });

                    layero.find('iframe').contents().find('[name="roleId"]').append($html);

                    //append后必须从新渲染
                    form.render('select');
                }
                // 渲染子页面下拉框
                layero.find('iframe').contents().find('[name="roleId"]').val(data_value);
            }
        })

    }

  exports('useradmin', {})
});