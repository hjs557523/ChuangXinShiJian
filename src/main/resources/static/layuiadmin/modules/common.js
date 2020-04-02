/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */

//退出系统代码
;layui.define(function (e) {
    var i = (layui.$, layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin);
    i.events.logout = function () {
        i.req({
            url: "http://localhost:8080/logout", type: "get", done: function (e) {
                i.exit(function () {
                    layer.msg('退出成功!', {
                        offset: '15px'
                        , icon: 1
                        , time: 1500
                    }, function () {
                        location.href = '/login.html'; //后台主页
                        layui.data(setter.tableName, {
                            key: setter.request.tokenName
                            ,value: null
                        });
                    });
                    //location.href = "./login.html"
                })
            }
        })
    }, e("common", {})
});