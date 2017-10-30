package praise.the.sun.weatherapp.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */

public class Coord {

    @SerializedName("lon")
    @Expose
    private Integer lon;
    @SerializedName("lat")
    @Expose
    private Integer lat;

    public Integer getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

}