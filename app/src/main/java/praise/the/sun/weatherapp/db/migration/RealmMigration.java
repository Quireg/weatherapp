package praise.the.sun.weatherapp.db.migration;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;

/**
 * Date 11/1/2017.
 *
 * @author Artur Menchenko
 */

public class RealmMigration implements io.realm.RealmMigration{

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("Weather")
                    .addField("title",String.class);

            oldVersion++;
        }
    }
}
