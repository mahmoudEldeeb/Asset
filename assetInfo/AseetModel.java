package com.g2m.asset.assetInfo;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AssetModel")

public class AseetModel  extends BaseObservable {

    @PrimaryKey
    public int id;
    public String barcode,loc_name,sub_loc_name,room_name;
    public int cat_id;
    public int loc_id,sub_loc_id,room;


       public int getId() {
        return id;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getSub_loc_name() {
        return sub_loc_name;
    }

    public void setSub_loc_name(String sub_loc_name) {
        this.sub_loc_name = sub_loc_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public int getSub_loc_id() {
        return sub_loc_id;
    }

    public int getRoom() {
        return room;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setSub_loc_id(int sub_loc_id) {
        this.sub_loc_id = sub_loc_id;
    }

    private String describtion;

    private String status;
    private String location;



    @Bindable
    public String getBarcode() {
        return barcode;
    }
    @Bindable
    public String getDescribtion() {
        return describtion;
    }
    @Bindable
    public String getStatus() {
        return status;
    }
    @Bindable
    public String getLocation() {
        return location;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;

    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;

//        notifyPropertyChanged(BR.assetModel);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
