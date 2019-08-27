package com.g2m.asset.models.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "SubLocTabel")

public class SubLocationModel {
    @PrimaryKey
    @ColumnInfo(name = "subloc_t_id")
    public int id;
    @ColumnInfo(name = "subloc_t_loc_id")
    public  int loc_id;
    @ColumnInfo(name = "subloc_t_name")
    public String name;
    @ColumnInfo(name = "subloc_t_barcode")

    public String barcode;

}
