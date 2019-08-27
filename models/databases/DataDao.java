package com.g2m.asset.models.databases;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.g2m.asset.assetInfo.AseetModel;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.AssetOfInventory;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.scannDialog.ScannModels;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;


@Dao
public interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOperations(List<InventoryTabel> asset_adjust);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAssetOfOperations(List<AssetOfInventory> listAsset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllLocs(List<LocationModel> items);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSubLocs(List<SubLocationModel> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllRoom(List<RoomModel> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAsset(List<AssetModel> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransfer(List<TransfeerModel> list);

    @Query("SELECT * FROM AssetTabel")
    LiveData<List<AssetModel>> getAllAsset();

    @Query("SELECT * "+
            " FROM AssetTabel "+
            "where AssetTabel.barcode=:barcode"
    )
    Single<AssetModel> getAssetModel(String barcode);

    @Query("SELECT AssetTabel.*,LocationTabel.*,SubLocTabel.*," +
            "RoomTabel.*" +
            " FROM AssetTabel INNER JOIN LocationTabel  " +
            "ON AssetTabel.loc_id = LocationTabel.loc_t_id  " +
            "INNER JOIN RoomTabel ON AssetTabel.room_id=RoomTabel.room_t_id "+
            "INNER JOIN SubLocTabel ON AssetTabel.sub_loc_id=SubLocTabel.subloc_t_loc_id " +
            "where AssetTabel.barcode=:barcode"
    )
    LiveData<AllDataField> getAssetData(String barcode);


    @Query("SELECT * FROM AssetTabel where room_id = (Select room_id From RoomTabel where barcode=:code)")
    Single<List<AssetModel>> getAsetOfRoom(String code);
    @Query("SELECT room_t_department FROM RoomTabel where room_t_barcode=:barcode")
    LiveData<String> getDepartment(String barcode);



    @Query("SELECT * FROM LocationTabel")
    LiveData<List<LocationModel>> getLocations();

    @Query("SELECT * FROM SubLocTabel where subloc_t_loc_id=:id")
    LiveData<List<SubLocationModel>> getSubLocations(int id);

    @Query("SELECT * FROM RoomTabel where room_t_loc_id=:idLoc and room_t_sub_loc_id=:idsubLoc")
    LiveData<List<RoomModel>> getRooms(int idLoc, int idsubLoc);

    @Query("SELECT * FROM TransferTabel")
    Single<List<TransfeerModel>> getTransfers();

    @Query("SELECT Count(*) FROM TransferTabel where status=:statue")
    LiveData<Integer> getTransfersCount(boolean statue);



    @Query("SELECT AssetTabel.* from AssetTabel" )
    Single<List<AssetModel>> getAllData();

    @Query("SELECT AssetTabel.name,AssetTabel.barcode , AssetOfInventory.status ,AssetOfInventory.asset_id FROM AssetOfInventory join AssetTabel on AssetTabel.id=AssetOfInventory.asset_id " +
            "where  AssetOfInventory.inv_id =:id " )
    LiveData<List<ScannModels>> getAssetsOfInventory(int id);


//@Query("Update AssetTabel set loc_id=:loc_id and sub_loc_id=:sub_loc and room_id=:room where barcode=:barcode")
//    int updateAssetLocation(int loc_id,int sub_loc,int room,String barcode);

    @Query("Update TransferTabel set status=:state where barcode=:barcode")
    void updateAssetTransferModel(boolean state,String barcode);

@Update
    void updateAsset(AssetModel assetModel);



    @Query("SELECT * from InventoryTabel" )
    Single<List<InventoryTabel>> getAllOperations();

    @Query("SELECT * from InventoryTabel " )
    InventoryTabel checkScannedOrNot();

    @Update
    void saveWhatScan(AssetOfInventory asset);
}
