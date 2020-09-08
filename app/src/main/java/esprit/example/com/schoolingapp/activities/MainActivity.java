package esprit.example.com.schoolingapp.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.transition.Explode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Agent;
import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.entities.Student;
import esprit.example.com.schoolingapp.local_storage.AgentSharedPreference;
import esprit.example.com.schoolingapp.local_storage.EnseignantSharedPReferences;
import esprit.example.com.schoolingapp.local_storage.StudentSharedPreference;
import esprit.example.com.schoolingapp.services.implementations.AgentServices;
import esprit.example.com.schoolingapp.services.implementations.EnseignantServices;
import esprit.example.com.schoolingapp.services.implementations.StudentServices;
import esprit.example.com.schoolingapp.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText editTextTextEmailAddress, editTextTextPassword;
    RadioButton radioButtonEtudiant, radioButtonEnseignant, agent;
    Button login, inscription;
    //ActionBarDrawerToggle toggleButton;
    //DrawerLayout drawerLayout;
    //Toolbar toolbar_main;
    ProgressDialog progressDialog;
    ImageButton imageButton13;
    boolean isSecret = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.progressDialog = new ProgressDialog(this);
        getSupportActionBar().hide();
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        radioButtonEnseignant = findViewById(R.id.radioButtonEnseignant);
        radioButtonEtudiant = findViewById(R.id.radioButtonEtudiant);
      /*  drawerLayout = findViewById(R.id.drawer_layout);
        toolbar_main = findViewById(R.id.toolbar_main);
        toolbar_main = findViewById(R.id.toolbar_main);*/

        imageButton13 = findViewById(R.id.imageButton13);
        imageButton13.setOnClickListener(v -> {
            if (isSecret) {
                editTextTextPassword.setTransformationMethod(null);
                imageButton13.setImageResource(R.drawable.ic_see);
                isSecret = !isSecret;
            } else {
                editTextTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                imageButton13.setImageResource(R.drawable.ic_no_see);

                isSecret = !isSecret;
            }
        });
        agent = findViewById(R.id.agent);
        login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            identification();
        });
        getWindow().setExitTransition(new Explode());
        inscription = findViewById(R.id.inscription);
        inscription.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginSignInActvity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        });


        //Drawer Options
       /* setSupportActionBar(toolbar_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        new ActionBarDrawerToggle(
                this, drawerLayout, R.string.app_name, R.string.nav_app_bar_open_drawer_description);
        toggleButton.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggleButton);*/
    }

    public void identification() {
        progressDialog.showDialog();
        if (!textChecker(editTextTextEmailAddress) && !textChecker(editTextTextPassword)) {
            if (radioButtonEnseignant.isChecked()) {
                login_enseigant(editTextTextEmailAddress.getText().toString(), editTextTextPassword.getText().toString());
            } else if (radioButtonEtudiant.isChecked()) {
                login_student(editTextTextEmailAddress.getText().toString(), editTextTextPassword.getText().toString());
            } else if (agent.isChecked()) {
                login_agent(editTextTextEmailAddress.getText().toString(), editTextTextPassword.getText().toString());
            } else {
                Toast.makeText(this, "Veuilliez cocher la Etudiant/Enseingant", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Veuilliez utiliser vos login", Toast.LENGTH_SHORT).show();
        }
    }

    private void login_enseigant(String email, String password) {
        EnseignantServices enseignantServices = new EnseignantServices();
        enseignantServices.login(new Callback<Enseignant>() {
            @Override
            public void onResponse(Call<Enseignant> call, Response<Enseignant> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        progressDialog.dismissDialog();
                        EnseignantSharedPReferences enseignantSharedPReferences = new EnseignantSharedPReferences(getSharedPreferences(EnseignantSharedPReferences.USER_FILE, Context.MODE_PRIVATE));
                        enseignantSharedPReferences.logIn(response.body());
                        if (enseignantSharedPReferences.isUserLogged()) {
                            Intent intent = new Intent(MainActivity.this, EspaceEnseignant.class);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Les identifiants saisies sont erronées", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Enseignant> call, Throwable t) {
                progressDialog.dismissDialog();
                Toast.makeText(MainActivity.this, "Les identifiants saisies sont erronées", Toast.LENGTH_LONG).show();

                Log.e("Login enseignant", t.getMessage());
            }
        }, email, password);
    }

    //Agent Login
    private void login_agent(String email, String password) {
        AgentServices agentServices = new AgentServices();
        agentServices.login(email, password, new Callback<Agent>() {
            @Override
            public void onResponse(Call<Agent> call, Response<Agent> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        progressDialog.dismissDialog();
                        AgentSharedPreference agentSharedPreference
                                = new AgentSharedPreference(getSharedPreferences(AgentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
                        agentSharedPreference.logIn(response.body());
                        Log.e("Here 1 ", response.body().toString());
                        if (agentSharedPreference.isUserLogged()) {
                            Intent intent = new Intent(MainActivity.this, EspaceAgentActivity.class);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        }
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Vos identifiants ne sont pas valable",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Agent> call, Throwable t) {
                Log.e("Erreur login 1", t.getMessage());
                progressDialog.dismissDialog();

                Toast.makeText(MainActivity.this,
                        "Erreur de connexion, vérifier votre connexion internet ou bien ressayer de vous connecter plus tard",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    // Student Login
    private void login_student(String email, String password) {
        StudentServices studentServices = new StudentServices();
        studentServices.login(email, password, new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.code() == 200) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        progressDialog.dismissDialog();

                        StudentSharedPreference studentSharedPreference
                                = new StudentSharedPreference(getSharedPreferences(StudentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
                        studentSharedPreference.logIn(response.body().get(0));
                        check_student_conectivity(studentSharedPreference);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Vos identifiants ne sont pas valable",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.e("Erreur login 1", t.getMessage());
                progressDialog.dismissDialog();

                Toast.makeText(MainActivity.this,

                        "Erreur de connexion, vérifier votre connexion internet ou bien ressayer de vous connecter plus tard",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void check_student_conectivity(StudentSharedPreference studentSharedPreference) {
        if (studentSharedPreference.isUserLogged()) {
            Intent intent = new Intent(MainActivity.this, EspaceEtudiant.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        }
    }


    public boolean textChecker(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }
/*
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggleButton.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggleButton.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }*/
}