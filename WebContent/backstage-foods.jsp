<%--
  Created by IntelliJ IDEA.
  User: chenyanbang
  Date: 2018/3/19
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>快点餐后台</title>
    <link rel="stylesheet"  href="css/bootstrap.css" type="text/css" >
    <link rel="stylesheet"  href="css/bootstrap-theme.css" type="text/css" >
    <link rel="stylesheet"  href="css/global.css" type="text/css" >
</head>
<body>
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<%@ include file="backbar.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">后台管理</div>
        <div class="panel-body">
            <div class="col-md-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <%@include file="backstagemenu.jsp"%>
                    </div>
                </div>
            </div>
            <div class="col-md-10">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <%@include file="backfoodsmenu.jsp"%>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-footer">
            <%@include file="copyright.jsp"%>
        </div>
    </div>
</div>
</body>
</html>
