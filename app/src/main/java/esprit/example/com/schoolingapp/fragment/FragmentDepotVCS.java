package esprit.example.com.schoolingapp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.DepotEnseignantActivity;
import esprit.example.com.schoolingapp.activities.NouvelleFichePFE;
import esprit.example.com.schoolingapp.adapters.UrlProjetAdapters;
import esprit.example.com.schoolingapp.entities.UrlProjet;
import esprit.example.com.schoolingapp.services.implementations.UrlProjetServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDepotVCS extends Fragment {
    UrlProjetAdapters adapters;
    RecyclerView list_liens_depose;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depot_vcs, container, false);
        View dialog_view = inflater.inflate(R.layout.dialog_new_vcs, container, false);
        setUpView(view, dialog_view);
        return view;
    }

    private void setUpView(View view, View dilog_view) {
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        list_liens_depose = view.findViewById(R.id.list_liens_depose);
        if (getActivity() instanceof DepotEnseignantActivity)
            floatingActionButton.setVisibility(View.GONE);
        Callback<List<UrlProjet>> callback = new Callback<List<UrlProjet>>() {
            @Override
            public void onResponse(Call<List<UrlProjet>> call, Response<List<UrlProjet>> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getActivity(), "Aucun urls n'a été attaché à cette fiche PFE", Toast.LENGTH_LONG).show();
                    } else {
                        adapters = new UrlProjetAdapters(getActivity(), response.body());
                        list_liens_depose.setAdapter(adapters);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        list_liens_depose.setLayoutManager(layoutManager);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UrlProjet>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur intern, veuilliez ressayer plus tard", Toast.LENGTH_SHORT).show();
            }
        };

        UrlProjetServices urlProjetServices = new UrlProjetServices();
        int fiche_id = 0;
        if (getActivity() instanceof NouvelleFichePFE)
            fiche_id = ((NouvelleFichePFE) getActivity()).getFichePFE().getId();
        else
            fiche_id = ((DepotEnseignantActivity) getActivity()).getFichePFE().getId();

        urlProjetServices.getUrlsById(fiche_id, callback);
        floatingActionButton.setOnClickListener(v -> showDialogNewUrl(dilog_view, callback));
    }

    private void showDialogNewUrl(View view, Callback<List<UrlProjet>> callback) {
        EditText editTextTextPersonName, editTextTextPersonName2, editTextTextPersonName3, editTextTextPersonName4;
        Button cancel, valider;
        editTextTextPersonName = view.findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName2 = view.findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = view.findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4 = view.findViewById(R.id.editTextTextPersonName4);
        cancel = view.findViewById(R.id.cancel);
        valider = view.findViewById(R.id.valider);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        cancel.setOnClickListener(v -> alertDialog.dismiss());
        valider.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editTextTextPersonName.getText())) {
                editTextTextPersonName.setError("Ce champ est obligatoire ");
            } else if (TextUtils.isEmpty(editTextTextPersonName2.getText())) {
                editTextTextPersonName2.setError("Ce champ est obligatoire ");
            } else if (TextUtils.isEmpty(editTextTextPersonName3.getText())) {
                editTextTextPersonName3.setError("Ce champ est obligatoire ");
            } else if (TextUtils.isEmpty(editTextTextPersonName4.getText())) {
                editTextTextPersonName4.setError("Ce champ est obligatoire ");
            } else {
                alertDialog.dismiss();
                rajouter_new_url(editTextTextPersonName.getText().toString(), editTextTextPersonName2.getText().toString(), editTextTextPersonName3.getText().toString(), editTextTextPersonName4.getText().toString(), callback);
            }
        });
        alertDialog.show();
    }

    private void rajouter_new_url(String toString, String toString1, String toString2, String toString3, Callback<List<UrlProjet>> callback) {
        int fiche_id = 0;
        if (getActivity() instanceof NouvelleFichePFE)
            fiche_id = ((NouvelleFichePFE) getActivity()).getFichePFE().getId();
        else
            fiche_id = ((DepotEnseignantActivity) getActivity()).getFichePFE().getId();
        UrlProjet urlProjet = new UrlProjet();
        urlProjet.setFiche_id(fiche_id);
        urlProjet.setTitre(toString);
        urlProjet.setLibelle(toString1);
        urlProjet.setDescription(toString2);
        urlProjet.setUrl_text(toString3);
        UrlProjetServices urlProjetServices = new UrlProjetServices();
        urlProjetServices.insertNewRecord(urlProjet, callback);


    }


}
