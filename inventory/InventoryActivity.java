package com.g2m.asset.inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ActivityInventoryBinding;
import com.g2m.asset.homePage.HomeActivity;
import com.g2m.asset.models.Prefrences;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.scannDialog.DialogInteract;
import com.g2m.asset.util.Constants;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements DialogInteract {
ActivityInventoryBinding inventoryBinding;
InventoryAdapter inventoryAdapter;
InventoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventoryBinding= DataBindingUtil.setContentView(this, R.layout.activity_inventory);
        inventoryBinding.resInventory.setLayoutManager(new LinearLayoutManager(this));
        Constants.context=this;
        inventoryAdapter=new InventoryAdapter(this);
        inventoryBinding.resInventory.setAdapter(inventoryAdapter);
        viewModel= ViewModelProviders.of(this).get(InventoryViewModel.class);
        viewModel.getOperations();
        viewModel.getOperationsOnline();
        viewModel.listOfOperation.observe(this, new Observer<List<InventoryTabel>>() {
            @Override
            public void onChanged(List<InventoryTabel> inventoryModels) {
                inventoryAdapter.addItems(inventoryModels);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.context=this;
    }

    @Override
    public void onclick() {
        startActivity(new Intent(InventoryActivity.this, HomeActivity.class));

    }
}
