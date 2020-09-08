package esprit.example.com.schoolingapp.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.fragment.DashboardEtudiant;
import esprit.example.com.schoolingapp.fragment.EtudiantHomeFragment;
import esprit.example.com.schoolingapp.fragment.NotificationEtudiant;
import esprit.example.com.schoolingapp.local_storage.StudentSharedPreference;

public class EspaceEtudiant extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_etudiant);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Fade());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setOnNavigationItemSelectedListener(this);
        getSupportActionBar().setCustomView(R.layout.tool_bar);
        commintFragment(new EtudiantHomeFragment());
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        Intent intent = new Intent(this, NouvelleFichePFE.class);
        startActivity(intent , ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        // Initialize Places.
        //Places.initialize(getApplicationContext(), getString(R.string.gmap_key));

// Create a new Places client instance.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_button, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            StudentSharedPreference studentSharedPreference = new StudentSharedPreference(getSharedPreferences(StudentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
                studentSharedPreference.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                commintFragment(new EtudiantHomeFragment());
                getSupportActionBar().setTitle("Home");
                break;
            case R.id.navigation_dashboard:
                commintFragment(new DashboardEtudiant());
                getSupportActionBar().setTitle("Dashboard");
                break;
            case R.id.navigation_notifications:
                commintFragment(new NotificationEtudiant());
                getSupportActionBar().setTitle("Notification");
                break;

        }
        return true;
    }


    public void commintFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}