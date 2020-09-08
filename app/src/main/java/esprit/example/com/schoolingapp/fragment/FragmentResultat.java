package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.ResultatAdapter;
import esprit.example.com.schoolingapp.entities.Resultat;

public class FragmentResultat extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultat, container, false);
        recyclerView = view.findViewById(R.id.list_resultat);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        populate_list();
        return view;
    }

    private void populate_list() {
        List<Resultat> list = new ArrayList<>();
        list.add(new Resultat("Matematic 1 ", 10.12f, 11, 15, 17));
        list.add(new Resultat("JEE ", 10.12f, 11, 15, 17));
        list.add(new Resultat(".Net ", 10.12f, 11, 15, 17));
        list.add(new Resultat("Unity ", 10.12f, 11, 15, 17));
        list.add(new Resultat("Java  ", 10.12f, 11, 15, 17));
        list.add(new Resultat("Java me ", 10.12f, 11, 15, 17));
        list.add(new Resultat("Android", 10.12f, 11, 15, 17));
        list.add(new Resultat("IOS ", 10.12f, 11, 15, 17));
        ResultatAdapter resultatAdapter = new ResultatAdapter(list, getActivity());
        recyclerView.setAdapter(resultatAdapter);
        resultatAdapter.notifyDataSetChanged();
    }
}
