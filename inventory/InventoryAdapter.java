package com.g2m.asset.inventory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    List<InventoryTabel> list;
    Context context;
    public InventoryAdapter(Context cxt) {
        list=new ArrayList<>();
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<InventoryTabel> inventoryModels) {
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