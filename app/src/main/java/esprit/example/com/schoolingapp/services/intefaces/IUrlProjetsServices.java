package esprit.example.com.schoolingapp.services.intefaces;

import java.util.List;

import esprit.example.com.schoolingapp.entities.UrlProjet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUrlProjetsServices {


    @GET("projet/get_by_id/{id}")
    public Call<List<UrlProjet>> get_by_id(@Path("id") int id);

    @POST("projet/add_url")
    public Call<List<UrlProjet>> insert_new_record(@Body UrlProjet urlProjet);
}
