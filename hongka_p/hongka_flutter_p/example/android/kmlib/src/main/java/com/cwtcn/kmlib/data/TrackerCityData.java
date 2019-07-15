package com.cwtcn.kmlib.data;

/**
 * 城市数据
 * Created by Allen on 2017/3/21.
 */

public class TrackerCityData {
    private String city;
    private String cityId;
    private String cnty;
    private double lat;
    private double lon;

    public TrackerCityData(String city, String cityId, String cnty, double lat, double lon) {
        this.city = city;
        this.cityId = cityId;
        this.cnty = cnty;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
