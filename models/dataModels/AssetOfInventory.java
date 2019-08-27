package com.g2m.asset.models.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AssetOfInventory",primaryKeys = {"inv_id","asset_id"})
public class AssetOfInventory {
    @ColumnInfo(name = "asset_id")
    public int asset_id;
    @ColumnInfo(name = "inv_id")
    public int inv_id;
    public int status;
}
