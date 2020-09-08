package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.NouvelleFichePFE;
import esprit.example.com.schoolingapp.adapters.FonctionaliteAdapter;
import esprit.example.com.schoolingapp.adapters.ProblematicAdapter;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.entities.Problematique;
import esprit.example.com.schoolingapp.local_storage.AppDatabase;

public class FragmetInformationFonctionelPFE extends Fragment implements View.OnClickListener {
    EditText problematique_tv, fonctionalite;
    ListView list_problematique, list_fonctionalite;
    ProblematicAdapter problematique_adapter;
    FonctionaliteAdapter fonctionaliteAdapter;
    ImageButton add_prob, add_func;
    int fiche_id;

    public ProblematicAdapter getProblematique_adapter() {
        return problematique_adapter;
    }

    public void setProblematique_adapter(ProblematicAdapter problematique_adapter) {
        this.problematique_adapter = problematique_adapter;
    }

    public FonctionaliteAdapter getFonctionaliteAdapter() {
        return fonctionaliteAdapter;
    }

    public void setFonctionaliteAdapter(FonctionaliteAdapter fonctionaliteAdapter) {
        this.fonctionaliteAdapter = fonctionaliteAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_fonctionel, container, false);
        setUpView(view);
        if (!AppDatabase.getAppDatabase(getActivity()).fichePFE().getAll().isEmpty()) {
            FichePFE fichePFE = AppDatabase.getAppDatabase(getActivity()).fichePFE().getAll().get(0);
            this.fiche_id = fichePFE.getId();

        }
        Log.e("Fiche_id", fiche_id + " ");

        List<Problematique> problematiques = AppDatabase.getAppDatabase(getActivity()).Problematiques().findProbs(this.fiche_id);
        List<Fonctionalite> fonctionalites = AppDatabase.getAppDatabase(getActivity()).FonctionalitesDAO().findProbs(this.fiche_id);

        problematiques = new ArrayList<>(new LinkedHashSet<>(problematiques));
        fonctionalites = new ArrayList<>(new LinkedHashSet<>(fonctionalites));
        problematique_adapter.getProblematiqueLis().addAll(problematiques);
        fonctionaliteAdapter.getFonctionalites().addAll(fonctionalites);
        problematique_adapter.notifyDataSetChanged();
        fonctionaliteAdapter.notifyDataSetChanged();

        return view;
    }

    private void setUpView(View view) {
        problematique_tv = view.findViewById(R.id.problematique_tv);
        list_problematique = view.findViewById(R.id.list_problematique);
        problematique_adapter = new ProblematicAdapter(new ArrayList<>(), getActivity());
        fonctionaliteAdapter = new FonctionaliteAdapter(new ArrayList<Fonctionalite>(), getActivity());
        list_problematique.setAdapter(problematique_adapter);
        add_prob = view.findViewById(R.id.add_prob);
        fonctionalite = view.findViewById(R.id.fonctionalite);
        add_func = view.findViewById(R.id.add_func);
        list_fonctionalite = view.findViewById(R.id.list_fonctionalite);
        list_fonctionalite.setAdapter(fonctionaliteAdapter);
        list_fonctionalite.setOnItemClickListener(fonctionaliteAdapter);
        list_problematique.setOnItemClickListener(problematique_adapter);
        add_prob.setOnClickListener(this);
        add_func.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_prob) {
            if (TextUtils.isEmpty(problematique_tv.getText()))
                problematique_tv.setError("Veuilliez renseigner ce champs");
            else {
                Problematique problematique;
                if (fiche_id != 0)
                    problematique = new Problematique(problematique_tv.getText().toString(), fiche_id);
                else
                    problematique = new Problematique(problematique_tv.getText().toString());

                problematique_adapter.getProblematiqueLis().add(problematique);
                if(getActivity() instanceof  NouvelleFichePFE )
                ((NouvelleFichePFE) getActivity()).save_fiche_draft();
                problematique_adapter.notifyDataSetChanged();
                problematique_tv.setText("");
            }
        } else if (v.getId() == R.id.add_func) {
            if (TextUtils.isEmpty(fonctionalite.getText()))
                fonctionalite.setError("Veuilliez renseigner ce champs");
            else {
                Fonctionalite fonctionalite;
                if (fiche_id != 0)
                    fonctionalite = new Fonctionalite(this.fonctionalite.getText().toString(), fiche_id);
                else
                    fonctionalite = new Fonctionalite(this.fonctionalite.getText().toString());

                fonctionaliteAdapter.getFonctionalites().add(fonctionalite);
                if(getActivity() instanceof  NouvelleFichePFE )
                    ((NouvelleFichePFE) getActivity()).save_fiche_draft();
                fonctionaliteAdapter.notifyDataSetChanged();
                this.fonctionalite.setText("");


            }
        }
    }
}
