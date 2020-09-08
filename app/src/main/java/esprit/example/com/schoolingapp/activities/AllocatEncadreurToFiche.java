package esprit.example.com.schoolingapp.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.FicheAdapter;
import esprit.example.com.schoolingapp.adapters.ListEncadreurAdapter;
import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.implementations.EnseignantServices;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import esprit.example.com.schoolingapp.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllocatEncadreurToFiche extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {
    Spinner spinner3;
    ImageView imageView12;
    TextView name;
    TextView specialite;
    RecyclerView list_projet;
    TextView nombre_projet;
    SearchView searchView4;
    RecyclerView list_encadreur;
    ListEncadreurAdapter listEncadreurAdapter;
    FichePFE fichePFE;
    ImageButton close;
    Button assigner;
    ConstraintLayout encadreur_layout, list_encadreur_layout;
    final ArrayList<Enseignant> initial_list = new ArrayList<>();

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocat_encadreur_to_fiche);
        setUpView();
        if (this.getApplicationContext().getPackageManager().checkPermission(Manifest.permission.CALL_PHONE, getPackageName()) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 101);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(FichePFEDetail.FICHE_KEY)) {
            this.fichePFE = bundle.getParcelable(FichePFEDetail.FICHE_KEY);
        }
    }

    private void setUpView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(FichePFEDetail.FICHE_KEY)) {
            FichePFE fichePFE = bundle.getParcelable(FichePFEDetail.FICHE_KEY);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(fichePFE.getTech());
            }
        }
        encadreur_layout = findViewById(R.id.encadreur_layout);
        list_encadreur_layout = findViewById(R.id.list_encadreur_layout);
        imageView12 = findViewById(R.id.imageView12);
        name = findViewById(R.id.name);
        specialite = findViewById(R.id.specialite);
        list_projet = findViewById(R.id.list_projet);
        nombre_projet = findViewById(R.id.nombre_projet);
        spinner3 = findViewById(R.id.spinner3);
        searchView4 = findViewById(R.id.searchView4);
        searchView4.setOnQueryTextListener(this);
        list_encadreur = findViewById(R.id.list_encadreur);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list_encadreur.setLayoutManager(layoutManager);

        listEncadreurAdapter = new ListEncadreurAdapter(this, initial_list);
        intial_data();
        list_encadreur.setAdapter(listEncadreurAdapter);
        spinner3.setOnItemSelectedListener(this);

        close = findViewById(R.id.close);
        assigner = findViewById(R.id.assigner);
        hide_details();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected_value = (String) spinner3.getItemAtPosition(position);

        if (!selected_value.equals("all")) {
            List<Enseignant> filtred_enseignant = new ArrayList<>();
            for (Enseignant e : initial_list) {
                if (e.getSpecialite().toLowerCase().contains(selected_value.toLowerCase())) {
                    filtred_enseignant.add(e);
                }
            }
            listEncadreurAdapter.setEnseignants(filtred_enseignant);
            listEncadreurAdapter.notifyDataSetChanged();
        } else {
            listEncadreurAdapter.setEnseignants(initial_list);
            listEncadreurAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        listEncadreurAdapter.setEnseignants(listEncadreurAdapter.getEnseignants());
        listEncadreurAdapter.notifyDataSetChanged();

    }

    private void intial_data() {
        EnseignantServices enseignantServices = new EnseignantServices();
        enseignantServices.getEnseignant(new Callback<List<Enseignant>>() {
            @Override
            public void onResponse(Call<List<Enseignant>> call, Response<List<Enseignant>> response) {
                if (response.code() == 200) {
                    if (response.body() != null && !response.body().isEmpty())
                        initial_list.addAll(response.body());
                    listEncadreurAdapter.setEnseignants(initial_list);
                    listEncadreurAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Enseignant>> call, Throwable t) {
                Log.e("GEt teacher erreur", t.getMessage());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filter_recycler(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter_recycler(newText);
        return true;
    }

    private void filter_recycler(String newText) {
        List<Enseignant> enseignants = new ArrayList<>();
        if (!TextUtils.isEmpty(newText)) {
            for (Enseignant e : initial_list) {
                if (e.getLast_name().toLowerCase().trim().contains(newText.trim().toLowerCase())) {
                    enseignants.add(e);
                } else if (e.getName().toLowerCase().trim().contains(newText.trim().toLowerCase())) {
                    enseignants.add(e);
                } else if (e.getSpecialite().toLowerCase().trim().contains(newText.trim().toLowerCase())) {
                    enseignants.add(e);
                }
            }
        } else
            enseignants = initial_list;
        listEncadreurAdapter.setEnseignants(enseignants);
        listEncadreurAdapter.notifyDataSetChanged();

    }

    public void changeViewToEncadreurDetails(Enseignant enseignant) {
        show_details();

        if (enseignant.getMedias() != null) {
            if (!enseignant.getMedias().isEmpty()) {
                Picasso.get().load(RetrofitClient.BASE_URL + enseignant.getMedias().get(0).getFile()).into(imageView12);
            }
        }
        if (enseignant.getFiches() != null) {
            if (!enseignant.getFiches().isEmpty()) {
                list_projet.setVisibility(View.VISIBLE);
                nombre_projet.setText(" " + enseignant.getFiches().size());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                list_projet.setLayoutManager(layoutManager);
                FicheAdapter ficheAdapter = new FicheAdapter(this, enseignant.getFiches());
                list_projet.setAdapter(ficheAdapter);
            } else {
                nombre_projet.setText("0");
                list_projet.setVisibility(View.GONE);
            }

        }
        String nom = enseignant.getName() + "  " + enseignant.getLast_name();
        name.setText(nom);
        specialite.setText(enseignant.getSpecialite());
        close.setOnClickListener(v -> {
            hide_details();
        });
        assigner.setOnClickListener(v -> inflate_dialog(enseignant));
    }

    public void inflate_dialog(Enseignant e) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_allocate_encadreur_fiche, null, false);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
        ImageView imageView7 = view.findViewById(R.id.imageView7);
        Picasso.get().load(RetrofitClient.BASE_URL + e.getMedias().get(0).getFile()).into(imageView7);
        TextView textView48 = view.findViewById(R.id.textView48);
        TextView textView58 = view.findViewById(R.id.textView58);
        Button cancel = view.findViewById(R.id.cancel);
        Button confirm = view.findViewById(R.id.confirm);
        String s = e.getName() + "  " + e.getLast_name();
        textView48.setText(s);
        textView58.setText(e.getE_mail());
        cancel.setOnClickListener(v -> alertDialog.dismiss());
        confirm.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(AllocatEncadreurToFiche.this);
            progressDialog.showDialog();
            alertDialog.dismiss();
            FicheServices ficheServices = new FicheServices();
            FichePFE fichePFE = ((AllocatEncadreurToFiche) this).getFichePFE();
            ficheServices.allocate_enseignant_fiche(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismissDialog();
                    if (response.code() == 200) {
                        FichePFE fichePFE = getFichePFE();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(FichePFEDetail.FICHE_KEY, fichePFE);
                        bundle.putParcelable(FichePFEDetail.ENSEIGNANT_KEY, e);
                        Intent intent = new Intent(AllocatEncadreurToFiche.this, FichePFEDetail.class);
                        intent.putExtras(bundle);
                        Log.e("FICHE", getFichePFE().toString());
                        AllocatEncadreurToFiche.this.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(AllocatEncadreurToFiche.this).toBundle());
                        Toast.makeText(AllocatEncadreurToFiche.this, "L'enseignant(e) sera notifié(e) de sa nouvelle allocation.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismissDialog();
                    Log.e("Failed to allocate", "onFailure: " + t.getMessage());
                }
            }, fichePFE.getId(), e.getId());

        });
    }

    public void hide_details() {

        list_encadreur_layout.setVisibility(View.VISIBLE);
        encadreur_layout.setVisibility(View.GONE);
        close.setVisibility(View.GONE);
        assigner.setVisibility(View.GONE);
        specialite.setVisibility(View.GONE);
        list_projet.setVisibility(View.GONE);
        imageView12.setVisibility(View.GONE);
        nombre_projet.setVisibility(View.GONE);
        findViewById(R.id.f).setVisibility(View.GONE);
    }

    public void show_details() {

        list_encadreur_layout.setVisibility(View.GONE);
        encadreur_layout.setVisibility(View.VISIBLE);
        close.setVisibility(View.VISIBLE);
        assigner.setVisibility(View.VISIBLE);
        specialite.setVisibility(View.VISIBLE);
        list_projet.setVisibility(View.VISIBLE);
        imageView12.setVisibility(View.VISIBLE);
        nombre_projet.setVisibility(View.VISIBLE);
        findViewById(R.id.f).setVisibility(View.VISIBLE);

    }
}