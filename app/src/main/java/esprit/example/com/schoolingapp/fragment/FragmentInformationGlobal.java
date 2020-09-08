package esprit.example.com.schoolingapp.fragment;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textview.MaterialTextView;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.MapActivity;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.local_storage.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInformationGlobal extends Fragment implements AdapterView.OnItemClickListener, TextWatcher {
    EditText nom_entreprise, titre_projet, description;
    LinearLayout tech;
    AutoCompleteTextView technologies;
    ListPopupWindow listPopup;
    EditText location;
    Point point;
    FusedLocationProviderClient fusedLocationProviderClient;
    ImageButton imageButton5;
    Adres adres;
    public static final String CURRENT_USER_LOCATION = "location_user";
    public static final String SEARCH_RESULT_LONGITUDE = "longi";
    public static final String SEARCH_RESULT_LATITUDE = "lati";
    public static final String SEARCH_RESULT_ALTITUDE = "alti";

    public Adres getAdres() {
        return adres;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_global_nouvel_fiche_pfe, container, false);
        checkPermission();
        viewInit(view);
        Bundle bundle = getActivity().getIntent().getExtras();
        this.adres = new Adres();
        if (!AppDatabase.getAppDatabase(getActivity()).fichePFE().getAll().isEmpty()) {
            FichePFE fichePFE = AppDatabase.getAppDatabase(getActivity()).fichePFE().getAll().get(0);
            if (bundle == null)
                adres = AppDatabase.getAppDatabase(getActivity()).adresDAO().findAdres(1);
            else if (bundle.containsKey("Adres")) {
                this.adres = bundle.getParcelable("Adres");
                adres.setIdentifiant(AppDatabase.getAppDatabase(getActivity()).adresDAO().findAdres(fichePFE.getId()).getIdentifiant());
                adres.setId_fiche(AppDatabase.getAppDatabase(getActivity()).adresDAO().findAdres(fichePFE.getId()).getIdentifiant());
                AppDatabase.getAppDatabase(getActivity()).adresDAO().updateFICHE(adres);
            }
            Log.e("adres", adres.toString());

            if (adres != null)
                location.setText(adres.getVille());
            nom_entreprise.setText(fichePFE.getNom_entreprise());
            titre_projet.setText(fichePFE.getTitre());
            description.setText(fichePFE.getDescription());
            String string = fichePFE.getTech().trim();
            List<String> techList = Arrays.asList(string.split(","));
            for (String s : techList) {
                addLabel(s);
            }
        }

        return view;
    }

    private void viewInit(View view) {
        nom_entreprise = view.findViewById(R.id.nom_entreprise);
        listPopup = new ListPopupWindow(getActivity());
        titre_projet = view.findViewById(R.id.entreprise_name);
        description = view.findViewById(R.id.description);
        tech = view.findViewById(R.id.tech);
        location = view.findViewById(R.id.location);
        location.addTextChangedListener(this);
        technologies = view.findViewById(R.id.technologies);
        technologies.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        addLabel(technologies.getText().toString());


                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        });

        technologies.setOnClickListener(v -> technologies.showDropDown());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getActivity().getResources().getStringArray(R.array.specialite));
        technologies.setAdapter(adapter);
        technologies.setOnItemClickListener(this);
        imageButton5 = view.findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(v -> {
            if (point != null) {


                Bundle bundle = new Bundle();
                bundle.putDouble(SEARCH_RESULT_LATITUDE, point.latitude());
                bundle.putDouble(SEARCH_RESULT_LONGITUDE, point.longitude());

                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                getLocation();
                Toast.makeText(getActivity(), " Veuilliez ressayer dans quelques secondes ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        point = Point.fromLngLat(location.getLatitude(), location.getLongitude());

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            getLocation();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addLabel(technologies.getText().toString());
        technologies.setText(null);

        technologies.clearFocus();
    }

    void addLabel(String s) {
        if (!TextUtils.isEmpty(s)) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            MaterialTextView editText = (MaterialTextView) layoutInflater.inflate(R.layout.label_techonologies, tech, false);
            editText.setText(s);
            editText.setOnClickListener(v -> {
                tech.removeView(v);
            });
            tech.addView(editText);
        }
    }


    private void geocodeLocation(String query) {
        String map_api = getString(R.string.map_key);
        if (point == null) {
            checkPermission();
        }
//        Log.e("Point", "geocodeLocation: " + point.toString());
        else {
            MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder().query(query).accessToken(map_api)
                    .proximity(point).autocomplete(true).build();
            mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                    List<CarmenFeature> results = response.body().features();

                    if (results.size() > 0) {
                        showCityListMenu(results);
                    } else {
                        new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setMessage("Lieux non trouvé, veuilliez essayer directement avec la map").setPositiveButton("OK"
                                , (dialog, which) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(getActivity(), MapActivity.class);
                                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                                });
                        Log.d("MAP_RESPONSE", "onResponse: No result found");
                    }

                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }

    }

    private void showCityListMenu(final List<CarmenFeature> results) {
        if (!listPopup.isShowing()) {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < results.size(); i++)
                names.add(results.get(i).placeName());
            ArrayAdapter<String> profileAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, names);
            listPopup.setAdapter(profileAdapter);
            listPopup.setAnchorView(location);
            listPopup.setOnItemClickListener((adapterView, view, position, log) -> {
                Point latLng = results.get(position).center();
                results.get(position).address();
                location.setText(adapterView.getItemAtPosition(position).toString());
                FragmentInformationGlobal.this.adres = new Adres();
                adres.setLati(latLng.latitude());
                adres.setLongi(latLng.longitude());
                adres.setVille(names.get(position));
                Log.e("Adre", "showCityListMenu: " + adres.toString());
            });
            listPopup.show();
        } else {
            listPopup.dismiss();
            showCityListMenu(results);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!TextUtils.isEmpty(s)) {
            geocodeLocation(s.toString());

        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
            geocodeLocation(s.toString());

        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            geocodeLocation(s.toString());
        }
    }


}
