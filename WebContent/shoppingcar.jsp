<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form action="foodservlet?action=search" method="post" class="form-inline text-center">
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
          <div class="panel-heading"><h3><img src="img/car.jpg" class="logo"/>购物车</h3></div>
          <div class="panel-body">
            <c:if test="${empty carinfo.foods}">购物车里还没有东西，赶快添加吧！</c:if>
            <c:if test="${not empty carinfo.foods}">
              <div id="car">
                <ul>
                  <li class="order-id">单品编号</li>
                  <li class="order-name">单品名称</li>
                  <li class="order-singleprice">单价</li>
                  <li class="order-amount">数量</li>
                  <li class="order-sumprice">合价</li>
                  <li class="order-more">备注</li>
                  <li class="order-do">操作</li>
                  <div class="clearfix"></div>
                </ul>
                <c:forEach var="p" items="${carinfo.foods}">
                  <ul>
                    <li  class="order-id">${p.id}</li>
                    <li  class="order-name"><img class="foodshow" src="image/${p.image}"><a href="" >${p.name}</a></li>
                    <li  class="order-singleprice">${p.price}</li>
                    <li  class="order-amount">
                      <div class="input-group input-num">
                        <input  onclick="addintocar(this)" id="${p.id}" type="button" value="+"/>
                        <input type="text" name="amount" value="${p.amount}" size="5" disabled/>
                        <input onclick="removefromcar(this)" id="${p.id}" type="button" value="-"/>
                      </div>
                    </li>
                    <li  class="order-sumprice">${p.totalprice}</li>
                    <li  class="order-more">${p.more}</li>
                    <li  class="order-do"><a class="btn btn-danger" href="car?action=delete&id=${p.id}">删除</a></li>
                    <div class="clearfix"></div>
                  </ul>
                </c:forEach>
              </div>
            </c:if>
          </div>
          <div class="panel-footer">
            <div class="text-right">
              <c:if test="${not empty carinfo.totalprice}" >
                <c:if test="${carinfo.totalprice != 0}" >
                <div class="bt">已选购<div class="tred">${carinfo.totalitems}</div>件,共<div class="tred">${carinfo.totalprice}</div>元</div>
                <div class="bt">
                  <a href="order?action=gotoadd" class="btn btn-danger">下单</a>
                </div>
                </c:if>
              </c:if>&nbsp;&nbsp;
                <div class="bt">
                    <a href="index.jsp" class="btn btn-danger">继续购物</a>
                </div>
            </div>
          </div>
        </div>
      </div>
  </body>
<script type="text/javascript">
  function addintocar(thisbutton) {
      window.location.href = "car?action=addintocar&id="+thisbutton.id;
  }
  function removefromcar(thisbutton) {
      window.location.href = "car?action=removefromcar&id="+thisbutton.id;
  }
</script>
</html>
