package br.edu.unidavi.cities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class CityViewModel extends AndroidViewModel {

    public final MutableLiveData<List<City>> cities = new MutableLiveData<>();
    public final MutableLiveData<City> cityLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> success = new MutableLiveData<>();

    public CityViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchCities() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cities.postValue(CityStore
                        .getInstance(getApplication())
                        .getCityDao().fetchCities());
                return null;
            }
        }.execute();
    }

    public void findCityById(final int id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                City city = CityStore.getInstance(getApplication())
                        .getCityDao().findById(id);
                cityLiveData.postValue(city);
                return null;
            }
        }.execute();
    }

    public void saveCity(final City city) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CityStore.getInstance(getApplication())
                        .getCityDao()
                        .insert(city);
                success.postValue(true);
                return null;
            }
        }.execute();
    }

    public void deleteCity(final City city) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                CityStore.getInstance(getApplication())
                        .getCityDao()
                        .delete(city);
                success.postValue(true);
                return null;
            }
        }.execute();
    }

    public void updateCity(final City city) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                CityStore.getInstance(getApplication())
                        .getCityDao()
                        .update(city);
                success.postValue(true);
                return null;
            }
        }.execute();
    }
}
