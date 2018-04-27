package edu.black.dao;

import edu.black.entity.Food;
import edu.black.util.DBData;
import edu.black.util.DBUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FoodDao {

    /**
     * 列出所有的食品,用于后台
     * @return 返回List<food>
     * @throws SQLException
     */
    public static List<Food> queryall(String param) throws SQLException {
        String paramsql = "";
        if ("insale".equals(param)){
            paramsql = "where status = '在售'";
        } else if ("notinsale".equals(param)) {
            paramsql = "where status = '已下架'";
        }
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foods"+dbUtil.seperater+paramsql;
        dbUtil.prepared(sql);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            food.setStatus(rs.getString("status"));
            list.add(food);
        }
        return list;
    }

    /**
     * 列出在售食品
     * @return
     * @throws SQLException
     */
    public static List<Food> queryallInSale() throws SQLException {
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foods where status = '在售'";
        dbUtil.prepared(sql);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            food.setStatus(rs.getString("status"));
            list.add(food);
        }
        return list;
    }

    public static List<Food> queryallInCarByUser(String userid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foodincar,foods where foods.id = foodid and userid = ?";
        dbUtil.prepared(sql,userid);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            food.setAmount(rs.getInt("amount"));
            list.add(food);
        }
        return list;
    }

    /**
     * 根据销量查询单品
     * @return
     * @throws SQLException
     */
    public static List<Food> queryallOrderBySalecolums() throws SQLException {
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foods order by salevolums desc";
        dbUtil.prepared(sql);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            list.add(food);
        }
        return list;
    }

    /**
     * 根据价格排序
     * @return
     * @throws SQLException
     */
    public static List<Food> queryallOrderByPrice(boolean isAsc) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql = null;
        if (isAsc){
            sql= "select * from foods order by price asc";
        }else {
            sql= "select * from foods order by price desc";
        }
        dbUtil.prepared(sql);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            list.add(food);
        }
        return list;
    }

    /**
     * 根据关键字查询
     * @return
     * @throws SQLException
     */
    public static List<Food> queryallInKeywords(String keywords) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foods where name like ?";
        String param = "%"+keywords+"%";
        dbUtil.prepared(sql,param);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            list.add(food);
        }
        return list;
    }


    /**
     * 根据订单ID查询所有食品
     * @param orderid
     * @return
     * @throws SQLException
     */
    public static List<Food> queryallByOrderId(int orderid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foods,foodinorder where foodid = foods.id and orderid = ?";
        dbUtil.prepared(sql,orderid);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
            food.setAmount(rs.getInt("amount"));
            list.add(food);
        }
        return list;
    }


    /**
     * 根据二维数据carInfo，来查找food集合，经测试sqlserver 支持 where id in (1,2,3) 语句
     * @param carinfo
     * @return
     * @throws SQLException
     */
    public static List<Food> queryInCar(int[][] carinfo) throws SQLException {
        if (carinfo == null){
            return null;
        }
        DBUtil dbUtil = new DBUtil();
        ArrayList<Food> list = new ArrayList<>();
        String sql= "select * from foods";
        String clause = "where id in "+"("+carinfo[0][0];
        for (int i=1;i<carinfo.length;i++){
            clause += ","+carinfo[i][0];
        }
        clause += ")";
        dbUtil.prepared(sql+dbUtil.seperater+clause);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            Food food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setId(rs.getInt("id"));
            list.add(food);
        }

        for (Food food:list){
            for (int i=0;i<carinfo.length;i++){
                if (food.getId()==carinfo[i][0]){
                    food.setAmount(carinfo[i][1]);
                }
            }
        }
        return list;
    }

    /**
     * 根据ID查询FOOD信息
     * @param id
     * @return
     * @throws SQLException
     */
    public static Food queryOneById(String id) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        Food food = null;
        String sql= "select * from foods where id = ?";
        dbUtil.prepared(sql,id);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            food = new Food(rs.getString("name"),
                    rs.getDouble("price"),
                    0, 0,
                    rs.getString("img"),
                    null);
            food.setSalevolums(rs.getInt("salevolums"));
            food.setId(rs.getInt("id"));
        }
        dbUtil.close();
        return food;
    }

    public static String getDatail(String foodid) throws SQLException {
        DBUtil dbUtil =  new DBUtil();
        String sql = "select detail from foods where id = ?";
        dbUtil.prepared(sql,foodid);
        ResultSet rs =  dbUtil.select();
        String detail = null;
        String detailok=null;
        if (rs.next()){
            detail = rs.getString("detail");
        }
        try {
            detailok = URLDecoder.decode(detail,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dbUtil.close();
        return detailok;
    }

    /**
     * 下架食品
     * @param id
     * @return
     * @throws SQLException
     */
    public static boolean downFood(String id) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql = "update foods set status = '已下架' where id = ?";
        dbUtil.prepared(sql,id);
        boolean result = dbUtil.update()==1?true:false;
        if (!result){
            dbUtil.close();
            downFood(id);
        }
        dbUtil.close();
        return result;
    }

    /**
     * 上架食品
     * @param id
     * @return
     * @throws SQLException
     */
    public static boolean upFood(String id) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql = "update foods set status = '在售' where id = ?";
        dbUtil.prepared(sql,id);
        boolean result = dbUtil.update()==1?true:false;
        if (!result){
            dbUtil.close();
            downFood(id);
        }
        dbUtil.close();
        return result;
    }


    /**
     * 新增食品信息
     * @param food
     * @return
     * @throws SQLException
     */
    public static boolean addFood(Food food) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql =  "insert into foods(name,img,price,detail,status) values(?,?,?,?,'在售')";
        dbUtil.prepared(sql,food.getName(),food.getImage(),food.getPrice(),food.getMore());
        boolean result = dbUtil.update()==1?true:false;
        dbUtil.close();
        return result;
    }

    /**
     * 保存更新后的食品信息
     * @param food
     * @return
     * @throws SQLException
     */
    public static boolean saveFood(Food food) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        String sql =  "update foods set name=?,img=?,price=? where id=?";
        dbUtil.prepared(sql,food.getName(),food.getImage(),food.getPrice(),food.getId());
        boolean result = dbUtil.update()==1?true:false;
        dbUtil.close();
        return result;
    }

}
