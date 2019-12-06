<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%--引入echarts插件--%>
    <script>

    </script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1年","30天","7天","1天"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显
    <%--$.get("${pageContext.request.contextPath}/statics/json/data.json",function (data) {--%>
    $.post("${pageContext.request.contextPath}/user/searchAllUser",function (data) {
        myChart.setOption({
            series:[
                {
                    name: '男',
                    type: 'bar',
                    data: [data.mans365,data.mans3183,data.mans30,data.mans7],
                },{
                    name: '女',
                    type: 'bar',
                    data: [data.womans365,data.womans183,data.womans30,data.womans7],

                }
            ]
        })
    },"json");

    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BS-6d9d03f533f34de9acf5c557aae0d433", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            // 手动将 字符串类型转换为 Json类型
            //var data = JSON.parse(message.content);
            //alert("Channel:" + message.channel + " content:" + message.content);
            $.post("${pageContext.request.contextPath}/user/searchAllUser",function (data) {
                myChart.setOption({
                     series:[
                        {
                            name: '男',
                            type: 'bar',
                            data: [data.mans365,data.mans3183,data.mans30,data.mans7],
                        },{
                            name: '女',
                            type: 'bar',
                            data: [data.womans365,data.womans183,data.womans30,data.womans7],

                        }
                    ]
                })
            },"json")
        }
    });

</script>