package edu.black.util;


import javax.servlet.http.Cookie;

public class ResolveCookies {
    public static int[][] getCarInfo(Cookie cookie){
        if (cookie.getName().equals("car")){
            String value = cookie.getValue();
            String[] values = value.split(",");
            int foodItemNumber = values.length;
            int[][] carInfo = new int[foodItemNumber][2];
            for (int i=0;i<foodItemNumber;i++){
                String[] foodAndNum = values[i].split("/");
                carInfo[i][0] = Integer.parseInt(foodAndNum[0]);
                carInfo[i][1] = Integer.parseInt(foodAndNum[1]);
            }
            return carInfo;
        }else {
            return null;
        }
    }

    public static Cookie update(Cookie cookie,String id){
        String value = cookie.getValue();
        String[] values = value.split(",");
        boolean over = false;
        for (int i=0;i<values.length;i++){
            if (values[i].split("/")[0].equals(id)){
                 values[i]= values[i].split("/")[0]+"/"+String.valueOf(Integer.parseInt(values[i].split("/")[1])+1) ;
                 over = true;
            }
        }

        String result = "";
        for (int i=0;i<values.length;i++){
            if (Integer.parseInt(values[i].split("/")[1])==0){
                continue;
            }
            result+=","+values[i];
        }

        //判断是否跳过新增
        if (!over){
            String newValue = id+"/"+1;
            result += ","+newValue;
        }

        if (result.length()>1) {
            cookie.setValue(result.substring(1, result.length()));
        }else {
            cookie.setValue("0/0");
        }
        return cookie;
    }

    /**
     * 某个商品的数量减一
     * @param cookie
     * @param id
     * @return
     */
    public static Cookie remove(Cookie cookie,String id){
        String value = cookie.getValue();
        String[] values = value.split(",");
        for (int i=0;i<values.length;i++){
            if (values[i].split("/")[0].equals(id)){
                values[i]= values[i].split("/")[0]+"/"+String.valueOf(Integer.parseInt(values[i].split("/")[1])-1) ;
            }
        }

        String result = "";
        for (int i=0;i<values.length;i++){
            if (Integer.parseInt(values[i].split("/")[1])==0){
                continue;
            }
            result+=","+values[i];
        }

        if (result.length()>1) {
            cookie.setValue(result.substring(1, result.length()));
        }else {
            cookie.setValue("0/0");
        }
        return cookie;
    }

    /**
     * 从购物车中删除某一件商品
     * @param cookie
     * @param id
     * @return
     */
    public static Cookie delete(Cookie cookie,String id){
        String value = cookie.getValue();
        String[] values = value.split(",");
        for (int i=0;i<values.length;i++){
            if (values[i].split("/")[0].equals(id)){
                values[i]= values[i].split("/")[0]+"/"+0 ;
            }
        }

        String result = "";
        for (int i=0;i<values.length;i++){
            if (Integer.parseInt(values[i].split("/")[1])==0){
                continue;
            }
            result+=","+values[i];
        }

        if (result.length()>1) {
            cookie.setValue(result.substring(1, result.length()));
        }else {
            cookie.setValue("0/0");
        }
        return cookie;
    }
}
