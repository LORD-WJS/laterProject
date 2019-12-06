<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/11/27
  Time: 17:18
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
    <link rel="stylesheet" href="${path}/statics/kindeditor/themes/default/default.css">
    <script src="${path}/statics/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${path}/statics/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id',{
                width:'700px',
                // 1. 指定图片上传路径
                uploadJson:"${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${pageContext.request.contextPath}/article/showAllImgs",
            });
            <%--window.editor = K.create('#editor_id', {--%>
            <%--//这里是指定的文件上传input的的属性名--%>
            <%--    filePostName: "uploadFile",--%>
            <%--//这里就是指定文件上传的请求地址，上面也已经说了，upload_json.jsp就相当去一个servlet去进行保存文件，这个地方很重要--%>
            <%--    uploadJson: '${path}/statics/kindeditor/jsp/upload_json.jsp',--%>
            <%--    resizeType: 1,--%>
            <%--    allowPreviewEmoticons: true,--%>
            <%--    allowImageUpload: true,--%>
            <%--});--%>
        })
    </script>
</head>
<body>

<form method="post" action="">
    <textarea id="editor_id" name="content" style="width: 700px; height: 300px;">
        请输入......
    </textarea>
    <input type="submit" value="提交" onclick="submitForm()">
</form>







</body>
</html>
