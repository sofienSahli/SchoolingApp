package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.DepotEnseignantActivity;
import esprit.example.com.schoolingapp.activities.FicheEncadrement;
import esprit.example.com.schoolingapp.entities.FichePFE;

public class EncadrementAdapter extends RecyclerView.Adapter<EncadrementAdapter.Holder> {

    Context context;
    List<FichePFE> fichePFEList;

    public EncadrementAdapter(Context context, List<FichePFE> fichePFEList) {
        this.context = context;
        this.fichePFEList = fichePFEList;
    }

    public List<FichePFE> getFichePFEList() {
        return fichePFEList;
    }

    public void setFichePFEList(List<FichePFE> fichePFEList) {
        this.fichePFEList = fichePFEList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.encadrement_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindView(fichePFEList.get(position));
    }

    @Override
    public int getItemCount() {
        if(fichePFEList!=null)
        return fichePFEList.size();
        else
            return 0 ;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name, nom_projet, company, textView63;
        ImageButton imageButton4;
        ImageView imageView8;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            nom_projet = itemView.findViewById(R.id.nom_projet);
            company = itemView.findViewById(R.id.company);
            imageButton4 = itemView.findViewById(R.id.imageButton4);
            imageView8 = itemView.findViewById(R.id.imageView8);
            textView63 = itemView.findViewById(R.id.textView63);

        }

        public void bindView(FichePFE fichePFE) {
            if (fichePFE.isAcceptedByEnseignant())
                textView63.setVisibility(View.GONE);

            String s = fichePFE.getStudent().getName() + "   " + fichePFE.getStudent().getLast_name();
            name.setText(s);
            nom_projet.setText(fichePFE.getTitre());
            company.setText(fichePFE.getNom_entreprise());
            imageButton4.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.popup_encadrement);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.call) {
                        call(fichePFE.getStudent().getPhone());
                    } else if (item.getItemId() == R.id.email) {
                        sendEmail(fichePFE.getStudent().getEmail());
                    } else if (item.getItemId() == R.id.details) {
                        Log.e("Fiche Adapter", fichePFE.toString());
                        if(!fichePFE.isAcceptedByEnseignant()){
                            Intent intent = new Intent(context, FicheEncadrement.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(FicheEncadrement.FICHE_KEY, fichePFE);
                            bundle.putParcelable(FicheEncadrement.ADDRESS, fichePFE.getAdress_entreprise());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }else {
                            Intent intent = new Intent(context, DepotEnseignantActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("fiche", fichePFE);
                            bundle.putParcelable(FicheEncadrement.ADDRESS, fichePFE.getAdress_entreprise());
                            intent.putExtras(bundle);
                            context.startActivity(intent);

                        }
                    }

                    return true;
                });
            });

        }

        private void call(long phone) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            context.startActivity(callIntent);
        }

        private void sendEmail(String e_mail) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            final PackageManager pm = context.getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            String className = null;

            for (final ResolveInfo info : matches) {
                if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                    className = info.activityInfo.name;

                    if (className != null && !className.isEmpty()) {
                        break;
                    }
                }
            }
            if (className != null)
                emailIntent.setClassName("com.google.android.gm", className);
            emailIntent.setType("message/rfc822");

            //emailIntent.setDataAndType(Uri.parse("mailto:" + e_mail),"message/rfc822" );
            emailIntent.putExtra(Intent.EXTRA_EMAIL, e_mail);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'encadrement pour PFE ");
            try {

                context.startActivity(emailIntent);

            } catch (Exception e) {
                Log.e("Emaio", e.getMessage());
            }
        }

    }
}
