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
import esprit.example.com.schoolingapp.entities.Problematique;

public class ProblematiqueDepotAdapter extends RecyclerView.Adapter<ProblematiqueDepotAdapter.Holder> {
    Context context;
    List<Problematique> problematiqueList;

    public ProblematiqueDepotAdapter(Context context, List<Problematique> problematiqueList) {
        this.context = context;
        this.problematiqueList = problematiqueList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Problematique> getProblematiqueList() {
        return problematiqueList;
    }

    public void setProblematiqueList(List<Problematique> problematiqueList) {
        this.problematiqueList = problematiqueList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_validate_fonct_prob, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
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

        public void bind_view(Problematique problematique) {
            switch2.setChecked(problematique.isIs_solved());
            textView84.setText(problematique.getProb());
            if(context instanceof NouvelleFichePFE)
                switch2.setEnabled(false);
            switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                problematique.setIs_solved(isChecked);
            });

        }
    }
}
