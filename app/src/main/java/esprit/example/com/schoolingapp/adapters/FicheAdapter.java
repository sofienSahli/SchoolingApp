package esprit.example.com.schoolingapp.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.FichePFEDetail;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.entities.FichePFE;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.Holder> {


    private Context context;
    private List<FichePFE> pfeList;

    public FicheAdapter(Context context, List<FichePFE> pfeList) {
        this.context = context;
        this.pfeList = pfeList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<FichePFE> getPfeList() {
        return pfeList;
    }

    public void setPfeList(List<FichePFE> pfeList) {
        this.pfeList = pfeList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_fiche_pfe, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setUpView(pfeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (pfeList != null)
            return pfeList.size();
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        ImageView imageView5;
        TextView textView54, textView55, textView56, textView57, depot;
        ImageButton imageButton;
        FichePFE fichePFE;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView5 = itemView.findViewById(R.id.imageView5);
            textView54 = itemView.findViewById(R.id.textView54);
            textView55 = itemView.findViewById(R.id.textView55);
            textView56 = itemView.findViewById(R.id.textView56);
            textView57 = itemView.findViewById(R.id.textView57);
            depot = itemView.findViewById(R.id.depot);
            imageButton = itemView.findViewById(R.id.imageButton);
        }

        void setUpView(FichePFE fichePFE) {
            this.fichePFE = fichePFE;
            if (fichePFE.getStudent() != null) {


                String name = fichePFE.getStudent().getName() + " " + fichePFE.getStudent().getLast_name();
                textView54.setText(name);

            }
            if (fichePFE.getDate_depot() == null) {
                depot.setVisibility(View.GONE);
            } else {
                depot.setText("Depôt le " + fichePFE.getDate_depot().toString());
            }
            textView55.setText(fichePFE.getTitre());
            textView56.setText(fichePFE.getTech().trim());
            textView57.setText(fichePFE.getCreated_at());
            PopupMenu popupMenu = new PopupMenu(context, imageButton);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.fiche_pfe_row_menu);
            imageButton.setOnClickListener(v -> popupMenu.show());


        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (item.getItemId() == R.id.details) {
                Log.e("Adapter Fiche", fichePFE.toString());
                Bundle bundle = new Bundle();
                bundle.putParcelable(FichePFEDetail.ADDRESS, fichePFE.getAdress_entreprise());
                fichePFE.setAdress_entreprise(null);
                bundle.putParcelable(FichePFEDetail.FICHE_KEY, fichePFE);
                Intent intent = new Intent(context, FichePFEDetail.class);
                intent.setExtrasClassLoader(Adres.class.getClassLoader());
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) context).toBundle());
            }
            return true;
        }
    }

}
