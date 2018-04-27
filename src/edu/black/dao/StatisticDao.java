package edu.black.dao;

import edu.black.entity.SelfData;
import edu.black.util.DBUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticDao {
    public static SelfData getSelfData() throws SQLException {
        DBUtil dbUtil =  new DBUtil();
        SelfData sd = new SelfData();
        String sql = "select name,SUM(amount) amount from foodorder,foodinorder,foods where foodorder.id = foodinorder.orderid  and foods.id = foodinorder.foodid group by name";
        dbUtil.prepared(sql);
        ResultSet rs = dbUtil.select();
        JSONArray name = new JSONArray();
        JSONArray data = new JSONArray();
        while (rs.next()){
            name.put(rs.getString("name"));
            JSONObject temp = new JSONObject();
            temp.put("value",rs.getInt("amount"));
            temp.put("name",rs.getString("name"));
            data.put(temp);
        }
        sd.setName(name);
        sd.setData(data);
        return sd;
    }
}
