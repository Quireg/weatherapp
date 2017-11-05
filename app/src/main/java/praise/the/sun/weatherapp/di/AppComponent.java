package praise.the.sun.weatherapp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import praise.the.sun.weatherapp.di.modules.ContextModule;
import praise.the.sun.weatherapp.di.modules.DbModule;
import praise.the.sun.weatherapp.di.modules.WeatherAppModule;
import praise.the.sun.weatherapp.mvp.models.WeatherAppService;
import praise.the.sun.weatherapp.mvp.presenters.DetermineLocationPresenter;
import praise.the.sun.weatherapp.mvp.presenters.WeatherPresenter;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */
@Singleton
@Component(modules = {ContextModule.class, WeatherAppModule.class, DbModule.class})
public interface AppComponent {
    Context getContext();
    WeatherAppService getWeatherService();

    void inject(DetermineLocationPresenter determineLocationPresenter);
    void inject(WeatherPresenter weatherPresenter);

}
