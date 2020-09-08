package esprit.example.com.schoolingapp.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.fragment.FragmentAppelEnseignant;
import esprit.example.com.schoolingapp.fragment.FragmentEncadrement;
import esprit.example.com.schoolingapp.local_storage.EnseignantSharedPReferences;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspaceEnseignant extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    FragmentEncadrement ecnadrementFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_enseignant);
        commintFragment(new FragmentAppelEnseignant());
        getWindow().setEnterTransition(new Fade());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        ecnadrementFragment = new FragmentEncadrement();
        commintFragment(ecnadrementFragment);
        fetch_fiche_pfe();
        getSupportActionBar().setTitle("Proposition de fiche");
    }

    private void fetch_fiche_pfe() {
        EnseignantSharedPReferences enseignantSharedPReferences = new EnseignantSharedPReferences(getSharedPreferences(EnseignantSharedPReferences.USER_FILE, Context.MODE_PRIVATE));
        long id_enseignant = enseignantSharedPReferences.getLong(EnseignantSharedPReferences.USER_ID);
        FicheServices ficheServices = new FicheServices();
        ficheServices.fiche_for_enseignant_id(new Callback<List<FichePFE>>() {
            @Override
            public void onResponse(Call<List<FichePFE>> call, Response<List<FichePFE>> response) {
                if (response.code() == 200) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        int unseen = 0;
                        for (FichePFE f : response.body()) {
                            if (!f.isAcceptedByEnseignant())
                                unseen++;
                            Log.e("TAG", "onResponse: " + f.getEnseignant());
                        }

                        if (unseen > 0) {
                            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.fiche);
                            badgeDrawable.setVisible(true);
                            badgeDrawable.setBackgroundColor(getColor(R.color.colorAccent));
                            badgeDrawable.setBadgeTextColor(Color.WHITE);
                            badgeDrawable.setNumber(unseen);
                        }
                        ecnadrementFragment.setFichePFES((ArrayList<FichePFE>) response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FichePFE>> call, Throwable t) {
                Log.e("Fiche PFE for Enseignant", "onFailure: " + t.getMessage());
            }
        }, id_enseignant);
    }


    public void commintFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            getSharedPreferences(EnseignantSharedPReferences.USER_FILE, Context.MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
        /*    case R.id.emploie:
                commintFragment(new SingleDayFragment());
                setTitle("Emploie du temps");
                break;
            case R.id.appel:
                commintFragment(new FragmentAppelEnseignant());
                getSupportActionBar().setTitle("Appel");
                break;
            case R.id.note:
                commintFragment(new FragmentNote());
                getSupportActionBar().setTitle("Notes");
                break;
            case R.id.conge:
                commintFragment(new FragmentDemandeConge());
                getSupportActionBar().setTitle("Demande de congé");

                break;*/
            case R.id.fiche:
                commintFragment(ecnadrementFragment);
                getSupportActionBar().setTitle("Notificaitons");
                break;
        }

        return true;
    }

    @Override
    protected void onPostResume() {
        fetch_fiche_pfe();

        super.onPostResume();
    }
}