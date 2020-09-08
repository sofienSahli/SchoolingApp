package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.FicheAdapter;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FragmentFicheApprouve extends Fragment {
    RecyclerView list_fiche;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fiche_valide, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list_fiche = view.findViewById(R.id.list_fiche);
        list_fiche.setLayoutManager(layoutManager);
        FicheServices ficheServices = new FicheServices();
        ficheServices.get_archived_fiche(new Callback<List<FichePFE>>() {
            @Override
            public void onResponse(Call<List<FichePFE>> call, Response<List<FichePFE>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        FicheAdapter ficheAdapter = new FicheAdapter(getActivity(), response.body());
                        list_fiche.setAdapter(ficheAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FichePFE>> call, Throwable t) {
                Timber.e("setUpView   :" + t.getMessage());
            }
        });

    }
}
