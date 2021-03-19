package com.example.lesson_411.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lesson_411.Preferences;
import com.example.lesson_411.R;

public class EditProfileFragment extends Fragment {
    public static final String KEY = "Key", KEYS = "KeyS";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSaving = view.findViewById(R.id.btnSaveForProfileFragment);
        EditText etText = view.findViewById(R.id.editNameForProfileFragment);
        btnSaving.setOnClickListener(v -> {
            String text = etText.getText().toString();
            Bundle bundle = new Bundle();
            Preferences.getInstance().save(text);
            bundle.putString(KEYS, text);
            getParentFragmentManager().setFragmentResult(KEY, bundle);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }
}