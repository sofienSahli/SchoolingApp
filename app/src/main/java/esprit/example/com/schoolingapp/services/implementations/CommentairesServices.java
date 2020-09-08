package esprit.example.com.schoolingapp.services.implementations;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Commentaire;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.ICommentairesServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CommentairesServices {

    ICommentairesServices iCommentairesServices;

    public CommentairesServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iCommentairesServices = retrofitClient.getRetrofit().create(ICommentairesServices.class);
    }

    public void insert_new_comment(Commentaire commentaire, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = iCommentairesServices.insertComment(commentaire);
        call.enqueue(callback);
    }

    public void commentaires_for_media_id(int media_id, Callback<List<Commentaire>> callback) {
        Call<List<Commentaire>> call = iCommentairesServices.commentaire_for_media_id(media_id);
        call.enqueue(callback);
    }
}
