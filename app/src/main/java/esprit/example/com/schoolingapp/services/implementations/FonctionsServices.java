package esprit.example.com.schoolingapp.services.implementations;


import java.util.List;

import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IFonctionsServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class FonctionsServices {
    IFonctionsServices iFonctionsServices;

    public FonctionsServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iFonctionsServices = retrofitClient.getRetrofit().create(IFonctionsServices.class);
    }

    public void update_fonctions(Callback<ResponseBody> callback, List<Fonctionalite> fonctionalites) {
        Call<ResponseBody> call = iFonctionsServices.update_fonctionalities(fonctionalites);
        call.enqueue(callback);
    }

    public void set_to_finished(Callback<ResponseBody> callback, List<Fonctionalite> fonctionalites) {
        Call<ResponseBody> call = iFonctionsServices.set_to_finished(fonctionalites);
        call.enqueue(callback);
    }

    public void set_problematic_to_solved(Callback<ResponseBody> callback, List<Problematique> problematiques) {
        Call<ResponseBody> call = iFonctionsServices.set_problematic_to_solved(problematiques);
        call.enqueue(callback);
    }
}
