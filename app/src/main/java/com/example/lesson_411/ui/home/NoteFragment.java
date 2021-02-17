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
    public static final String KEY_FOR_TXT = "Key1";
    public static final String KEY_FOR_EDT_TXT = "Key2";
    public static final String KEY_TXT = "K1";
    public static final String KEY_EDT_TXT = "K2";

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
            if (checkBtn) saveAfterEditing();
            else save();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }

    public void save() {
        String text = edtTxt.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TXT, text);
        getParentFragmentManager().setFragmentResult(KEY_FOR_TXT, bundle);
    }

    public void saveAfterEditing() {
        String txt = edtTxt.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_EDT_TXT, txt);
        getParentFragmentManager().setFragmentResult(KEY_FOR_EDT_TXT, bundle);
    }
}