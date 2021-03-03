package com.example.lesson_411.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileFragmentModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ProfileFragmentModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}