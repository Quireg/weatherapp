package praise.the.sun.weatherapp.mvp.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import praise.the.sun.weatherapp.R;
import praise.the.sun.weatherapp.models.Weather;
import praise.the.sun.weatherapp.mvp.presenters.WeatherPresenter;
import praise.the.sun.weatherapp.mvp.views.WeatherView;

/**
 * Date 11/1/2017.
 *
 * @author Artur Menchenko
 */

public class WeatherActivity extends MvpAppCompatActivity implements WeatherView {

    @InjectPresenter
    WeatherPresenter mWeatherPresenter;

    @BindView(R.id.detail_city_name)
    TextView detail_city_name;
    @BindView(R.id.detail_date_textview)
    TextView detail_date_textview;
    @BindView(R.id.detail_temp_textview)
    TextView detail_temp_textview;
    @BindView(R.id.detail_high_low_textview)
    TextView detail_high_low_textview;
    @BindView(R.id.detail_humidity_textview)
    TextView detail_humidity_textview;
    @BindView(R.id.detail_pressure_textview)
    TextView detail_pressure_textview;
    @BindView(R.id.detail_wind_textview)
    TextView detail_wind_textview;
    @BindView(R.id.background)
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        mWeatherPresenter.showWeather();

    }

    @Override
    public void showWeather(Weather weather) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.earthfromspace);
        background.setImageBitmap(bitmap);

        DateTimeFormatter DATE_DISPLAY_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.systemDefault());

        detail_city_name.setText(String.format(Locale.getDefault(), "%s, %s", weather.getName(), weather.getSys().getCountry()));
        detail_date_textview.setText(String.format(Locale.getDefault(), "Last updated:\n%s", DATE_DISPLAY_FORMAT.format(Instant.ofEpochSecond(weather.getDt()))));
        detail_temp_textview.setText(String.format(Locale.getDefault(), "%.0f%s", weather.getMain().getTemp(), (char) 0x00B0));
        detail_high_low_textview.setText(String.format(Locale.getDefault(), "Min/max %.0f%s/%.0f%s", weather.getMain().getTempMin(), (char) 0x00B0, weather.getMain().getTempMax(), (char) 0x00B0));
        detail_humidity_textview.setText(String.format(Locale.getDefault(), "Humidity %d%%", weather.getMain().getHumidity()));
        detail_pressure_textview.setText(String.format(Locale.getDefault(), "Pressure %dhPa", weather.getMain().getPressure()));
        detail_wind_textview.setText(String.format(Locale.getDefault(), "Wind %.0f%s %.0fmps ", weather.getWind().getDeg(), (char) 0x00B0, weather.getWind().getSpeed()));

    }
}
