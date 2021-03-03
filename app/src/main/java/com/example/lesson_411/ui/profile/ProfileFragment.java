package com.example.lesson_411.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lesson_411.App;
import com.example.lesson_411.R;
import com.example.lesson_411.ui.home.HomeFragment;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private CircleImageView circleImageView;
    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<String> content;
    private TextView txtName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleImageView = view.findViewById(R.id.imgView1);
        txtName = view.findViewById(R.id.nameTxtProfile);
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String a = sharedPreferences.getString(EditProfileFragment.keyName, "");
        txtName.setText(sharedPreferences.getString("name", a));
        txtName.setOnLongClickListener(v -> {
            clearShared();
            return true;
        });
        circleImageView.setOnClickListener(v -> {
            ProfileFragment.this.content.launch("image/*");
        });
        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                circleImageView.setImageURI(result);
            }
        });
        getParentFragmentManager().setFragmentResultListener(EditProfileFragment.key, getViewLifecycleOwner(), (requestKey, result) -> {
            txtName.setText(result.getString(EditProfileFragment.keyProfile));
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popap_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editForGalleryFragment) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.editFragmentForProfileFragment);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    public void clearShared() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        txtName.setText("");
    }
}