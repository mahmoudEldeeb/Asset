package com.g2m.asset.oldTransfeer;

import androidx.room.Embedded;

import com.g2m.asset.models.dataModels.TransfeerModel;

public class OldTransfeerModel {
    public String name;
    @Embedded
    public TransfeerModel transfeerModel;
}
