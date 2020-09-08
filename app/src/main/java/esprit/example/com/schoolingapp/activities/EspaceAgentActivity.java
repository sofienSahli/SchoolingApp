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
import android.widget.Toast;

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
import esprit.example.com.schoolingapp.fragment.FragmentDemandeFIche;
import esprit.example.com.schoolingapp.fragment.FragmentFicheApprouve;
import esprit.example.com.schoolingapp.local_storage.AgentSharedPreference;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspaceAgentActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int JOB_SERVICE_ID = 101;
    BottomNavigationView bottomNavigationView2;
    FragmentDemandeFIche fragmentDemandeFIche;
    FragmentFicheApprouve fragmentFicheApprouve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_agent);
        bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView2.setOnNavigationItemSelectedListener(this);
        getWindow().setEnterTransition(new Fade());
       /* JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName name = new ComponentName(this, FicheJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_SERVICE_ID, name).
                setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        jobScheduler.schedule(jobInfo);
        */
        fragmentDemandeFIche = new FragmentDemandeFIche();
        fragmentFicheApprouve = new FragmentFicheApprouve();
        getFichesPFE();

    }

    public void getFichesPFE() {
        FicheServices ficheServices = new FicheServices();
        ficheServices.get_unseen_fiches(new Callback<List<FichePFE>>() {
            @Override
            public void onResponse(Call<List<FichePFE>> call, Response<List<FichePFE>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        int untreated_count = 0;
                        for (FichePFE f : response.body()) {
                            if (f.getEnseignant() == null)
                                untreated_count++;
                        }

                        BadgeDrawable badgeDrawable = bottomNavigationView2.getOrCreateBadge(R.id.fichePFE);
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setBackgroundColor(getColor(R.color.colorAccent));
                        badgeDrawable.setBadgeTextColor(Color.WHITE);
                        badgeDrawable.setNumber(untreated_count);
                        List<FichePFE> fichePFES = new ArrayList<>(response.body());

                        fragmentDemandeFIche.setFichePFES(response.body());
                        if (fragmentDemandeFIche.getFicheAdapter() != null) {
                            fragmentDemandeFIche.getFicheAdapter().setPfeList(response.body());
                            fragmentDemandeFIche.getFicheAdapter().notifyDataSetChanged();
                        }
                        commintFragment(fragmentDemandeFIche);

                    }


                }
            }

            @Override
            public void onFailure(Call<List<FichePFE>> call, Throwable t) {
                Log.e("Fiche PFE service ", t.getMessage());
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.fichePFE) {
            commintFragment(fragmentDemandeFIche);
        } else if (menuItem.getItemId() == R.id.reclamation) {
            commintFragment(fragmentFicheApprouve);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            AgentSharedPreference sharedPreference = new AgentSharedPreference(getSharedPreferences(AgentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
            sharedPreference.getSharedPreferences().edit().clear().apply();
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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