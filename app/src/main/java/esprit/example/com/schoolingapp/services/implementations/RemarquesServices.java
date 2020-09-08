package esprit.example.com.schoolingapp.services.implementations;

import esprit.example.com.schoolingapp.entities.Remarques;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IRemarquesServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RemarquesServices {
    IRemarquesServices iRemarquesServices;

    public RemarquesServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        this.iRemarquesServices = retrofitClient.getRetrofit().create(IRemarquesServices.class);
    }

    public void posterRemarques(Remarques remarques, Callback<ResponseBody> responseBodyCallback) {
        Call<ResponseBody> call = this.iRemarquesServices.remarque(remarques);
        call.enqueue(responseBodyCallback);
    }
}
