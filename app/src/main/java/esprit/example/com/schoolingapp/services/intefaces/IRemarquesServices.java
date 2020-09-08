package esprit.example.com.schoolingapp.services.intefaces;

import esprit.example.com.schoolingapp.entities.Remarques;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRemarquesServices {
    @POST("remarque")
    Call<ResponseBody> remarque(@Body Remarques remarques);

}
