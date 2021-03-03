package com.example.lesson_411.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.lesson_411.R;

public class EditProfileFragment extends Fragment {
    private Button btnSave;
    private EditText editText;
    public static final String keyProfile = "keyProfile", keyName = "keyName", key = "key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = view.findViewById(R.id.btnSaveForProfileFragment);
        editText = view.findViewById(R.id.editNameForProfileFragment);
        btnSave.setOnClickListener(v -> {
            save();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }

    public void save() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(keyName, editText.getText().toString()).apply();
        Bundle bundle = new Bundle();
        bundle.putSerializable(keyProfile, editText.getText().toString());
        getParentFragmentManager().setFragmentResult(key, bundle);
    }
}