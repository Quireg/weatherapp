package praise.the.sun.weatherapp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import praise.the.sun.weatherapp.di.modules.ContextModule;
import praise.the.sun.weatherapp.di.modules.WeatherAppModule;
import praise.the.sun.weatherapp.mvp.WeatherAppService;
import praise.the.sun.weatherapp.mvp.presenters.DetermineLocationPresenter;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */
@Singleton
@Component(modules = {ContextModule.class, WeatherAppModule.class})
public interface AppComponent {
    Context getContext();
    WeatherAppService getWeatherService();

    void inject(DetermineLocationPresenter determineLocationPresenter);

}
