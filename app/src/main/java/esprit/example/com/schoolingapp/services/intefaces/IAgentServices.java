package esprit.example.com.schoolingapp.services.intefaces;


import esprit.example.com.schoolingapp.entities.Agent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAgentServices {
    @GET("person/login/{email}/{password}")
    Call<Agent> login(@Path("email") String email, @Path("password") String password);


}
