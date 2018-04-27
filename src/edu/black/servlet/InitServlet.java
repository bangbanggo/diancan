package edu.black.servlet;

import edu.black.util.DBData;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "InitServlet",urlPatterns = "/init")
public class InitServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        DBData.setDriver(servletContext.getInitParameter("driver"));
        DBData.setUrl(servletContext.getInitParameter("url"));
        DBData.setUser(servletContext.getInitParameter("user"));
        DBData.setPassword(servletContext.getInitParameter("password"));
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
