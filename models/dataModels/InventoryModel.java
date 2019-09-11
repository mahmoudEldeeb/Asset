package com.g2m.asset.models.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;



public class InventoryModel {


    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String display_name,date;
    private List<Integer>line_ids;


    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getLine_ids() {
        return line_ids;
    }

    public void setLine_ids(List<Integer> line_ids) {
        this.line_ids = line_ids;
    }
}
