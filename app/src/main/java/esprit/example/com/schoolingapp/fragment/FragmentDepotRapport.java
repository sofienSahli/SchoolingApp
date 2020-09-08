package esprit.example.com.schoolingapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.DepotEnseignantActivity;
import esprit.example.com.schoolingapp.activities.NouvelleFichePFE;
import esprit.example.com.schoolingapp.adapters.RapportsAdapters;
import esprit.example.com.schoolingapp.entities.Media;
import esprit.example.com.schoolingapp.services.implementations.MediaServices;
import esprit.example.com.schoolingapp.utils.FileUtils;
import esprit.example.com.schoolingapp.utils.ProgressDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDepotRapport extends Fragment {
    private static final int PICKFILE_REQUEST_CODE = 101;
    RecyclerView recyclerView;
    ConstraintLayout constraint_add_rapport;
    ImageButton imageButton10;
    EditText titre;
    ImageButton imageButton11;
    Uri last_saved_path;
    RapportsAdapters rapportsAdapters;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depot_rapport, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        set_up_view(view);
        progressDialog = new ProgressDialog(getActivity());
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            getActivity().requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 101);
        }

        return view;
    }

    private void set_up_view(View view) {
        MediaServices mediaServices = new MediaServices();
        int id_fiche = 0;
        if (getActivity() instanceof NouvelleFichePFE)
            id_fiche = ((NouvelleFichePFE) getActivity()).getFichePFE().getId();
        else
            id_fiche = ((DepotEnseignantActivity) getActivity()).getFichePFE().getId();

        imageButton11 = view.findViewById(R.id.imageButton11);
        imageButton11.setOnClickListener(v -> {
            upload_rapport();
        });
        titre = view.findViewById(R.id.titre);
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        imageButton10 = view.findViewById(R.id.imageButton10);
        constraint_add_rapport = view.findViewById(R.id.constraint_add_rapport);
        if (getActivity() instanceof DepotEnseignantActivity)
            constraint_add_rapport.setVisibility(View.GONE);

      /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    constraint_add_rapport.setVisibility(View.VISIBLE);
                } else {
                    constraint_add_rapport.setVisibility(View.GONE);
                }
            }
        });*/
        imageButton10.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");
            startActivityForResult(intent, PICKFILE_REQUEST_CODE);
        });

        mediaServices.reports_for_id(id_fiche, new Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (!response.body().isEmpty()) {
                        rapportsAdapters = new RapportsAdapters(getActivity(), response.body());
                        recyclerView.setAdapter(rapportsAdapters);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {
                Log.e("Get Reports", "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "Erreur serveur interne veuilliez ressayer de recharger", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICKFILE_REQUEST_CODE) {
                if (null == data)
                    return;
                Uri myUri = data.getData();
                File file = new File(myUri.getPath());
                titre.setText(file.getName());
                FragmentDepotRapport.this.last_saved_path = myUri;
                Log.e("file selected", "onActivityResult: " + myUri.getAuthority());
            }
        }

    }

    public void upload_rapport() {
        progressDialog.showDialog();
        if (last_saved_path == null)
            Toast.makeText(getActivity(), "Veuilliez selectioner le rapport ", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(titre.getText())) {
            titre.setError("Veuilliez remplire ce champs");
        } else {
            String path = FileUtils.getPath(last_saved_path, getActivity());
            File file;
            if (path == null) {
                Toast.makeText(getActivity(), "Erreur de récupération du fichier", Toast.LENGTH_LONG).show();
                return;
            } else
                file = new File(path);
            RequestBody filePart = RequestBody.create(MediaType.parse("file"), file);
            int fiche_id;
            if (getActivity() instanceof NouvelleFichePFE)
                fiche_id = ((NouvelleFichePFE) getActivity()).getFichePFE().getId();
            else
                fiche_id = ((DepotEnseignantActivity) getActivity()).getFichePFE().getId();

            MediaServices mediaServices = new MediaServices();
            mediaServices.add_rapport(new Callback<List<Media>>() {
                @Override
                public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                    progressDialog.dismissDialog();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Toast.makeText(getActivity(), "Sauvegarde réussite", Toast.LENGTH_LONG).show();
                            rapportsAdapters.setMedia(response.body());
                            rapportsAdapters.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Internal erreur! Veuilliez ressayer plus tard", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Media>> call, Throwable t) {
                    progressDialog.dismissDialog();
                    Log.e("Services add rapport", "onFailure: " + t.getMessage());
                }
            }, fiche_id, titre.getText().toString(), filePart);


        }

    }


}


