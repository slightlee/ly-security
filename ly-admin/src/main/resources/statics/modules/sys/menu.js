var ptable=null,tableId='treeTable';

var define = layui.config({
    base : '../../../../statics/modules/extend-table/'
}).extend({
    treeGrid : 'treeGrid'
}).define(['table','treeGrid','form','layer'], function(exports) {
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,admin = layui.admin
        ,layer = layui.layer
        ,treeGrid = layui.treeGrid; //很重要;

        ptable=treeGrid.render({
            id:tableId
            ,elem: '#'+tableId
            ,idField:'id'
            ,url:'../../sys/menu/list'
            ,cellMinWidth: 100
            ,treeId:'menuId'//树形id字段名称
            ,treeUpId:'parentId'//树形父id字段名称
            ,treeShowName:'name'//以树形式显示的字段
            ,cols: [[
                {type:'checkbox', fixed: 'left'}
                ,{field:'name',width:200,align:'center',title: '菜单名称'}
                ,{title: '图标',toolbar: '#table-menu-icon',align:'center', minWidth: 80, width: 100}
                ,{title: '类型',toolbar: '#table-menu-type',align:'center', minWidth: 80, width: 100}
                ,{field:'menuId',width:100, align:'center',title: '排序'}
                ,{field:'url',width:250, align:'center',title: '路由'}
                ,{field:'',width:200, align:'center',title: '授权标识'}
                ,{title: '操作',align:'center', align:'center'
                    ,templet: function(d){
                        var html='';
                        var addBtn='<a class="layui-btn layui-btn-xs" lay-event="add"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
                        var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete">删除</a>';
                        return addBtn+delBtn;
                    }
                }
            ]]
            ,page:false
        });

        treeGrid.on('tool('+tableId+')',function (obj) {
            if(obj.event === 'del'){//删除行
                del(obj);
            }else if(obj.event==="add"){//添加行
                add(obj.data);
            }
        });

        // 设置菜单默认关闭


    //事件
    var active = {

        add: function(){
            layer.open({
                type: 2
                ,title: '添加'
                ,content: 'menuform.html'
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
                            url :  "../../sys/user/save"
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


exports('menu', {})
});