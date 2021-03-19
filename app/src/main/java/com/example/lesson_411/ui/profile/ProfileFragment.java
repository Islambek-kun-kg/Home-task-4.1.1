package com.example.lesson_411.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lesson_411.Preferences;
import com.example.lesson_411.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private CircleImageView circleImage;
    private String temp;
    private TextView txtName;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference storageRef = storage.getReference();
    private final StorageReference spaceRef = storageRef.child("images/profileImage.jpg");
    private final DocumentReference referenceToDoc =
            FirebaseFirestore.getInstance().collection("users").document("User: 1");


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preferences.getInstance().getSave() != null) {
            temp = Preferences.getInstance().getSave();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleImage = view.findViewById(R.id.imgView1);
        circleImage.setOnClickListener(v -> openSomeActivityForResult());
        txtName = view.findViewById(R.id.nameTxtProfile);
        setHasOptionsMenu(true);
        getParentFragmentManager().setFragmentResultListener(EditProfileFragment.KEY, getViewLifecycleOwner(), (requestKey, result) -> {
            String name = result.getString(EditProfileFragment.KEYS);
            txtName.setText(name);
        });
        if (Preferences.getInstance().getImg() != null) {
            String imageUri = Preferences.getInstance().getImg();
            Uri uri = Uri.parse(imageUri);
            Glide.with(getContext())
                    .load(uri)
                    .into(circleImage);
        } else {
            referenceToDoc.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String s = task.getResult().getString("image");
                    if (!(s == null || s.isEmpty())) {
                        Glide.with(getContext())
                                .load(Uri.parse(s))
                                .into(circleImage);
                        Preferences.getInstance().saveImg(s);
                    } else {
                        Log.e("error", "there is no image!!!");
                    }
                }
            });
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                circleImage.setImageURI(uri);
                UploadTask uploadTask = spaceRef.putFile(uri);
                uploadTask.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        uploadTask.continueWithTask(task1 -> {
                            if (!task1.isSuccessful()) {
                                throw task1.getException();
                            }
                            return spaceRef.getDownloadUrl();
                        }).addOnCompleteListener(task12 -> {
                            if (task12.isSuccessful()) {
                                Preferences.getInstance().saveImg(task12.getResult().toString());
                                Map<String, Object> data = new HashMap<>();
                                data.put("image", task12.getResult().toString());
                                referenceToDoc.set(data);
                                Toast.makeText(getContext(), "image saved!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("error", "can not able to get uri: ");
                            }
                        });
                    }
                });
            });

    public void openSomeActivityForResult() {
        mGetContent.launch("image/*");
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
}