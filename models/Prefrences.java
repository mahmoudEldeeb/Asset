package com.g2m.asset.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.g2m.asset.util.Constants;

public class Prefrences
{
        public static SharedPreferences getPrefrence(){
            SharedPreferences preference;
            preference= Constants.context.getSharedPreferences("com.g2m.asset", Context.MODE_PRIVATE);
            return preference;
        }
        public static SharedPreferences.Editor getEditor(){
            return   getPrefrence().edit();
        }
        public static void changeFirstTime(){
            getEditor().putBoolean("isFirstTime",false).commit();
        }
        public static boolean iSfirstTime(){
            return getPrefrence().getBoolean("isFirstTime",true);
        }


    public static void changeFirstInventory(){
        getEditor().putBoolean("FirstInventory",false).commit();
    }
    public static boolean iSFirstInventory(){
        return getPrefrence().getBoolean("FirstInventory",true);
    }

}
