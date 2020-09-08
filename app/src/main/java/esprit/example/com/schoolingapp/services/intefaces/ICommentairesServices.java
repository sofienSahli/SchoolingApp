package esprit.example.com.schoolingapp.services.intefaces;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Commentaire;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICommentairesServices {

    @POST("coms")
    Call<ResponseBody> insertComment(@Body Commentaire commentaire);

    @GET("coms/{id}")
    Call<List<Commentaire>> commentaire_for_media_id(@Path("id") int media_id);

}
