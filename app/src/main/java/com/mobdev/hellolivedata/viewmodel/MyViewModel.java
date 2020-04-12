package com.mobdev.hellolivedata.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Marco Picone picone.m@gmail.com on 12,April,2020
 * Mobile System Development - University Course
 * Example View Model Class to show how dynamically refresh data through the observe pattern
 */
public class MyViewModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<String> currentValue;

    public MutableLiveData<String> getCurrentValue() {
        if (currentValue == null) {
            currentValue = new MutableLiveData<String>();
        }
        return currentValue;
    }

}
