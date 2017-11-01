package praise.the.sun.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import praise.the.sun.weatherapp.mvp.presenters.DetermineLocationPresenter;
import praise.the.sun.weatherapp.mvp.presenters.WeatherPresenter;
import praise.the.sun.weatherapp.mvp.views.DetermineLocationView;
import praise.the.sun.weatherapp.mvp.views.WeatherView;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements DetermineLocationView, WeatherView {

    @InjectPresenter DetermineLocationPresenter mDetermineLocationPresenter;
    @InjectPresenter WeatherPresenter mWeatherPresenter;

    @BindView(R.id.rippleBackground) RippleBackground rippleBackground;
    @BindView(R.id.centerImage)ImageView rippleImageView;
    @BindView(R.id.loading) TextView loadingText;

    public static final int REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDetermineLocationPresenter.startDetermingLocation();

        rippleImageView.setOnClickListener(view -> {
            initGps();
            mDetermineLocationPresenter.startDetermingLocation();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults[0] == PERMISSION_GRANTED){

            }else{
                //
            }
        }

    }

    private void initGps(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION
            );
        }
    }

    @Override
    public void setIdleState() {
        rippleBackground.stopRippleAnimation();
        loadingText.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setLocationLookupState() {
        rippleBackground.startRippleAnimation();
        loadingText.setVisibility(View.VISIBLE);
        loadingText.setText("Determining your location...");
    }

    @Override
    public void setFetchingDataState() {
        rippleBackground.startRippleAnimation();
        loadingText.setVisibility(View.VISIBLE);
        loadingText.setText("Fetching data...");

    }

    @Override
    public void setLocationLookupFailureState() {
        rippleBackground.stopRippleAnimation();
        loadingText.setText("Failed to determine your location =(");
    }

    @Override
    public void setFetchingDataFailureState() {
        rippleBackground.stopRippleAnimation();
        loadingText.setText("Failed to fetch weather data for your location =(");
    }

    @Override
    public void showWeather() {

    }
}
