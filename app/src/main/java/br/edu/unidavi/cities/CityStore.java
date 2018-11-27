package br.edu.unidavi.cities;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = City.class, version = 2)
public abstract class CityStore extends RoomDatabase {

    public abstract CityDao getCityDao();

    private static CityStore instance = null;

    public static CityStore getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CityStore.class, "cities.db").build();
        }
        return instance;
    }
}
