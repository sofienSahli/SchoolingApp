package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.EncadrementAdapter;
import esprit.example.com.schoolingapp.entities.FichePFE;

public class FragmentEncadrement extends Fragment {
    ArrayList<FichePFE> fichePFES;
    RecyclerView list_encdrement;
    EncadrementAdapter encadrementAdapter;

    public ArrayList<FichePFE> getFichePFES() {
        return fichePFES;
    }

    public void setFichePFES(ArrayList<FichePFE> fichePFES) {

        this.fichePFES = fichePFES;
        if (encadrementAdapter != null) {
            encadrementAdapter.setFichePFEList(this.fichePFES);
            encadrementAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encadrement, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list_encdrement = view.findViewById(R.id.list_encdrement);
        list_encdrement.setLayoutManager(layoutManager);
        encadrementAdapter = new EncadrementAdapter(getActivity(), fichePFES);
        list_encdrement.setAdapter(encadrementAdapter);
        return view;
    }
}
