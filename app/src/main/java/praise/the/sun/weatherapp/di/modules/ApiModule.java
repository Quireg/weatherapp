package praise.the.sun.weatherapp.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import praise.the.sun.weatherapp.app.WeatherApi;
import retrofit2.Retrofit;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public WeatherApi provideApi(Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }
}
