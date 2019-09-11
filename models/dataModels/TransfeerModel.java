package com.g2m.asset.models.dataModels;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "TransferTabel")
public class TransfeerModel implements Serializable {
@PrimaryKey
@NonNull
    public String barcode;
    public  int location_id_old ;
    public  int   sub_location_old;
    public  int  room_old;
    public  int   asset_id_old;
    public  int       location_id_new;
    public  int  sub_location_new;
    public  int       room_new;
    public boolean status;
}
