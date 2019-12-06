<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/11/26
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.*,com.wjs.entity.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>前台</title>
    <!--引入css bootstrap -->
    <link rel="stylesheet" href="${path}/statics/boot/css/bootstrap.min.css">
    <!--引入jqgrid的css-->
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入 jquery -->
    <script src="${path}/statics/boot/js/jquery-3.4.1.min.js"></script>
    <!--引入 jqgrid-->
    <script src="${path}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <!--引入 jqgrid 国际化-->
    <script src="${path}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!--引入 boot的js-->
    <script src="${path}/statics/boot/js/bootstrap.min.js"></script>
    <script src="${path}/statics/boot/js/ajaxfileupload.js"></script>

    <%--引入富文本编辑器插件--%>
    <link rel="stylesheet" href="${path}/statics/kindeditor/themes/default/default.css">
    <script src="${path}/statics/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${path}/statics/kindeditor/lang/zh-CN.js"></script>
    <%--引入echarts插件--%>
    <script charset="UTF-8" src="${path}/statics/echarts/echarts.min.js"></script>
    <%--引入goeasy插件--%>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                width:'700px',
                // 1. 指定图片上传路径
                uploadJson:"${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${pageContext.request.contextPath}/article/showAllImgs",
                afterBlur:function () {
                    this.sync();
                }
            });
        });
        // 1. 获取上师信息 在表单回显
        function getMasterList(){

            $.get("${path}/master/searchAllMaster",function (data) {
                //var option = "<option value='0'>通用文章</option>";
                data.forEach(function (master) {
                    var option = "<option value='"+master.id+"'>"+master.nickName+"</option>"
                    // if (guru.id=="1"){
                    //     option += "<option selected value='"+master.id+"'>"+master.nickName+"</option>"
                    // }
                $("#masterList").append(option);
                });
            },"json");
        }

        // 2. 异步提交数据并上传文件
        function sub() {
            var id = $('#id').val();
            var name = $('#name').val();
            var status = $('#status').val();
            var masterId = $('#masterList').val();
            var content=$('#editor_id').val();
            var url;
            if(id==""){
                url="${pageContext.request.contextPath}/article/insertArticle";
            }else{
                url="${pageContext.request.contextPath}/article/changeArticle";
            }
            $.ajaxFileUpload({
                url:url,
                datatype:"json",
                type:"post",
                fileElementId:"inputfile",//上传文件标签的Id
                // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
                //                只支持 Json格式上传数据
                // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
                data:{id:id,name:name,masterId:masterId,status:status,content:content},
                success:function (data) {
                    alert("上传成功");
                    $("#table").trigger("reloadGrid");
                }
            })
        }
    </script>
</head>
<body>
<!-- KindEditor模态框 -->
<div class="modal fade" id="kind" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 750px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="kindfrm" method="post" class="form-horizontal">
                    <div class="form-group">
                        <input type="hidden" name="id" id="id" class="form-control">
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="name" id="name" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="inputfile">封面上传</label>
                        <div class="col-sm-5">
                            <input type="file" name="cover" id="inputfile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="name">所属上师</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="masterList" name="masterId" >

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="展示">通用</option>
                                <option value="不展示">非通用</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button"  class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" onclick="sub()"  class="btn btn-primary" data-dismiss="modal">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<%--导航栏--%>
<nav class="navbar navbar-default ">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><p>持名法舟后台管理系统 <small>v1.0</small></p></a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <span>欢迎:admin&nbsp;&nbsp;&nbsp;&nbsp;退出登录</span>
                <a href="">
                    <span class="glyphicon glyphicon-log-out" aria-hidden="true" style="margin-top: 18px;"></span>&nbsp;
                </a>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">


                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                <h3>用户管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('${path}/back/user.jsp');" id="btn" class="list-group-item ">
                                    用户信息管理
                                </a>
                                <%--                                <a href="javascript:$('#content').load('${path}/back/user/list.jsp?name=userAdd');" class="list-group-item">--%>
                                <a href="javascript:$('#content').load('${path}/back/echarts.jsp');" id="btn" class="list-group-item ">
                                    用户注册趋势
                                </a>
                                <a href="javascript:$('#content').load('${path}/back/map.jsp');" id="btn" class="list-group-item ">
                                    用户注册分布
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                <h3>轮播图管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${path}/back/banner.jsp');" class="list-group-item ">
                                轮播图信息
                            </a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                <h3>上师管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${path}/back/master.jsp');" class="list-group-item ">
                                上师信息
                            </a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                <h3>文章管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${path}/back/article.jsp');"  class="list-group-item ">
                                文章列表
                            </a>
                            <a href="javascript:$('#content').load('${path}/back/emp/list.jsp?name=userList');" class="list-group-item ">
                                文章搜索
                            </a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                <h3>专辑管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${path}/back/album.jsp');"  class="list-group-item ">
                                专辑信息
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div >
            <div class="col-xs-10" id="content">

            <div class="container-fluid">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                <h3>欢迎使用持名法舟后台管理系统！</h3>
                            </a>
                        </h4>
                    </div>
                </div>
            </div>
            <div id="myCarousel" class="carousel slide">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${path}/statics/img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg" alt="First slide">
                        <div class="carousel-caption">标题 1</div>
                    </div>
                    <div class="item">
                        <img src="${path}/statics/img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg" alt="Second slide">
                        <div class="carousel-caption">标题 2</div>
                    </div>
                    <div class="item">
                        <img src="${path}/statics/img/098ca7cad1c8a786b4e6a0366609c93d71cf5049.jpg" alt="Third slide">
                        <div class="carousel-caption">标题 3</div>
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h4 style="text-align: center">诸夏出品 1372339756@qq.com</h4>
</div>
</body>
</html>
