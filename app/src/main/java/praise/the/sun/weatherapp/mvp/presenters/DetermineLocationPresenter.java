package praise.the.sun.weatherapp.mvp.presenters;

import android.content.Context;
import android.location.Location;
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
import praise.the.sun.weatherapp.mvp.models.LocProvider;
import praise.the.sun.weatherapp.mvp.models.WeatherAppService;
import praise.the.sun.weatherapp.mvp.views.DetermineLocationView;


/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

@InjectViewState
public class DetermineLocationPresenter extends BasePresenter<DetermineLocationView> {

    private static final String LOG_TAG = DetermineLocationPresenter.class.getSimpleName();

    @Inject Context mContext;
    @Inject WeatherAppService mWeatherAppService;
    @Inject DbService dbService;
    @Inject LocProvider locProvider;

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
        Location location = locProvider.provideLoc();

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

}
