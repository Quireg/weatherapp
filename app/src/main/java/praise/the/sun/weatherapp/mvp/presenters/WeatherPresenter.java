package praise.the.sun.weatherapp.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;

import praise.the.sun.weatherapp.app.WeatherApp;
import praise.the.sun.weatherapp.mvp.views.WeatherView;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

@InjectViewState
public class WeatherPresenter extends BasePresenter<WeatherView>  {

    public WeatherPresenter() {
        WeatherApp.getAppComponent().inject(this);
    }


}
