package praise.the.sun.weatherapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import praise.the.sun.weatherapp.models.Weather;
import praise.the.sun.weatherapp.mvp.presenters.WeatherPresenter;
import praise.the.sun.weatherapp.mvp.views.WeatherView;

/**
 * Date 11/1/2017.
 *
 * @author Artur Menchenko
 */

public class WeatherActivity extends MvpAppCompatActivity implements WeatherView {

    @InjectPresenter WeatherPresenter mWeatherPresenter;

    @BindView(R.id.detail_day_textview) TextView detail_day_textview;
    @BindView(R.id.detail_date_textview) TextView detail_date_textview;
    @BindView(R.id.detail_high_textview) TextView detail_high_textview;
    @BindView(R.id.detail_low_textview) TextView detail_low_textview;
    @BindView(R.id.detail_humidity_textview) TextView detail_humidity_textview;
    @BindView(R.id.detail_pressure_textview) TextView detail_pressure_textview;
    @BindView(R.id.detail_wind_textview) TextView detail_wind_textview;
    @BindView(R.id.detail_forecast_textview) TextView detail_forecast_textview;
    @BindView(R.id.detail_icon) ImageView detail_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        mWeatherPresenter.showWeather();

    }

    @Override
    public void showWeather(Weather weather) {
        detail_day_textview.setText(String.format(Locale.getDefault(), "%d",weather.getDt()));
        detail_date_textview.setText(String.format(Locale.getDefault(), "%d", weather.getDt()));
        detail_high_textview.setText(String.format(Locale.getDefault(), "%f", weather.getMain().getTempMax()));
        detail_low_textview.setText(String.format(Locale.getDefault(), "%f", weather.getMain().getTempMin()));
        detail_humidity_textview.setText(String.format(Locale.getDefault(), "%d", weather.getMain().getHumidity()));
        detail_pressure_textview.setText(String.format(Locale.getDefault(), "%d", weather.getMain().getPressure()));
        detail_wind_textview.setText(String.format(Locale.getDefault(), "%f%s%f ",weather.getWind().getDeg(), (char) 0x00B0, weather.getWind().getSpeed()));
        //detail_forecast_textview.setText(String.format(Locale.getDefault(), "%s ",weather.getSys()));

    }
}
