package esprit.example.com.schoolingapp.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Commentaire;
import esprit.example.com.schoolingapp.entities.Media;
import esprit.example.com.schoolingapp.local_storage.EnseignantSharedPReferences;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.implementations.CommentairesServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {
    Media media;
    public static final String MEDIA_LABEL = "media";
    PDFView pdfView;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null && !bundle.containsKey(MEDIA_LABEL))
            finish();

        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(v -> showDialog());
        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progressBar);
        media = bundle.getParcelable(MEDIA_LABEL);
        String s = RetrofitClient.BASE_URL + media.getFile();
        LoadPDF loadPDF = new LoadPDF(s);

        loadPDF.execute();

    }

    public void showDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_update_fonc_prob, null, false);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
        ((TextView) view.findViewById(R.id.textView53)).setText("Veuilliez saisir votre commentaire");
        ((Button) view.findViewById(R.id.button3)).setOnClickListener(v -> alertDialog.dismiss());
        ((Button) view.findViewById(R.id.button3)).setText("Annuler");
        ((Button) view.findViewById(R.id.button4)).setText("Commenter");
        ((Button) view.findViewById(R.id.button4)).setOnClickListener(v -> {
            EditText editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
            if (TextUtils.isEmpty(editTextTextMultiLine.getText()))
                editTextTextMultiLine.setError("Veuilliez saisir votre commentaire");
            else {

                insertComment(editTextTextMultiLine.getText().toString());
                alertDialog.dismiss();
            }
        });

    }

    private void insertComment(String message) {
        Commentaire commentaire = new Commentaire();
        EnseignantSharedPReferences enseignantSharedPReferences = new EnseignantSharedPReferences(getSharedPreferences(EnseignantSharedPReferences.USER_FILE, Context.MODE_PRIVATE));
        long enseignant_id = enseignantSharedPReferences.getLong(EnseignantSharedPReferences.USER_ID);
        int media_id = media.getId();
        commentaire.setMessages(message);
        commentaire.setId_enseignant(enseignant_id);
        commentaire.setMedia_id(media_id);
        CommentairesServices commentairesServices = new CommentairesServices();
        commentairesServices.insert_new_comment(commentaire, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200)
                    Toast.makeText(WebViewActivity.this, "Comment inserted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Comment insertion failed", "onFailure: " + t.getMessage());
            }
        });
    }


    class LoadPDF extends AsyncTask<Void, Void, Void> {
        String s;
        InputStream input;

        public LoadPDF(String url) {
            this.s = url;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                input = new URL(s).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pdfView.fromStream(input).load();
            progressBar.setVisibility(View.GONE);
        }
    }

}