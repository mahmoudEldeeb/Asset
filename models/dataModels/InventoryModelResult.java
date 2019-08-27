package com.g2m.asset.models.dataModels;

import java.util.List;

public class InventoryModelResult {
    List<InventoryModel>asset_adjust;

    private boolean status;
    private String message;
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InventoryModel> getAsset_adjust() {
        return asset_adjust;
    }

    public void setAsset_adjust(List<InventoryModel> asset_adjust) {
        this.asset_adjust = asset_adjust;
    }
}
