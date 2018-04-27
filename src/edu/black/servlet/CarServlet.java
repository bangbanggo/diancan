package edu.black.servlet;

import edu.black.dao.CarDao;
import edu.black.dao.FoodDao;
import edu.black.entity.Car;
import edu.black.entity.Food;
import edu.black.exception.NotHaveOneId;
import edu.black.util.ResolveCookies;
import edu.black.util.SearchCookie;
import edu.black.util.Verification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CarServlet",urlPatterns = "/car")
public class CarServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        try {
            if ("addintocar".equals(action)){
                //单击主页上的加入购物车，跳转到这里
                addIntoCar(request,response);
            }else if ("show".equals(action)){
                //查看购物车
                showCar(request,response);
            }else if ("removefromcar".equals(action)){
                //删减购物车上的商品
                removefromcar(request,response);
            }else if ("check".equals(action)){
                //登陆后检查登陆前，游客状态时购物车是否为空
                checkFoodsInCar(request,response);

            }else if ("delete".equals(action)){
                //删除购物车上的一种商品
                deleteFoodInCar(request,response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }catch (NotHaveOneId e){
            if (e.getMessage()!=null){
                request.setAttribute("error",e.getMessage());
            }else {
                request.setAttribute("error","非正确操作。");
            }
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }


    }

    /**
     * 用户将商品加入购物车后，检车cookie中是否已经存在信息，也就是是否存在已经添加的商品
     * duicookie查重，重复添加
     * @param request
     * @param response
     */
    private  void  addIntoCar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NotHaveOneId {
        String id = request.getParameter("id");
        String userid = (String)request.getSession().getAttribute("userid");
        if (userid != null){
            if (id == null || id.equals("")){
                throw new NotHaveOneId();
            }
            if (!CarDao.insertFoodIntoCarInDatabase(Integer.parseInt(id),userid)){
                throw new NotHaveOneId("添加失败");
            }
        }else {
            if (id== null || id.equals("")){
                throw new NotHaveOneId();//当没有获得到商品id时，跳出
            }
            Cookie cookie = SearchCookie.getCookie(request.getCookies(),"car");
            String cookievalue = id+"/"+1;
            //购物车信息用利用json数据保存，也可以用String自己构造,此处自己构造
            //购物车存放N条数据，每条数据存放两个维度，一个商品ID，一个商品数量NUM，采用"ID/NUM"形式构造
            if (cookie == null){
                cookie = new Cookie("car",cookievalue);
            }else {
                //查重，重复添加
                cookie = ResolveCookies.update(cookie,id);
            }
            response.addCookie(cookie);
        }
        request.getRequestDispatcher("car.jsp").forward(request,response);
    }

    private  void  removefromcar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NotHaveOneId {
        String id = request.getParameter("id");

        String userid = (String)request.getSession().getAttribute("userid");
        if (userid != null){
            if (id == null || id.equals("")){
                throw new NotHaveOneId();
            }
            if (!CarDao.removeFoodFromCarInDatabase(Integer.parseInt(id),userid)){
                request.setAttribute("error","删除失败");
            }
        }else {
            if (id== null || id.equals("")){
                throw new NotHaveOneId();
            }
            Cookie cookie = SearchCookie.getCookie(request.getCookies(),"car");
            String cookievalue = id+"/"+1;
            //购物车信息用利用json数据保存，也可以用String自己构造,此处自己构造
            //购物车存放N条数据，每条数据存放两个维度，一个商品ID，一个商品数量NUM，采用"ID/NUM"形式构造
            if (cookie == null){
                cookie = new Cookie("car",cookievalue);
            }else {
                //查重，重复添加
                cookie = ResolveCookies.remove(cookie,id);
            }
            response.addCookie(cookie);
        }
        request.getRequestDispatcher("car.jsp").forward(request,response);
    }


    private void showCar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Cookie cookie= SearchCookie.getCookie(request.getCookies(),"car");
        String userid = (String)request.getSession().getAttribute("userid");

        if (userid != null){
            Car car = CarDao.Calculate(new Car(FoodDao.queryallInCarByUser(userid)));
            request.setAttribute("carinfo",car);
        }else {
            if (cookie ==null){

            }else {
                //解析cookie,返回购物车信息，拿到食品信息，构建购物车对象
                int[][] carinfo = ResolveCookies.getCarInfo(cookie);
                List<Food> foods = FoodDao.queryInCar(carinfo);
                Car car  = CarDao.Calculate(new Car(foods));
                request.setAttribute("carinfo",car);
            }
        }
        request.getRequestDispatcher("shoppingcar.jsp").forward(request,response);

    }

    private void checkFoodsInCar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Cookie carCookie= SearchCookie.getCookie(request.getCookies(),"car");
        String userid = (String)request.getSession().getAttribute("userid");
        if (carCookie != null && userid != null){
            int[][] carinfo = ResolveCookies.getCarInfo(carCookie);
            List<Food> foods = FoodDao.queryInCar(carinfo);
            CarDao.updateFoodIntoCarInDatabase(foods,userid);
            request.getRequestDispatcher("car?action=show").forward(request,response);
        }else {
            request.setAttribute("error","信息不全。");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }

    private void deleteFoodInCar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NotHaveOneId {
        String id = request.getParameter("id");
        if (id!=null&& !id.equals("")){
            if (Verification.verificate(request.getSession())){
                if (!CarDao.removeFoodFromCarInDatabaseAtOnce(Integer.parseInt(id),(String)request.getSession().getAttribute("userid"))){
                    request.setAttribute("error","删除失败。");
                }

            }else {
                Cookie car = SearchCookie.getCookie(request.getCookies(),"car");
                car = ResolveCookies.delete(car,id);
                response.addCookie(car);
            }
            request.getRequestDispatcher("car.jsp").forward(request,response);
        }else {
            throw new NotHaveOneId();
        }

    }
}
