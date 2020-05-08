/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "index", 'util'], function (t) {
    var e = layui.$, i = layui.table, n = layui.form, $ = layui.$, util = layui.util
    i.render({
        elem: "#LAY-app-content-list", //分页容器id
        url: 'http://localhost:8080/teacher/weekPaper/getWeekPaper',
        cols: [[{type: "checkbox", fixed: "left"},
            // {field: "uuid", width: 100, title: "UUID", sort: !0, align: "center", templet: function (d) {
            //         return d.weekPaper.uuid
            //     }},
            {field: "studentId", title: "学号", width: 100, align: "center", templet: function (d) {
                    return d.student.studentId
                }},
            {field: "name", title: "姓名", width: 100, align: "center", templet: function (d) {
                    return d.student.name
                }},
            {field: "avatar", title: "头像", width: 60, templet: "#imgTpl"},

            {field: "createTime", title: "上传时间", width: 160, align: "center", templet: function (d) {
                    return util.toDateString(d.weekPaper.createTime, "yyyy-MM-dd HH:mm:ss");
                }},

            {field: "thisWeekWork", title: "本周工作", align: "center", templet: function (d) {
                    return d.weekPaper.thisWeekWork
                }},
            {field: "nextWeekWork", title: "下周计划", align: "center", templet: function (d) {
                    return d.weekPaper.nextWeekWork
                }},
            // {field: "file", title: "附件存储地址", align: "center", templet: function (d) {
            //         return d.weekPaper.file || ''
            //     }},
            {field: "selected", title: "周报附件下载", templet: "#buttonTpl", width: 130, align: "center"},
            ]],
        page: !0,
        limit: 10,
        text: "对不起，加载出现异常！"
    }), i.on("tool(LAY-app-content-list)", function (t) {
        var data = t.data;
        "del" === t.event ? layer.confirm("确定删除此课题？", function (e) {
            $.ajax({
                url: 'http://localhost:8080/teacher/subject/delete',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    subjectId: data.subjectId,
                },
                success: function (res) {
                    layui.table.reload('LAY-app-content-list'); //重载表格
                    layer.close(e);
                }
            });
        }) : "edit" === t.event && layer.open({
            type: 2,
            title: "编辑课题",
            content: "../../../templates/app/content/listform.html",
            maxmin: !0,
            area: ["550px", "450px"],
            btn: ["确定", "取消"],
            yes: function (e, i) {
                var l = window["layui-layer-iframe" + e],
                    a = i.find("iframe").contents().find("#layuiadmin-app-form-edit");
                l.layui.form.on("submit(layuiadmin-app-form-edit)", function (i) {
                    var l = i.field;

                    $.ajax({
                        url: 'http://localhost:8080/teacher/subject/update',
                        type: 'GET',
                        contentType: 'application/json',
                        data: {
                            subjectId: t.data.subject.subjectId,
                            subjectName: l.subjectName,
                            subjectDetail: l.subjectDetail,
                            remark: l.remark
                        },
                        success: function (res) {
                            layui.table.reload('LAY-app-content-list'); //重载表格
                            layer.close(e);
                        }
                    });


                    // n.render(), layer.close(e)


                }), a.trigger("click")
            },

            success: function (t, e) {
                var n = t.find("iframe").contents().find("#layuiadmin-app-form-list").click();
                n.find('input[name="subjectName"]').val(data.subjectName);
                n.find('textarea[name="subjectDetail"]').val(data.subjectDetail);
                n.find('textarea[name="remark"]').val(data.remark);
            }
        })

        if ("download" === t.event) {
            console.log(data)
            window.location.href="/teacher/weekPaper/downLoadexFile?uuid=" + data.weekPaper.uuid
        }
    }), i.render({
        elem: "#LAY-app-content-tags",
        url: layui.setter.base + "json/content/tags.js",
        cols: [[{type: "numbers", fixed: "left"}, {field: "id", width: 100, title: "ID", sort: !0}, {
            field: "tags",
            title: "分类名",
            minWidth: 100
        }, {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#layuiadmin-app-cont-tagsbar"}]],
        text: "对不起，加载出现异常！"
    }), i.on("tool(LAY-app-content-tags)", function (t) {
        var i = t.data;
        console.log(i)
        if ("del" === t.event) layer.confirm("确定删除此分类？", function (e) {
            t.del(), layer.close(e)
        }); else if ("edit" === t.event) {
            e(t.tr);
            layer.open({
                type: 2,
                title: "编辑分类",
                content: "../../../templates/app/content/tagsform.html?subjectId=" + i.subjectId,
                area: ["450px", "200px"],
                btn: ["确定", "取消"],
                yes: function (e, i) {
                    var n = i.find("iframe").contents().find("#layuiadmin-app-form-tags"),
                        l = n.find('input[name="tags"]').val();
                    l.replace(/\s/g, "") && (t.update({tags: l}), layer.close(e))
                },
                success: function (t, e) {
                    var n = t.find("iframe").contents().find("#layuiadmin-app-form-tags").click();
                    n.find('input[name="tags"]').val(i.tags)
                    console.log(i.tags)
                }
            })
        }
    }), i.render({
        elem: "#LAY-app-content-comm",
        url: layui.setter.base + "json/content/comment.js",
        cols: [[{type: "checkbox", fixed: "left"}, {
            field: "id",
            width: 100,
            title: "ID",
            sort: !0
        }, {field: "reviewers", title: "评论者", minWidth: 100}, {
            field: "content",
            title: "评论内容",
            minWidth: 100
        }, {field: "commtime", title: "评论时间", minWidth: 100, sort: !0}, {
            title: "操作",
            width: 150,
            align: "center",
            fixed: "right",
            toolbar: "#table-content-com"
        }]],
        page: !0,
        limit: 10,
        limits: [10, 15, 20, 25, 30],
        text: "对不起，加载出现异常！"
    }), i.on("tool(LAY-app-content-comm)", function (t) {
        t.data;
        "del" === t.event ? layer.confirm("确定删除此条评论？", function (e) {
            t.del(), layer.close(e)
        }) : "edit" === t.event && layer.open({
            type: 2,
            title: "编辑评论",
            content: "../../../views/app/content/contform.html",
            area: ["450px", "300px"],
            btn: ["确定", "取消"],
            yes: function (t, e) {
                var n = window["layui-layer-iframe" + t], l = "layuiadmin-app-comm-submit",
                    a = e.find("iframe").contents().find("#" + l);
                n.layui.form.on("submit(" + l + ")", function (e) {
                    e.field;
                    i.reload("LAY-app-content-comm"), layer.close(t)
                }), a.trigger("click")
            },
            success: function (t, e) {
            }
        })
    }), t("weekpaper", {})
});