package com.g2m.asset.models;

import com.g2m.asset.models.dataModels.InventoryModel;

import java.util.List;

public class EggsModel {
public List<List<Model>>asset_code;

    public List<List<Model>> getAsset_code() {
        return asset_code;
    }

    public List<InventoryModel> getAsset_adjust() {
        return asset_adjust;
    }

    public  List<InventoryModel>asset_adjust;
}
