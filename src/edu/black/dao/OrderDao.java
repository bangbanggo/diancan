package edu.black.dao;

import com.sun.javafx.collections.MappingChange;
import edu.black.entity.*;
import edu.black.util.CreateID;
import edu.black.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderDao {
    /**
     * 游客下单
     * @param client
     * @param car
     * @return
     * @throws SQLException
     */
    public static String addOrder(Client client, Car car) throws SQLException {
        boolean result = false;
        DBUtil dbUtil = new DBUtil();
        dbUtil.getConn().setAutoCommit(false);
        client.setId(CreateID.createClientID());
        String sqlclient =
                "INSERT INTO client\n" +
                "           (tel\n" +
                "           ,id\n" +
                "           ,name\n" +
                "           ,address\n" +
//                "           ,userid\n" +
                "           ,taste\n" +
                "           )\n" +
                "     VALUES\n" +
                "           (?" +
                "           ,?" +
                "           ,?" +
                "           ,?" +
                "           ,?" +
                "           )" ;
        dbUtil.prepared(sqlclient,client.getTel(),client.getId(),client.getName(),client.getAddress(),client.getTaste());
        result = dbUtil.update()==1?true:false;
        if (!result){
            dbUtil.getConn().rollback();
            dbUtil.close();
            return addOrder(client,car);
        }
        car.setId(CreateID.createCarID());
        String sqlcar =
                "INSERT INTO foodorder\n" +
                "           (id\n" +
//                "           (userid\n" +
                "           ,clientid\n" +
                "           ,price\n" +
                "           ,start\n" +
                "           ,arrivetime\n" +
//                "           ,more\n" +
                "           ,status)\n" +
                "     VALUES\n" +
                "           (?,?,?,now(),?,?)";
        dbUtil.prepared(sqlcar,car.getId(),client.getId(),car.getTotalprice(),client.getArrivetime(),new String("已下单"));
        result = dbUtil.update()==1?true:false;
        if (!result){
            dbUtil.getConn().rollback();
            dbUtil.close();
            return addOrder(client,car);
        }

        for (Food food:car.getFoods()){
            String sqlfood = "insert into foodinorder (orderid,foodid,amount) values(?,?,?)";
            dbUtil.prepared(sqlfood,car.getId(),food.getId(),food.getAmount());
            result =dbUtil.update()==1?true:false;
            if (!result){
                dbUtil.getConn().rollback();
                dbUtil.close();
                return addOrder(client,car);
            }
            String sqlfoodupdate = "update foods set salevolums=salevolums+ ?  where id = ?";
            dbUtil.prepared(sqlfoodupdate,food.getAmount(),food.getId());
            result =dbUtil.update()==1?true:false;
            if (!result){
                dbUtil.getConn().rollback();
                dbUtil.close();
                return addOrder(client,car);
            }
        }
        dbUtil.getConn().commit();
        dbUtil.close();
        return car.getId();
    }

    /**
     * 注册用户下单，与游客不同之处，每条信息都携带有用户信息id，可备用于用户信息操作
     * @param client
     * @param car
     * @return
     * @throws SQLException
     */
    public static String addOrderByUser(Client client, Car car,String userid) throws SQLException {
        boolean result = false;
        DBUtil dbUtil = new DBUtil();
        dbUtil.getConn().setAutoCommit(false);
        car.setId(CreateID.createCarID());
        String sqlcar =
                "INSERT INTO foodorder\n" +
                        "           (id\n" +
                "                   ,userid\n" +
                        "           ,clientid\n" +
                        "           ,price\n" +
                        "           ,start\n" +
                        "           ,arrivetime\n" +
//                "           ,more\n" +
                        "           ,status)\n" +
                        "     VALUES\n" +
                        "           (?,?,?,?,now(),?,?)";
        dbUtil.prepared(sqlcar,car.getId(),userid,client.getId(),car.getTotalprice(),client.getArrivetime(),new String("已下单"));
        result = dbUtil.update()==1?true:false;
        if (!result){
            dbUtil.getConn().rollback();
            dbUtil.close();
            return addOrderByUser(client,car,userid);
        }

        for (Food food:car.getFoods()){
            String sqlfood = "insert into foodinorder (orderid,foodid,amount) values(?,?,?)";
            dbUtil.prepared(sqlfood,car.getId(),food.getId(),food.getAmount());
            result =dbUtil.update()==1?true:false;
            if (!result){
                dbUtil.getConn().rollback();
                dbUtil.close();
                return addOrderByUser(client,car,userid);
            }
            String sqlfoodupdate = "update foods set salevolums=salevolums+ ?  where id = ?";
            dbUtil.prepared(sqlfoodupdate,food.getAmount(),food.getId());
            result =dbUtil.update()==1?true:false;
            if (!result){
                dbUtil.getConn().rollback();
                dbUtil.close();
                return addOrderByUser(client,car,userid);
            }
            String sqldeletefoodincar = "delete from  foodincar where userid = ? and foodid = ? ";
            dbUtil.prepared(sqldeletefoodincar,userid,food.getId());
            result = dbUtil.update()==1?true:false;
            if (!result){
                dbUtil.getConn().rollback();
                dbUtil.close();
                return addOrderByUser(client,car,userid);
            }
        }
        dbUtil.getConn().commit();
        dbUtil.close();
        return car.getId();
    }

    public static List<Order> queryAllByOrderId(String orderid,String clienttel) throws SQLException {
        List<Order> list =  new ArrayList<>();
        DBUtil dbUtil = new DBUtil();
        String sql = "select foodorder.id orderid,start,arrivetime,price,clientid,status from foodorder,client where client.id = foodorder.clientid and  client.tel  = ? and foodorder.id = ? order by start desc ";
        dbUtil.prepared(sql,clienttel,orderid);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("orderid"));
            order.setStart(rs.getString("start"));
            order.setArrivetime(rs.getString("arrivetime"));
            order.setPrice(rs.getDouble("price"));
//            order.setMore(rs.getString("more"));
            order.setClient(ClientDao.getOneById(rs.getString("clientid")));
            order.setStatus(rs.getString("status"));
            order.setFoods(FoodDao.queryallByOrderId(order.getId()));
            list.add(order);
        }
        dbUtil.close();
        return list;
    }


    /**
     * 根据用户id查询订单信息
     * @param userid
     * @return
     * @throws SQLException
     */
    public static List<Order> queryAllByUserid(String userid) throws SQLException {
        List<Order> list =  new ArrayList<>();
        DBUtil dbUtil = new DBUtil();
        String sql = "select foodorder.id orderid,start,arrivetime,price,clientid,status from foodorder,client where client.id = foodorder.clientid and  foodorder.userid = ? order by start desc ";
        dbUtil.prepared(sql,userid);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("orderid"));
            order.setStart(rs.getString("start"));
            order.setArrivetime(rs.getString("arrivetime"));
            order.setPrice(rs.getDouble("price"));
//            order.setMore(rs.getString("more"));
            order.setClient(ClientDao.getOneById(rs.getString("clientid")));
            order.setStatus(rs.getString("status"));
            order.setFoods(FoodDao.queryallByOrderId(order.getId()));
            list.add(order);
        }
        dbUtil.close();
        return list;
    }

    /**
     * 系统用户查看所有订单
     * @return
     * @throws SQLException
     */

    public static List<Order> queryallByOrderId(String orderid) throws SQLException {
        String param = "";//查询时间参数
        if (orderid == null) {
            param = "";
        } else {
            param = "and order.id like '%" + orderid + "%'";
        }
        return queryWithParam(param);
    }

    public static List<Order> queryallByClientName(String clientname) throws SQLException {
        String param = "";//查询时间参数
        if (clientname == null) {
            param = "";
        } else {
            param = "and client.name like '%" + clientname + "%'";
        }
        return queryWithParam(param);
    }

    public static List<Order> queryAll(String date) throws SQLException {
        String param = "";//查询时间参数
        if (date == null) {
            param = "";
        } else {
            param = "and start like '" + date + "%'";
        }
        return queryWithParam(param);
    }


    public static List<Order> queryWithParam(String param) throws SQLException {
        List<Order> list =  new ArrayList<>();
        DBUtil dbUtil = new DBUtil();
        String sql = "select foodorder.id orderid,start,arrivetime,price,clientid,status from foodorder,client where client.id = foodorder.clientid "+param+" order by start desc";
        dbUtil.prepared(sql);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("orderid"));
            order.setStart(rs.getString("start"));
            order.setArrivetime(rs.getString("arrivetime"));
            order.setPrice(rs.getDouble("price"));
//            order.setMore(rs.getString("more"));
            order.setClient(ClientDao.getOneById(rs.getString("clientid")));
            order.setStatus(rs.getString("status"));
            order.setFoods(FoodDao.queryallByOrderId(order.getId()));
            list.add(order);
        }
        dbUtil.close();
        return list;
    }

    public static Map<String,List<String>> queryAllDate() throws SQLException {
        Map<String,List<String>> map = new HashMap<>();
        DBUtil dbUtil = new DBUtil();

        List<String> days = new ArrayList<>();
        String sqldays = "select left(start,10) t  from foodorder,client where client.id = foodorder.clientid group by ( t)";
        dbUtil.prepared(sqldays);
        ResultSet rsDays = dbUtil.select();
        while (rsDays.next()){
            String date = rsDays.getString("t");
            days.add(date);
        }
        map.put("days",days);

        List<String> months = new ArrayList<>();
        String sqlmonths = "select left(start,7) t  from foodorder,client where client.id = foodorder.clientid group by t";
        dbUtil.prepared(sqlmonths);
        ResultSet rsMonths = dbUtil.select();
        while (rsMonths.next()){
            String month = rsMonths.getString("t");
            months.add(month);
        }
        map.put("months",months);

        List<String> years = new ArrayList<>();
        String sqlyears = "select left(start,4) t  from foodorder,client where client.id = foodorder.clientid group by t";
        dbUtil.prepared(sqlyears);
        ResultSet rsYears = dbUtil.select();
        while (rsYears.next()){
            String year = rsYears.getString("t");
            years.add(year);
        }
        map.put("years",years);


        dbUtil.close();
        return map;
    }

    /**
     * 订单更周，接单，送餐，结单
     * @param id
     * @return
     * @throws SQLException
     */
    public static boolean takeOrder(String id) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql = "update foodorder set status = '等待送餐' where id = ?";
        dbUtil.prepared(sql,id);
        boolean result = dbUtil.update()==1?true:false;
        dbUtil.close();
        return result;
    }
    public static boolean deliverOrder(String id) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql = "update foodorder set status = '等待取餐' where id = ?";
        dbUtil.prepared(sql,id);
        boolean result = dbUtil.update()==1?true:false;
        dbUtil.close();
        return result;
    }
    public static boolean overOrder(String id) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql = "update foodorder set status = '已完成' where id = ?";
        dbUtil.prepared(sql,id);
        boolean result = dbUtil.update()==1?true:false;
        dbUtil.close();
        return result;
    }

    public static Map<String,Double> viewIncome() throws SQLException {
        Map<String,Double> map = new HashMap<>();
        DBUtil dbUtil = new DBUtil();
        String sqldays = "select sum(price) income,status  from foodorder,client where client.id = foodorder.clientid group by status";
        dbUtil.prepared(sqldays);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            map.put(rs.getString("status"),rs.getDouble("income"));
        }
        dbUtil.close();
        return map;
    }

    public static boolean cancelOrderINDataBase(String userid,String orderid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql = "update foodorder set status = '已取消订单' where status = '已下单' and id = ?";
        dbUtil.prepared(sql,orderid);
        boolean result = dbUtil.update()==1?true:false;

        String log = "insert  into foodlog (userid,logmessage,logdate) values(?,?,now())";
        String logmessage = "取消了订单"+orderid+"。";
        dbUtil.prepared(log,userid,logmessage);
        dbUtil.update();
        dbUtil.close();
        return result;
    }

}
