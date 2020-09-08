package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.NouvelleFichePFE;
import esprit.example.com.schoolingapp.entities.Fonctionalite;

public class FonctionsDepotAdapter extends RecyclerView.Adapter<FonctionsDepotAdapter.Holder> {
    Context context;
    List<Fonctionalite> problematiqueList;

    public FonctionsDepotAdapter(Context context, List<Fonctionalite> problematiqueList) {
        this.context = context;
        this.problematiqueList = problematiqueList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Fonctionalite> getProblematiqueList() {
        return problematiqueList;
    }

    public void setProblematiqueList(List<Fonctionalite> problematiqueList) {
        this.problematiqueList = problematiqueList;
    }

    @NonNull
    @Override
    public FonctionsDepotAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_validate_fonct_prob, parent, false);
        return new FonctionsDepotAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FonctionsDepotAdapter.Holder holder, int position) {
        holder.bind_view(problematiqueList.get(position));
    }

    @Override
    public int getItemCount() {
        return problematiqueList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView84;
        Switch switch2;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView84 = itemView.findViewById(R.id.textView84);
            switch2 = itemView.findViewById(R.id.switch2);
        }

        public void bind_view(Fonctionalite problematique) {
            switch2.setChecked(problematique.isIs_implemented());
            textView84.setText(problematique.getFunc());
            if (context instanceof NouvelleFichePFE)
                switch2.setEnabled(false);
            switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                problematique.setIs_implemented(isChecked);
            });
        }
    }
}
