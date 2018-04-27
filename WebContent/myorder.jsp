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
<%@include file="bar.jsp"%>
<c:set var="id" value="${userid}" scope="session"></c:set>
<c:if test="${empty id}">
  <div class="container">
    <form action="order" method="post" class="form-inline">
      <input type="hidden" name="action" value="show"/>
      <div class="form-group">
        <label class="control-label">输入订单编号：</label>
        <input type="text" name="orderid" class="form-control">
      </div>
      <div class="form-group">
        <label class="control-label">输入手机号：</label>
        <input type="text" name="clienttel" class="form-control">
      </div>
      <div class="form-group">
        <label class="control-label sr-only">提交：</label>
        <input type="submit" class="btn btn-default">
      </div>
    </form>
  </div>
</c:if>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading"><h3><img src="img/car.jpg" class="logo"/>购物车</h3></div>
    <div class="panel-body">
      <c:if test="${empty orderlist}">
        <h4>没有订单。</h4>
      </c:if>
      <c:if test="${not empty orderlist}">
      <div id="car">
        <ul>
          <li class="order-id">订单编号</li>
          <li class="order-name">订餐信息</li>
          <li class="order-singleprice">下单时间</li>
          <li class="order-amount">数量</li>
          <li class="order-sumprice">总价</li>
          <li class="order-more">状态</li>
          <div class="clearfix"></div>
        </ul>
        <c:forEach var="p" items="${orderlist}">
          <ul class="bg-info">
            <li  class="order-id">${p.id}</li>
            <li  class="order-name">姓名：${p.client.name}<br/>住址：${p.client.address}<br/>电话：${p.client.tel}</li>
            <li  class="order-singleprice">${p.start}</li>
            <li  class="order-amount">${p.totalitems}</li>
            <li  class="order-sumprice">${p.price}</li>
            <li  class="order-more"><p>${p.status}</p>
              <p><c:if test="${p.status=='已下单'}">
              <a href="order?action=cancel&id=${p.id}">取消订单</a>
            </c:if></p>
            </li>
            <div class="clearfix"></div>
          </ul>
          <ul>
            <li class="order-id"> </li>
            <li class="order-id">单品编号</li>
            <li class="order-name">单品名称</li>
            <li class="order-singleprice">单价</li>
            <li class="order-amount">数量</li>
            <li class="order-sumprice">合价</li>
            <li class="order-more">备注</li>
            <div class="clearfix"></div>
          </ul>
          <c:forEach var="food" items="${p.foods}">
            <ul>
              <li  class="order-id">&nbsp;</li>
              <li  class="order-id">${food.id}</li>
              <li  class="order-name"><img class="foodshow" src="image/${food.image}"><a href="" >${food.name}</a></li>
              <li  class="order-singleprice">${food.price}</li>
              <li  class="order-amount">
                <div class="input-group input-num">
                  <input type="text" name="amount" value="${food.amount}" size="5" disabled/>
                </div>
              </li>
              <li  class="order-sumprice">${food.totalprice}</li>
              <li  class="order-more">${food.more}</li>
              <div class="clearfix"></div>
            </ul>
          </c:forEach>
        </c:forEach>
      </div>
      </c:if>
    </div>
    <div class="panel-footer"></div>
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
