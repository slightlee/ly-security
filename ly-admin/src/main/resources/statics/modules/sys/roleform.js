/**
  * @Description:  js 仅供参考 移入 role js 里面
  * 
  * @Author MING
  * @Email  lmm_work@163.com
  * @Date   2019/1/22 10:06
　*/

layui.define(['table','form','configs','tree','util'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form
  ,configs = layui.configs
  ,tree = layui.tree
  ,util = layui.util;



    $.ajax({
        url: configs.base_server + 'sys/menu/list'
        ,type:'get'
        ,success:function (data) {
            var menulList = data.data;
           console.log(menulList);

           var newList = newMenuList(menulList);

           function newMenuList(arr) {

               let treeArr = arr;
               let temp = [];

                treeArr.forEach((item, index) => {

                    let newTreeArr = treeArr;

                    newTreeArr[index] = {};
                    newTreeArr[index].id = item.menuId;
                    newTreeArr[index].parentId = item.parentId;
                    newTreeArr[index].title = item.name;

                    temp.push(newTreeArr[index]);
                });

                return temp;
            }

            var authMenulList = arrayToTree(newList , 0);

            function arrayToTree(arr, parentId) {

                //  arr 是返回的数据  parendId 父id
                let temp = [];

                let treeArr = arr;

                treeArr.forEach((item, index) => {

                    if (item.parentId == parentId) {

                        if (arrayToTree(treeArr, treeArr[index].id).length > 0) {

                            // 递归调用此函数

                            treeArr[index].children = arrayToTree(treeArr, treeArr[index].id);

                        }
                        temp.push(treeArr[index]);
                    }

                });
                return temp;
            }
        //    console.log(arrayToTree(newList , 0));   // 第一级的父目录id是0；写死调用


            //无连接线风格
            tree.render({
                elem: '#menuTree'
                ,data: authMenulList
                ,id: 'treeId'
                ,showLine: false  //是否开启连接线
                ,showCheckbox: true
            });

            // 获取 roleId
            var roleId = $("#roleId").val();
            if(roleId != null || roleId != ""){
                $.ajax({
                    type: "POST",
                    url : configs.base_server + "sys/role/roloInfo",
                    data: {roleId:roleId},
                    success: function(data){
                        var menuList = data.sysRole.codes;
                        tree.setChecked('treeId', menuList); //勾选指定节点
                    }
                });
            }

        }

    });





      //模拟数据1
  /*   var data1 = [
         {
          title: '江西'
          ,id: 1
          ,children: [{
              title: '南昌'
              ,id: 1000
              ,children: [{
                  title: '青山湖区'
                  ,id: 10001
              },{
                  title: '高新区'
                  ,id: 10002
              }]
          },{
              title: '九江'
              ,id: 1001
          },{
              title: '赣州'
              ,id: 1002
          }]
         },{
          title: '广西'
          ,id: 2
          ,children: [{
              title: '南宁'
              ,id: 2000
          },{
              title: '桂林'
              ,id: 2001
          }]
      },{
          title: '陕西'
          ,id: 3
          ,children: [{
              title: '西安'
              ,id: 3000
          },{
              title: '延安'
              ,id: 3001
          }]
      }];*/

    //按钮事件
    util.event('lay-demo', {
        getChecked: function(othis){
            var checkedData = tree.getChecked('treeId'); //获取选中节点的数据

            //  layer.alert(JSON.stringify(checkedData), {shade:0});
            //  console.log(checkedData);
            var ids = getCheckedId(checkedData);
            console.log(ids);

            //code.push(ids);
            //console.log(code);

           function getCheckedId(jsonObj) {
                var id = "";
               let code = [];
                $.each(jsonObj, function (index, item) {
                    if (id != "") {
                        id = id + "," + item.id;
                    }
                    else {
                        id = item.id;
                    }
                    var i = getCheckedId(item.children);
                    if (i != "") {
                        id = id + "," + i;
                    }
                });
                return id;
            }
        }
        ,setChecked: function(){
            tree;
            tree.setChecked('treeId', [2]); //勾选指定节点
        }
        ,reload: function(){
            //重载实例
            tree.reload('treeId', {

            });

        }
    });

/*
    //无连接线风格
    tree.render({
        elem: '#test13'
        ,data: data1
        ,id: 'demoId1'
        ,showLine: false  //是否开启连接线
        ,showCheckbox: true
    });
*/


    form.on('submit(lay-user-submit)', function(data){

       var field = data.field;

        var checkedData = tree.getChecked('treeId'); //获取选中节点的数据
        var codes = getCheckedId(checkedData);
        console.log(codes);
        // 去除第一位 根节点
      //  codes.splice(0,1);
        field.codes = codes;

        function getCheckedId(jsonObj) {

            let code = [];

            $.each(jsonObj, function (index, item) {

                code.push(item.id);

               var newCode = getCheckedId(item.children);
               if(newCode.length > 0){
                   for (var i=0;i<newCode.length;i++){
                       code.push(newCode[i]);
                   }

               }
            });

            return code;
        }

    //    var faild = JSON.stringify(field);
        // 根据角色编号是否存在 判断 添加 还是修改
        if(field.roleId == null || field.roleId == ""){
            // 添加
            $.ajax({
                url :  configs.base_server + "sys/role/save"
                ,contentType: "application/json"
                ,type : 'post'
                ,data : JSON.stringify(field)
                ,success :function (data) {
                    if(data.code == 0){
                        layer.msg("添加成功",{icon: 1,time:1500},function () {
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                            parent.location.reload();//刷新
                        });
                    }else{
                        layer.msg(data.msg,{icon: 2});
                    }
                }
            });

        }else {
            // 修改
            $.ajax({
                url :  configs.base_server + "sys/role/update"
                ,contentType: "application/json"
                ,type : 'post'
                ,data : JSON.stringify(field)
                ,success :function (data) {
                    if(data.code == 0){
                        layer.msg("修改成功",{icon: 1,time:1500},function () {
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                            parent.location.reload();//刷新
                        });
                    }else{
                        layer.msg(data.msg,{icon: 2});
                    }
                }
            });
        }

    });


  exports('roleform', {})
});