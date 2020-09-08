package esprit.example.com.schoolingapp.services.intefaces;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IStudentServices {
    @POST("student")
    Call<ResponseBody> sign_up(@Body Student student);

    @GET("student/login/{email}/{password}")
    Call<List<Student>> log_in(@Path("email") String email, @Path("password") String password);

}
