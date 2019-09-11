package com.g2m.asset.models.databases;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.g2m.asset.assetInfo.AseetModel;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.AssetOfInventory;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.util.Constants;


@Database(entities = {LocationModel.class, SubLocationModel.class,
        RoomModel.class, AssetModel.class, AssetOfInventory.class,TransfeerModel.class, InventoryTabel.class},version = 1, exportSchema = true)

public abstract class LocalDatabase extends RoomDatabase {
    public abstract DataDao dao();
    private static volatile LocalDatabase INSTANCE;
    public static LocalDatabase getDatabase() {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(Constants.context.getApplicationContext(),
                            LocalDatabase.class, Constants.DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}