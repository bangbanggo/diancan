package edu.black.dao;

import edu.black.entity.Car;
import edu.black.entity.Food;
import edu.black.util.DBUtil;

import java.sql.SQLException;
import java.util.List;

public class CarDao {

    /**
     * 计算购物车，获得购物车的总价，总量
     * @param car
     * @return
     */
    public static Car Calculate(Car car){
        double totalprice = 0;
        int totalitems = 0;
        for (Food food:car.getFoods()){
            totalprice += food.getPrice()*food.getAmount();
            food.setTotalprice(food.getPrice()*food.getAmount());
            totalitems += food.getAmount();
        }
        car.setTotalprice(totalprice);
        car.setTotalitems(totalitems);
        return car;
    }

    public static boolean insertFoodIntoCarInDatabase(int foodid,String userid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        boolean result = false;
        String sql = "update foodincar set amount=amount+1 where foodid = ? and userid = ? ";
        dbUtil.prepared(sql,foodid,userid);
        result = dbUtil.update()==1?true:false;
        if (!result){
            String sqlinsert = "insert into foodincar (foodid,userid,amount) values(?,?,?)";
            dbUtil.prepared(sqlinsert,foodid,userid,Integer.parseInt("1"));
            result = dbUtil.update()==1?true:false;
        }
        dbUtil.close();
        return result;
    }

    /**
     * 合并数据库中的购物车与Cookie中的购物车
     * @param foods
     * @param userid
     * @return
     * @throws SQLException
     */
    public static boolean updateFoodIntoCarInDatabase(List<Food> foods, String userid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        dbUtil.getConn().setAutoCommit(false);
        boolean result = false;
        String sql = null;
        String sqlinsert = null ;
        for (Food food : foods){
            sql = "update foodincar set amount=amount+? where foodid = ? and userid = ? ";
            dbUtil.prepared(sql,food.getAmount(),food.getId(),userid);
            result = dbUtil.update()==1?true:false;
            if (!result){
                sqlinsert = "insert into foodincar (foodid,userid,amount) values(?,?,?)";
                dbUtil.prepared(sqlinsert,food.getId(),userid,food.getAmount());
                result = dbUtil.update()==1?true:false;
                if(!result){
                    dbUtil.getConn().rollback();
                    dbUtil.close();
                    return updateFoodIntoCarInDatabase(foods,userid);
                }
            }
        }
        dbUtil.getConn().commit();
        dbUtil.close();
        return result;
    }

    /**
     * 将购物车某件商品数量减一
     * @param foodid
     * @param userid
     * @return
     * @throws SQLException
     */
    public static boolean removeFoodFromCarInDatabase(int foodid,String userid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        boolean result = false;
        String sqlupdate = "update foodincar set amount=amount-1 where foodid = ? and userid = ?";
        dbUtil.prepared(sqlupdate,foodid,userid);
        result = dbUtil.update()==1?true:false;
        if (!result){
            return result;
        }
        String sqldelete = "delete from foodincar where foodid = ? and userid = ? and amount = 0";
        dbUtil.prepared(sqldelete,foodid,userid);
        dbUtil.update();
        dbUtil.close();
        return result;
    }

    /**
     * 从购物车中删除某件商品
     * @param foodid
     * @param userid
     * @return
     * @throws SQLException
     */
    public static boolean removeFoodFromCarInDatabaseAtOnce(int foodid,String userid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        boolean result = false;
        String sqldelete = "delete from foodincar where foodid = ? and userid = ?";
        dbUtil.prepared(sqldelete,foodid,userid);
        dbUtil.update();
        dbUtil.close();
        return result;
    }


}
