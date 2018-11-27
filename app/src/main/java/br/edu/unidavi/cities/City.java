package br.edu.unidavi.cities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "city")
public class City {

    @PrimaryKey(autoGenerate = true)
    private final Integer id;
    private final String name;
    private final String cep;
    private final String uf;
    private final String latitude;
    private final String longitude;

    @Ignore
    public City(String name, String cep, String uf, String latitude, String longitude) {
        this.id = null;
        this.name = name;
        this.cep = cep;
        this.uf = uf;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(Integer id, String name, String cep, String uf, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.cep = cep;
        this.uf = uf;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getCep() { return cep; }

    public String getUf() { return uf; }

    public String getLatitude() { return latitude; }

    public String getLongitude() { return longitude; }
}
