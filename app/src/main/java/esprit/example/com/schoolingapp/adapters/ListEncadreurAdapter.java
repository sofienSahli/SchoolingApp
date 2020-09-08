package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.AllocatEncadreurToFiche;
import esprit.example.com.schoolingapp.entities.Enseignant;
import esprit.example.com.schoolingapp.entities.Media;
import esprit.example.com.schoolingapp.services.RetrofitClient;
import esprit.example.com.schoolingapp.services.implementations.MediaServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ListEncadreurAdapter extends RecyclerView.Adapter<ListEncadreurAdapter.Holder> {
    Context context;
    List<Enseignant> enseignants;
    public static final int QUOTA = 2;

    public ListEncadreurAdapter(Context context, List<Enseignant> enseignants) {
        this.context = context;
        this.enseignants = enseignants;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_student_note, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setUpView(enseignants.get(position));
    }

    @Override
    public int getItemCount() {
        return enseignants.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name, matricule, textView25, moyenne_module;
        ImageView imageView4;
        ImageButton saisir_note;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            matricule = itemView.findViewById(R.id.matricule);
            saisir_note = itemView.findViewById(R.id.saisir_note);
            textView25 = itemView.findViewById(R.id.textView25);
            moyenne_module = itemView.findViewById(R.id.moyenne_module);

            imageView4 = itemView.findViewById(R.id.imageView4);
        }

        void setUpView(Enseignant e) {
            String s = e.getName() + " " + e.getLast_name();
            name.setText(s);
            matricule.setText(e.getIdentifiant());
            if (e.getFiches() != null) {
                if (e.getFiches().size() > 0) {
                    if (e.getFiches().size()+1 <= QUOTA) {
                        textView25.setTextColor(context.getColor(R.color.colorAccent));
                    } else {
                        textView25.setTextColor(context.getColor(R.color.colorPrimary));
                    }
                    textView25.setText("encadre " + e.getFiches().size() + " projets");

                } else {
                    textView25.setText("N'est pas encadreur pour aucun projet");
                }
            }

            moyenne_module.setText(e.getSpecialite());
            saisir_note.setImageDrawable(context.getDrawable(R.drawable.ic_dots_menu));
            saisir_note.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.affecter_encadreur_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.call) {
                        call(e.getPhone());
                    } else if (item.getItemId() == R.id.email) {
                        sendEmail(e.getE_mail());
                    } else if (item.getItemId() == R.id.allocate) {
                        if (e.getFiches().size()+1 <= QUOTA)
                            ((AllocatEncadreurToFiche) context).inflate_dialog(e);
                        else if (e.getFiches().size()+1 >= QUOTA)
                            Toast.makeText(context, "Le quota d'encadrement pour cette personne est déja atteint", Toast.LENGTH_LONG).show();
                    }


                    return true;
                });
            });
            if (e.getMedias() != null) {
                if (!e.getMedias().isEmpty())
                    Picasso.get().load(RetrofitClient.BASE_URL + e.getMedias().get(0).getFile()).into(imageView4);
            }


            if (context instanceof AllocatEncadreurToFiche)
                textView25.setOnClickListener(v -> ((AllocatEncadreurToFiche) context).changeViewToEncadreurDetails(e));
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
            Toast.makeText(context, e_mail, Toast.LENGTH_SHORT).show();
            try {

                context.startActivity(emailIntent);

            } catch (Exception e) {
                Log.e("Emaio", e.getMessage());
            }
        }


        private void getEnseignant_image(Enseignant e, ImageView imageView7) {
            MediaServices mediaServices = new MediaServices();
            if (e.getMedias() != null) {
                if (!e.getMedias().isEmpty())
                    Picasso.get().load(RetrofitClient.BASE_URL + e.getMedias().get(0)).into(imageView7);
            } else {
                mediaServices.get_enseignant_image(e.getId(), new Callback<List<Media>>() {
                    @Override
                    public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                        if (response.code() == 200) {
                            if (!response.body().isEmpty()) {
                                Picasso.get().load(RetrofitClient.BASE_URL + response.body().get(0)).into(imageView7);
                                e.setMedias(response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Media>> call, Throwable t) {
                        Timber.e("getEnseignant_image   " + t.getMessage());
                    }
                });
            }
        }

    }


}
