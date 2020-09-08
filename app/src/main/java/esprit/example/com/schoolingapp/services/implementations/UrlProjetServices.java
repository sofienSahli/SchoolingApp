package esprit.example.com.schoolingapp.services.implementations;

import java.util.List;

import esprit.example.com.schoolingapp.entities.UrlProjet;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IUrlProjetsServices;
import retrofit2.Call;
import retrofit2.Callback;

public class UrlProjetServices {
    IUrlProjetsServices iUrlProjetsServices;

    public UrlProjetServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iUrlProjetsServices = retrofitClient.getRetrofit().create(IUrlProjetsServices.class);
    }


    public void getUrlsById(int id, Callback<List<UrlProjet>> callback) {
        Call<List<UrlProjet>> call = iUrlProjetsServices.get_by_id(id);
        call.enqueue(callback);
    }

    public void insertNewRecord(UrlProjet urlProjet, Callback<List<UrlProjet>> urlProjetCallback) {
        Call<List<UrlProjet>> call = iUrlProjetsServices.insert_new_record(urlProjet);
        call.enqueue(urlProjetCallback);
    }
}
