package com.example.lesson_411;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavController();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            navController.navigate(R.id.phoneFragment);
        if (!new Preferences(this).isShown()) navController.navigate(R.id.boardFragment);
    }

//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Выход")
//                .setMessage("Вы уверены?").setPositiveButton("Да", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        }).setNegativeButton("Нет", null).show();
//    }

    private void initNavController() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_gallery).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(R.id.navigation_home);
                list.add(R.id.navigation_dashboard);
                list.add(R.id.navigation_notifications);
                list.add(R.id.navigation_gallery);
                if (list.contains(destination.getId())) navView.setVisibility(View.VISIBLE);
                else navView.setVisibility(View.GONE);
                if (destination.getId() == R.id.boardFragment) getSupportActionBar().hide();
                else getSupportActionBar().show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}