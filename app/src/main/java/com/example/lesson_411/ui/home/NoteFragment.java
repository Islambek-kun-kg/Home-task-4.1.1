package com.example.lesson_411.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lesson_411.App;
import com.example.lesson_411.R;
import com.example.lesson_411.models.NoteModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteFragment extends Fragment {
    private NoteModel noteModel;
    private EditText edtTxt;
    private boolean checkBtn = false;
    private String id;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle s) {
        return i.inflate(R.layout.fragment_note, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        edtTxt = v.findViewById(R.id.edtTxt);
        if (getArguments() != null) {
            noteModel = (NoteModel) requireArguments().getSerializable(HomeFragment.CHECK);
            id = getArguments().getString(HomeFragment.CHECK_KEY);
        }
        if (noteModel != null) {
            edtTxt.setText(noteModel.getTitle());
            checkBtn = true;
        }
        v.findViewById(R.id.btnSave).setOnClickListener(v1 -> {
            if (checkBtn) {
                updateFireStore(edtTxt.getText().toString());
            } else {
                saveToFireStore(edtTxt.getText().toString());
            }
        });
    }

    private void saveToFireStore(String txt) {
        NoteModel model = new NoteModel(txt);
        FirebaseFirestore.getInstance().collection("note").add(model).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                App.getDataBase().noteModelDao().insert(model);
                Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();
                Log.e("KKU", "onComplete: Note has been added");
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            } else Log.e("KKU", "onComplete: is failed");
        });
    }


    private void updateFireStore(String txt) {
        noteModel.setTitle(txt);
        FirebaseFirestore.getInstance().collection("note").document(id).set(noteModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        App.getDataBase().noteModelDao().update(noteModel);
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();
                        Log.e("KKU", "onComplete: Updated to" + noteModel.getTitle());
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
                    } else Log.e("KKU", "onComplete: failed to update");
                });
    }

}