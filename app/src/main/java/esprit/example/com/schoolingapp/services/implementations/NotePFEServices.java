package esprit.example.com.schoolingapp.services.implementations;

import esprit.example.com.schoolingapp.entities.NotePFE;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.INotePfeServices;
import retrofit2.Call;
import retrofit2.Callback;

public class NotePFEServices {
    INotePfeServices iNotePfeServices;

    public NotePFEServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iNotePfeServices = retrofitClient.getRetrofit().create(INotePfeServices.class);
    }

    public void store(NotePFE notePFE, Callback<NotePFE> callback) {
        Call<NotePFE> call = iNotePfeServices.insert(notePFE);
        call.enqueue(callback);
    }

    public void getByID(long fiche_id, Callback<NotePFE> callback) {
        Call<NotePFE> call = iNotePfeServices.getNoteById(fiche_id);
        call.enqueue(callback);
    }
}
