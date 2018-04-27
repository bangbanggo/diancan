package edu.black.dao;

import edu.black.entity.Client;
import edu.black.util.CreateID;
import edu.black.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    /**
     * 根据客户ID得到个人信息
     * @param id
     * @return
     * @throws SQLException
     */
    public static Client getOneById(String id) throws SQLException {
        Client client = null;
        DBUtil dbUtil = new DBUtil();
        String sql = "select * from client where id = ? ";
        dbUtil.prepared(sql,id);
        ResultSet rs = dbUtil.select();
        if (rs.next()){
            client=new Client();
            client.setId(rs.getString("id"));
            client.setName(rs.getString("name"));
            client.setAddress(rs.getString("address"));
            client.setTaste(rs.getString("taste"));
            client.setTel(rs.getString("tel"));
        }
        dbUtil.close();
        return client;
    }

    /**
     * 获得用户所拥有的取餐人个人信息
     * @param userid
     * @return 取餐人集合
     * @throws SQLException
     */
    public static List<Client> getOneByUserid(String userid) throws SQLException {
        List<Client> list = new ArrayList<>();
        Client client = null;
        DBUtil dbUtil = new DBUtil();
        String sql = "select * from client where userid = ? ";
        dbUtil.prepared(sql,userid);
        ResultSet rs = dbUtil.select();
        while (rs.next()){
            client=new Client();
            client.setId(rs.getString("id"));
            client.setName(rs.getString("name"));
            client.setAddress(rs.getString("address"));
            client.setTaste(rs.getString("taste"));
            client.setTel(rs.getString("tel"));
            list.add(client);
        }
        dbUtil.close();
        return list;
    }

    public static boolean addClientInUser(Client client,String userid) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        dbUtil.getConn().setAutoCommit(false);
        boolean result = false;
        client.setId(CreateID.createClientID());
        String sqlclient =
                "INSERT INTO client\n" +
                        "           (tel\n" +
                        "           ,id\n" +
                        "           ,name\n" +
                        "           ,address\n" +
                        "           ,userid\n" +
                        "           ,taste\n" +
                        "           )\n" +
                        "     VALUES\n" +
                        "           (?" +
                        "           ,?" +
                        "           ,?" +
                        "           ,?" +
                        "           ,?" +
                        "           ,?)" ;
        dbUtil.prepared(sqlclient,client.getTel(),client.getId(),client.getName(),client.getAddress(),userid,client.getTaste());
        result = dbUtil.update()==1?true:false;
        if (!result){
            dbUtil.getConn().rollback();
            dbUtil.close();
            return addClientInUser(client,userid);
        }
        dbUtil.getConn().commit();
        dbUtil.close();
        return result;
    }
}
