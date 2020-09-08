package esprit.example.com.schoolingapp.services.implementations;

import java.sql.Date;
import java.util.List;

import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IFicheServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class FicheServices {
    IFicheServices iFicheServices;

    public FicheServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iFicheServices = retrofitClient.getRetrofit().create(IFicheServices.class);

    }

    public void upload_fiche(FichePFE fichePFE, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = iFicheServices.upload_fiche(fichePFE);
        call.enqueue(callback);

    }

    public void get_unseen_fiches(Callback<List<FichePFE>> callback) {
        Call<List<FichePFE>> call = iFicheServices.get_new_uploaded_fiches();
        call.enqueue(callback);
    }

    public void allocate_enseignant_fiche(Callback<ResponseBody> callback, int id_fiche, int id_enseignant) {
        Call<ResponseBody> call = iFicheServices.allocate_encadreur_fiche(id_fiche, id_enseignant);
        call.enqueue(callback);
    }

    public void fiche_for_enseignant_id(Callback<List<FichePFE>> callback, long id_enseignant) {
        Call<List<FichePFE>> call = iFicheServices.fiche_for_enseignant_id(id_enseignant);
        call.enqueue(callback);

    }

    public void refuser_ficher_by_enseignant(Callback<ResponseBody> callback, long id_fiche) {
        Call<ResponseBody> call = iFicheServices.refuser_fiche_by_enseigant(id_fiche);
        call.enqueue(callback);
    }

    public void accepter_fiche_by_enseignant(Callback<ResponseBody> callback, long id_fiche) {
        Call<ResponseBody> call = iFicheServices.accepter_fiche_by_enseigant(id_fiche);
        call.enqueue(callback);
    }

    public void get_fiche_by_id_student(Callback<FichePFE> callback, long id_student) {
        Call<FichePFE> call = iFicheServices.get_fiche_by_id_student(id_student);
        call.enqueue(callback);
    }

    public void accepte_fiche_by_agent(long id_fiche, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = iFicheServices.accepte_fiche_by_agent(id_fiche);
        call.enqueue(callback);
    }

    public void refuser_fiche_by_agent(long id_fiche, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = iFicheServices.refuser_fiche_by_agent(id_fiche);
        call.enqueue(callback);
    }

    public void set_depot_date(long id_fiche, Date date, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = iFicheServices.set_depot_date(id_fiche, date);
        call.enqueue(callback);
    }

    public void get_archived_fiche(Callback<List<FichePFE>> callback) {
        Call<List<FichePFE>> call = iFicheServices.get_archived_fiches();
        call.enqueue(callback);
    }

}
