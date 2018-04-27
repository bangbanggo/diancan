package edu.black.servlet;

import edu.black.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BackStageServlet",urlPatterns = "/back")
public class BackStageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String userid = (String)session.getAttribute("userid");
        String username = (String)session.getAttribute("username");
        try {
            if (userid != null && username != null && UserDao.verificate(username,userid)){
                request.getRequestDispatcher("order?action=showall").forward(request,response);
            }else {
                request.getRequestDispatcher("backlogin.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
