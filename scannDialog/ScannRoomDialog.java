package com.g2m.asset.scannDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.g2m.asset.R;
import com.g2m.asset.databinding.DialogScannRoomBinding;
import com.g2m.asset.homePage.HomeActivity;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.ViewDialog;

import java.util.List;

public class ScannRoomDialog extends Dialog  implements ClickListener {
    Context context;
    int id;
    public ScannRoomDialog(Context context,int id) {
        super(context);
        this.context=context;
        this.id=id;
    }
DialogScannRoomBinding scannRoomBinding;
    ScannAdapter scannAdapter;
    ScannViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        scannRoomBinding=  DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_scann_room, null, false);
        setContentView(scannRoomBinding.getRoot());
        scannRoomBinding.setClickListener(this);
        scannRoomBinding.resScann.setLayoutManager(new LinearLayoutManager(context));
        viewModel= ViewModelProviders.of((FragmentActivity) context).get(ScannViewModel.class);
        scannAdapter =new ScannAdapter(context);
        scannRoomBinding.resScann.setAdapter(scannAdapter);

        scannRoomBinding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getOwnerActivity().startActivity(new Intent(getOwnerActivity(),HomeActivity.class));
//                DialogInteract dialogInteract= (DialogInteract) Constants.context;
//                dialogInteract.onclick();
//
//
               // viewModel.assetConfirm(scannAdapter.scannModelsList,id);
                dismiss();
            }
        });

        viewModel.getAssetsOfInventory(id).observe((LifecycleOwner)context, new Observer<List<ScannModels>>() {
            @Override
            public void onChanged(List<ScannModels> scannModels) {
                scannAdapter.addItems(scannModels);
            }
        });

    }

    @Override
    public void onClick() {

    }

    @Override
    public void onTextChange() {
        String code =scannRoomBinding.barcode.getText().toString();
        if(code.length()==6) {
            int asset_id=scannAdapter.check(code);
           if(asset_id==-1)
           {
               ViewDialogNotPlace.showDialog(code);
           }
           else{
                viewModel.saveWhatScan(id,asset_id);
           }

        }
    }
}
