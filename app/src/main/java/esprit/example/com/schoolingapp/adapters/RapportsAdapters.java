package esprit.example.com.schoolingapp.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.CommentaireActivity;
import esprit.example.com.schoolingapp.activities.NouvelleFichePFE;
import esprit.example.com.schoolingapp.activities.WebViewActivity;
import esprit.example.com.schoolingapp.entities.Media;

public class RapportsAdapters extends RecyclerView.Adapter<RapportsAdapters.Holder> {

    Context context;
    List<Media> medias;

    public RapportsAdapters(Context context, List<Media> media) {
        this.context = context;
        this.medias = media;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Media> getMedia() {
        return medias;
    }

    public void setMedia(List<Media> media) {
        this.medias = media;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_rapport, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindView(medias.get(position));
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView73;
        TextView textView75, textView85;
        ImageButton imageButton9, imageButton12;


        public Holder(@NonNull View itemView) {
            super(itemView);
            textView73 = itemView.findViewById(R.id.textView73);
            textView75 = itemView.findViewById(R.id.textView75);
            imageButton9 = itemView.findViewById(R.id.imageButton9);
            imageButton12 = itemView.findViewById(R.id.imageButton12);
            textView85 = itemView.findViewById(R.id.textView85);

            if (context instanceof NouvelleFichePFE) {
                imageButton9.setVisibility(View.GONE);
                imageButton12.setVisibility(View.VISIBLE);
                textView85.setText("Afficher les commentaires");
                textView85.setTextColor(context.getColor(R.color.colorAccent));
                textView85.setAlpha(1.0f);

            } else {
                imageButton12.setVisibility(View.GONE);

            }

        }

        void bindView(Media media) {
            Log.e("Media", "bindView: " + media.toString());
            textView73.setText(media.getCreated_at());
            textView75.setText(media.getTitre());
            imageButton9.setOnClickListener(v -> {
                Intent intent = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebViewActivity.MEDIA_LABEL, media);
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) context).toBundle());
                // context.startActivity(intent);
            });
            if (context instanceof NouvelleFichePFE)
                imageButton12.setOnClickListener(v -> {
                    showDialog(media);
                });
        }

        public void showDialog(Media media) {
            if (media.getCommentaires().isEmpty()) {
                Toast.makeText(context, "Aucun commentaires à afficher", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(context, CommentaireActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(CommentaireActivity.MEDIA_ID, media.getId());
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) context).toBundle());
            }
        }


    }

}
