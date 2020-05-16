/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "index", 'util', 'admin', "carousel", "laytpl", "echarts"], function (t) {
    var e = layui.$, i = layui.table, n = layui.form, $ = layui.$, util = layui.util, a = (layui.admin, layui.carousel), r = layui.element, d = layui.device();
    var layptl = layui.laytpl;
    var form = layui.form;
    var table = layui.table;
    var echarts = layui.echarts;


    // 对每个layadmin-carousel初始化一下
    $(".layadmin-carousel").each(function () {
        var t = $(this);
        a.render({
            elem: this,
            width: "100%",
            arrow: "none",
            interval: t.data("interval"),
            autoplay: t.data("autoplay") === !0,
            trigger: d.ios || d.android ? "click" : "hover",
            anim: t.data("anim")
        })
    }), r.render("progress");


    var t2 = [], i2 = [{
        title: {text: "任务完成统计", subtext: "来源: 系统服务器后台（测试数据）"},
        tooltip: {
            trigger: "axis",
            formatter: "真实姓名：{b} <br/>完成任务：{c} 个"
        },
        calculable: !0,
        xAxis: [{
            type: "category",
            data: ["黄继升", "卓别林","周星驰","周润发", "刘波"]
        }],
        yAxis: [{type: "value", axisLabel: {formatter: "{value} 个"}}],
        series: [{
            name: "共完成任务",
            type: "bar",
            data: [12, 22, 15, 6, 9],
            itemStyle:{
                //通常情况下：
                normal:{
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组中的颜色
                    label: {
                        distance: 0,
                        show: true,		//开启显示
                        position: 'top',	//在上方显示
                        textStyle: {	    //数值样式
                            color: 'black',
                            fontSize: 15
                        }
                    },
                    color: function (params){
                        var colorList = [
                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                            '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                        ];
                        return colorList[params.dataIndex];
                    },
                },
                //鼠标悬停时：
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    }], n2 = $("#LAY-index-normcol").children("div"), l2 = function (e) {
        t2[e] = echarts.init(n2[e], layui.echartsTheme), t2[e].setOption(i2[e]), window.onresize = t2[e].resize
    };

    if (n2[0]) {
        l2(0)
    }


    // 请求小组列表数据
    $.ajax({
        url: 'http://localhost:8080/teacher/subject/findAllMine',
        type: 'GET',
        contentType: 'application/json',
        data: {
            page: 0,
            limit: 0
        },
        success: function (res) {
            var data = {
                "subjectList": res.data.list
            }
            console.log("task小组请求成功")
            var getTpl = $("#selectMode").get(0).innerHTML;
            var view = $("#selectSubject").get(0);
            layptl(getTpl).render(data, function (html) {
                view.innerHTML = html;
            })
            form.render('select');

            if (data.subjectList.length !== 0) {
                $.ajax({
                    url: 'http://localhost:8080/teacher/Group/findSubjectGroup',
                    type: 'GET',
                    contentType: 'application/json',
                    data: {
                        subjectId: data.subjectList[0].subjectId,
                        page: 0,
                        limit: 0
                    },
                    success: function (res) {
                        var data2 = {
                            "groupList": res.data.list
                        }

                        console.log(data2.groupList)


                        console.log($("#selectMode2"))
                        var getTpl2 = $("#selectMode2").get(0).innerHTML;
                        var view2 = $("#selectGroup").get(0);
                        layptl(getTpl2).render(data2, function (html) {
                            view2.innerHTML = html;
                        })
                        console.log($("#selectMode2"))
                        form.render('select');
                        if (data2.groupList.length !== 0) {
                            i.render({
                                elem: "#LAY-app-content-list", //分页容器id
                                url: 'http://localhost:8080/teacher/process/taskStatistic',
                                where: {
                                    groupId: data2.groupList[0].gid
                                },
                                cols: [[{type: "checkbox", fixed: "left"},
                                    {field: "sid", width: 100, title: "ID", sort: !0, align: "center"},
                                    {field: "studentId", title: "学生学号", width: 100, align: "center"},
                                    {field: "name", title: "真实姓名", width: 100, align: "center"},
                                    {field: "avatar", title: "头像", width: 60, templet: "#imgTpl"},
                                    {field: "githubName", title: "github地址", width: 240, align: "center", templet: function (d) {
                                            return 'github.com/' + d.githubName
                                        }},
                                    {field: "count", title: "承担任务/issues数量", align: "center", sort: !0, templet: function (d) {
                                            return d.count + '个'
                                        }},
                                    {field: "finish", title: "完成任务/issues数量", align: "center", sort: !0, templet: function (d) {
                                            return d.finish + '个'
                                        }},
                                    {field: "percent", title: "任务占有率（%）", align: "center", sort: !0, templet: function (d) {
                                            return d.percent.toFixed(2) + '%'
                                        }},

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
                                        var l = window["layui-layer-iframe" + e], a = i.find("iframe").contents().find("#layuiadmin-app-form-edit");
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
                            });


                            $.ajax({
                                url: 'http://localhost:8080/teacher/process/taskStatistic',
                                type: 'GET',
                                contentType: 'application/json',
                                data: {
                                    groupId: data2.groupList[0].gid
                                },
                                success: function (res) {
                                    var array = [];
                                    var nameList = [];
                                    for (var i = 0; i < res.data.length; i++) {
                                        array.push(res.data[i].finish)
                                        nameList.push(res.data[i].name)
                                    }
                                    var option2 = {
                                        title: {text: "任务完成统计", subtext: "来源: 系统服务器后台（测试数据）"},
                                        tooltip: {
                                            trigger: "axis",
                                            formatter: "真实姓名：{b} <br/>完成任务：{c} 个"
                                        },
                                        calculable: !0,
                                        xAxis: [{
                                            type: "category",
                                            data: nameList
                                        }],
                                        yAxis: [{type: "value", axisLabel: {formatter: "{value} 个"}}],
                                        series: [{
                                            name: "共完成任务",
                                            type: "bar",
                                            data: array,
                                            itemStyle:{
                                                //通常情况下：
                                                normal:{
                                                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组中的颜色
                                                    label: {
                                                        distance: 0,
                                                        show: true,		//开启显示
                                                        position: 'top',	//在上方显示
                                                        textStyle: {	    //数值样式
                                                            color: 'black',
                                                            fontSize: 15
                                                        }
                                                    },
                                                    color: function (params){
                                                        var colorList = [
                                                            '#B5C334','#C1232B','#FCCE10','#E87C25','#27727B',
                                                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                                            '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                                        ];
                                                        return colorList[params.dataIndex];
                                                    },
                                                },
                                                //鼠标悬停时：
                                                emphasis: {
                                                    shadowBlur: 10,
                                                    shadowOffsetX: 0,
                                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                                }
                                            }
                                        }]
                                    };




                                    t2[0].setOption(option2, true);
                                    window.onresize = t2[0].resize;


                                    form.render();
                                }
                            });
                        }
                        else {
                            var getTpl2 = $("#selectMode2").get(0).innerHTML;
                            var view2 = $("#selectGroup").get(0);
                            layptl(getTpl2).render(data2, function (html) {
                                view2.innerHTML = html;
                            })
                        }
                    }
                })
            }
            form.render('select');
        }
    });


    form.on('select(chooseSubject)', function (option) {
        console.log(option.value);
        $.ajax({
            url: 'http://localhost:8080/teacher/Group/findSubjectGroup',
            type: 'GET',
            contentType: 'application/json',
            data: {
                subjectId: option.value,
                page: 0,
                limit: 0
            },
            success: function (res) {
                var data2 = {
                    "groupList": res.data.list
                }

                console.log(data2.groupList);

                // 先清空
                $("#selectGroup").empty();
                var option = $("<option>").val("").text("点击下拉选择");
                option.selected = true;
                $("#selectGroup").append(option)

                for (var i = 0; i < data2.groupList.length; i++) {
                    var option = $("<option>").val(data2.groupList[i].gid).text(data2.groupList[i].groupName);
                    $("#selectGroup").append(option)
                }

                form.render('select');
            }
        })
    })


    form.on('select(chooseGroup)', function (option) {
        console.log(option.value)
    })


    //监听按钮
    form.on('submit(LAY-app-contlist-search)', function(data){
        var $ = layui.$
        var field = data.field;
        console.log(field)
        var groupId = field.selectGroup;
        var index = layer.load(1);
        $.ajax({
            url: 'http://localhost:8080/teacher/process/taskStatistic',
            type: 'GET',
            contentType: 'application/json',
            data: {
                groupId: groupId
            },
            success: function (res) {
                layer.close(index);
                var array = [];
                var nameList = [];
                for (var i = 0; i < res.data.length; i++) {
                    array.push(res.data[i].finish)
                    nameList.push(res.data[i].name)
                }
                var option2 = {
                    title: {text: "任务完成统计", subtext: "来源: 系统服务器后台（测试数据）"},
                    tooltip: {
                        trigger: "axis",
                        formatter: "真实姓名：{b} <br/>完成任务：{c} 个"
                    },
                    calculable: !0,
                    xAxis: [{
                        type: "category",
                        data: nameList
                    }],
                    yAxis: [{type: "value", axisLabel: {formatter: "{value} 个"}}],
                    series: [{
                        name: "共完成任务",
                        type: "bar",
                        data: array,
                        itemStyle:{
                            //通常情况下：
                            normal:{
                                //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组中的颜色
                                label: {
                                    distance: 0,
                                    show: true,		//开启显示
                                    position: 'top',	//在上方显示
                                    textStyle: {	    //数值样式
                                        color: 'black',
                                        fontSize: 15
                                    }
                                },
                                color: function (params){
                                    var colorList = [
                                        '#B5C334','#C1232B','#FCCE10','#E87C25','#27727B',
                                        '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                        '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                    ];
                                    return colorList[params.dataIndex];
                                },
                            },
                            //鼠标悬停时：
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }]
                };




                t2[0].setOption(option2, true);
                window.onresize = t2[0].resize;
                form.render();

            }
        });

        //执行重载
        table.reload('LAY-app-content-list', {
            where: {
                groupId: groupId
            }
        });



    }), t("task", {})
});