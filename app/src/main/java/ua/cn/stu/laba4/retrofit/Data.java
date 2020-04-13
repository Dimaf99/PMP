package ua.cn.stu.laba4.retrofit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private int id;
    private String name;
    private String nametype;
    private double mass;
    private String year;
    private double reclat;
    private double reclong;
    private String recclass;
    private String fall;
    @JsonIgnore
    private String geolocation;

    public String getFall() {
        return fall;
    }

    public int getId() {
        return id;
    }

    public String getRecclass() {
        return recclass;
    }

    public String getName() {
        return name;
    }

    public String getNametype() {
        return nametype;
    }

    public double getMass() {
        return mass;
    }

    public String getYear() {
        return year;
    }

    public double getReclat() {
        return reclat;
    }

    public double getReclong() {
        return reclong;
    }
}
