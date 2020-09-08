package esprit.example.com.schoolingapp.services.implementations;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IEnseignantServices;
import retrofit2.Call;
import retrofit2.Callback;

public class EnseignantServices {
    public void getEnseignant(Callback<List<Enseignant>> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IEnseignantServices iEnseignantServices = retrofitClient.getRetrofit().create(IEnseignantServices.class);
        Call<List<Enseignant>> call = iEnseignantServices.get_enseignant();
        call.enqueue(callback);
    }

    public void login(Callback<Enseignant> callback, String email, String password) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IEnseignantServices iEnseignantServices = retrofitClient.getRetrofit().create(IEnseignantServices.class);
        Call<Enseignant> call = iEnseignantServices.login(email, password);
        call.enqueue(callback);
    }

}
