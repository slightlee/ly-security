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
    ,url: configs.base_server + 'sys/role/list'
    ,toolbar: true
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'roleName', title: '角色名称', minWidth: 100}
      ,{field: 'remark', title: '备注', minWidth: 100}
      ,{field: 'createTime',title: '创建时间',sort: true}
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

    //事件
    var active = {
        del: function(){
            var checkStatus = table.checkStatus('LAY-table-manage')
                ,checkData = checkStatus.data //得到选中的数据
                ,roleIds= [];   //  定义空数组 用来存放 多个id   var userId = new Array();
            for (var index in checkData){
                console.log(checkData[index].roleId);
                roleIds.push(checkData[index].roleId)
            }
            var  aa = JSON.stringify(roleIds);

            if(checkData.length === 0){
                return layer.msg('请选择数据');
            }
            layer.confirm('确定删除吗？', function() {

                $.ajax({
                    type: "POST",
                    url : configs.base_server + "sys/role/delete",
                    contentType: "application/json",
                    data: JSON.stringify(roleIds),
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
                ,title: '添加角色'
                ,content: 'roleform.html'
                ,maxmin: true
                ,area: ['600px', '500px']
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

      var roleIds = [];
      roleIds.push(data.roleId);

        $.ajax({
            type: "POST",
            url : configs.base_server + "sys/role/delete",
            contentType: "application/json",
            data: JSON.stringify(roleIds),
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
        ,title: '编辑角色'
        ,content: 'roleform.html'
        ,maxmin: true
        ,area: ['600px', '500px']
        ,success: function(layero, index){

          var div = layero.find('iframe').contents().find('#layuiadmin-form-useradmin');

          var data = obj.data;
          div.find('#roleName').val(data.roleName);
          div.find('#remark').val(data.remark);
          div.find('#roleId').val(data.roleId);

        }
      });
    }
  });

  exports('role', {})
});