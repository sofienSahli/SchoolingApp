package esprit.example.com.schoolingapp.services.intefaces;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Enseignant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IEnseignantServices {

    @GET("enseigant")
    public Call<List<Enseignant>> get_enseignant();

    @GET("enseigant/login/{email}/{password}")
    public Call<Enseignant> login(@Path("email") String email, @Path("password") String password);
}
