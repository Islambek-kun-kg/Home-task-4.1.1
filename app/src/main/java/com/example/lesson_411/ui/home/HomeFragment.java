package com.example.lesson_411.ui.home;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson_411.App;
import com.example.lesson_411.R;
import com.example.lesson_411.ui.interfaces.ItemClickListener;
import com.example.lesson_411.models.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment implements ItemClickListener {
    public static final String CHECK = "Check", CHECK_KEY = "Check_Key";
    private NoteAdapter adapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference reference = db.collection("note");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteAdapter.setKEY(getContext());
        adapter = NoteAdapter.getKEY();
        adapter.setClickListener(this);
        setHasOptionsMenu(true);
        loadData();
    }

    private void loadData() {
        reference.addSnapshotListener(requireActivity(), (value, error) -> {
            assert value != null;
            adapter.getList().clear();
            adapter.getDocumentSnapshotArrayList().clear();
            adapter.getSearchList().clear();
            adapter.getDocumentSnapshotArrayList().addAll(value.getDocuments());
            value.forEach(queryDocumentSnapshot -> {
                NoteModel model = queryDocumentSnapshot.toObject(NoteModel.class);
                adapter.getList().add(model);
                adapter.getSearchList().add(model);
            });
            adapter.notifyDataSetChanged();
        });
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
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.noteFragment);
        });
    }

    @Override
    public void onItemClick(int position, String id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHECK, adapter.getItem(position));
        bundle.putString(CHECK_KEY, id);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.noteFragment, bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popup_menu_for_home_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortForHomeFragment) {
            adapter.getList().clear();
            adapter.getList().addAll(App.getDataBase().noteModelDao().sortAll());
            adapter.notifyDataSetChanged();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}