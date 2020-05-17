/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "index", 'util', 'admin', "carousel", "laytpl", "echarts"], function (t) {
    var e = layui.$, i = layui.table, n = layui.form, $ = layui.$, util = layui.util, a = (layui.admin, layui.carousel),
        r = layui.element, d = layui.device();
    var layptl = layui.laytpl;
    var form = layui.form;
    var table = layui.table;
    var echarts = layui.echarts;


    // 对每个layadmin-carousel初始化一下
    // $(".layadmin-carousel").each(function () {
    //     var t = $(this);
    //     a.render({
    //         elem: this,
    //         width: "100%",
    //         arrow: "none",
    //         interval: t.data("interval"),
    //         autoplay: t.data("autoplay") === !0,
    //         trigger: d.ios || d.android ? "click" : "hover",
    //         anim: t.data("anim")
    //     })
    // }), r.render("progress");

    function formatDate(now) {
        var year=now.getFullYear();  //取得4位数的年份
        var month=(now.getMonth()+1 < 10 ? '0' + (now.getMonth() + 1) : date.getMonth() + 1);  //取得日期中的月份，其中0表示1月，11表示12月
        var date=now.getDate();      //返回日期月份中的天数（1到31）
        var hour=now.getHours() < 10 ? '0' + now.getHours() : now.getHours();     //返回日期中的小时数（0到23）
        var minute=now.getMinutes() < 10 ? '0' + now.getMinutes() : now.getMinutes(); //返回日期中的分钟数（0到59）
        var second=now.getSeconds() < 10 ? '0' + now.getSeconds() : now.getSeconds(); //返回日期中的秒数（0到59）
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
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

                            // $.ajax({
                            //     url: 'http://localhost:8080/teacher/process/taskStatistic',
                            //     type: 'GET',
                            //     contentType: 'application/json',
                            //     data: {
                            //         groupId: data2.groupList[0].gid
                            //     },
                            //     success: function (res) {
                            //         var array = [];
                            //         var nameList = [];
                            //         for (var i = 0; i < res.data.length; i++) {
                            //             array.push(res.data[i].finish)
                            //             nameList.push(res.data[i].name)
                            //         }
                            //         var option2 = {
                            //             title: {text: "任务完成统计", subtext: "来源: 系统服务器后台（测试数据）"},
                            //             tooltip: {
                            //                 trigger: "axis",
                            //                 formatter: "真实姓名：{b} <br/>完成任务：{c} 个"
                            //             },
                            //             calculable: !0,
                            //             xAxis: [{
                            //                 type: "category",
                            //                 data: nameList
                            //             }],
                            //             yAxis: [{type: "value", axisLabel: {formatter: "{value} 个"}}],
                            //             series: [{
                            //                 name: "共完成任务",
                            //                 type: "bar",
                            //                 data: array,
                            //                 itemStyle: {
                            //                     //通常情况下：
                            //                     normal: {
                            //                         //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组中的颜色
                            //                         label: {
                            //                             distance: 0,
                            //                             show: true,		//开启显示
                            //                             position: 'top',	//在上方显示
                            //                             textStyle: {	    //数值样式
                            //                                 color: 'black',
                            //                                 fontSize: 15
                            //                             }
                            //                         },
                            //                         color: function (params) {
                            //                             var colorList = [
                            //                                 '#B5C334', '#C1232B', '#FCCE10', '#E87C25', '#27727B',
                            //                                 '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                            //                                 '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                            //                             ];
                            //                             return colorList[params.dataIndex];
                            //                         },
                            //                     },
                            //                     //鼠标悬停时：
                            //                     emphasis: {
                            //                         shadowBlur: 10,
                            //                         shadowOffsetX: 0,
                            //                         shadowColor: 'rgba(0, 0, 0, 0.5)'
                            //                     }
                            //                 }
                            //             }]
                            //         };
                            //
                            //
                            //         t2[0].setOption(option2, true);
                            //         window.onresize = t2[0].resize;
                            //
                            //
                            //         form.render();
                            //     }
                            // });
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
    form.on('submit(LAY-app-contlist-search)', function (data) {
        var $ = layui.$
        var field = data.field;
        console.log(field)
        var groupId = field.selectGroup;
        var index = layer.load(1);
        $.ajax({
            url: 'http://localhost:8080/teacher/process/getAllProcessInfoByGid',
            type: 'GET',
            contentType: 'application/json',
            data: {
                groupId: groupId
            },
            success: function (res) {
                layer.close(index);
                console.log(res.data)
                console.log($("#dyn"))
                // 先清空
                $("#dyn").empty();
                console.log("成功")
                var addHTML = "";
                for (var i = 0; i < res.data.length; i++) {
                    var executors = "";
                    for (var t = 0; t < res.data[i].executorList.length; t++) {
                        executors += res.data[i].executorList[t].name + "  ";
                    }

                    var finish = "";
                    if (res.data[i].process.processStatus === 0) {
                        finish = "&nbsp&nbsp&nbsp&nbsp&nbsp<span class=\"layui-badge layui-bg-gray\">进行中</span>\n";
                    }
                    else if (res.data[i].process.processStatus === 1) {
                        finish = "&nbsp&nbsp&nbsp&nbsp&nbsp<span class=\"layui-badge layui-bg-lightgreen\">已完成</span>\n";
                    }
                    else {
                        finish = "&nbsp&nbsp&nbsp&nbsp&nbsp<span class=\"layui-badge layui-bg-red2\">已过期</span>\n";
                    }

                    addHTML +=
                        "<li class=\"layui-timeline-item\">\n" +
                        "                  <i class=\"layui-icon layui-timeline-axis\"></i>\n" +
                        "                  <div class=\"layui-timeline-content layui-text\">\n" +
                        "                    <h3 class=\"layui-timeline-title\">" + "任务时间点：" +formatDate(new Date(res.data[i].process.createTime)) +"</h3>\n" +
                        "                    <ul>\n" +
                        "                      <li><p><span class=\"layui-badge layui-bg-orange\">任务名称</span><span style=\"font-size: 13px; font-weight: bold\"\">&nbsp&nbsp&nbsp" + res.data[i].process.processTitle + "</span>" + finish +
                        "                      </p></li>\n" +
                        "                      \n" +
                        "                      <li><p><span class=\"layui-badge layui-bg-cyan\">创建人员</span><span style=\"font-size: 13px; font-weight: bold\"\">&nbsp&nbsp&nbsp" + res.data[i].publisher.name + "</span>\n" +
                        "                      </p></li>\n" +
                        "                      \n" +
                        "                      <li><p><span class=\"layui-badge layui-bg-blue\">任务说明</span><span style=\"font-size: 13px; font-weight: bold\"\">&nbsp&nbsp&nbsp" + res.data[i].process.processDetail + "</span>\n" +
                        "                      </p></li>\n" +
                        "                      \n" +
                        "                      <li><p><span class=\"layui-badge\">截止时间</span><span style=\"font-size: 13px; font-weight: bold\"\">&nbsp&nbsp&nbsp" + formatDate(new Date(res.data[i].process.endTime)) + "</span>\n" +
                        "                      </p></li>\n" +
                        "                      \n" +
                        "                      <li><p><span class=\"layui-badge layui-bg-green\">分配成员</span><span style=\"font-size: 13px; font-weight: bold\"\">&nbsp&nbsp&nbsp" + executors + "</span>\n" +
                        "                      </p></li>\n" +
                        "                    </ul>\n" +
                        "                  </div>\n" +
                        "                </li>"
                }
                $("#dyn").append(addHTML);

            }
        });




    }), t("time", {})
});