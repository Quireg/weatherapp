package praise.the.sun.weatherapp.app;

import android.app.Application;

import praise.the.sun.weatherapp.di.AppComponent;
import praise.the.sun.weatherapp.di.DaggerAppComponent;
import praise.the.sun.weatherapp.di.modules.ContextModule;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */

public class WeatherApp extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

}
