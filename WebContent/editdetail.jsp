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
    <form action="foodservlet" method="post" class="form-horizontal">
        <div class="panel panel-default">
            <div class="panel-heading"><h3>${food.name}</h3><div class="float-right"><button onclick="back()" type="button" class="btn btn-default right">返回</button>
                <button  type="submit" class="btn btn-default right">保存</button></div><input type="hidden" name="action" value="save"/> </div>
            <div class="panel-body">
                <c:set var="p" value="${food}"></c:set>
                <div>
                    <div class="thumbnail">
                        <img src="image/${p.image}" id="upimg" alt="...">
                    </div>
                    <input name="id" value="${p.id}" type="hidden"/>
                    <input type="hidden" name="img" value="${p.image}" id="img"/>
                    <div class="caption">
                        <h5>
                            <div class="input-group">
                                <div class="input-group-addon">名称</div>
                                <input class="form-control" type="text" name="name" value="${food.name}"/>
                            </div>
                        </h5>
                        <div class="form-inline">

                            <div class="input-group">
                                <div class="input-group-addon">价格</div>
                                <input class="form-control" type="text" name="singleprice" value="${p.price}" />
                                <div class="input-group-addon">元</div>
                            </div>
                        </div>
                    </div>
                    <p>
                    <div class="price">销量:${p.salevolums}</div>
                    </p>
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
    </form>
</div>

</div>
<div id="headphotomodel" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">上传图片</h4>
            </div>
            <div class="modal-body">
                <form method="post" >
                    <input type="file" id="headimage" name="headimage"/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="uploadheadimage">上传</button>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#upimg").click(function () {
            $("#headphotomodel").modal("show");
        });
        $("#uploadheadimage").click(function () {
            var ofile = document.getElementById("headimage").files[0];
            var xmlhttp = new XMLHttpRequest();
            var oFormData = new FormData();
            oFormData.append("file", ofile);
            xmlhttp.open("POST", "/upload", true);
            xmlhttp.send(oFormData);
            xmlhttp.onload = function (ev) {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
                    $("#headphotomodel").modal("hide");
                    $("#headphotoimage").attr("src","/upload/"+xmlhttp.responseText);
                    $("#image").attr("value",xmlhttp.responseText);
                    document.getElementById("upimg").src = 'image/'+xmlhttp.responseText;
                    document.getElementById("img").value = xmlhttp.responseText;
                }
            }
        });
    });
    function back() {
        window.location.href = "foodservlet?action=showback";
    }
</script>
</html>
