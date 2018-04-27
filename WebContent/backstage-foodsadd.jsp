<%--
  Created by IntelliJ IDEA.
  User: chenyanbang
  Date: 2018/3/19
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>快点餐后台</title>
    <link rel="stylesheet"  href="css/bootstrap.css" type="text/css" >
    <link rel="stylesheet"  href="css/bootstrap-theme.css" type="text/css" >
    <link rel="stylesheet"  href="css/global.css" type="text/css" >
</head>
<body>
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<%@ include file="backbar.jsp" %>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">后台管理</div>
        <div class="panel-body">
            <div class="col-md-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <%@include file="backstagemenu.jsp"%>
                    </div>
                </div>
            </div>
            <div class="col-md-10">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form action="foodservlet" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-md-4">单品名称</label>
                                <div class="col-md-8">
                                    <input type="text" name="name" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">单价</label>
                                <div class="col-md-8">
                                    <input type="text" name="singleprice" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">描述</label>
                                <div class="col-md-8">
                                    <input type="text" name="more" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">图片描述</label>
                                <div class="col-md-8">
                                    <input type="hidden" name="img" id="img"/><label id="imglabel"></label><button type="button" class="btn btn-default" id="upimg">上传图片</button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4 sr-only">提交</label>
                                <div class="col-md-8">
                                    <input type="hidden" name="action" value="add">
                                    <input type="submit" class="btn btn-success" value="添加"/>
                                </div>
                            </div>


                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-footer">
            <%@include file="copyright.jsp"%>
        </div>
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
                    document.getElementById("img").value = xmlhttp.responseText;
                    document.getElementById("imglabel").innerText = xmlhttp.responseText;
                }
            }
        });
    });
</script>
</html>
