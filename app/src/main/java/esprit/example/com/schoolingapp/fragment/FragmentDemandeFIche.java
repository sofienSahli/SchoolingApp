package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.EspaceAgentActivity;
import esprit.example.com.schoolingapp.adapters.FicheAdapter;
import esprit.example.com.schoolingapp.entities.FichePFE;

public class FragmentDemandeFIche extends Fragment {
    RecyclerView list;
    FicheAdapter ficheAdapter;
    List<FichePFE> fichePFES;

    public FicheAdapter getFicheAdapter() {
        return ficheAdapter;
    }

    public void setFicheAdapter(FicheAdapter ficheAdapter) {
        this.ficheAdapter = ficheAdapter;
    }

    public List<FichePFE> getFichePFES() {
        return fichePFES;
    }

    public void setFichePFES(List<FichePFE> fichePFES) {
        this.fichePFES = fichePFES;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demande_fiche_agent, container, false);
        list = view.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);
        populate_list_view();
        if (getActivity() instanceof EspaceAgentActivity)
            list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (isMaxScrollReached(recyclerView)) {
                        ((EspaceAgentActivity)getActivity()).getFichesPFE();
                    }

                }
            });
        return view;
    }

    private boolean isMaxScrollReached(RecyclerView recyclerView) {
        int maxScroll = recyclerView.computeVerticalScrollRange();
        int currentScroll = recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent();
        return currentScroll >= maxScroll;
    }

    private void populate_list_view() {

        ficheAdapter = new FicheAdapter(getActivity(), fichePFES);
        list.setAdapter(ficheAdapter);
    }
}
