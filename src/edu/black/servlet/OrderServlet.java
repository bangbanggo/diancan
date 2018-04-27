package edu.black.servlet;

import edu.black.dao.*;
import edu.black.entity.Car;
import edu.black.entity.Client;
import edu.black.entity.Order;
import edu.black.entity.User;
import edu.black.exception.NotHaveOneId;
import edu.black.util.ResolveCookies;
import edu.black.util.SearchCookie;
import edu.black.util.Verification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "OrderServlet",urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)){
                //新增订单
                addOrder(request,response);
            }else if ("show".equals(action)){
                //显示自己的订单
                showMyOrder(request,response);
            } else if ("gotoadd".equals(action)) {
                //进入下单页
                gotoadd(request,response);
            }else if ("addclient".equals(action)){
                //注册用户添加取餐人
                addclientIntoUser(request,response);
            }else if ("showall".equals(action)){
                //显示所有人订单
                showAllOrder(request,response);
            } else if ("take".equals(action)) {
                takeOrder(request,response);
            }else if ("deliver".equals(action)) {
                deliver(request,response);
            }else if ("over".equals(action)) {
                overOrder(request,response);
            }else if ("cancel".equals(action)){
                //取消订单
                cancelOrder(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NotHaveOneId e){
            request.setAttribute("error","非法操作。");
            request.getRequestDispatcher("error").forward(request,response);
        }
    }

    /**
     * 增加订单，对用户进行判断，分为游客，以及登录用户
     * @param request 请求
     * @param response 相应
     */
    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
//        Cookie userid = SearchCookie.getCookie(request.getCookies(),"userid");
        //更改为从session中获取userid
        String userid =(String) request.getSession().getAttribute("userid");
        Cookie foods = SearchCookie.getCookie(request.getCookies(),"car");
        if (userid != null && !userid.equals("")){
            //注册用户添加订单
//            Client client = getClientFromRequest(request,response);
            Client client = new Client();
            client.setId(request.getParameter("clientid"));
            String time = request.getParameter("arrivetime");
            client.setArrivetime(getArriveTime(Integer.parseInt(time)));
            Car car = CarDao.Calculate(new Car(FoodDao.queryallInCarByUser(userid)));
            String orderid  = OrderDao.addOrderByUser(client,car,userid);
            request.setAttribute("orderid",orderid);
            request.getRequestDispatcher("process.jsp").forward(request,response);
        }else if ((userid == null || "".equals(userid)) && foods!= null && !foods.getValue().equals("0/0")){
            //游客身份添加订单
            Client client =getClientFromRequest(request,response);
            Car car = CarDao.Calculate(new Car(FoodDao.queryInCar(ResolveCookies.getCarInfo(foods))));
            String orderid  = OrderDao.addOrder(client,car);
            if (orderid!=null){
                foods= new Cookie("car","0/0");
                response.addCookie(foods);
                request.setAttribute("orderid",orderid);
                request.getRequestDispatcher("process.jsp").forward(request,response);
            }else {
                request.getRequestDispatcher("error.jsp").forward(request,response);
            }
        }else{
            request.setAttribute("error","非法操作。");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }

    private void showMyOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String orderid = request.getParameter("orderid");
        String clientTel = request.getParameter("clienttel");
        String userid =(String) request.getSession().getAttribute("userid");
        if (orderid != null && clientTel != null){
            request.setAttribute("orderlist",OrderDao.queryAllByOrderId(orderid,clientTel));
            request.getRequestDispatcher("myorder.jsp").forward(request,response);
        }else if(userid != null){
            request.setAttribute("orderlist",OrderDao.queryAllByUserid(userid));
            request.getRequestDispatcher("myorder.jsp").forward(request,response);
        }else {
            request.setAttribute("error","没有输入订单信息，或者没有登陆。");
            request.getRequestDispatcher("myorder.jsp").forward(request,response);
        }
    }

    /**
     * 从request中获得client对象
     * @param request 请求
     * @param response 相应
     * @return 客户
     */
    private Client getClientFromRequest(HttpServletRequest request, HttpServletResponse response){
        Client client = new Client();
        client.setName(request.getParameter("name"));
        client.setTel(request.getParameter("tel"));
        client.setAddress(request.getParameter("address"));
//        String time = request.getParameter("arrivetime");
//        client.setArrivetime(getArriveTime(Integer.parseInt(time)));
        client.setTaste("taste");
        return client;
    }
    private void gotoadd(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String userid = (String)request.getSession().getAttribute("userid");
        if(userid!=null && !userid.equals("")){
            request.setAttribute("clientlist", ClientDao.getOneByUserid(userid));
        }
        request.getRequestDispatcher("personalinfo.jsp").forward(request,response);
    }
    private void addclientIntoUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Client client = getClientFromRequest(request,response);
        String userid = (String) request.getSession().getAttribute("userid");
        ClientDao.addClientInUser(client,userid);
        request.getRequestDispatcher("order?action=gotoadd").forward(request,response);
    }

    private String getArriveTime(int arrivetime){
        long time = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return df.format(time+arrivetime*1000*60);
    }

    //查看所有订单
    private void showAllOrder(HttpServletRequest request, HttpServletResponse response)throws SQLException, ServletException, IOException{
        //验证用户信息
        HttpSession session = request.getSession();
        if (UserDao.verificate((String)session.getAttribute("username"),(String)session.getAttribute("userid"))){
            String date = request.getParameter("date");
            String clientname = request.getParameter("clientname");
            String orderid = request.getParameter("orderid");
            List<Order> list;
            if (clientname != null){
                list = OrderDao.queryallByClientName(clientname);
            }else if (orderid!=null){
                list = OrderDao.queryallByOrderId(orderid);
            }else {
                list = OrderDao.queryAll(date);
            }
            request.setAttribute("orderlist",list);
            Map<String,Double> income = calIncome(list);
            request.setAttribute("datelist",OrderDao.queryAllDate());
            request.setAttribute("incomedata",income);
            request.getRequestDispatcher("backstage.jsp").forward(request,response);
        }else {
            request.setAttribute("error","你没有改权限查看。");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }

    /**
     * 订单惨重操作，接单，送餐，结单
     * @param request 清除
     * @param response 回应
     * @throws SQLException 错误
     * @throws ServletException 错误
     * @throws IOException 错误
     */
    private void takeOrder(HttpServletRequest request, HttpServletResponse response)throws SQLException, ServletException, IOException{
        HttpSession session = request.getSession();
        if (UserDao.verificate((String)session.getAttribute("username"),(String)session.getAttribute("userid"))){
            String date = "";//传空
            String id = request.getParameter("id");
            if (OrderDao.takeOrder(id))
            request.setAttribute("orderlist",OrderDao.queryAll(date));
            request.setAttribute("datelist",OrderDao.queryAllDate());
            request.getRequestDispatcher("backstage.jsp").forward(request,response);
        }else {
            request.setAttribute("error","你没有改权限查看。");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
    private void deliver(HttpServletRequest request, HttpServletResponse response)throws SQLException, ServletException, IOException{
        HttpSession session = request.getSession();
        if (UserDao.verificate((String)session.getAttribute("username"),(String)session.getAttribute("userid"))){
            String date = "";//传空
            String id = request.getParameter("id");
            if (OrderDao.deliverOrder(id))
                request.setAttribute("orderlist",OrderDao.queryAll(date));
            request.setAttribute("datelist",OrderDao.queryAllDate());
            request.getRequestDispatcher("backstage.jsp").forward(request,response);
        }else {
            request.setAttribute("error","你没有改权限查看。");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
    private void overOrder(HttpServletRequest request, HttpServletResponse response)throws SQLException, ServletException, IOException{
        HttpSession session = request.getSession();
        if (UserDao.verificate((String)session.getAttribute("username"),(String)session.getAttribute("userid"))){
            String date = "";//传空
            String id = request.getParameter("id");
            if (OrderDao.overOrder(id)){
                request.setAttribute("orderlist",OrderDao.queryAll(date));
                request.setAttribute("datelist",OrderDao.queryAllDate());
                request.getRequestDispatcher("backstage.jsp").forward(request,response);
            }else {
                request.setAttribute("error","系统错误。");
                request.getRequestDispatcher("error.jsp").forward(request,response);
            }

        }else {
            request.setAttribute("error","你没有改权限查看。");
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }

    private Map<String,Double> calIncome(List<Order> list){
        Map<String,Double> map = new HashMap<>();
        HashSet<String> names = new HashSet<>();
        for (Order order : list){
            names.add(order.getStatus());
        }
        for (String name:names){
            map.put(name,Double.valueOf("0"));
        }
        for (Order order:list){
            for (String name:names){
                if (order.getStatus().equals(name)){
                    map.put(name,map.get(name)+order.getPrice());
                }
            }
        }
        return map;
    }

    //取消订单
    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NotHaveOneId {
        String id = request.getParameter("id");
        if (id!=null){
            if (Verification.verificateNormalUser(request.getSession()) ||Verification.verificate(request.getSession())){
                OrderDao.cancelOrderINDataBase((String)request.getSession().getAttribute("userid"),id);
                request.setAttribute("error","取消成功");
            }else {
                request.setAttribute("error","游客请联系客服取消订单");
            }
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }else {
            throw new NotHaveOneId();
        }
    }
}
