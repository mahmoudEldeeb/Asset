package com.g2m.asset.models.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class AllDataField {

    @Embedded
    AssetModel assetModel=new AssetModel();
    @Embedded
    LocationModel locationModel=new LocationModel();
    @Embedded
    SubLocationModel subLocationModel=new SubLocationModel();
    @Embedded
    RoomModel roomModel=new RoomModel();
    public String getFullLocation(){
        if(locationModel.loc_name!=null){
            return locationModel.loc_name+" / "+subLocationModel.name+" / "
                    +roomModel.name;
        }
        return "";
    }
    public SubLocationModel getSubLocationModel() {
        return subLocationModel;
    }

    public void setSubLocationModel(SubLocationModel subLocationModel) {
        this.subLocationModel = subLocationModel;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public void setRoomModel(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    public AssetModel getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(AssetModel assetModel) {
        this.assetModel = assetModel;
    }





}
