package praise.the.sun.weatherapp.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import praise.the.sun.weatherapp.models.Weather;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherView extends MvpView {
    void showWeather(Weather weather);
}
