package praise.the.sun.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */

public class Rain extends RealmObject {

    @SerializedName("3h")
    @Expose
    private Integer _3h;

    public Integer get3h() {
        return _3h;
    }

    public void set3h(Integer _3h) {
        this._3h = _3h;
    }

}
