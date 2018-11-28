package br.edu.unidavi.cities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class DetailCityActivity extends AppCompatActivity {

    private City city;
    private CityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_city);

        viewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        viewModel.cityLiveData.observe(this, city -> {
            if (city != null) {
                this.city = city;

                ((EditText) findViewById(R.id.input_city_name)).setText(this.city.getName());
                ((EditText) findViewById(R.id.input_city_cep)).setText(this.city.getCep());
                ((EditText) findViewById(R.id.input_city_uf)).setText(this.city.getUf());
                ((EditText) findViewById(R.id.input_city_latitude)).setText(this.city.getLatitude());
                ((EditText) findViewById(R.id.input_city_longitude)).setText(this.city.getLongitude());

                setTitle(city.getName());
            }
        });

        viewModel.success.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                finish();
            }
        });

        int id = getIntent().getIntExtra("id", 0);
        viewModel.findCityById(id);

        Button buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(v -> viewModel.deleteCity(city));

        Button buttonDone = findViewById(R.id.button_save);
        buttonDone.setOnClickListener(v ->
                viewModel.updateCity(
                        new City(
                                city.getId(),
                                ((EditText) findViewById(R.id.input_city_name)).getText().toString(),
                                ((EditText) findViewById(R.id.input_city_cep)).getText().toString(),
                                ((EditText) findViewById(R.id.input_city_uf)).getText().toString(),
                                ((EditText) findViewById(R.id.input_city_latitude)).getText().toString(),
                                ((EditText) findViewById(R.id.input_city_longitude)).getText().toString()
                        )
                )
        );

        Button buttonMap = findViewById(R.id.button_map);
        buttonMap.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("id", city.getId());
            startActivity(intent);
        });
    }
}
