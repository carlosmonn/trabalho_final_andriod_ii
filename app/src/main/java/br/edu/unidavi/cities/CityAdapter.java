package br.edu.unidavi.cities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private final OnTaskClickListener listener;
    private List<City> cities = new ArrayList<>();

    public CityAdapter(OnTaskClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final City city = cities.get(i);
        viewHolder.name.setText(cities.get(i).getName() + " - " + cities.get(i).getUf());
        viewHolder.longLat.setText(cities.get(i).getLatitude() + ", " + cities.get(i).getLongitude());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onClick(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setup(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView longLat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(android.R.id.text1);
            longLat = itemView.findViewById(android.R.id.text2);
        }
    }

    interface OnTaskClickListener {
        void onClick(City city);
    }
}
