package ma.ensa.mobile_position;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;

    private RequestQueue requestQueue;

    private String insertUrl = "http://100.110.66.112:8087/position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        requestQueue = Volley.newRequestQueue(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap=googleMap;
        myMap.setMaxZoomPreference(17);
        JSONObject jsonRequest = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, insertUrl, jsonRequest,
                response -> {
                    try {
                        JSONArray positions = response.getJSONArray("all");
                        Log.d("Response", positions.toString());
                        for (int i = 0; i < positions.length(); i++) {
                            JSONObject position = positions.getJSONObject(i);

                            myMap.addMarker(new MarkerOptions().position(new
                                    LatLng(position.getDouble("latitude"),
                                    position.getDouble("longitude"))).title("Marker"));
                        }
                    } catch (Exception e) {

                    }
                },
                error -> {
                    Log.d("ErrorMap",error.toString()+error.getMessage());
                }
        );
        requestQueue.add(jsonObjectRequest);


    }
}