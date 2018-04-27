<%--
  Created by IntelliJ IDEA.
  User: chenyanbang
  Date: 2018/3/19
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="order">
    <c:if test="${not empty datelist}">
        <div class="row">
            <c:forEach var="name" items="${incomedata}">
                <div class="col-md-3"><h3>${name.key}:<font color="red">${name.value}</font>元</h3></div>
            </c:forEach>
        </div>
        <div class="row">
            <div class="col-md-2 text-right">
                <p>按年： </p>
            </div>
            <div class="col-md-10 text-left">
                <p><c:forEach var="year" items="${datelist.years}"><a href="order?action=showall&date=${year}">${year}</a>&nbsp;&nbsp;</c:forEach> </p>
            </div>
        </div>
        <div  class="row">
            <div class="col-md-2 text-right">
                <p>按月：</p>
            </div>
            <div class="col-md-10 text-left">
                <p><c:forEach var="month" items="${datelist.months}"><a  href="order?action=showall&date=${month}">${month}</a>&nbsp;&nbsp;</c:forEach> </p>
            </div>
        </div>
        <div  class="row">
            <div class="col-md-2 text-right">
                <p>按天：</p>
            </div>
            <div class="col-md-10 text-left">
                <p><c:forEach var="day" items="${datelist.days}"><a href="order?action=showall&date=${day}">${day}</a>&nbsp;&nbsp;</c:forEach> </p>
            </div>
        </div>
        <div  class="row">
            <form action="order" method="post" class="form-inline">
            <div class="col-md-2 text-right">
                <p>单号：</p>
            </div>
                <input name="action" value="showall" type="hidden"/>
            <div class="col-md-10 text-left">
                <p><input class="form-control" type="text" name="orderid" /><input class="form-control" type="submit" value="查询"></p>
            </div>
            </form>
        </div>
        <div  class="row">
            <form action="order" method="post" class="form-inline">
                <div class="col-md-2 text-right">
                    <p>姓名：</p>
                </div>
                <input name="action" value="showall" type="hidden"/>
                <div class="col-md-10 text-left">
                    <p><input class="form-control" type="text" name="clientname" /><input class="form-control" type="submit" value="查询"></p>
                </div>
            </form>
        </div>
    </c:if>
    <c:if test="${empty orderlist}">
        <h4>没有订单。</h4>
    </c:if>
    <c:if test="${not empty orderlist}">
        <div>
            <c:forEach var="p" items="${orderlist}">
                <ul  class="bg-info order">
                    <li class="order-back-id">订单编号</li>
                    <li class="order-back-info">订餐信息</li>
                    <li class="order-back-time">下单时间</li>
                    <li class="order-back-amount">数量</li>
                    <li class="order-back-sumprice">总价</li>
                    <li class="order-back-more">状态</li>
                    <li class="order-back-more">操作</li>
                    <div class="clearfix"></div>
                </ul>
                <ul class="order">
                    <li  class="order-back-id">${p.id}</li>
                    <li  class="order-back-info">姓名：${p.client.name}<br/>住址：${p.client.address}<br/>电话：${p.client.tel}</li>
                    <li  class="order-back-time">${p.start}</li>
                    <li  class="order-back-amount">${p.totalitems}</li>
                    <li  class="order-back-sumprice">${p.price}</li>
                    <li  class="order-back-more">${p.status}</li>
                    <li  class="order-back-more">
                        <c:if test="${p.status=='已下单'}"><a class="btn btn-primary" href="order?action=take&id=${p.id}">接单</a></c:if>
                        <c:if test="${p.status=='等待送餐'}"><a class="btn btn-success" href="order?action=deliver&id=${p.id}">送餐</a></c:if>
                        <c:if test="${p.status=='等待取餐'}"><a class="btn btn-danger" href="order?action=over&id=${p.id}">结单</a></c:if>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <c:if test="${not empty p.foods}">
                    <ul class="food">
                        <li class="order-back-id">单品编号</li>
                        <li class="order-back-name">单品名称</li>
                        <li class="order-back-singleprice">单价</li>
                        <li class="order-back-amount">数量</li>
                        <li class="order-back-sumprice">合价</li>
                        <li class="order-back-more">备注</li>
                        <div class="clearfix"></div>
                    </ul>
                    <c:forEach var="food" items="${p.foods}">
                        <ul class="foods">
                            <li  class="order-back-id">${food.id}</li>
                            <li  class="order-back-name"><img class="foodshow" src="image/${food.image}"><a href="" >${food.name}</a></li>
                            <li  class="order-back-singleprice">${food.price}</li>
                            <li  class="order-back-amount">
                                    ${food.amount}
                            </li>
                            <li  class="order-back-sumprice">${food.totalprice}</li>
                            <li  class="order-back-more">${food.more}</li>
                            <div class="clearfix"></div>
                        </ul>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </div>
    </c:if>
</div>