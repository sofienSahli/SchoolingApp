package esprit.example.com.schoolingapp.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.FonctionaliteAdapter;
import esprit.example.com.schoolingapp.adapters.ProblematicAdapter;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.Media;
import esprit.example.com.schoolingapp.entities.Remarques;
import esprit.example.com.schoolingapp.entities.Student;
import esprit.example.com.schoolingapp.local_storage.AgentSharedPreference;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import esprit.example.com.schoolingapp.services.implementations.MediaServices;
import esprit.example.com.schoolingapp.services.implementations.RemarquesServices;
import esprit.example.com.schoolingapp.utils.ExpandCollapsAnim;
import esprit.example.com.schoolingapp.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FichePFEDetail extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    public final static String FICHE_KEY = "key";
    public final static String ENSEIGNANT_KEY = "ense";
    public static final String ADDRESS = "adres";
    public static final String ISAGENT = "agent";
    TextView information_etudiant, information_projet, info_encadreur;
    TextView student_name, email, phone, tv_fonctionalite, tv_problematics;
    TextView enc_name, email_enc, phone_enc;
    ListView list_fonctionalite, list_problematique;
    EditText nom_entreprise, description, tech, titre_projet;
    ConstraintLayout info_etu_constrint, info_project, info_encad_constraint;
    FichePFE fichePFE;
    ExpandCollapsAnim info_pro, info_etu, info_enca, info_prob, info_fonct;
    FrameLayout aaaa1, aaaa2;
    EditText editTextTextMultiLine3;
    ImageButton imageButton7;
    ProgressDialog progressDialog;
    ImageView image_enc, imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_fiche_p_f_e_detail);
        Bundle bundle = getIntent().getExtras();
        if (this.getApplicationContext().getPackageManager().checkPermission(Manifest.permission.CALL_PHONE, getPackageName()) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 101);
        }
        if (bundle != null) {
            this.fichePFE = bundle.getParcelable(FICHE_KEY);
            if (bundle.containsKey(ENSEIGNANT_KEY)) {
                Enseignant e = bundle.getParcelable(ENSEIGNANT_KEY);
                this.fichePFE.setEnseignant(e);
            }
            if (bundle.containsKey(ADDRESS)) {
                Adres adres = bundle.getParcelable(ADDRESS);
                fichePFE.setAdress_entreprise(adres);
            }
            getSupportActionBar().setTitle(fichePFE.getTitre());
            setUpView();
            if (bundle.containsKey(ISAGENT)) {
                if (!bundle.getBoolean(ISAGENT))
                    setViewForStudent();
            }
        }
    }

    private void getEnseignant_image(Enseignant e) {
        MediaServices mediaServices = new MediaServices();
        if (e.getMedias() != null) {
            if (!e.getMedias().isEmpty())
                Picasso.get().load(RetrofitClient.BASE_URL + e.getMedias().get(0).getFile()).into(image_enc);
        } else {
            mediaServices.get_enseignant_image(e.getId(), new Callback<List<Media>>() {
                @Override
                public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                    if (response.code() == 200) {
                        if (!response.body().isEmpty()) {
                            Picasso.get().load(RetrofitClient.BASE_URL + response.body().get(0).getFile()).into(image_enc);
                            e.setMedias(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Media>> call, Throwable t) {
                    Timber.e("getEnseignant_image   " + t.getMessage());
                }
            });
        }
    }

    private void getStudent_image(Student student) {
        if (student.getMedias() != null) {
            if (!student.getMedias().isEmpty())
                Picasso.get().load(RetrofitClient.BASE_URL + student.getMedias().get(0).getFile()).into(imageView6);
        } else {
            MediaServices mediaServices = new MediaServices();
            mediaServices.get_student_image(student.getId(), new Callback<List<Media>>() {
                @Override
                public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                    if (response.code() == 200) {
                        if (!response.body().isEmpty()) {
                            Picasso.get().load(RetrofitClient.BASE_URL + response.body().get(0).getFile()).into(imageView6);
                            student.setMedias(response.body());
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Media>> call, Throwable t) {
                    Timber.e("getStudent_image   " + t.getMessage());

                }
            });

        }
    }

    private void setUpView() {
        Log.e("Setup view", fichePFE.toString());
        image_enc = findViewById(R.id.image_enc);
        imageView6 = findViewById(R.id.imageView6);
        enc_name = findViewById(R.id.enc_name);
        email_enc = findViewById(R.id.email_enc);
        phone_enc = findViewById(R.id.phone_enc);
        if (fichePFE.getEnseignant() != null) {
            getEnseignant_image(fichePFE.getEnseignant());
            String s = fichePFE.getEnseignant().getName() + " " + fichePFE.getEnseignant().getLast_name();
            enc_name.setText(s);
            String e = fichePFE.getEnseignant().getPhone() + "";
            phone_enc.setText(e);
            email_enc.setText(fichePFE.getEnseignant().getE_mail());
        }
        if (fichePFE.getStudent() != null)
            getStudent_image(fichePFE.getStudent());
        if (fichePFE.isAcceptedByAgent()) {
            findViewById(R.id.accepter_fiche).setVisibility(View.GONE);
            findViewById(R.id.refuser_fiche).setVisibility(View.GONE);
        }
        set_up_transition();
        find_all_views();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        findViewById(R.id.ajouter_remarque).setOnClickListener(this);
        if (fichePFE.getStudent() != null) {
            String s = fichePFE.getStudent().getName() + " " + fichePFE.getStudent().getLast_name();
            student_name.setText(s);
            email.setText(fichePFE.getStudent().getEmail());
            String p = fichePFE.getStudent().getPhone() + " ";
            phone.setText(p);
        }
        if (fichePFE.getFunctions() != null) {
            FonctionaliteAdapter fonctionaliteAdapter = new FonctionaliteAdapter(fichePFE.getFunctions(), this);
            list_fonctionalite.setAdapter(fonctionaliteAdapter);

        }
        if (fichePFE.getProblematiques() != null) {
            ProblematicAdapter problematicAdapter = new ProblematicAdapter(fichePFE.getProblematiques(), this);
            list_problematique.setAdapter(problematicAdapter);
        }
        nom_entreprise.setText(fichePFE.getNom_entreprise());
        description.setText(fichePFE.getDescription());
        tech.setText(fichePFE.getTech());
        titre_projet.setText(fichePFE.getTitre());
        if (fichePFE.getAdress_entreprise() != null) {
            editTextTextMultiLine3.setText(fichePFE.getAdress_entreprise().getVille());
        }

        info_pro = new ExpandCollapsAnim(info_project);
        info_etu = new ExpandCollapsAnim(info_etu_constrint);
        info_enca = new ExpandCollapsAnim(info_encad_constraint);
        info_fonct = new ExpandCollapsAnim(aaaa1);
        info_prob = new ExpandCollapsAnim(aaaa2);

        Drawable d = getDrawable(R.drawable.ic_arrow_up_dark);
        information_etudiant.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
        information_projet.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
        information_etudiant.setOnClickListener(v -> {
            Drawable drawable = getDrawable(R.drawable.ic_arrow_up_dark);
            if (info_etu.isExpanded()) {
                info_etu.collapse();
                drawable = getDrawable(R.drawable.ic_arrow_up_dark);
            } else {
                info_etu.expand();
                drawable = getDrawable(R.drawable.ic_arrow_down_dark);

            }
            information_etudiant.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        });
        tv_fonctionalite.setOnClickListener(v -> {
            Drawable drawable = getDrawable(R.drawable.ic_arrow_up_dark);

            if (info_fonct.isExpanded()) {
                info_fonct.collapse();
                drawable = this.getDrawable(R.drawable.ic_arrow_up_dark);

            } else {
                info_fonct.expand();
                drawable = this.getDrawable(R.drawable.ic_arrow_down_dark);


            }
            tv_fonctionalite.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        });
        tv_problematics.setOnClickListener(v -> {
            Drawable drawable = getDrawable(R.drawable.ic_arrow_up_dark);

            if (info_prob.isExpanded()) {
                info_prob.collapse();
                drawable = this.getDrawable(R.drawable.ic_arrow_up_dark);

            } else {
                info_prob.expand();
                drawable = this.getDrawable(R.drawable.ic_arrow_down_dark);


            }
            tv_problematics.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        });
        information_projet.setOnClickListener(v -> {
            Drawable drawable = getDrawable(R.drawable.ic_arrow_up_dark);

            if (info_pro.isExpanded()) {
                info_pro.collapse();
                drawable = this.getDrawable(R.drawable.ic_arrow_up_dark);

            } else {
                info_pro.expand();
                drawable = this.getDrawable(R.drawable.ic_arrow_down_dark);


            }
            information_projet.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        });
        if (fichePFE != null) {

            if (fichePFE.getEnseignant() != null) {

                info_encadreur.setOnClickListener(v -> {
                    Drawable drawable = getDrawable(R.drawable.ic_arrow_up_dark);
                    if (info_enca.isExpanded()) {
                        info_encadreur.setCompoundDrawablesWithIntrinsicBounds(this.getDrawable(R.drawable.ic_arrow_up_dark), null, null, null);
                        drawable = getDrawable(R.drawable.ic_arrow_up_dark);

                        info_enca.collapse();
                    } else {
                        info_encadreur.setCompoundDrawablesWithIntrinsicBounds(this.getDrawable(R.drawable.ic_arrow_down_dark), null, null, null);
                        drawable = getDrawable(R.drawable.ic_arrow_down_dark);
                        info_enca.expand();
                    }
                    info_encadreur.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                });
            } else {
                Drawable drawable = FichePFEDetail.this.getDrawable(R.drawable.ic_alert_dark);
                info_encadreur.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                info_enca.collapse();
                info_encadreur.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(FICHE_KEY, fichePFE);
                    Intent intent = new Intent(this, AllocatEncadreurToFiche.class);
                    intent.putExtras(bundle);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                });
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collapse_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.collapse) {
            info_enca.collapse();
            info_prob.collapse();
            info_fonct.collapse();
            info_etu.collapse();
            info_pro.collapse();

        }
        return true;
    }

    private void set_up_transition() {
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
    }

    private void find_all_views() {
        findViewById(R.id.accepter_fiche).setOnClickListener(this);
        findViewById(R.id.refuser_fiche).setOnClickListener(this);
        findViewById(R.id.imageButton2).setOnClickListener(this);
        findViewById(R.id.imageButton3).setOnClickListener(this);
        findViewById(R.id.email_encad).setOnClickListener(this);
        findViewById(R.id.phone_encad).setOnClickListener(this);
        info_etu_constrint = findViewById(R.id.info_etu_constrint);
        aaaa1 = findViewById(R.id.aaaa1);
        aaaa2 = findViewById(R.id.aaaa2);
        information_etudiant = findViewById(R.id.information_etudiant);
        information_projet = findViewById(R.id.information_projet);
        info_project = findViewById(R.id.info_project);
        info_encadreur = findViewById(R.id.info_encadreur);
        info_encad_constraint = findViewById(R.id.info_encad_constraint);
        nom_entreprise = findViewById(R.id.nom_entreprise);
        description = findViewById(R.id.description);
        tech = findViewById(R.id.tech);
        editTextTextMultiLine3 = findViewById(R.id.editTextTextMultiLine3);
        titre_projet = findViewById(R.id.entreprise_name);
        email = findViewById(R.id.email);
        student_name = findViewById(R.id.student_name);
        phone = findViewById(R.id.phone);
        list_fonctionalite = findViewById(R.id.list_fonctionalite);
        list_problematique = findViewById(R.id.list_problematique);
        tv_fonctionalite = findViewById(R.id.tv_fonctionalite);
        tv_problematics = findViewById(R.id.tv_problematics);
        imageButton7 = findViewById(R.id.imageButton7);
        imageButton7.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MapActvityDetailsFiche.FICHE_KEY, fichePFE);
            Intent intent = new Intent(FichePFEDetail.this, MapActvityDetailsFiche.class);
            intent.putExtras(bundle);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FichePFEDetail.this).toBundle());
        });
    }

    private void call(long phone) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }

    private void sendEmail(String e_mail) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        final PackageManager pm = getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        String className = null;

        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                className = info.activityInfo.name;

                if (className != null && !className.isEmpty()) {
                    break;
                }
            }
        }
        if (className != null)
            emailIntent.setClassName("com.google.android.gm", className);
        emailIntent.setType("message/rfc822");

        //emailIntent.setDataAndType(Uri.parse("mailto:" + e_mail),"message/rfc822" );
        emailIntent.putExtra(Intent.EXTRA_EMAIL, e_mail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'encadrement pour PFE ");
        try {

            startActivity(emailIntent);

        } catch (Exception e) {
            Log.e("Email send intent", e.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton2:
                if (fichePFE.getStudent() != null)
                    sendEmail(fichePFE.getStudent().getEmail());
                break;
            case R.id.imageButton3:

                if (fichePFE.getStudent() != null)
                    call(fichePFE.getStudent().getPhone());
                break;
            case R.id.email_encad:
                if (fichePFE.getEnseignant() != null)
                    sendEmail(fichePFE.getEnseignant().getE_mail());
                break;
            case R.id.phone_encad:
                if (fichePFE.getEnseignant() != null)
                    call(fichePFE.getEnseignant().getPhone());
                break;
            case R.id.accepter_fiche:
                accetper_fiche_agent();
                break;
            case R.id.refuser_fiche:
                refuser_fiche_agent();
                break;
            case R.id.ajouter_remarque:
                showRemarqueDialog();
                break;
        }

    }

    private void showRemarqueDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_update_fonc_prob, null, false);
        view.findViewById(R.id.button3).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.textView53)).setText("Nouvelle remarque");
        ((Button) view.findViewById(R.id.button4)).setText("Poster");
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
        ((Button) view.findViewById(R.id.button4)).setOnClickListener(v -> {
            if (!TextUtils.isEmpty(((EditText) view.findViewById(R.id.editTextTextMultiLine)).getText())) {


                alertDialog.dismiss();
                Remarques remarques = new Remarques();
                String s = ((EditText) view.findViewById(R.id.editTextTextMultiLine)).getText().toString();
                AgentSharedPreference agentSharedPreference = new AgentSharedPreference(getSharedPreferences(AgentSharedPreference.USER_FILE, Context.MODE_PRIVATE));
                long id = agentSharedPreference.getLong(AgentSharedPreference.USER_ID);
                remarques.setMessage(s);
                remarques.setFiche_id(fichePFE.getId());
                remarques.setId_person(id);
                ajouter_remarque(remarques);
            } else {
                ((EditText) view.findViewById(R.id.editTextTextMultiLine)).setError("Veuilliez renseigner ce champs");
            }
        });

    }

    private void ajouter_remarque(Remarques remarques) {
        RemarquesServices remarquesServices = new RemarquesServices();
        remarquesServices.posterRemarques(remarques, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(FichePFEDetail.this, "Remarque Posté", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(FichePFEDetail.this, "Echec veuilliez ressayer ultérieurment", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Remarques", "onFailure: " + t.getMessage());
                Toast.makeText(FichePFEDetail.this, "Echec veuilliez ressayer ultérieurment", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void accetper_fiche_agent() {
        findViewById(R.id.accepter_fiche).setVisibility(View.GONE);
        findViewById(R.id.refuser_fiche).setVisibility(View.GONE);
        FicheServices ficheServices = new FicheServices();
        ficheServices.accepte_fiche_by_agent(fichePFE.getId(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(FichePFEDetail.this, "Fiche Validée, veuilliez allouer un encadreur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Accepter Fiche", "onFailure: " + t.getMessage());
            }
        });
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(this, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Timber.e("Date" , ""year + month + day);
        progressDialog.showDialog();
        Date date = new Date(year, month, day);
        FicheServices ficheServices = new FicheServices();
        ficheServices.set_depot_date(fichePFE.getId(), date, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismissDialog();
                if (response.code() == 200)
                    Toast.makeText(FichePFEDetail.this, "Date de depôt modifié", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismissDialog();
                Toast.makeText(FichePFEDetail.this, "Erreur interne veuillez réessayer ultiérieurement", Toast.LENGTH_SHORT).show();
                Log.e("Set Date depot", "onFailure: " + t.getMessage());

            }
        });
    }

    private void refuser_fiche_agent() {
        FicheServices ficheServices = new FicheServices();
        ficheServices.refuser_fiche_by_agent(fichePFE.getId(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(FichePFEDetail.this, EspaceAgentActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FichePFEDetail.this).toBundle());
                    Toast.makeText(FichePFEDetail.this, "La fiche a été rejetée avec succées", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Refuser fiche", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setViewForStudent() {
        info_encadreur.setOnClickListener(null);
        findViewById(R.id.accepter_fiche).setVisibility(View.GONE);
        findViewById(R.id.refuser_fiche).setVisibility(View.GONE);
        findViewById(R.id.ajouter_remarque).setVisibility(View.GONE);

    }

}