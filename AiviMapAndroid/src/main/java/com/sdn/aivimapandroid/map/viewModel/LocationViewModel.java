package com.sdn.aivimapandroid.map.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 *
 * Class viewModel will be used to Observe the data and
 * location needed
 * @Todo : need to add that view model in Class AiviMapFragment
 */

public class LocationViewModel extends ViewModel {
    private MutableLiveData<List<LatLng>> locations;

    public LiveData<List<LatLng>> getLocation() {
        return locations;
    }

    public void setLocations(List<LatLng> listaya) {
        locations.setValue(listaya);
    }
}
