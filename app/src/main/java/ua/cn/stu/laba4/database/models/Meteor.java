package ua.cn.stu.laba4.database.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


//https://data.nasa.gov/api/id/gh4g-9sfh.json?$query=select%20*%2C%20%3Aid%20where%20(%60year%60%20%3E%3D%20%272002-04-01T00%3A00%3A00%27%20and%20%60year%60%20%3C%3D%20%272020-04-01T00%3A00%3A00%27)%20limit%20100
// https://data.nasa.gov/api/id/gh4g-9sfh.json?$query=select *, :id where (`year` >= '2002-04-01T00:00:00' and `year` <= '2020-04-01T00:00:00') limit 100
@Entity
public class Meteor implements Serializable {

    @PrimaryKey
    public int id;
    public String name;
    public String year;
    private String nametype;
    private double mass;
    private double reclat;
    private double reclong;
    private String recclass;
    private String fall;

    public String getNametype() {
        return nametype;
    }

    public double getMass() {
        return mass;
    }

    public double getReclat() {
        return reclat;
    }

    public double getReclong() {
        return reclong;
    }

    public String getRecclass() {
        return recclass;
    }

    public String getFall() {
        return fall;
    }

    public Meteor(int id, String name, String year, String nametype, double mass, double reclat, double reclong, String recclass, String fall) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.nametype = nametype;
        this.mass = mass;
        this.reclat = reclat;
        this.reclong = reclong;
        this.recclass = recclass;
        this.fall = fall;
    }

    public Meteor(int id, String name, String year, String data) {
        this.id = id;
        this.year = year;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setReclat(double reclat) {
        this.reclat = reclat;
    }

    public void setReclong(double reclong) {
        this.reclong = reclong;
    }

    public void setRecclass(String recclass) {
        this.recclass = recclass;
    }

    public void setFall(String fall) {
        this.fall = fall;
    }
}
