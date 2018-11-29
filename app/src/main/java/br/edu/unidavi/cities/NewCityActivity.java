package br.edu.unidavi.cities;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class NewCityActivity extends AppCompatActivity {

    private CityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);

        viewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        viewModel.success.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                finish();
            }
        });

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> {

            EditText input_city_name = findViewById(R.id.input_city_name);
            EditText input_city_cep = findViewById(R.id.input_city_cep);
            EditText input_city_uf = findViewById(R.id.input_city_uf);
            EditText input_city_latitude = findViewById(R.id.input_city_latitude);
            EditText input_city_longitude = findViewById(R.id.input_city_longitude);

            if (
                    !input_city_name.getText().toString().isEmpty() &&
                            !input_city_cep.getText().toString().isEmpty() &&
                            !input_city_uf.getText().toString().isEmpty() &&
                            !input_city_latitude.getText().toString().isEmpty() &&
                            !input_city_longitude.getText().toString().isEmpty()
                    ) {
                viewModel.saveCity(new City(
                        input_city_name.getText().toString(),
                        input_city_cep.getText().toString(),
                        input_city_uf.getText().toString(),
                        input_city_latitude.getText().toString(),
                        input_city_longitude.getText().toString()
                ));
            }
        });

        Button buttonLocation = findViewById(R.id.button_location);
        buttonLocation.setOnClickListener(v -> {
            Log.i("Permissão: ", String.valueOf(hasFineLocationPermission()));
            if (hasFineLocationPermission()) {
                setLocation();
                return;
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    NewCityActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(NewCityActivity.this)
                        .setTitle("Por favor, não negue a permissão dessa vez!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermission();
                            }
                        })
                        .show();
            } else {
                requestPermission();
            }
        });
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(
                NewCityActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this,
                        "Localização Negada",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Localização Concedida!",
                        Toast.LENGTH_SHORT).show();
                setLocation();
            }
        }
    }

    boolean hasFineLocationPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void setLocation() {
        FusedLocationProviderClient local = LocationServices.getFusedLocationProviderClient(this);
        local.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("Latitude: ", String.valueOf(location.getLatitude()));
                Log.i("Longitude: ", String.valueOf(location.getLongitude()));

                ((EditText) findViewById(R.id.input_city_latitude)).setText(String.valueOf(location.getLatitude()));
                ((EditText) findViewById(R.id.input_city_longitude)).setText(String.valueOf(location.getLongitude()));
            }
        });
    }
}
