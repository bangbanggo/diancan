package edu.black.servlet;

import edu.black.dao.FoodDao;
import edu.black.entity.Food;
import edu.black.util.Verification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FoodServlet",urlPatterns = "/foodservlet")
public class FoodServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        try {
            if ("queryall".equals(action)){
                request.setAttribute("list", FoodDao.queryallInSale());
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if ("showback".equals(action)){
                request.setAttribute("list", FoodDao.queryall(request.getParameter("param")));
                request.getRequestDispatcher("backstage-foods.jsp").forward(request,response);
            }else if ("search".equals(action)){
                String keywords = request.getParameter("keywords");
                request.setAttribute("list", FoodDao.queryallInKeywords(keywords));
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if ("orderbysalecolums".equals(action)){
                request.setAttribute("list", FoodDao.queryallOrderBySalecolums());
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if ("orderbypriceasc".equals(action)){
                request.setAttribute("list", FoodDao.queryallOrderByPrice(true));
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if ("orderbypricenotasc".equals(action)){
                request.setAttribute("list", FoodDao.queryallOrderByPrice(false));
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if ("detail".equals(action)){
                String foodid = request.getParameter("id");
                if (foodid != null){
                    request.setAttribute("food",FoodDao.queryOneById(foodid));
                    try {
                        request.setAttribute("content",FoodDao.getDatail(foodid));
                    } catch (NullPointerException e) {
                        System.out.println("编号"+foodid+"没有详情页。");
                    }
                    request.getRequestDispatcher("detail.jsp").forward(request,response);
                }else {
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                }

            }else if ("backdetail".equals(action)){
                String foodid = request.getParameter("id");
                if (foodid != null){
                    request.setAttribute("food",FoodDao.queryOneById(foodid));
                    try {
                        request.setAttribute("content",FoodDao.getDatail(foodid));
                    } catch (NullPointerException e) {
                        System.out.println("编号"+foodid+"没有详情页。");
                    }
                    request.getRequestDispatcher("backdetail.jsp").forward(request,response);
                }else {
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                }

            }else if ("gotoedit".equals(action)){
                String foodid = request.getParameter("id");
                if (foodid != null){
                    request.setAttribute("food",FoodDao.queryOneById(foodid));
                    try {
                        request.setAttribute("content",FoodDao.getDatail(foodid));
                    } catch (NullPointerException e) {
                        System.out.println("编号"+foodid+"没有详情页。");
                    }
                    request.getRequestDispatcher("editdetail.jsp").forward(request,response);
                }else {
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                }

            }else if ("downfoods".equals(action)){
                if (Verification.verificate(request.getSession())){
                    String id = request.getParameter("id");
                    if (id != null && FoodDao.downFood(id)){
                        response.sendRedirect("foodservlet?action=showback");
                    }else {
                        request.setAttribute("error","操作有误。");
                        request.getRequestDispatcher("error.jsp").forward(request,response);
                    }

                }else {
                    request.setAttribute("error","无权操作。");
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                }
            }else if ("upfoods".equals(action)){
                if (Verification.verificate(request.getSession())){
                    String id = request.getParameter("id");
                    if (id != null && FoodDao.upFood(id)){
                        response.sendRedirect("foodservlet?action=showback");
                    }else {
                        request.setAttribute("error","操作有误。");
                        request.getRequestDispatcher("error.jsp").forward(request,response);
                    }

                }else {
                    request.setAttribute("error","无权操作。");
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                }
            }else if ("gotoadd".equals(action)){
                request.getRequestDispatcher("backstage-foodsadd.jsp").forward(request,response);
            }else if ("add".equals(action)){
                addfood(request,response);
            }else if ("save".equals(action)){
                saveFood(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增食品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void addfood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name  = request.getParameter("name");
        String singleprice = request.getParameter("singleprice");
        String more = request.getParameter("more");
        String img =request.getParameter("img");
        if (name!=null && singleprice != null && more != null && img != null){
            Food food = new Food();
            food.setName(name);
            food.setPrice(Double.parseDouble(singleprice));
            food.setImage(img);
            food.setMore(more);
            if (FoodDao.addFood(food)){
                response.sendRedirect("foodservlet?action=showback");
            }else {
                request.setAttribute("error","未能正确添加该信息，请稍后重新添加。");
                request.getRequestDispatcher("error.jsp").forward(request,response);
            }
        }else{
            request.setAttribute("error","信息不全");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }

    /**
     * 保存更新后的食品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void saveFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name  = request.getParameter("name");
        String singleprice = request.getParameter("singleprice");
        String img =request.getParameter("img");
        String id = request.getParameter("id");
        if (name!=null && singleprice != null && id != null && img != null){
            Food food = new Food();
            food.setName(name);
            food.setPrice(Double.parseDouble(singleprice));
            food.setImage(img);
            food.setId(Integer.parseInt(id));
            if (FoodDao.saveFood(food)){
                response.sendRedirect("foodservlet?action=showback");
            }else {
                request.setAttribute("error","未能正确添加该信息，请稍后重新添加。");
                request.getRequestDispatcher("error.jsp").forward(request,response);
            }
        }else{
            request.setAttribute("error","信息不全");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
}
