package com.example.lesson_411.ui.home;

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

import com.example.lesson_411.App;
import com.example.lesson_411.R;
import com.example.lesson_411.models.NoteModel;

public class NoteFragment extends Fragment {
    private NoteModel noteModel;
    private EditText edtTxt;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle s) {
        return i.inflate(R.layout.fragment_note, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        edtTxt = v.findViewById(R.id.edtTxt);
        noteModel = (NoteModel) requireArguments().getSerializable(HomeFragment.CHECK);
        if (noteModel != null) edtTxt.setText(noteModel.getTitle());
        v.findViewById(R.id.btnSave).setOnClickListener(v1 -> {
            String txt = edtTxt.getText().toString().trim();
            if (noteModel == null) {
                noteModel = new NoteModel(txt);
                App.getDataBase().noteModelDao().insert(noteModel);
            } else {
                noteModel.setTitle(txt);
                App.getDataBase().noteModelDao().update(noteModel);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(HomeFragment.CHECK, noteModel);
            getParentFragmentManager().setFragmentResult(HomeFragment.CHECK_KEY, bundle);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }
}