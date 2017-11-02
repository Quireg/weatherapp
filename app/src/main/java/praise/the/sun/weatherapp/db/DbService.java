package praise.the.sun.weatherapp.db;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import praise.the.sun.weatherapp.db.migration.RealmMigration;
import praise.the.sun.weatherapp.models.Weather;

/**
 * Date 11/1/2017.
 *
 * @author Artur Menchenko
 */

public class DbService {

    private RealmConfiguration mConfig = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(new RealmMigration())
            .build();

    public <T extends RealmObject> Observable<Weather> save(Weather object) {
        Realm realm = Realm.getInstance(mConfig);

        long id;

        try {
            id = realm.where(Weather.class).max("dt").longValue() + 1;
        } catch (Exception e) {
            id = 0L;
        }
        object.setId(id);

        return Observable.just(object)
                .flatMap(t -> Observable.just(t)
                        .doOnSubscribe((o) -> {
                            realm.beginTransaction();
                        })
                        .doFinally(() -> {
                            realm.commitTransaction();
                            realm.close();
                        })
                        .doOnNext((realm::copyToRealmOrUpdate))
                );
    }

    public <T extends RealmObject> Observable<Weather> get() {
        Realm realm = Realm.getInstance(mConfig);

        return Observable.just(Weather.class)
                .flatMap(t -> Observable.just(t)
                        .map(type -> realm.where(type).findAllSorted("dt").last())
                );

    }
}
