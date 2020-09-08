package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.DepotEnseignantActivity;
import esprit.example.com.schoolingapp.adapters.FonctionsDepotAdapter;
import esprit.example.com.schoolingapp.adapters.ProblematiqueDepotAdapter;
import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;
import esprit.example.com.schoolingapp.services.implementations.FonctionsServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFonctionaliteProblematiqueDepot extends Fragment {

    List<Fonctionalite> fonctionalites;
    List<Problematique> problematiques;
    RecyclerView recyclerView;
    Button soumettre;

    public List<Fonctionalite> getFonctionalites() {
        return fonctionalites;
    }

    public void setFonctionalites(List<Fonctionalite> fonctionalites) {
        this.fonctionalites = fonctionalites;
    }

    public List<Problematique> getProblematiques() {
        return problematiques;
    }

    public void setProblematiques(List<Problematique> problematiques) {
        this.problematiques = problematiques;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_validate_fonctionalites, container, false);
        recyclerView = view.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        soumettre = view.findViewById(R.id.soumettre);
        set_up_view();
        return view;
    }

    private void set_up_view() {
        if (fonctionalites != null) {
            FonctionsDepotAdapter fonctionsDepotAdapter = new FonctionsDepotAdapter(getActivity(), fonctionalites);
            recyclerView.setAdapter(fonctionsDepotAdapter);
            if (getActivity() instanceof DepotEnseignantActivity) {
                soumettre.setOnClickListener(v -> {
                    FonctionsServices fonctionsServices = new FonctionsServices();
                    fonctionsServices.set_to_finished(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200)
                                Toast.makeText(getActivity(), "Changement sauvegardé ", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("fonctionalité updat fail", "onFailure: " + t.getMessage());
                            Toast.makeText(getActivity(), "Erreur interne", Toast.LENGTH_SHORT).show();
                        }
                    }, fonctionsDepotAdapter.getProblematiqueList());
                });
            } else
                soumettre.setVisibility(View.GONE);

        } else if (problematiques != null) {
            ProblematiqueDepotAdapter problematiqueDepotAdapter = new ProblematiqueDepotAdapter(getActivity(), problematiques);
            recyclerView.setAdapter(problematiqueDepotAdapter);
            if (getActivity() instanceof DepotEnseignantActivity)
                soumettre.setOnClickListener(v -> {
                    FonctionsServices fonctionsServices = new FonctionsServices();
                    fonctionsServices.set_problematic_to_solved(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200)
                                Toast.makeText(getActivity(), "Changement sauvegardé ", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("fonctionalité updat fail", "onFailure: " + t.getMessage());
                            Toast.makeText(getActivity(), "Erreur interne", Toast.LENGTH_SHORT).show();

                        }
                    }, problematiqueDepotAdapter.getProblematiqueList());
                });
            else
                soumettre.setVisibility(View.GONE);
        }
    }
}
