package com.g2m.asset.inventory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.g2m.asset.R;
import com.g2m.asset.assetInfo.AssetInfoActivity;
import com.g2m.asset.databinding.ItemInventoryBinding;
import com.g2m.asset.homePage.HomeModel;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.scannDialog.ScannRoomDialog;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    List<InventoryTabel> list;
    Context context;
    InventoryViewModel viewModel;
    public InventoryAdapter(Context cxt,InventoryViewModel viewModel) {
        list=new ArrayList<>();
        this.viewModel=viewModel;
        context=cxt;
    }

    @NonNull
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemInventoryBinding itemPageBinding=
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_inventory,
                        viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(itemPageBinding);
        itemPageBinding.setClickListener(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.ViewHolder viewHolder, final int i) {
      //  viewHolder.itemPageBinding.setHomeModel(list.get(i));
        viewHolder.itemPageBinding.setInventoryModel(list.get(i));
        viewHolder.itemPageBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Helper.isNetworkAvailable()) ViewDialog.showDialog(context.getResources().getString(R.string.no_internet),false);
                else viewModel.sendOnlineInventory(list.get(i).inv_id);
            }
        });
        viewHolder.itemPageBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 deleteDialog(list.get(i).inv_id);
            }
        });
    }

    private void deleteDialog(final int inv_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    });
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            viewModel.delete(inv_id);
                dialogInterface.dismiss();
        }
    });


  AlertDialog dialog = builder.create();
  dialog.show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<InventoryTabel> inventoryModels) {
        list.clear();
        list.addAll(inventoryModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ItemInventoryBinding itemPageBinding;
        public ViewHolder(ItemInventoryBinding itemview) {
            super(itemview.getRoot());
            itemPageBinding=itemview;

        }

        @Override
        public void onClick() {
            Log.v("sssss",getAdapterPosition()+"    b");
           // context.startActivity(new Intent(context, AssetInfoActivity.class));
            ScannRoomDialog scannRoomDialog=new ScannRoomDialog(context,list.get(getAdapterPosition()).inv_id);
            scannRoomDialog.setCanceledOnTouchOutside(false);
            scannRoomDialog.show();


        }

        @Override
        public void onTextChange() {

        }
    }
}