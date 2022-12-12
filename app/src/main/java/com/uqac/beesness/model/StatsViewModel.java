package com.uqac.beesness.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This class handles the data for the stats fragment
 */
public class StatsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stats fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}