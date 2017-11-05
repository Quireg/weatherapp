package praise.the.sun.weatherapp.mvp.models;

import android.location.Location;

/**
 * Created by Artur Menchenko on 11/5/2017.
 */

public interface LocProvider {
    Location provideLoc();
}
