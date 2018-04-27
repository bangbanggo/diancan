package edu.black.util;

import edu.black.dao.OrderDao;
import edu.black.dao.UserDao;
import edu.black.entity.Order;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Verification {
    public static boolean verificate(HttpSession session) throws SQLException {
        return UserDao.verificate((String)session.getAttribute("username"),(String)session.getAttribute("userid"));
    }

    public static boolean verificateNormalUser(HttpSession session) throws SQLException {
        return UserDao.verificateNormalUser((String)session.getAttribute("username"),(String)session.getAttribute("userid"));
    }
}
