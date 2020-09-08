package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Credit;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.Holder> {

    Context context;
    List<Credit> credits;

    public CreditAdapter(Context context, List<Credit> credits) {
        this.context = context;
        this.credits = credits;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.credit_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Credit credit = credits.get(position);
        holder.textView3.setText(credit.getTitle());
        holder.textView4.setText(credit.getClasse());
        holder.credit_detail.setOnClickListener(v -> {
            Toast.makeText(context, "You will be notified", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView3, textView4;
        Button credit_detail;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            credit_detail = itemView.findViewById(R.id.credit_detail);

        }
    }
}
