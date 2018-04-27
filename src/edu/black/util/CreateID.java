package edu.black.util;

import java.util.Random;

public class CreateID {
    public static String createClientID(){
        Random r = new Random();
        String time = String.valueOf(System.currentTimeMillis());
        String clientid = time.substring(time.length()-6,time.length())+
                String.valueOf(r.nextInt(10)*1
                                +r.nextInt(10)*10
                                +r.nextInt(10)*100
                                +r.nextInt(10)*1000);
        return clientid;
    }

    public static String createUserID(){
        Random r = new Random();
        String time = String.valueOf(System.currentTimeMillis());
        String clientid ="U" +time.substring(time.length()-6,time.length())+
                String.valueOf(r.nextInt(10)*1
                        +r.nextInt(10)*10
                        +r.nextInt(10)*100
                        +r.nextInt(10)*1000);
        return clientid;
    }

    public static String createCarID(){
        Random r = new Random();
        String time = String.valueOf(System.currentTimeMillis());
        String clientid = time.substring(time.length()-4,time.length())+
                String.valueOf(r.nextInt(10)*1
                        +r.nextInt(10)*10
                        +r.nextInt(10)*100
                        +r.nextInt(10)*1000);
        return clientid;
    }
}
