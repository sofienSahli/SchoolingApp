package esprit.example.com.schoolingapp.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.local_storage.AgentSharedPreference;
import esprit.example.com.schoolingapp.local_storage.EnseignantSharedPReferences;
import esprit.example.com.schoolingapp.local_storage.StudentSharedPreference;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {
    ImageView logo;
    boolean isLoggedIn;
    boolean isAgentLoggedIn;
    boolean isEnseignantLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setExitTransition(new AutoTransition());
        getWindow().setEnterTransition(new AutoTransition());
        StudentSharedPreference studentSharedPreference = new StudentSharedPreference(getSharedPreferences(StudentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
        isLoggedIn = studentSharedPreference.isUserLogged();
        isAgentLoggedIn = false;
        isEnseignantLoggedIn = false;
        if (!isLoggedIn) {
            AgentSharedPreference agentSharedPreference = new AgentSharedPreference(getSharedPreferences(AgentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
            isAgentLoggedIn = agentSharedPreference.isUserLogged();
        }
        if (!isAgentLoggedIn){
            EnseignantSharedPReferences enseignantSharedPReferences = new EnseignantSharedPReferences(getSharedPreferences(EnseignantSharedPReferences.USER_FILE, Context.MODE_PRIVATE));
            isEnseignantLoggedIn = enseignantSharedPReferences.isUserLogged();
        }
        logo = findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_splash_anim);
        logo.startAnimation(animation);
        getSupportActionBar().hide();
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (isLoggedIn) {
            Intent intent = new Intent(SplashScreen.this, EspaceEtudiant.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());
        } else if (isAgentLoggedIn) {
            Intent intent = new Intent(SplashScreen.this, EspaceAgentActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());

        } else if (isEnseignantLoggedIn) {

            Intent intent = new Intent(SplashScreen.this, EspaceEnseignant.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());

        } else {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}