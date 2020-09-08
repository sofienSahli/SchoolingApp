package esprit.example.com.schoolingapp.activities;

import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.FonctionaliteAdapter;
import esprit.example.com.schoolingapp.adapters.ProblematicAdapter;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FicheEncadrement extends AppCompatActivity {
    public static final String ADDRESS = "add";
    TextView entreprise_name, titre_projet, textView64;
    ListView probelmatiques, functions;
    public static final String FICHE_KEY = "Fiche";
    FichePFE fichePFE;
    Button refuser, accepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_encadrement);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Erreur de chargement de la fiche veuilliez ressayer", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (bundle.containsKey(FICHE_KEY)) {
                this.fichePFE = bundle.getParcelable(FICHE_KEY);
            }
        }
        Log.e("TAG", "onCreate: " + fichePFE.getFunctions());
        view_cast();

    }

    private void view_cast() {
        getSupportActionBar().hide();
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Fiche de " + fichePFE.getStudent().getLast_name());
        entreprise_name = findViewById(R.id.entreprise_name);
        titre_projet = findViewById(R.id.titre_projet);
        textView64 = findViewById(R.id.textView64);
        probelmatiques = findViewById(R.id.probelmatiques);
        functions = findViewById(R.id.functions);
        refuser = findViewById(R.id.refuser);
        accepter = findViewById(R.id.accepter);
        refuser.setOnClickListener(v -> refuser_fiche());
        accepter.setOnClickListener(v -> accepter_fiche());
        if (fichePFE.getFunctions() != null) {
            FonctionaliteAdapter fonctionaliteAdapter = new FonctionaliteAdapter(fichePFE.getFunctions(), this);
            probelmatiques.setAdapter(fonctionaliteAdapter);
            probelmatiques.setOnItemClickListener(fonctionaliteAdapter);
        }
        if (fichePFE.getProblematiques() != null) {
            ProblematicAdapter problematicAdapter = new ProblematicAdapter(fichePFE.getProblematiques(), this);
            functions.setAdapter(problematicAdapter);
            functions.setOnItemClickListener(problematicAdapter);
        }
        entreprise_name.setText(fichePFE.getNom_entreprise());
        titre_projet.setText(fichePFE.getTitre());
        textView64.setText(fichePFE.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (fichePFE != null) {
            if (!fichePFE.isAcceptedByEnseignant())
                getMenuInflater().inflate(R.menu.encadrement_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refuser_fiche) {
            refuser_fiche();
        } else if (item.getItemId() == R.id.accepter_fiche) {
            accepter_fiche();
        }
        return true;
    }

    private void accepter_fiche() {
        FicheServices ficheServices = new FicheServices();
        ficheServices.accepter_fiche_by_enseignant(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        if (response.body() != null && response.body().string().equals("ok")) {
                            Toast.makeText(FicheEncadrement.this, "La demande d'encadrement a été enregistrée les personnes concernées seront notifié sous peut", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Snackbar.make(findViewById(R.id.scrool), "Echec de l'action", Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                                accepter_fiche();
                            }).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Acceptance", "onFailure: " + t.getMessage());
            }
        }, fichePFE.getId());
    }

    private void refuser_fiche() {
        FicheServices ficheServices = new FicheServices();
        ficheServices.refuser_ficher_by_enseignant(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        if (response.body() != null && response.body().string().equals("ok")) {
                            Toast.makeText(FicheEncadrement.this, "Le refus d'encadrement a été enregistré les personnes concernée " +
                                    "seront notifié de votre décision", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Snackbar.make(findViewById(R.id.scrool), "Echec de l'action", Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                                refuser_fiche();
                            }).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Refus d'encadrement", "onFailure: " + t.getMessage());
            }
        }, fichePFE.getId());
    }
}