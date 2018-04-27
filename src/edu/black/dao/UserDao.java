package edu.black.dao;

import edu.black.entity.User;
import edu.black.util.CreateID;
import edu.black.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    /**
     * 用户登录登陆
     * @param username
     * @param password
     * @return 一个用户携带用户名和用户ID
     * @throws SQLException
     */
    public static User login(String username,String password) throws SQLException {
        boolean result = false;
        User user = null;
        DBUtil dbUtil = new DBUtil();
        String sql = "select * from clientuser where username= ?  and password = ?";
        dbUtil.prepared(sql,username,password);
        ResultSet rs = dbUtil.select();
        if (rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setUsername(rs.getString("username"));
        }
        dbUtil.close();
        return user;
    }

    /**
     * 系统用户登录
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static User backlogin(String username,String password) throws SQLException {
        boolean result = false;
        User user = null;
        DBUtil dbUtil = new DBUtil();
        String sql = "select * from systemuser where username= ?  and password = ?";
        dbUtil.prepared(sql,username,password);
        ResultSet rs = dbUtil.select();
        if (rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setUsername(rs.getString("username"));
        }
        dbUtil.close();
        return user;
    }

    public static User register(User user) throws SQLException {
        user.setId(CreateID.createUserID());
        DBUtil dbUtil = new DBUtil();
        String sql = "insert into clientuser (id,username,password) values(?,?,?)";
        dbUtil.prepared(sql,user.getId(),user.getUsername(),user.getPassword());
        boolean result = dbUtil.update()==1?true:false;
        dbUtil.close();
        if (result){
            return user;
        }else {
            return null;
        }
    }

    /**
     * 管理员验证
     * @param username
     * @param userid
     * @return
     * @throws SQLException
     */
    public static boolean verificate(String username,String userid) throws SQLException {
        boolean result = false;
        DBUtil dbUtil = new DBUtil();
        String sql = "select * from systemuser where username= ?  and id = ?";
        dbUtil.prepared(sql,username,userid);
        ResultSet rs = dbUtil.select();
        if (rs.next()){
            dbUtil.close();
            return true;
        }else {
            dbUtil.close();
            return false;
        }
    }

    /**
     * 普通用户验证
     * @param username
     * @param userid
     * @return
     * @throws SQLException
     */
    public static boolean verificateNormalUser(String username,String userid) throws SQLException {
        boolean result = false;
        DBUtil dbUtil = new DBUtil();
        String sql = "select * from clientuser where username= ?  and id = ?";
        dbUtil.prepared(sql,username,userid);
        ResultSet rs = dbUtil.select();
        if (rs.next()){
            dbUtil.close();
            return true;
        }else {
            dbUtil.close();
            return false;
        }
    }

}
