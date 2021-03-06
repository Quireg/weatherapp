package praise.the.sun.weatherapp.mvp.models;

import praise.the.sun.weatherapp.app.WeatherApi;
import praise.the.sun.weatherapp.models.Weather;
import io.reactivex.Observable;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

public class WeatherAppService {

    private WeatherApi weatherApi;

    public WeatherAppService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }
    public Observable<Weather> getWeather(double lat, double lon){
        return weatherApi.getWeather(lat,lon, "metric");
    }

}
