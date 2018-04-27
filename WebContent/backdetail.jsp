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
  <%@ include file="backbar.jsp" %>
      <div class="container">
       <div class="panel panel-default">
         <div class="panel-heading"><h3>${food.name}</h3><div class="float-right"><button onclick="back()" type="button" class="btn btn-default right">返回</button>
         <button onclick="gotoedit()" type="button" class="btn btn-default right">编辑</button></div> </div>
         <div class="panel-body">
             <c:set var="p" value="${food}"></c:set>
                 <div>
                     <div class="thumbnail"><img src="image/${p.image}" alt="..."></div>
                     <div class="caption">
                         <h5><a href="foodservlet?action=detail&id=${p.id}">${p.name}</a> </h5>
                         <p><div class="price">${p.price}元</div><div class="price">销量:${p.salevolums}</div></p>
                     </div>
                 </div>
             <div class="container">
                 ${content}
                 <c:if test="${empty content}">
                     老板比较懒，没有写这些东西。
                 </c:if>
             </div>
         </div>
         <div class="panel-footer"></div>
       </div>
      </div>
  </body>
<script type="text/javascript">
    function back() {
        window.location.href = "foodservlet?action=showback";
    }
    function gotoedit() {
        window.location.href = "foodservlet?action=gotoedit&id=${p.id}";
    }

</script>
</html>
