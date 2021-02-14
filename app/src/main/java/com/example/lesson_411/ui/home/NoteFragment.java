package com.example.lesson_411.ui.home;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson_411.R;
import com.example.lesson_411.ui.useGallery.GalleryUseFragment;
import com.google.android.material.imageview.ShapeableImageView;

public class NoteFragment extends Fragment {
    private ShapeableImageView shapeableImageView;
    private ActivityResultLauncher<String> content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
        });
        shapeableImageView = view.findViewById(R.id.imgView1);
        shapeableImageView.setOnClickListener(v -> {
            NoteFragment.this.openGallery();
        });
        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                shapeableImageView.setImageURI(result);
            }
        });
    }

    public void openGallery() {
        content.launch("key");
    }
}