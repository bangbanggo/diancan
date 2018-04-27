package edu.black.util;

import javax.servlet.ServletContext;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class DBUtil {
    private String url = null;
    private String user = null;
    private String password = null;
    private String driver = null;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Connection conn;
    public final String seperater  = " ";

    public DBUtil(){
        this.url = DBData.getUrl();
        this.password = DBData.getPassword();
        this.driver = DBData.getDriver();
        this.user = DBData.getUser();



        try {
            getConnection();
        } catch (SQLException e) {
            System.out.println("未获得连接");
            e.printStackTrace();
        }
    }

    private boolean init(){
        this.url = DBData.getUrl();
        this.password = DBData.getPassword();
        this.driver = DBData.getDriver();
        this.user = DBData.getUser();
        if (url== null && driver == null && user == null && password == null){
                System.out.println("没有初始化操作，请重新启动服务器。");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return init();
        }
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())+"-正常连接数据库。");
        return true;
    }

    public Connection getConnection() throws SQLException {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("严重错误，未找到对应的驱动包");
        }
        conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    public void close() throws SQLException {
        if (rs!=null){
            rs.close();
        }
        if (pstmt!=null){
            pstmt.close();
        }
        if (conn!=null){
            conn.close();
        }
    }

    public void prepared(String sql, Object ... objects) throws SQLException {
        pstmt = conn.prepareStatement(sql);
        for (int i=0;i<objects.length;i++){
            pstmt.setObject(i+1,objects[i]);
        }
    }

    public ResultSet select() throws SQLException {
        rs = pstmt.executeQuery();
        return rs;
    }

    public int update() throws SQLException {
        return pstmt.executeUpdate();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
