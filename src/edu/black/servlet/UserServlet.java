package edu.black.servlet;

import edu.black.dao.StatisticDao;
import edu.black.dao.UserDao;
import edu.black.entity.SelfData;
import edu.black.entity.User;
import edu.black.util.SearchCookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserServlet",urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action=request.getParameter("action");
        try {
            if ("register".equals(action)){
                User user = new User();
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user = UserDao.register(user);
                if (user == null){
                    request.setAttribute("error","注册失败，请重新注册。");
                    request.getRequestDispatcher("register.jsp").forward(request,response);
                }else {
                    request.setAttribute("error","注册成功，前往<a href='login.jsp'>登陆</a>");
                    request.getRequestDispatcher("index.jsp").forward(request,response);
                }
            }else if ("login".equals(action)){
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                User user =  null;
                if (username!=null && password!= null){
                    user  = UserDao.login(username,password);

                }
                if (user == null){
                    request.setAttribute("error","登陆失败，请重新登陆。");
                    request.getRequestDispatcher("login.jsp").forward(request,response);
                }else {

                    Cookie useridCookie = new Cookie("userid",user.getId());
                    Cookie usernameCookie = new Cookie("username",user.getUsername());
                    if ("7".equals(request.getParameter("date"))){
                        useridCookie.setMaxAge(7*24*60*60);
                        usernameCookie.setMaxAge(7*24*60*60);
                        response.addCookie(useridCookie);
                        response.addCookie(usernameCookie);
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("userid",useridCookie.getValue());
                    session.setAttribute("username",usernameCookie.getValue());
                    request.getRequestDispatcher("index.jsp").forward(request,response);
                }
            } else if ("showmyself".equals(action)) {
                String userid = (String)request.getSession().getAttribute("userid");
                SelfData sd =StatisticDao.getSelfData();
                sd.setUserid(userid);
                request.setAttribute("info",sd);
                request.getRequestDispatcher("my.jsp").forward(request,response);
            }else if ("backlogin".equals(action)){
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                User user =  null;
                if (username!=null && password!= null){
                    user  = UserDao.backlogin(username,password);

                }
                if (user == null){
                    request.setAttribute("error","登陆失败，请重新登陆。");
                    request.getRequestDispatcher("login.jsp").forward(request,response);
                }else {
                    Cookie useridCookie = new Cookie("userid",user.getId());
                    Cookie usernameCookie = new Cookie("username",user.getUsername());
                    if ("7".equals(request.getParameter("date"))){
                        useridCookie.setMaxAge(7*24*60*60);
                        usernameCookie.setMaxAge(7*24*60*60);
                        response.addCookie(useridCookie);
                        response.addCookie(usernameCookie);
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("userid",useridCookie.getValue());
                    session.setAttribute("username",usernameCookie.getValue());
                    request.getRequestDispatcher("back").forward(request,response);
                }
            }else if ("logout".equals(action)){
                Cookie userid = SearchCookie.getCookie(request.getCookies(),"userid");
                Cookie username = SearchCookie.getCookie(request.getCookies(),"username");
                request.getSession().invalidate();
                if (userid!=null && username != null){
                    System.out.println(userid.getMaxAge());
                    userid.setMaxAge(-1);
                    username.setMaxAge(-1);
                    response.addCookie(userid);
                    response.addCookie(username);
                }
                request.setAttribute("error","需要关闭浏览器，重新打开页面。");
                request.getRequestDispatcher("error.jsp").forward(request,response);
//                request.getRequestDispatcher("index.jsp").forward(request,response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
