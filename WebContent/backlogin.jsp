<%--
  Created by IntelliJ IDEA.
  User: chenyanbang
  Date: 2018/3/16
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>快点餐</title>
  <link rel="stylesheet"  href="css/bootstrap.css" type="text/css" >
  <link rel="stylesheet"  href="css/global.css" type="text/css" >
</head>
<body>
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<%@ include file="bar.jsp" %>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading"><h3><img src="img/car.jpg" class="logo"/>登陆</h3></div>
    <div class="panel-body">
      <div class="col-md-offset-2 col-md-4">
        <form action="user" method="post" class="form-horizontal">
          <c:if test="${not empty error}">
            <div class="form-group text-danger">
              <label class="control-label col-md-4">错误提示：</label>
              <div class="col-md-8">
                <label>${error}</label>
              </div>
            </div>
          </c:if>
          <div class="form-group">
            <label class="control-label col-md-4">用户名：</label>
            <div class="col-md-8">
              <input class="form-control" name="username" type="text"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-4">密码：</label>
            <div class="col-md-8">
              <input class="form-control" name="password" type="text"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-4">有效期：</label>
            <div class="col-md-8">
              <input class="form-inline" name="date" type="checkbox" value="7"/>&nbsp;&nbsp;7天免登陆
            </div>
          </div>
          <input type="hidden" name="action" value="backlogin"/>
          <div class="form-group">
            <label class="control-label col-md-4 sr-only">提交：</label>
            <div class="col-md-8">
              <input class="btn btn-danger" type="submit" value="登陆"/>
            </div>
          </div>
        </form>
      </div>
    </div>
      <div class="panel-footer">

      </div>

</div>
</div>
</body>
</html>
