package com.example.lesson_411.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson_411.App;
import com.example.lesson_411.R;
import com.example.lesson_411.ui.interfaces.ItemClickListener;
import com.example.lesson_411.models.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment implements ItemClickListener {
    public static final String CHECK = "Check", CHECK_KEY = "Check_Key";
    private boolean checkAdd = false;
    private int position;
    private NoteAdapter adapter;
    private List<NoteModel> list;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
        adapter.setClickListener(this);
        setHasOptionsMenu(true);
        loadData();
    }

    private void loadData() {
        list = App.getDataBase().noteModelDao().getAll();
        adapter.setList(list);
    }

    public View onCreateView(@NonNull LayoutInflater i, ViewGroup c, Bundle s) {
        return i.inflate(R.layout.fragment_home, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> {
            checkAdd = false;
            openNote(null);
        });
        getParentFragmentManager().setFragmentResultListener(CHECK_KEY,
                getViewLifecycleOwner(), (requestKey, result) -> {
                    NoteModel noteModel = (NoteModel) result.getSerializable(CHECK);
                    if (checkAdd) adapter.getList(position, noteModel);
                    else adapter.addItem(noteModel);
                });
    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        checkAdd = true;
        NoteModel noteModel = adapter.getItem(position);
        openNote(noteModel);
    }

    @Override
    public void onLongClick(int position) {
        new AlertDialog.Builder(getContext()).setTitle("Are sure about that?").setMessage("Are you sure??")
                .setPositiveButton("Yes!", (dialog, which) -> {
                    App.getDataBase().noteModelDao().delete(adapter.getItem(position));
                    App.getDataBase().noteModelDao().update(adapter.getItem(position));
                    adapter.remove(position);
                }).setNegativeButton("No...", null).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popup_menu_for_home_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortForHomeFragment) {
            adapter.sortList(App.getDataBase().noteModelDao().sortAll());
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    public void openNote(NoteModel noteModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHECK, noteModel);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.noteFragment, bundle);
    }
}