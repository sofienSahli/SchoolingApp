package esprit.example.com.schoolingapp.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.RemarquesAdapter;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;
import esprit.example.com.schoolingapp.fragment.FragmentDepotRapport;
import esprit.example.com.schoolingapp.fragment.FragmentDepotVCS;
import esprit.example.com.schoolingapp.fragment.FragmentFonctionaliteProblematiqueDepot;
import esprit.example.com.schoolingapp.fragment.FragmentInformationGlobal;
import esprit.example.com.schoolingapp.fragment.FragmetInformationFonctionelPFE;
import esprit.example.com.schoolingapp.local_storage.AppDatabase;
import esprit.example.com.schoolingapp.local_storage.StudentSharedPreference;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import esprit.example.com.schoolingapp.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NouvelleFichePFE extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    PagerTabStrip page_indicator;
    PagerAdapter pagerAdapter;
    FichePFE fichePFE;
    ProgressDialog progressDialog;
    Button button7;

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_fiche_p_f_e);
        viewPager = findViewById(R.id.viewPager);
        fetch_fiche_fromServer();
        set_up_view_pager();
        findViewById(R.id.root_status).setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        getSupportActionBar().setTitle(pagerAdapter.getPageTitle(position));

    }

    @Override
    public void onPageSelected(int position) {
        getSupportActionBar().setTitle(pagerAdapter.getPageTitle(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nouvel_fiche_pfe, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_draft) {
            save_fiche_draft();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.upload) {
            upload_fiche();
        }
        return true;
    }

    private void upload_fiche() {
        this.progressDialog.showDialog();
        StudentSharedPreference studentSharedPreference = new StudentSharedPreference(getSharedPreferences(StudentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
        boolean v = studentSharedPreference.getBoolean(StudentSharedPreference.IS_FICHE_EDITABLE);
        if (v) {
            List<FichePFE> fichePFE = AppDatabase.getAppDatabase(this).fichePFE().getAll();
            if (fichePFE.isEmpty()) {
                Toast.makeText(this, "Veuillez remplire puis sauvegarder votre fiche en draft avant de continuer", Toast.LENGTH_LONG).show();
            } else {
                FichePFE fichePFE1 = fichePFE.get(0);
                fichePFE1.setId_etudiant(studentSharedPreference.getLong(StudentSharedPreference.USER_ID));
                Adres adres = AppDatabase.getAppDatabase(this).adresDAO().findAdres(fichePFE1.getId());
                fichePFE1.setAdress_entreprise(adres);
                fichePFE1.setFunctions(AppDatabase.getAppDatabase(this).FonctionalitesDAO().findProbs(fichePFE1.getId()));
                fichePFE1.setProblematiques(AppDatabase.getAppDatabase(this).Problematiques().findProbs(fichePFE1.getId()));

                new AlertDialog.Builder(this).setMessage("Voulez-vous continuer ? Notez qu'une fois upload vous ne pourrez plus modifier le contenu de votre fiche")
                        .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Confirmer", (dialog, which) -> {
                            studentSharedPreference.insertBollean(StudentSharedPreference.IS_FICHE_EDITABLE, false);
                            fichePFE1.setEditableByStudent(false);
                            consume_web_service(fichePFE1);
                        }).show();
            }
        } else {
            Toast.makeText(this, "Votre fiche est en cours de traitement", Toast.LENGTH_LONG).show();
        }
    }

    private void fetch_fiche_fromServer() {
        long id_student;
        StudentSharedPreference studentSharedPreference = new StudentSharedPreference(getSharedPreferences(StudentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
        id_student = studentSharedPreference.getLong(StudentSharedPreference.USER_ID);
        FicheServices ficheServices = new FicheServices();
        ficheServices.get_fiche_by_id_student(new Callback<FichePFE>() {
            @Override
            public void onResponse(Call<FichePFE> call, Response<FichePFE> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        fichePFE = response.body();
                        findViewById(R.id.root_status).setVisibility(View.VISIBLE);
                        findViewById(R.id.button7).setOnClickListener(v -> {
                            Intent intent = new Intent(NouvelleFichePFE.this, FichePFEDetail.class);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(FichePFEDetail.ISAGENT, false);
                            bundle.putParcelable(FichePFEDetail.FICHE_KEY, fichePFE);
                            bundle.putParcelable(FichePFEDetail.ENSEIGNANT_KEY, fichePFE.getEnseignant());
                            bundle.putParcelable(FichePFEDetail.ADDRESS, fichePFE.getAdress_entreprise());
                            intent.putExtras(bundle);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NouvelleFichePFE.this).toBundle());
                        });
                        getSupportActionBar().hide();
                        viewPager.setVisibility(View.GONE);
                        studentSharedPreference.insertBollean(StudentSharedPreference.IS_FICHE_EDITABLE, response.body().isEditableByStudent());
                        if (response.body().isAcceptedByEnseignant()) {
                            pagerAdapter.setSecondConfig();
                            findViewById(R.id.root_status).setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);

                        }
                        Log.e("TAG", "onResponse: " + response.body().toString());

                        setup_status_view(response.body());
                    } else {
                        Log.e("TAG", "onResponse:  True");
                        findViewById(R.id.root_status).setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        studentSharedPreference.insertBollean(StudentSharedPreference.IS_FICHE_EDITABLE, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<FichePFE> call, Throwable t) {
                Log.e("TAG", "onResponse: " + t.getMessage());
                studentSharedPreference.insertBollean(StudentSharedPreference.IS_FICHE_EDITABLE, true);
                findViewById(R.id.root_status).setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);


            }
        }, id_student);
    }

    private void setup_status_view(FichePFE fichePFE) {
        ((TextView) findViewById(R.id.date_depot)).setText(fichePFE.getCreated_at());
        if (fichePFE.isAcceptedByAgent()) {
            ((ImageView) findViewById(R.id.imageView9)).setImageResource(android.R.drawable.ic_dialog_info);
            ((ImageView) findViewById(R.id.imageView9)).setColorFilter(getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        if (fichePFE.getId_enseignant() != 0) {
            ((ImageView) findViewById(R.id.imageView10)).setImageResource(android.R.drawable.ic_dialog_info);
            ((ImageView) findViewById(R.id.imageView10)).setColorFilter(getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        if (fichePFE.isAcceptedByEnseignant()) {
            ((ImageView) findViewById(R.id.imageView11)).setImageResource(android.R.drawable.ic_dialog_info);
            ((ImageView) findViewById(R.id.imageView11)).setColorFilter(getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        }
        if (fichePFE.getRemarques() != null && !fichePFE.getRemarques().isEmpty()) {
            RecyclerView messages_list = findViewById(R.id.messages_list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            messages_list.setLayoutManager(layoutManager);
            RemarquesAdapter remarquesAdapter = new RemarquesAdapter(this, fichePFE.getRemarques());
            messages_list.setAdapter(remarquesAdapter);
        }

    }

    private void set_up_view_pager() {

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        page_indicator = findViewById(R.id.page_indicator);
        page_indicator.setDrawFullUnderline(true);
        page_indicator.setTabIndicatorColor(Color.WHITE);
        page_indicator.setTextColor(Color.WHITE);

        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Explode());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pagerAdapter.getItem(position) instanceof FragmetInformationFonctionelPFE) {
                    save_fiche_draft();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void consume_web_service(FichePFE fichePFE) {

        fichePFE.setProblematiques(new ArrayList<>(new LinkedHashSet<>(fichePFE.getProblematiques())));
        fichePFE.setFunctions(new ArrayList<>(new LinkedHashSet<>(fichePFE.getFunctions())));

        FicheServices ficheServices = new FicheServices();
        ficheServices.upload_fiche(fichePFE, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    progressDialog.dismissDialog();
                    try {
                        if (response.body().string().equals("already existing")) {
                            Toast.makeText(NouvelleFichePFE.this, "Votre fiche est en cours de traitement, vous serez notifié(e) en cas de changement.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(NouvelleFichePFE.this, "Succés  d'upload de la fiche, vous serez notifié(e) en cas de changement.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(NouvelleFichePFE.this, EspaceEtudiant.class);
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NouvelleFichePFE.this).toBundle());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(NouvelleFichePFE.this, "Erreur interne du serveur ou connexion inexistante veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Erreur upload", t.getMessage());
                progressDialog.dismissDialog();

                Toast.makeText(NouvelleFichePFE.this, "Erreur interne du serveur ou connexion inexistante veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void save_fiche_draft() {
        StudentSharedPreference studentSharedPreference = new StudentSharedPreference(getSharedPreferences(StudentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
        boolean isEditable = studentSharedPreference.getBoolean(StudentSharedPreference.IS_FICHE_EDITABLE);
        if (!isEditable) {
            Toast.makeText(this, "Votre fiche est encore sous traitement", Toast.LENGTH_LONG).show();

        } else {
            FichePFE fichePFE;
            Adres adres;
            if (AppDatabase.getAppDatabase(this).fichePFE().getAll().isEmpty()) {
                fichePFE = new FichePFE();
                adres = new Adres();

            } else {
                fichePFE = AppDatabase.getAppDatabase(this).fichePFE().getAll().get(0);
                adres = AppDatabase.getAppDatabase(this).adresDAO().findAdres(fichePFE.getId());
            }
            long student_id = studentSharedPreference.getLong(StudentSharedPreference.USER_ID);
            FragmentInformationGlobal fragmentInformationGlobal = (FragmentInformationGlobal) pagerAdapter.fragments.get(0);
            FragmetInformationFonctionelPFE fragmetInformationFonctionelPFE = (FragmetInformationFonctionelPFE) pagerAdapter.fragments.get(1);
            EditText entreprise_name = fragmentInformationGlobal.getView().findViewById(R.id.nom_entreprise);
            //EditText address = fragmentInformationGlobal.getView().findViewById(R.id.address);
            //Spinner spinner2 = fragmentInformationGlobal.getView().findViewById(R.id.spinner2);
            // EditText address2 = fragmentInformationGlobal.getView().findViewById(R.id.address2);
            EditText titre_projet = fragmentInformationGlobal.getView().findViewById(R.id.entreprise_name);
            EditText description = fragmentInformationGlobal.getView().findViewById(R.id.description);
            LinearLayout tech = fragmentInformationGlobal.getView().findViewById(R.id.tech);
            fichePFE.setNom_entreprise(entreprise_name.getText().toString());
            if (((FragmentInformationGlobal) pagerAdapter.getItem(0)).getAdres() != null) {
                if (adres == null)
                    adres = new Adres();
                adres.setId_fiche(1);
                adres.setLati(((FragmentInformationGlobal) pagerAdapter.getItem(0)).getAdres().getLati());
                adres.setLongi(((FragmentInformationGlobal) pagerAdapter.getItem(0)).getAdres().getLongi());
                adres.setVille(((FragmentInformationGlobal) pagerAdapter.getItem(0)).getAdres().getVille());

            }
            if (AppDatabase.getAppDatabase(getApplicationContext()).adresDAO().getAll().contains(adres)) {
                AppDatabase.getAppDatabase(this).adresDAO().updateFICHE(adres);
            } else {
                AppDatabase.getAppDatabase(this).adresDAO().insertAll(adres);

            }
            fichePFE.setAdress_entreprise(adres);
            fichePFE.setTitre(titre_projet.getText().toString());
            fichePFE.setDescription(description.getText().toString());
            StringBuilder technologies = new StringBuilder(" ");
            for (int i = 0; i < tech.getChildCount(); i++) {
                MaterialTextView textView = (MaterialTextView) tech.getChildAt(i);
                technologies.append(textView.getText()).append(", ");
            }
            fichePFE.setTech(technologies.toString());

            fichePFE.setId_etudiant(student_id);
            List<Fonctionalite> fonctionalites = fragmetInformationFonctionelPFE.getFonctionaliteAdapter().getFonctionalites();
            List<Problematique> problematiques = fragmetInformationFonctionelPFE.getProblematique_adapter().getProblematiqueLis();
            if (AppDatabase.getAppDatabase(this).fichePFE().getAll().isEmpty()) {//Nouvel fiche
                Log.e("11212312312312 ", "1231312312312");
                AppDatabase.getAppDatabase(this).fichePFE().insertAll(fichePFE);

              /* for (Fonctionalite f : fonctionalites) {

                    f.setFiche_id(1);
                    AppDatabase.getAppDatabase(this).FonctionalitesDAO().insertAll(f);
                    Log.e("Saving fonct", "save_fiche_draft: " + f.toString());


                }
                for (Problematique p : problematiques) {
                    p.setFiche_id(1);
                    AppDatabase.getAppDatabase(this).Problematiques().insertAll(p);
                    Log.e("Saving Prob", "save_fiche_draft: " + p.toString());
                }*/
                adres.setId_fiche(fichePFE.getId());
                AppDatabase.getAppDatabase(this).adresDAO().insertAll(adres);
                Snackbar.make(viewPager, "Nouvel Fiche sauvegardé en draft", Snackbar.LENGTH_SHORT).show();
            } else {// Update fiche
                Log.e("KJZJZJAJAJA ", "JAJEAJEAJEJAEJ");

                AppDatabase.getAppDatabase(this).fichePFE().updateFICHE(fichePFE);
                if (adres != null)
                    AppDatabase.getAppDatabase(this).adresDAO().updateFICHE(adres);
                Log.e("Fiche To string", AppDatabase.getAppDatabase(this).fichePFE().getAll().toString());
                for (Fonctionalite f : fonctionalites) {

                    if (AppDatabase.getAppDatabase(this).FonctionalitesDAO().find(f.getId_fonct()) == null) {
                        f.setFiche_id(1);
                        AppDatabase.getAppDatabase(this).FonctionalitesDAO().insertAll(f);
                        Log.e("Updateing fonct", "save_fiche_draft: " + f.toString());


                    } else
                        AppDatabase.getAppDatabase(this).FonctionalitesDAO().update(f);

                }
                for (Problematique p : problematiques) {

                    if (AppDatabase.getAppDatabase(this).Problematiques().find(p.getId_prob()) == null) {
                        p.setFiche_id(1);
                        AppDatabase.getAppDatabase(this).Problematiques().insertAll(p);
                        Log.e("Updating Prob", "save_fiche_draft: " + p.toString());


                    } else
                        AppDatabase.getAppDatabase(this).Problematiques().update(p);
                }
                Snackbar.make(viewPager, "Modification sauvegardé en draft", Snackbar.LENGTH_SHORT).show();


            }

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        boolean isFirst = true;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            FragmetInformationFonctionelPFE fragmetInformationFonctionelPFE = new FragmetInformationFonctionelPFE();
            FragmentInformationGlobal fragmentInformationGlobal = new FragmentInformationGlobal();
            this.fragments = new ArrayList<>();
            fragments.add(fragmentInformationGlobal);
            fragments.add(fragmetInformationFonctionelPFE);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (isFirst) {
                switch (position) {
                    case 0:
                        return "Informations Générales";
                    case 1:
                        return "Informations Fonctionnelles";
                }
            } else {
                switch (position) {
                    case 0:
                        return "Rapport";
                    case 1:
                        return "Liens VCS";
                    case 2:
                        return "Grille d'évaluation";
                    case 3:
                        return "Problematiques";

                }
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public void setSecondConfig() {
            fragments.clear();
            isFirst = !isFirst;
            getSupportActionBar().hide();

            FragmentFonctionaliteProblematiqueDepot problematiqueDepot = new FragmentFonctionaliteProblematiqueDepot();
            FragmentFonctionaliteProblematiqueDepot fonctionaliteProblematiqueDepot = new FragmentFonctionaliteProblematiqueDepot();
            problematiqueDepot.setProblematiques(fichePFE.getProblematiques());
            fonctionaliteProblematiqueDepot.setFonctionalites(fichePFE.getFunctions());
            fragments.add(new FragmentDepotRapport());
            fragments.add(new FragmentDepotVCS());
            fragments.add(problematiqueDepot);
            fragments.add(fonctionaliteProblematiqueDepot);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}