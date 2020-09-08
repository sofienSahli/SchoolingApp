package esprit.example.com.schoolingapp.services.intefaces;

import esprit.example.com.schoolingapp.entities.NotePFE;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface INotePfeServices {
    @GET("notes/get_note/{id}")
    Call<NotePFE> getNoteById(@Path("id") long fiche_id);

    @POST("notes/store")
    Call<NotePFE> insert(@Body NotePFE notePFE);
}
