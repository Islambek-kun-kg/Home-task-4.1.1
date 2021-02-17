package com.example.lesson_411.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson_411.R;
import com.example.lesson_411.ui.ItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements ItemClickListener {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    public static final String CHECK = "Check";
    public static final String CHECK_KEY = "Check_Key";
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
        adapter.setClickListener(this);
    }

    public View onCreateView(@NonNull LayoutInflater i, ViewGroup c, Bundle s) {
        return i.inflate(R.layout.fragment_home, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.noteFragment);
        });
        getParentFragmentManager().setFragmentResultListener(NoteFragment.KEY_FOR_TXT,
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String text = result.getString(NoteFragment.KEY_TXT);
                        adapter.addItem(text);
                    }
                });
        getParentFragmentManager().setFragmentResultListener(NoteFragment.KEY_FOR_EDT_TXT,
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String txt = result.getString(NoteFragment.KEY_EDT_TXT);
                        adapter.getList().set(position, txt);
                        adapter.notifyItemChanged(position);
                    }
                });
    }

    @Override
    public void onItemClick(int position, String txt) {
        Bundle bundle = new Bundle();
        bundle.putString(CHECK, txt);
        getParentFragmentManager().setFragmentResult(CHECK_KEY, bundle);
        this.position = position;
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.noteFragment);
    }
}