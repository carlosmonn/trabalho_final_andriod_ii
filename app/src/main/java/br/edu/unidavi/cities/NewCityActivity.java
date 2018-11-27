package br.edu.unidavi.cities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
    }
}
