package com.g2m.asset.models.dataModels;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LocationTabel")
public class LocationModel {
    @PrimaryKey
    public int loc_t_id;
    public String loc_name;
    public String loc_barcode;
}
