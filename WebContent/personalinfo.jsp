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
<c:set var="id" value="${userid}" scope="session"></c:set>
<div class="container">
    <div class="col-md-6">
        <div class="panel panel-default">
            <div class="panel-heading"><h3><img src="img/car.jpg" class="logo"/>填写送餐信息</h3></div>
            <div class="panel-body">
                <c:if test="${empty id}">
                    <div class="col-md-offset-1 col-md-8">
                        <form action="order" method="post" class="form-horizontal">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group">
                                <label class="control-label col-md-4">姓名：</label>
                                <div class="col-md-8">
                                    <input class="form-control" name="name" type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">电话：</label>
                                <div class="col-md-8">
                                    <input class="form-control" name="tel" type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">住址：</label>
                                <div class="col-md-8">
                                    <input class="form-control" name="address" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">送餐时间：</label>
                                <div class="col-md-8">
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="30"/> 下单后：30分钟
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="45"/> 下单后：45分钟
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="60"/> 下单后：60分钟
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="90"/> 下单后：90分钟
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="120"/> 下单后：120分钟
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">期望口味：</label>
                                <div class="col-md-8">
                                    <input class="form-control" name="taste" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4 sr-only">提交：</label>
                                <div class="col-md-8">
                                    <input class="btn btn-danger" type="submit" value="保存"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:if>
                <c:if test="${not empty id}">
                    <div id="chooseclient">
                        <form action="order" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-md-3">选择取餐人:</label>
                                <div class="col-md-9">
                                    <c:forEach var="client" items="${clientlist}">
                                        <div class="checkbox">
                                            <label class="col-md-12">
                                                <input type="radio"  class="col-md-1" name="clientid" value="${client.id}"/>
                                                <div class="col-md-10">
                                                    姓名： ${client.name}<br/>
                                                    电话：${client.tel}<br/>
                                                    住址：${client.address}<br/>
                                                    口味：${client.taste}<br/>
                                                </div>
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 sr-only">送餐时间：</label>
                                <div class="col-md-9">
                                    <button type="button" class="btn btn-default" id="addclient"><span class="glyphicon glyphicon-plus"></span></button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">送餐时间：</label>
                                <div class="col-md-9">
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="30"/> 下单后：30分钟
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="45"/> 下单后：45分钟
                                        </label>
                                    </div>
                                     <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="60"/> 下单后：60分钟
                                        </label>
                                    </div>
                                     <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="90"/> 下单后：90分钟
                                        </label>
                                    </div>
                                     <div class="checkbox">
                                        <label>
                                            <input type="radio" name="arrivetime" value="120"/> 下单后：120分钟
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4 sr-only">提交：</label>
                                <div class="col-md-8">
                                    <input type="hidden" name="action" value="add">
                                    <input class="btn btn-danger" type="submit" value="保存"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:if>
               <div id="addarea" class="sr-only">
                   <form action="order" method="post" class="form-horizontal">
                       <input type="hidden" name="action" value="addclient"/>
                       <div class="form-group">
                           <label class="control-label col-md-4">姓名：</label>
                           <div class="col-md-8">
                               <input class="form-control" name="name" type="text"/>
                           </div>
                       </div>
                       <div class="form-group">
                           <label class="control-label col-md-4">电话：</label>
                           <div class="col-md-8">
                               <input class="form-control" name="tel" type="text"/>
                           </div>
                       </div>
                       <div class="form-group">
                           <label class="control-label col-md-4">住址：</label>
                           <div class="col-md-8">
                               <input class="form-control" name="address" type="text" />
                           </div>
                       </div>
                       <div class="form-group">
                           <label class="control-label col-md-4">期望口味：</label>
                           <div class="col-md-8">
                               <input class="form-control" name="taste" type="text" />
                           </div>
                       </div>
                       <div class="form-group">
                           <label class="control-label col-md-4 sr-only">提交：</label>
                           <div class="col-md-8">
                               <input class="btn btn-danger" type="submit" value="保存"/>
                           </div>
                       </div>
                   </form>
               </div>
            </div>
            <div class="panel-footer">
            </div>

        </div>
    </div>
    <div class="col-md-6">
        <c:if test="${empty id}">
            <div>
                <h1>已经注册？</h1>
                <a href="user/action=login" class="btn btn-success">去登陆</a>
                <h1>未注册？</h1>
                <a href="user/action=register" class="btn btn-primary">去注册</a>
            </div>
        </c:if>
        <c:if test="${not empty id}">
            <div class="thumbnail">
                <img src="img/saletaocan.jpg">
            </div>
        </c:if>
        <br/>
        <div class="thumbnail">
            <img src="img/sale.jpg">
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var formhtml = ""
    $(document).ready(function () {
        $("#addclient").click(function () {
            $("#chooseclient").attr("class","sr-only");
            $("#addarea").attr("class","")
        });
    })
</script>
</html>
