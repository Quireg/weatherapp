package praise.the.sun.weatherapp.mvp.presenters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import praise.the.sun.weatherapp.app.WeatherApp;
import praise.the.sun.weatherapp.mvp.WeatherAppService;
import praise.the.sun.weatherapp.mvp.models.Weather;
import praise.the.sun.weatherapp.mvp.views.DetermineLocationView;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

@InjectViewState
public class DetermineLocationPresenter extends BasePresenter<DetermineLocationView> {

    @Inject Context mContext;
    @Inject WeatherAppService weatherAppService;


    public DetermineLocationPresenter() {
        WeatherApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setIdleState();

    }

    public void startDetermingLocation(){
        getViewState().setLocationLookupState();
        Location location = getLastBestLocation();
        if(location == null){
            getViewState().setLocationLookuFailureState();
        }else{
            getViewState().setFetchingDataState();
            final Observable<Weather> observable = weatherAppService.getWeather(location.getLatitude(), location.getLongitude());
            Subscription subscription = observable
                    .subscribeOn(Schedulers.io())
                    .subscribe(weather -> {
                        System.out.println(weather.toString());
                    }, error -> {

                    });
            unsubscribeOnDestroy(subscription);
        }

    }




    @Nullable
    private Location getLastBestLocation(){
        if (!isGpsEnabled()) {
            return null;
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return null;
        }
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager == null){
            return null;
        }

        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }

    private boolean isGpsEnabled() {
        return Settings.Secure
                .isLocationProviderEnabled(mContext.getContentResolver(),
                        LocationManager.GPS_PROVIDER);
    }
}
