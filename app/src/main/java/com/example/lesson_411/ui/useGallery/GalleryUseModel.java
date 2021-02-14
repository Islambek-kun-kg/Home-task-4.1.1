package com.example.lesson_411.ui.useGallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryUseModel extends ViewModel {
    private MutableLiveData<String> mText;

    public GalleryUseModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}