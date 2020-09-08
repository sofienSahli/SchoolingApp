package esprit.example.com.schoolingapp.services.intefaces;

import java.sql.Date;
import java.util.List;

import esprit.example.com.schoolingapp.entities.FichePFE;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFicheServices {
    @POST("fiche")
    Call<ResponseBody> upload_fiche(@Body FichePFE fichePFE);

    @GET("fiche")
    Call<List<FichePFE>> get_new_uploaded_fiches();

    @GET("fiche/alloc/{id_fiche}/{id_enseignant}")
    Call<ResponseBody> allocate_encadreur_fiche(@Path("id_fiche") int id_fiche, @Path("id_enseignant") int id_enseignant);

    @GET("fiche/forid/{id}")
    Call<List<FichePFE>> fiche_for_enseignant_id(@Path("id") long id_enseignant);

    @GET("fiche/accepter/{id}")
    Call<ResponseBody> accepter_fiche_by_enseigant(@Path("id") long id_fiche);

    @GET("fiche/refuser/{id}")
    Call<ResponseBody> refuser_fiche_by_enseigant(@Path("id") long id_fiche);

    @GET("fiche/getById/{id}")
    Call<FichePFE> get_fiche_by_id_student(@Path("id") long id_fiche);

    @GET("fiche/accepte_fiche_by_agent/{id_fiche}")
    Call<ResponseBody> accepte_fiche_by_agent(@Path("id_fiche") long id_fiche);

    @GET("fiche/refuser_fiche_by_agent/{id_fiche}")
    Call<ResponseBody> refuser_fiche_by_agent(@Path("id_fiche") long id_fiche);

    @GET("fiche/set_depot_date/{id_fiche}/{date}")
    Call<ResponseBody> set_depot_date(@Path("id_fiche") long id_fiche, @Path("date") Date date);

    @GET("get_fiches")
    Call<List<FichePFE>> get_archived_fiches();
}
