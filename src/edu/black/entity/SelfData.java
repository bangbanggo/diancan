package edu.black.entity;

import org.json.JSONArray;

public class SelfData {
    private String userid;
    private JSONArray name;
    private JSONArray data;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public JSONArray getName() {
        return name;
    }

    public void setName(JSONArray name) {
        this.name = name;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
