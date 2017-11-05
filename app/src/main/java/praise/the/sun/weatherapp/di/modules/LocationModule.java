package praise.the.sun.weatherapp.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import praise.the.sun.weatherapp.mvp.models.LocProvider;
import praise.the.sun.weatherapp.mvp.models.LocProviderImpl;

/**
 * Created by Artur Menchenko on 11/5/2017.
 */
@Module
public class LocationModule {

    @Provides
    @Singleton
    public LocProvider getProvider(Context context){
        return new LocProviderImpl(context);
    }
}
