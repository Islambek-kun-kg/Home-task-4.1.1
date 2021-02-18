package com.example.lesson_411.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lesson_411.R;

public class NoteFragment extends Fragment {
    public boolean checkBtn = false;
    private EditText edtTxt;
    public static final String KEY_FOR_TXT = "Key1", KEY_FOR_EDT_TXT = "Key2", KEY_TXT = "K1", KEY_EDT_TXT = "K2";

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle s) {
        return i.inflate(R.layout.fragment_note, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        Button btnSave = v.findViewById(R.id.btnSave);
        edtTxt = v.findViewById(R.id.edtTxt);
        getParentFragmentManager().setFragmentResultListener(HomeFragment.CHECK_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                edtTxt.setText(result.getString(HomeFragment.CHECK));
                checkBtn = true;
            }
        });
        btnSave.setOnClickListener(v1 -> {
            if (checkBtn) saveMet(KEY_EDT_TXT, KEY_FOR_EDT_TXT);
            else saveMet(KEY_TXT, KEY_FOR_TXT);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }

    public void saveMet(String key1, String key2) {
        Bundle bundle = new Bundle();
        bundle.putString(key1, edtTxt.getText().toString());
        getParentFragmentManager().setFragmentResult(key2, bundle);
    }
}