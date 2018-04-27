<%@ page import="edu.black.util.SearchCookie" %>
<%@ page import="edu.black.util.DBData" %><%--
  Created by IntelliJ IDEA.
  User: chenyanbang
  Date: 2018/3/16
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%

    if (DBData.getDriver()== null && DBData.getUrl() == null && DBData.getUser() == null && DBData.getPassword() == null){
        response.sendRedirect("init");
    }else {
        Cookie userid = SearchCookie.getCookie(request.getCookies(),"userid");
        Cookie username = SearchCookie.getCookie(request.getCookies(),"username");
            if (userid != null && username != null){
                session.setAttribute("userid",userid.getValue());
                session.setAttribute("username",username.getValue());
                System.out.println(userid.getMaxAge());
            }


        request.getRequestDispatcher("foodservlet?action=queryall").forward(request,response);
    }
%>
</body>
</html>
