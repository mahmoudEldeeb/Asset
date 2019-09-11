package com.g2m.asset.models.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InventoryTabel")
public class InventoryTabel
{
    @PrimaryKey(autoGenerate = true)
    public int inv_id;
    public String display_name,date;

    @ColumnInfo(name = "sendOrNot",index = false)
    public boolean sendOrNot;

    public InventoryTabel(int inv_id, String display_name, String date) {
        this.inv_id = inv_id;
        this.display_name = display_name;
        this.date = date;
    }
}
