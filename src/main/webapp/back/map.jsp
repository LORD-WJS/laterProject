<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 引入 echarts.js -->
    <script src="${path}/statics/echarts/echarts.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${path}/statics/echarts/china.js" charset="UTF-8"></script>
    <script src="${path}/statics/boot/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-96f06067e18b4e4f83fec4a0315833f1", //替换为您的应用appkey
        });
        goEasy.publish({
            channel: "cmfz", //替换为您自己的channel
            message: "用户范围" //替换为您想要发送的消息内容
        });
    </script>
    <script type="text/javascript">
        $(function () {

                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('userMap'));
                var option = {
                    title: {
                        text: '用户分布图',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['用户','男', '女']
                    },
                    visualMap: {
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: []
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            $.post("${path}/user/searchLocationRange",function(data){
                myChart.setOption({
                    series:[
                            {
                            name: '用户',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data
                        }
                    ]
                })
            });

            //使用goeasy及时获取更新后的图表数据
            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BS-6d9d03f533f34de9acf5c557aae0d433", //替换为您的应用appkey
            });
            goEasy.subscribe({
                channel: "cmfz", //替换为您自己的channel
                onMessage: function (message) {
                    $.post("${path}/user/searchLocationRange",function(data){
                        myChart.setOption({
                            series:[
                                {
                                    name: '用户',
                                    type: 'map',
                                    mapType: 'china',
                                    roam: false,
                                    label: {
                                        normal: {
                                            show: false
                                        },
                                        emphasis: {
                                            show: true
                                        }
                                    },
                                    data: data
                                }
                            ]
                        })
                    });
                }
            });


            });



    </script>
</head>

<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap" style="width: 600px;height:400px;"></div>

</body>
</html>