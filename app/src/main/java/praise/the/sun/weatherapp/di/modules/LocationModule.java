package praise.the.sun.weatherapp.di.modules;

import javax.inject.Singleton;

import dagger.Provides;
import praise.the.sun.weatherapp.mvp.models.LocProvider;
import praise.the.sun.weatherapp.mvp.models.LocProviderImpl;

/**
 * Created by Artur Menchenko on 11/5/2017.
 */

public class LocationModule {

    @Provides
    @Singleton
    public LocProvider getProvider(){
        return new LocProviderImpl();
    }
}
