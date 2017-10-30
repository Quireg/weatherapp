package praise.the.sun.weatherapp.app;

import praise.the.sun.weatherapp.BuildConfig;
import praise.the.sun.weatherapp.mvp.models.Weather;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */

public interface WeatherApi {

    @GET("/data/2.5/weather")
    Observable<Weather> getWeather(@Query("lat") double lat, @Query("lon") double lon);

}
