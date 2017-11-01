package praise.the.sun.weatherapp.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import praise.the.sun.weatherapp.app.WeatherApp;
import praise.the.sun.weatherapp.db.DbService;
import praise.the.sun.weatherapp.mvp.views.WeatherView;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

@InjectViewState
public class WeatherPresenter extends BasePresenter<WeatherView>  {

    private static final String LOG_TAG = WeatherPresenter.class.getSimpleName();

    @Inject DbService dbService;

    public WeatherPresenter() {
        WeatherApp.getAppComponent().inject(this);
    }

    public void showWeather(){
        Disposable d = dbService.get().subscribe(
                (weather) -> getViewState().showWeather(weather),
                (e) -> Log.e(LOG_TAG, e.getMessage())
        );
        disposeOnDestroy(d);

    }


}
