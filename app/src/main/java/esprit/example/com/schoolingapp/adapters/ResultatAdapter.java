package esprit.example.com.schoolingapp.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import esprit.example.com.schoolingapp.activities.ResultatDetailActivity;
import esprit.example.com.schoolingapp.entities.Resultat;

public class ResultatAdapter extends RecyclerView.Adapter<ResultatAdapter.Holder> {
    List<Resultat> list;
    Context context;

    public ResultatAdapter(List<Resultat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.resultat_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Resultat resultat = list.get(position);

        holder.textView5.setText(resultat.getSubject_name());
        holder.textView7.setText(resultat.getMoyenne() + " ");
        holder.details.setOnClickListener(v -> {
            if (context instanceof ResultatDetailActivity) {
                ((ResultatDetailActivity) context).setUpResultat(resultat);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(ResultatDetailActivity.TITLE, resultat.getSubject_name());
                bundle.putFloat(ResultatDetailActivity.MOYENNE_CC, resultat.getNote_cc());
                bundle.putFloat(ResultatDetailActivity.MOYENNE_DS, resultat.getNote_ds());
                bundle.putFloat(ResultatDetailActivity.MOYENNE_EXAMEN, resultat.getNote_examens());
                bundle.putFloat(ResultatDetailActivity.MOYENNE, resultat.getMoyenne());
                bundle.putFloat(ResultatDetailActivity.ID, resultat.getId());
                Intent intent = new Intent(context, ResultatDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            }


        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView5, textView7;
        Button details;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView5 = itemView.findViewById(R.id.textView5);
            textView7 = itemView.findViewById(R.id.textView7);
            details = itemView.findViewById(R.id.details);

        }
    }
}
