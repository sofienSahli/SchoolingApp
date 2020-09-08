package esprit.example.com.schoolingapp.services.implementations;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Media;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IMediaServices;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MediaServices {
    IMediaServices iCircuitServices;
    public MediaServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iCircuitServices = retrofitClient.getRetrofit().create(IMediaServices.class);
    }

    public void add_rapport(Callback<List<Media>> callback, int fiche_id, String titre, RequestBody filePart) {

        MultipartBody.Part file = MultipartBody.Part.createFormData("n", "n", filePart);
        Call<List<Media>> call = iCircuitServices.uploadRapprot_pfe(fiche_id,titre, file);
        call.enqueue(callback);

    }
    public void reports_for_id(int id , Callback<List<Media>> callback){
        Call<List<Media>> call = iCircuitServices.reports_for_id(id);
        call.enqueue(callback);
    }

    public void get_enseignant_image(long id, Callback<List<Media>>callback){
        Call<List<Media>> call = iCircuitServices.get_enseignant_photo(id);
        call.enqueue(callback);
    }

    public void get_student_image(long id, Callback<List<Media>>callback){
        Call<List<Media>> call = iCircuitServices.get_student_photo(id);
        call.enqueue(callback);
    }

}
