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

      <form action="#" method="post" class="form-inline text-center">
        <div class="container">
          <div class="form-group">
            <label class="control-label">站内搜索&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <input class="form-control" type="text" name="keywords" id="keywods"/>
          </div>
          <div class="form-group">
            <label class="control-label sr-only">搜索</label>
            <input class="form-control btn-primary" type="submit" name="keywords"/>
          </div>
        </div>
      </form>
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading"><h3><img src="img/car.jpg" class="logo"/>订单状态</h3></div>
          <div class="panel-body">
           <div id="process">
             <div class="row">
               <div class="ppp col-md-6 col-lg-offset-3"><p class="done">${error}<c:if test="${empty error}">你访问的页面找不到了</c:if></p></div>
             </div>
           </div>
          </div>
          <div class="panel-footer">

          </div>
        </div>
      </div>
  </body>
</html>
