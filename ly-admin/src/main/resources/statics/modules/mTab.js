

layui.define(['element'],function (exports) {

    var element = layui.element,
        $ = layui.jquery,

        LEFT_MENU_TAB = 'leftMenuTab',

     mTab ={

        tabAdd: function (title,url, id) {

            element.tabAdd(LEFT_MENU_TAB, {
                title: title
                ,content:' <iframe src=" '+url+'"></iframe>'   //支持传入html
                ,id: id         //'选项卡标题的lay-id属性值'
            });

        },
         //切换到指定Tab项
        tabChange:function (id) {
             element.tabChange(LEFT_MENU_TAB, id);
        },
         //删除指定Tab项
        tabDelete:function(id){
            element.tabDelete(LEFT_MENU_TAB, id);
        },
         //删除所有
        tabDeleteAll: function (ids) {
             $.each(ids, function (i) {
                var id =  ids[i];
                 element.tabDelete(LEFT_MENU_TAB, id);
             })
        }
    }

    exports('mTab',mTab)

});