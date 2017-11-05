package praise.the.sun.weatherapp.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import praise.the.sun.weatherapp.app.WeatherApi;
import praise.the.sun.weatherapp.mvp.models.WeatherAppService;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */
@Module(includes = {ApiModule.class})
public class WeatherAppModule {
    @Provides
    @Singleton
    public WeatherAppService provideWeatherAppService(WeatherApi weatherApi) {
        return new WeatherAppService(weatherApi);
    }
}
