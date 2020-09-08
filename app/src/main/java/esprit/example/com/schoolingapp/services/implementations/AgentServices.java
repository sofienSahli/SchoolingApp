package esprit.example.com.schoolingapp.services.implementations;


import esprit.example.com.schoolingapp.entities.Agent;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.intefaces.IAgentServices;
import retrofit2.Call;
import retrofit2.Callback;

public class AgentServices {

    public void login(String email, String password, Callback<Agent> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IAgentServices iAgentServices = retrofitClient.getRetrofit().create(IAgentServices.class);
        Call<Agent> call = iAgentServices.login(email, password);
        call.enqueue(callback);
    }
}
