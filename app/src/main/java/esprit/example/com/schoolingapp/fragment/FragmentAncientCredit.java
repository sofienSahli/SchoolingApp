package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.CreditAdapter;
import esprit.example.com.schoolingapp.entities.Credit;

public class FragmentAncientCredit extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit, container, false);
        recyclerView = view.findViewById(R.id.list_credits);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        populate_list();

        return view;
    }

    private void populate_list() {
        List<Credit> list = new ArrayList<>();
        list.add(new Credit("Matematic de base 1 ", "1A12 ", "5 UAT"));
        list.add(new Credit("Matematic de base 2 ", "1A12 ", "4 UAT"));
        list.add(new Credit(" N tiers .net ", "3A2 ", "8 UAT"));
        list.add(new Credit("PI Dev ", "3A2 ", "10 UAT"));
        list.add(new Credit("Complexité ", "4SIM3 ", "5 UAT"));
        list.add(new Credit("Theorie des graphs", "4SIM3 ", "6 UAT"));
        CreditAdapter creditAdapter = new CreditAdapter(getActivity(), list);
        recyclerView.setAdapter(creditAdapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
