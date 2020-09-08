package esprit.example.com.schoolingapp.activities;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.CommentaireAdapter;
import esprit.example.com.schoolingapp.entities.Commentaire;
import esprit.example.com.schoolingapp.services.implementations.CommentairesServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentaireActivity extends AppCompatActivity {
    RecyclerView comentaire;
    public static final String MEDIA_ID = "media";
    private int media_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentaire);
        setUpView();
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Fade());

    }

    private void setUpView() {
        comentaire = findViewById(R.id.comentaire);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        comentaire.setLayoutManager(layoutManager);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            finish();
        else {
            if (bundle.containsKey(MEDIA_ID))
                media_id = bundle.getInt(MEDIA_ID);
            else
                finish();
        }
        CommentairesServices commentairesServices = new CommentairesServices();
        commentairesServices.commentaires_for_media_id(media_id, new Callback<List<Commentaire>>() {
            @Override
            public void onResponse(Call<List<Commentaire>> call, Response<List<Commentaire>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        CommentaireAdapter commentaireAdapter = new CommentaireAdapter(CommentaireActivity.this, response.body());
                        comentaire.setAdapter(commentaireAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Commentaire>> call, Throwable t) {
                Toast.makeText(CommentaireActivity.this, "Erreur lors de la récupération des commentaires", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}