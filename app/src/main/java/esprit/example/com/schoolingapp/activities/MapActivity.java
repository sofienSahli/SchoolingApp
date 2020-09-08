package esprit.example.com.schoolingapp.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Adres;
import esprit.example.com.schoolingapp.fragment.FragmentInformationGlobal;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener {

    private MapView mapView;
    EditText loc;
    MarkerViewManager markerViewManager;
    MarkerView markerView;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.map_key));

        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        loc = findViewById(R.id.loc);
        getSupportActionBar().hide();
        findViewById(R.id.imageButton6).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Adres adres = new Adres();
            adres.setVille(loc.getText().toString());
            adres.setLongi(latLng.getLongitude());
            adres.setLati(latLng.getLatitude());
            bundle.putParcelable("Adres", adres);
            Toast.makeText(this, "longi "+ adres.getLongi() + " lati "+ adres.getLati(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MapActivity.this, NouvelleFichePFE.class);
            intent.putExtras(bundle);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MapActivity.this).toBundle());
        });
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        double longi = 0;
        markerViewManager = new MarkerViewManager(mapView, mapboxMap);
        double lati = 0;
        if (getIntent().getExtras().containsKey(FragmentInformationGlobal.SEARCH_RESULT_LONGITUDE)) {
            Bundle bundle = getIntent().getExtras();
            longi = bundle.getDouble(FragmentInformationGlobal.SEARCH_RESULT_LONGITUDE);
            lati = bundle.getDouble(FragmentInformationGlobal.SEARCH_RESULT_LATITUDE);

        }
        latLng = new LatLng(longi, lati);
        ImageView view = new ImageView(this);
        view.setImageResource(android.R.drawable.ic_menu_mylocation);
        view.setColorFilter(getColor(R.color.colorAccent));
        markerView = new MarkerView(new LatLng(longi, lati), view);
        markerViewManager.addMarker(markerView);
        view.requestLayout();

                /*
                    getLayoutParams()
                        Get the LayoutParams associated with this view.
                */

        // Apply the new height for ImageView programmatically
        view.getLayoutParams().height = 46;

        // Apply the new width for ImageView programmatically
        view.getLayoutParams().width = 46;

        // Set the scale type for ImageView image scaling
        view.setScaleType(ImageView.ScaleType.FIT_XY);

        geocode_device(longi, lati, mapboxMap);
        mapboxMap.addOnMapClickListener(this);

        mapboxMap.setStyle(Style.OUTDOORS, style -> {
        });
        mapboxMap.getUiSettings().setCompassEnabled(false);

    }

    private void geocode_device(double longi, double lati, MapboxMap mapbox) {
        Geocoder geocoder = new Geocoder(this);
        try {
            Address address = geocoder.getFromLocation(lati, longi, 1).get(0);
            LatLng latLng = new LatLng(address.getLongitude(), address.getLatitude());
            mapbox.animateCamera(CameraUpdateFactory
                    .newCameraPosition(new CameraPosition.Builder()
                            .target(latLng)
                            .zoom(13)
                            .build()), 1500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        this.latLng = point;
        Geocoder geocoder = new Geocoder(this);
        markerViewManager.removeMarker(markerView);
        if (markerView != null)
            markerView.setLatLng(point);
        markerViewManager.addMarker(markerView);
        try {
            Address address = geocoder.getFromLocation(point.getLatitude(), point.getLongitude(), 1).get(0);
            Log.e("TAG", "onQueryTextChange: " + address.toString());
            // showCityListMenu();
            // Initialize the AutocompleteSupportFragment.
            loc.setText(address.getLocality() + ", " + address.getSubAdminArea() + ". " + address.getAdminArea());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}