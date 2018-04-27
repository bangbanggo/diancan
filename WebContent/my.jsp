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
         <div class="panel-heading">用户信息</div>
         <div class="panel-body">
           <h3 class="text-center">用户ID：${info.userid}</h3>
           <div id="tab1"></div>
         </div>
         <div class="panel-footer"></div>
       </div>
      </div>
  </body>
<script type="text/javascript" src="js/echarts.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var myChart = echarts.init(document.getElementById('tab1'));
        var option = {
            title : {
                text: '本店用户所点菜品比例',
                subtext: '全局',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ${info.name}
            },
            series : [
                {
                    name: '单品',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:${info.data},
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        myChart.setOption(option)
    })
</script>
</html>
