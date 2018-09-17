package sr.pago.sdk.model;

import android.content.Context;
import android.content.SharedPreferences;

import sr.pago.sdk.SrPagoV2;
import sr.pago.sdk.definitions.Definitions;

/**
 * Created by desarrolladorandroid on 12/01/17.
 */

public class Settings {

    protected static Settings instance;
    protected volatile SharedPreferences preferenceInstance = null;
    final static Context mContext = SrPagoV2.getInstance().getContext();

    protected Settings(){
        preferenceInstance = getInstance(Definitions.SR_PAGO_SDK());
    }

    private static SharedPreferences getInstance() {
        if(instance == null) {
            instance = new Settings();
        }
        return instance.preferenceInstance;
    }

    private static SharedPreferences getInstance(String filename){
        return mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(){
        return getInstance().edit();
    }

    public synchronized static String getString(String key){
        return getInstance().getString(key, null);
    }

    public synchronized static void setString(String key, String value){
        getEditor().putString(key, value).commit();
    }

    public synchronized static boolean getBoolean(String key){
        return getInstance().getBoolean(key, false);
    }

    public synchronized static void setBoolean(String key, boolean value){
        getEditor().putBoolean(key, value).commit();
    }

    public synchronized static int getInt(String key){
        return getInstance().getInt(key, 0);
    }

    public synchronized static void setInt(String key, int value){
        getEditor().putInt(key, value).commit();
    }

    public synchronized static long getLong(String key){
        return getInstance().getLong(key, -1);
    }

    public synchronized static void setLong(String key, long value){
        getEditor().putLong(key, value).commit();
    }

    public synchronized static void clearPreference(String key){
        getEditor().remove(key).commit();
    }

    public synchronized static void clearAllPreferences(){
        getEditor().clear().commit();
    }
}
