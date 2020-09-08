package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Remarques;

public class RemarquesAdapter extends RecyclerView.Adapter<RemarquesAdapter.Holder> {

    Context context;
    List<Remarques> remarques;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Remarques> getRemarques() {
        return remarques;
    }

    public void setRemarques(List<Remarques> remarques) {
        this.remarques = remarques;
    }

    public RemarquesAdapter(Context context, List<Remarques> remarques) {
        this.context = context;
        this.remarques = remarques;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_remarques, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindView(remarques.get(position));
    }

    @Override
    public int getItemCount() {
        return remarques.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView message, name, date;

        public Holder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
        }

        public void bindView(Remarques remarques) {
            message.setText(remarques.getMessage());
            date.setText(remarques.getCreated_at());
        }
    }
}
