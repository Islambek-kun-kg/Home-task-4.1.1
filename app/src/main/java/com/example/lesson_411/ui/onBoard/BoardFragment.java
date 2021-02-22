package com.example.lesson_411.ui.onBoard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson_411.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class BoardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle s) {
        return i.inflate(R.layout.fragment_board, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SpringDotsIndicator dots = view.findViewById(R.id.dots);
        ViewPager2 v = view.findViewById(R.id.viewPager);
        Button btnSkip = view.findViewById(R.id.btnSkipForBoard);
        BoardAdapter adapter = new BoardAdapter();
        v.setAdapter(adapter);
        dots.setViewPager2(v);
        adapter.setListener(new BoardAdapter.onStartClickListener() {
            @Override
            public void onClick() {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            }
        });
        btnSkip.setOnClickListener(v1 -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }
}