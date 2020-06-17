/*
    配置模块
 */

layui.define(function (exports) {

    var configs ={
        base_server : 'http://127.0.0.1:8866/',
        tableName: 'LY',    // 存储表名
        autoRender: false,      // 窗口大小改变后是否自动重新渲染表格，解决layui数据表格非响应式的问题
        engine: '.html',        //视图文件后缀名
        pageTabs: true ,        //是否开启页 面选项卡功能。iframe版推荐开启
        debug: false            //是否开启调试模式。如开启，接口异常时会抛出异常 URL 等信息
    }


    exports('configs', configs);
});

