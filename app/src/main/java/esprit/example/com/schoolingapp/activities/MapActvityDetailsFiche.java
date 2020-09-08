package esprit.example.com.schoolingapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.FichePFE;

public class MapActvityDetailsFiche extends AppCompatActivity implements OnMapReadyCallback {

    public static final String FICHE_KEY = "fiche";
    FichePFE fichePFE;
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Mapbox.getInstance(this, getString(R.string.map_key));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_actvity_details_fiche);
        view_init(savedInstanceState);
        getFiche();
    }

    private void view_init(Bundle savedInstanceState) {
        findViewById(R.id.imageButton8).setOnClickListener(v -> finish());
        getSupportActionBar().hide();
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void getFiche() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(FICHE_KEY)) {
            this.fichePFE = bundle.getParcelable(FICHE_KEY);
        } else {
            Toast.makeText(this, "Erreur systéme, veuilliez ressayer ultérireurement", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        MarkerViewManager markerViewManager = new MarkerViewManager(mapView, mapboxMap);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.marker_map_layout, mapView, false);
        TextView student_name, company_name;
        company_name = view.findViewById(R.id.entreprise_name);
        student_name = view.findViewById(R.id.student_name);
        company_name.setText(fichePFE.getNom_entreprise());
        student_name.setText(fichePFE.getStudent().getName() + " " + fichePFE.getStudent().getLast_name());
        double longi = fichePFE.getAdress_entreprise().getLongi();
        double lati = fichePFE.getAdress_entreprise().getLati();
        LatLng latLng = new LatLng();
        latLng.setLatitude(lati);
        latLng.setLatitude(longi);
        MarkerView markerView = new MarkerView(latLng, view);
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(12)
                        .build()), 1500);
        markerViewManager.addMarker(markerView);
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
        });
        mapboxMap.getUiSettings().setCompassEnabled(false);
    }
}