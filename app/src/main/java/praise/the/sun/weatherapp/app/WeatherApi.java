package praise.the.sun.weatherapp.app;

import io.reactivex.Observable;
import praise.the.sun.weatherapp.models.Weather;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */

public interface WeatherApi {

    @GET("/data/2.5/weather")
    Observable<Weather> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("units") String units);

}
