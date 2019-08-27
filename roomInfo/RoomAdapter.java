package com.g2m.asset.roomInfo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.g2m.asset.R;
import com.g2m.asset.assetInfo.AssetInfoActivity;
import com.g2m.asset.databinding.ItemAssetBinding;
import com.g2m.asset.homePage.HomeModel;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.AssetModel;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    List<AssetModel> list;
    Context context;
    public RoomAdapter(Context cxt) {
        list=new ArrayList<>();
        context=cxt;


    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemAssetBinding itemPageBinding=
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_asset,
                        viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(itemPageBinding);
        itemPageBinding.setClickListener(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.itemPageBinding.setAssetModel(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ItemAssetBinding itemPageBinding;
        public ViewHolder(ItemAssetBinding itemview) {
            super(itemview.getRoot());
            itemPageBinding=itemview;

        }

        @Override
        public void onClick() {
            Log.v("sssss",getAdapterPosition()+"    b");
            context.startActivity(new Intent(context, AssetInfoActivity.class));

        }

        @Override
        public void onTextChange() {

        }
    }
}