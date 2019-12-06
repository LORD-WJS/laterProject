<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/11/26
  Time: 16:23
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
    <title>管理员登录</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>前台</title>
    <!--引入css bootstrap -->
    <link rel="stylesheet" href="${path}/statics/boot/css/bootstrap.min.css">
    <!--引入jqgrid的css-->
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入 jquery -->
    <script src="${path}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <!--引入 jqgrid-->
    <script src="${path}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <!--引入 jqgrid 国际化-->
    <script src="${path}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!--引入 boot的js-->
    <script src="${path}/statics/boot/js/bootstrap.min.js"></script>
    <script>
        /* 验证码 */
        function changeCaptcha(){
        var captchaImg=document.getElementById("captchaImg");
        captchaImg.src="${path}/captcha/captcha?"+Math.random();
        }

        //js登录
        function login(){
            var username = $.trim($("#username").val());
            var password = $.trim($("#password").val());
            var captchaCode = $.trim($("#captchaCode").val());
            //alert(captchaCode)
            if(username == ""){
                $('#msg').text("请输入用户名");
                //alert("请输入用户名");
                return false;
            }else if(password == ""){
                $('#msg').text("请输入密码");
               // alert("请输入密码");
                return false;
            }
            //ajax去服务器端校验
            $.post('${path}/admin/login',{username:$('#username').val(),password:$('#password').val(),captchaCode:$('#captchaCode').val()},(result)=>{
                if (true){
                    window.location.href='${path}/back/index.jsp';
                }else{
                    $('#msg').text(result);
                }
            });
        }

    </script>


</head>
<body style=" background: url(${path}/statics/img/980186345982b2b7bcce9fcb3cadcbef76099b35.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">管理员登录</h4>
        </div>
        <form id="loginForm" method="post" >
            <div class="modal-body" id = "model-body">

                <div class="form-group">
                    <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username" id="username">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password" id="password">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="captchaCode" id="captchaCode">
                    <span style="color:red;font-size:18px">${param.errorMessage}</span>
                </div>
                <div class="form-group">
                    <img id="captchaImg" src="${path}/captcha/captcha" onclick="changeCaptcha()">
                    <a  onclick="changeCaptcha()" href="javascript:void(0)">换一张</a>
                </div>
                <span id="msg" style="color:red;"></span>
            </div>

                <div class="modal-footer">
                    <button type="reset" class="btn btn-default" data-dismiss="modal">重置
                    </button>
                    <button type="button" class="btn btn-primary" onclick="login()">
                        提交
                    </button>
                </div>
        </form>
    </div>
</div>
</body>
</html>
