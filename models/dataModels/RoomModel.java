package com.g2m.asset.models.dataModels;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RoomTabel")
public class RoomModel {
    @PrimaryKey
    @ColumnInfo(name = "room_t_id")
    public int id;
    @ColumnInfo(name = "room_t_loc_id")
    public int loc_id;
    @ColumnInfo(name = "room_t_sub_loc_id")
    public int sub_loc_id;
    @ColumnInfo(name = "room_t_name")
    public String name;
    @ColumnInfo(name = "room_t_department")
    public  String department;
    @ColumnInfo(name = "room_t_barcode")
    public String barcode;
}
