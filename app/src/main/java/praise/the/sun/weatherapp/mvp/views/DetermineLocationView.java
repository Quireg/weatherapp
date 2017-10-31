package praise.the.sun.weatherapp.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Date 10/31/2017.
 *
 * @author Artur Menchenko
 */

@StateStrategyType(AddToEndStrategy.class)
public interface DetermineLocationView extends MvpView {
    void setIdleState();

    void setLocationLookupState();

    void setFetchingDataState();

    void setLocationLookuFailureState();

    void setFetchingDataFailureState();

}
