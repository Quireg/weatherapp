package praise.the.sun.weatherapp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import praise.the.sun.weatherapp.di.modules.ContextModule;
import praise.the.sun.weatherapp.di.modules.OkHttpModule;
import praise.the.sun.weatherapp.di.modules.RetrofitModule;

/**
 * Date 10/30/2017.
 *
 * @author Artur Menchenko
 */
@Singleton
@Component(modules = {ContextModule.class, RetrofitModule.class, OkHttpModule.class})
public interface AppComponent {
    Context getContext();

}
