package esprit.example.com.schoolingapp.services.intefaces;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Media;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IMediaServices {
    @Multipart
    @POST("media/add_report")
    Call<List<Media>> uploadRapprot_pfe(@Part("owner_id") int fiche_id,@Part("titre")String titre, @Part MultipartBody.Part file);
    @GET("media/get_report/{id}")
    Call<List<Media>> reports_for_id(@Path("id") int fiche_id);
    @GET("media/get_student_photo/{id}")
    Call<List<Media>> get_student_photo(@Path("id") long fiche_id);
    @GET("media/get_enseignant_photo/{id}")
    Call<List<Media>> get_enseignant_photo(@Path("id") long fiche_id);

}
