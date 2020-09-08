package esprit.example.com.schoolingapp.services.implementations;

import java.util.List;

import esprit.example.com.schoolingapp.entities.Student;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IStudentServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class StudentServices {
    public void SignUpUser(Student user, Callback<ResponseBody> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IStudentServices iStudentServices = retrofitClient.getRetrofit().create(IStudentServices.class);
        Call<ResponseBody> call = iStudentServices.sign_up(user);
        call.enqueue(callback);
    }

    public void login(String email, String password, Callback<List<Student>> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IStudentServices iStudentServices = retrofitClient.getRetrofit().create(IStudentServices.class);
        Call<List<Student>> call = iStudentServices.log_in(email, password);
        call.enqueue(callback);
    }
}
