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
        <a href="foodservlet?action=orderbysalecolums" class="btn btn-default"><span class="glyphicon glyphicon-arrow-down"></span>按销量</a>
        <a href="foodservlet?action=orderbypriceasc" class="btn btn-default"><span class="glyphicon glyphicon-arrow-up"></span>价格升序</a>
        <a href="foodservlet?action=orderbypricenotasc" class="btn btn-default"><span class="glyphicon glyphicon-arrow-down"></span>价格降序</a>
      </div>
      <div class="container">
        <div id="foods">
          <ul>
            <c:forEach var="p" items="${list}">
              <li>
                <img src="image/${p.image}" alt="...">
                <div class="caption">
                  <h5><a href="foodservlet?action=detail&id=${p.id}">${p.name}</a> </h5>
                  <p><div class="price">${p.price}元</div><div class="price">销量:${p.salevolums}</div></p>
                  <p><a href="car?action=addintocar&id=${p.id}" class="btn btn-primary" role="button">加入购物车</a>&nbsp;<a href="foodservlet?action=detail&id=${p.id}" class="btn btn-success" role="button">详情</a></p>
                </div>
              </li>
            </c:forEach>
          </ul>
        </div>

      </div>
  </body>
</html>
