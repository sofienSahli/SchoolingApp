package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Commentaire;
import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.entities.Media;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.implementations.MediaServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.Holder> {
    Context context;
    List<Commentaire> commentaireList;

    public CommentaireAdapter(Context context, List<Commentaire> commentaireList) {
        this.context = context;
        this.commentaireList = commentaireList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_commentaire, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindView(commentaireList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentaireList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView13;
        TextView name, date, message;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView13 = itemView.findViewById(R.id.imageView13);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.message);
        }

        public void bindView(Commentaire commentaire) {
            getEnseignant_image(commentaire.getEnseignant());
            name.setText(commentaire.getEnseignant().getName() + " " + commentaire.getEnseignant().getLast_name());
            date.setText(commentaire.getCreated_at());
            message.setText(commentaire.getMessages());
        }

        private void getEnseignant_image(Enseignant e) {
            MediaServices mediaServices = new MediaServices();
            if (e.getMedias() != null) {
                if (!e.getMedias().isEmpty())
                    Picasso.get().load(RetrofitClient.BASE_URL + e.getMedias().get(0).getFile()).into(imageView13);
            } else {
                mediaServices.get_enseignant_image(e.getId(), new Callback<List<Media>>() {
                    @Override
                    public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                        if (response.code() == 200) {
                            if (!response.body().isEmpty()) {
                                Picasso.get().load(RetrofitClient.BASE_URL + response.body().get(0).getFile()).into(imageView13);
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

    }
}
