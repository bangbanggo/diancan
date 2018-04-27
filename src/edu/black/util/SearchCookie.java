package edu.black.util;

import javax.servlet.http.Cookie;

public class SearchCookie {
    /**
     * 搜索所有Cookie值，找到需要的Cookie
     * @param cookies 所有的Cookie
     * @param cookiename 需要的Cookie名字
     * @return 如果存在返回该Cookie，不催在返回null
     */
    public static Cookie getCookie(Cookie[] cookies,String cookiename){
        Cookie cookie = null;
        if (cookies==null){
            return null;
        }
        for (Cookie c:cookies){
            if (cookiename.equals(c.getName())){
                cookie = c;
            }
        }
        return cookie;
    }

    /**
     * 搜索所有Cookie值，找到需要的Cookie
     * @param cookies 所有的Cookie
     * @param cookiename 需要的Cookie名字
     * @return 如果存在返回该Cookie，不催在返回new Cookie(cookiename,defaultvalue)
     */
    public static Cookie getCookie(Cookie[] cookies,String cookiename,String defaultvalue){
        Cookie cookie = getCookie(cookies,cookiename);
        if (cookie==null){
            cookie = new Cookie(cookiename,defaultvalue);
        }
        return cookie;
    }
}
