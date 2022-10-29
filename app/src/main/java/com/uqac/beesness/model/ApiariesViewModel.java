package com.uqac.beesness.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApiariesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ApiariesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is apiaries fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}