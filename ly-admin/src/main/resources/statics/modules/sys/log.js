/**
  * @Description: 日志列表
  * 
  * @Author MING
  * @Email  lmm_work@163.com
  * @Date   2019/1/22 10:06
　*/

layui.define(['table', 'form'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form
  ,admin = layui.admin;

  //用户管理
  table.render({
    elem: '#LAY-user-manage'
    ,url: '/sys/log/list'
    ,toolbar: true
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'username', title: '用户名', minWidth: 80, width: 100}
      ,{field: 'operation',title: '用户操作'}
      // ,{field: 'url',title: '请求URL'}
      ,{field: 'method',title: '请求方式'}
      ,{field: 'params',title: '请求参数'}
      ,{field: 'time',title: '请求时长',sort: true}
      ,{field: 'ip',title: 'IP地址'}
      // ,{field: 'userAgent',title: 'User_Agent'}
      ,{field: 'createDate',title: '创建时间',sort: true}
    ]]
    ,page: true
    ,limit: 10
    ,height: 'full-80'
    ,text: {
      none : '暂无数据'
    }
  });


    //监听搜索
    form.on('submit(LAY-user-front-search)', function(data){
        var field = data.field;

        //执行重载
        table.reload('LAY-user-manage', {
            where: field
        });
    });

  exports('useradmin', {})
});