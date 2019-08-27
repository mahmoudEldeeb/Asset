package com.g2m.asset.models.dataModels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "AssetTabel")
public class AssetModel {
    @PrimaryKey
    public int id;
    public  int loc_id,sub_loc_id,room_id,cat_id;
    public String name,statues,describtion,cat_name;
    public String barcode;



}
