package praise.the.sun.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import praise.the.sun.weatherapp.models.Weather;
import praise.the.sun.weatherapp.mvp.presenters.DetermineLocationPresenter;
import praise.the.sun.weatherapp.mvp.views.DetermineLocationView;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends MvpAppCompatActivity implements DetermineLocationView {

    @InjectPresenter DetermineLocationPresenter mDetermineLocationPresenter;

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
        rippleImageView.setOnClickListener(view -> {
            initGps();
            mDetermineLocationPresenter.onImageTap();
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
        if(rippleBackground.isRippleAnimationRunning()){
            rippleBackground.stopRippleAnimation();
        }
        loadingText.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setLocationLookupState() {
        changeRippleBackgroundAnimation(true);
        loadingText.setVisibility(View.VISIBLE);
        loadingText.setText("Determining your location...");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setFetchingDataState(Location location) {
        changeRippleBackgroundAnimation(true);
        loadingText.setVisibility(View.VISIBLE);
        loadingText.setText(String.format("Fetching data for coordinates:\n %f, %f ", location.getLongitude(), location.getLatitude()));
    }

    @Override
    public void setLocationLookupFailureState() {
        changeRippleBackgroundAnimation(false);
        loadingText.setText("Failed to determine your location =(");
    }

    @Override
    public void setFetchingDataFailureState(String errorMessage) {
        changeRippleBackgroundAnimation(false);
        loadingText.setText("Failed to fetch weather data for your location. Error message: " + errorMessage);
    }

    @Override
    public void setFetchingCompletedState() {
        changeRippleBackgroundAnimation(false);
        loadingText.setText("Blaze it!");

        Intent i =  new Intent(this, WeatherActivity.class);
        i.setAction(Intent.ACTION_MAIN);
        startActivity(i);
        finish();
    }

    private void changeRippleBackgroundAnimation(boolean shouldAnimate){
        if(rippleBackground == null){
            return;
        }
        if(rippleBackground.isRippleAnimationRunning()){
            if(!shouldAnimate){
                rippleBackground.stopRippleAnimation();
            }
        }
        if(!rippleBackground.isRippleAnimationRunning()){
            if(shouldAnimate){
                rippleBackground.startRippleAnimation();
            }
        }
    }
}
