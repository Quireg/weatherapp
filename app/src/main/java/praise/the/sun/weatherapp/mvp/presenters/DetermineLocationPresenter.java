package praise.the.sun.weatherapp.mvp.presenters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import praise.the.sun.weatherapp.app.WeatherApp;
import praise.the.sun.weatherapp.db.DbService;
import praise.the.sun.weatherapp.models.Weather;
import praise.the.sun.weatherapp.mvp.WeatherAppService;
import praise.the.sun.weatherapp.mvp.views.DetermineLocationView;


/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

@InjectViewState
public class DetermineLocationPresenter extends BasePresenter<DetermineLocationView> {

    private static final String LOG_TAG = DetermineLocationPresenter.class.getSimpleName();

    @Inject
    Context mContext;
    @Inject
    WeatherAppService mWeatherAppService;
    @Inject
    DbService dbService;

    private Disposable weatherSubscription;
    private boolean isProcessing = false;

    public DetermineLocationPresenter() {
        WeatherApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setIdleState();

    }

    public void onImageTap() {
        if (isProcessing) {
            isProcessing = false;
            getViewState().setIdleState();

            if (weatherSubscription != null && !weatherSubscription.isDisposed()) {
                weatherSubscription.dispose();
            }
            return;
        }

        isProcessing = true;
        getViewState().setLocationLookupState();
        Location location = getLastBestLocation();

        if (location == null) {
            getViewState().setLocationLookupFailureState();
        } else {
            getViewState().setFetchingDataState(location);

            final Observable<Weather> observable = mWeatherAppService.getWeather(location.getLatitude(), location.getLongitude());
            weatherSubscription = observable
                    .subscribeOn(Schedulers.io())
                    .flatMap((w) -> dbService.save(w))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weather -> {
                        Log.d(LOG_TAG, weather.getName());
                        getViewState().setFetchingCompletedState();
                    }, error -> {
                        getViewState().setFetchingDataFailureState(error.getMessage());
                        Log.e(LOG_TAG, error.getMessage());
                    });

            disposeOnDestroy(weatherSubscription);
        }

    }

    public void onGpsFailure() {
        getViewState().setLocationLookupFailureState();
    }

    @Nullable
    @SuppressLint("MissingPermission")
    private Location getLastBestLocation() {
        if (!isGpsOk()) {
            getViewState().setLocationLookupFailureState();
            return null;
        }

        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return null;
        }

        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long gpslocationtime = 0;
        if (null != locationGPS) {
            gpslocationtime = locationGPS.getTime();
        }

        long netLocationTime = 0;

        if (null != locationNet) {
            netLocationTime = locationNet.getTime();
        }

        if (0 < gpslocationtime - netLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    private boolean isGpsOk() {
        return isGpsPermissionOk() && isGpsEnabled();
    }

    private boolean isGpsPermissionOk() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isGpsEnabled() {
        return Settings.Secure
                .isLocationProviderEnabled(mContext.getContentResolver(),
                        LocationManager.GPS_PROVIDER);
    }
}
