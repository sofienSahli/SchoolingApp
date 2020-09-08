package esprit.example.com.schoolingapp.services.intefaces;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IFonctionsServices {
    @POST("functions/mass_update")
    Call<ResponseBody> update_fonctionalities(@Body List<Fonctionalite> fonctionalites);

    @POST("fonctions/set_to_finished")
    Call<ResponseBody> set_to_finished(@Body List<Fonctionalite> fonctionalites);

    @POST("fonctions/set_problematic_to_solved")
    Call<ResponseBody> set_problematic_to_solved(@Body List<Problematique> problematiques);
}
