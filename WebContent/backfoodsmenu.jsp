<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chenyanbang
  Date: 2018/3/19
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-12">
    <a href="foodservlet?action=showback" class="btn btn-default"><span class="glyphicon glyphicon-arrow-down">全部</span></a>&nbsp;&nbsp;
    <a href="foodservlet?action=showback&param=insale"  class="btn btn-default"><span class="glyphicon glyphicon-arrow-down">在售</span></a>&nbsp;&nbsp;
    <a href="foodservlet?action=showback&param=notinsale"  class="btn btn-default"><span class="glyphicon glyphicon-arrow-down">已下架</span></a>
</div>
<div id="foods">
    <ul>
        <c:forEach var="p" items="${list}">
            <li>
                <a href="foodservlet?action=backdetail&id=${p.id}"><img src="image/${p.image}" alt="..."></a>
                <div class="caption">
                    <h5><a href="foodservlet?action=backdetail&id=${p.id}">${p.name}</a>&nbsp;&nbsp;<font color="blue">${p.status}</font></h5>
                    <p><div class="price">${p.price}元</div><div class="price">销量:${p.salevolums}</div></p>
                    <p>
                        <c:if test="${p.status=='在售'}"><a href="foodservlet?action=downfoods&id=${p.id}" class="btn btn-danger" role="button">下架</a></c:if>
                        <c:if test="${p.status=='已下架'}"><a href="foodservlet?action=upfoods&id=${p.id}" class="btn btn-success" role="button">上架</a></c:if>
                        <a href="car?action=addintocar&id=${p.id}" class="btn btn-primary" role="button">加入购物车</a>
                        &nbsp;
                        <a href="foodservlet?action=backdetail&id=${p.id}" class="btn btn-success" role="button">详情</a></p>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
