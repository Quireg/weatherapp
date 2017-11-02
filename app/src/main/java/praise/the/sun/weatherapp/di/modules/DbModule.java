package praise.the.sun.weatherapp.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import praise.the.sun.weatherapp.db.DbService;

/**
 * Date 11/1/2017.
 *
 * @author Artur Menchenko
 */
@Module
public class DbModule {
    @Provides
    @Singleton
    public DbService provideDb(Context context) {
        return new DbService();
    }
}
