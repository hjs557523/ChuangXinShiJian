/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */
;layui.define(function (e) {
    layui.use(["admin", "carousel", "laytpl", 'form', 'table', 'echarts', "index", 'util'], function () {
        var $ = layui.$, a = (layui.admin, layui.carousel), r = layui.element, d = layui.device();
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
        }), r.render("progress")


        var t = [],  n = $("#LAY-index-normline").children("div"), i = [{
            title: {text: "每周代码统计", subtext: "来源: GitHub API v3 (测试数据)"},
            tooltip: {
                trigger: "axis"
            },
            legend: {data: ["黄继升", "卓别林","周星驰","周润发"]},
            calculable: !0,
            xAxis: [{type: "category", boundaryGap: !1, data: ["第1周", "第2周", "第3周", "第4周", "第5周", "第6周", "第7周"]}],
            yAxis: [{type: "value", axisLabel: {formatter: "{value} 行"}}],
            series: [{
                name: "周润发",
                type: "line",
                stack: "总量",
                data: [110, 110, 150, 130, 120, 130, 100],
            }, {
                name: "黄继升",
                type: "line",
                stack: "总量",
                data: [10, 20, 20, 50, 30, 20, 0],
            }, {
                name: "周星驰",
                type: "line",
                stack: "总量",
                data: [10, 26, 50, 51, 30, 20, 10],
            }, {
                name: "卓别林",
                type: "line",
                stack: "总量",
                data: [10, 0, 10, 0, 0, 2, 10],
            }, {
                name: "刘波",
                type: "line",
                stack: "总量",
                data: [10, 0, 10, 30, 10, 2, 10],
            }]
        }],  l = function (e) {
            console.log("caocaofa")
            t[e] = echarts.init(n[e], layui.echartsTheme), t[e].setOption(i[e]), window.onresize = t[e].resize
        };

        if (n[0]) {
            l(0)
        }


        var t2 = [], i2 = [{
            title: {text: "代码总量统计", subtext: "来源: GitHub API v3 (测试数据)"},
            tooltip: {
                trigger: "axis",
                formatter: "真实姓名：{b} <br/>编码总量：{c} 行"
            },
            calculable: !0,
            xAxis: [{
                type: "category",
                data: ["黄继升", "卓别林","周星驰","周润发", "刘波"]
            }],
            yAxis: [{type: "value", axisLabel: {formatter: "{value} 行"}}],
            series: [{
                name: "共编写代码",
                type: "bar",
                data: [666, 888, 1688, 4399, 2345],
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


        var t3 = [], i3 = [{
            title: {text: "小组成员仓库Commit次数统计", x: "center", textStyle: {fontSize: 20}, subtext: "来源: GitHub API v3 (测试数据)"},
            tooltip: {trigger: "item", formatter: "{a} <br/>{b} : {c} ({d}%)"},
            legend: {
                orient: "vertical",
                x: "left",
                data: ["卓别林", "周润发", "周星驰", "刘波", "黄继升"]
            },
            series: [{
                name: "GitHub API V3",
                type: "pie",
                radius: "55%",
                center: ["50%", "50%"],
                data: [
                    {value: 30, name: "卓别林"},
                    {value: 20, name: "周润发"},
                    {value: 32, name: "周星驰"},
                    {value: 53, name: "刘波"},
                    {value: 60, name: "黄继升"}]
            }]
        }], n3 = $("#LAY-index-dataview").children("div"), l3 = function (e) {
            t3[e] = echarts.init(n3[e], layui.echartsTheme), t3[e].setOption(i3[e]), window.onresize = t3[e].resize
        };
        if (n3[0]) {
            l3(0);
        }


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
            var field = data.field;
            console.log(field)
            var groupId = field.selectGroup;
            var index = layer.load(1);
            $.ajax({
                url: 'http://localhost:8080/teacher/process/commitStatistic',
                type: 'GET',
                contentType: 'application/json',
                data: {
                    groupId: groupId
                },
                success: function (res) {
                    layer.close(index);
                    console.log(res.data);
                    var nameList = [];
                    var weekNameList = [];
                    var commitList = [];
                    var series = [];
                    var total = [];
                    for (var i = 0; i < res.data.length; i++) {
                        nameList.push(res.data[i].realName);
                        series.push({
                            name: res.data[i].realName,
                            type: "line",
                            stack: "总量",
                            data: res.data[i].weekCodeNum,
                        })
                        total.push(res.data[i].totalCodeNum)
                        commitList.push({
                            value: res.data[i].totalCommit,
                            name: res.data[i].realName
                        })
                    }


                    if (res.data.length > 0) {
                        for (var i = 0; i < res.data[0].weekCodeNum.length; i++) {
                            weekNameList.push('第' + (i + 1) + '周');
                        }
                    }


                    var option = {
                        title: {text: "每周代码统计", subtext: "来源: GitHub API v3 (当前最新数据)"},
                        tooltip: {trigger: "axis"},
                        legend: {data: nameList},
                        calculable: !0,
                        xAxis: [{type: "category", boundaryGap: !1, data: weekNameList}],
                        yAxis: [{type: "value", axisLabel: {formatter: "{value} 行"}}],
                        series: series
                    };

                    var option2 = {
                        title: {text: "代码总量统计", subtext: "来源: GitHub API v3 (当前最新数据)"},
                        tooltip: {
                            trigger: "axis",
                            formatter: "真实姓名：{b} <br/>编码总量：{c} 行"
                        },
                        calculable: !0,
                        xAxis: [{
                            type: "category",
                            data: nameList
                        }],
                        yAxis: [{type: "value", axisLabel: {formatter: "{value} 行"}}],
                        series: [{
                            name: "共编写代码",
                            type: "bar",
                            data: total,
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

                    var option3 = {
                        title: {text: "小组成员仓库Commit次数统计", x: "center", textStyle: {fontSize: 20}, subtext: "来源: GitHub API v3 (当前最新数据)"},
                        tooltip: {trigger: "item", formatter: "{a} <br/>{b} : {c} ({d}%)"},
                        legend: {
                            orient: "vertical",
                            x: "left",
                            data: nameList
                        },
                        series: [{
                            name: "GitHub API V3",
                            type: "pie",
                            radius: "55%",
                            center: ["50%", "50%"],
                            data: commitList
                        }]
                    };

                    t[0].setOption(option, true);
                    t2[0].setOption(option2, true);
                    t3[0].setOption(option3, true);

                    window.onresize = t[0].resize;
                    window.onresize = t2[0].resize;
                    window.onresize = t3[0].resize;

                    form.render();
                },

                error: function(e) {
                    layer.close(index);
                    layer.alert('GitHub API请求失败，请重新请求', {
                        icon: 2,
                        time: 2000
                    });
                }
            })


        });



        // console.log("caocaofa2")
        // t[0] = echarts.init(n[0], layui.echartsTheme);
        // t[0].setOption(option);
        // window.onresize = t[0].resize



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
                var getTpl = $("#selectMode").get(0).innerHTML;
                var view = $("#selectSubject").get(0);
                layptl(getTpl).render(data, function (html) {
                    view.innerHTML = html;
                })

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

                            var getTpl2 = $("#selectMode2").get(0).innerHTML;
                            var view2 = $("#selectGroup").get(0);
                            layptl(getTpl2).render(data2, function (html) {
                                view2.innerHTML = html;
                            })
                            form.render('select');

                        }
                    })
                }
                form.render('select');
            }
        })



    }), e("senior", {})
});