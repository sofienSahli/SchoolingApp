package esprit.example.com.schoolingapp.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.UrlProjet;

public class UrlProjetAdapters extends RecyclerView.Adapter<UrlProjetAdapters.Holder> {
    Context context;
    List<UrlProjet> urlProjets;

    public UrlProjetAdapters(Context context, List<UrlProjet> urlProjets) {
        this.context = context;
        this.urlProjets = urlProjets;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_vcs, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindView(urlProjets.get(position));
    }

    @Override
    public int getItemCount() {
        return urlProjets.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView date_depot;
        TextView libelle;
        TextView textView81;
        TextView url_text;
        ImageButton copy_url;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date_depot = itemView.findViewById(R.id.date_depot);
            libelle = itemView.findViewById(R.id.libelle);
            textView81 = itemView.findViewById(R.id.textView81);
            url_text = itemView.findViewById(R.id.url_text);
            copy_url = itemView.findViewById(R.id.copy_url);
        }

        public void bindView(UrlProjet urlProjet) {
            date_depot.setText(urlProjet.getCreated_at());
            libelle.setText(urlProjet.getLibelle());
            textView81.setText(urlProjet.getTitre());
            url_text.setText(urlProjet.getUrl_text());
            copy_url.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Lien git projet", urlProjet.getUrl_text());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Text copié!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
