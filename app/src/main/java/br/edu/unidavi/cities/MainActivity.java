package br.edu.unidavi.cities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CityAdapter adapter = new CityAdapter(city -> {
       Intent intent = new Intent(getApplicationContext(), DetailCityActivity.class);
       intent.putExtra("id", city.getId());
       startActivity(intent);
    });

    public CityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        viewModel.cities.observe(this, cities -> {
            if (cities != null) {
                adapter.setup(cities);
            }
        });

        RecyclerView cityList = findViewById(R.id.city_list);
        cityList.setLayoutManager(new LinearLayoutManager(this));
        cityList.setAdapter(adapter);

        /*FloatingActionButton button_detail = findViewById(R.id.button_detail);
        button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailCityActivity.class);
                intent.putExtra("id", viewModel.cities.getValue().get(0).getId());
                startActivity(intent);
            }
        });*/

        FloatingActionButton button_create = findViewById(R.id.button_create);
        button_create.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NewCityActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchCities();
    }
}
