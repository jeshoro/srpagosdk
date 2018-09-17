package sr.pago.sdk.object;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.readers.qpos.QposReader;
import sr.pago.sdk.utils.Logger;
import sr.pago.sdk.enums.PixzelleDefinitions;

/**
 * Created by Rodolfo on 07/08/2015.
 */
public class Global {
    private static Global instance;

    public String amount;
    public String holder;
    public static String device;

    public static String REGISTER_TOKEN = "registerToken";

    //From Other app
    public final static String LINK_PACKAGE = "package";
    public final static String LINK_AMOUNT = "amount";
    public final static String LINK_HUID = "huid";
    public final static String LINK_EMAIL = "email";
    public final static String LINK_REFERENCE = "reference";
    public final static String LINK_CURRENCY = "currency";
    public final static String LINK_APP_NAME = "appName";
    public final static String LINK_IMAGE = "image";
    public final static String LINK_IMAGE_PRINT = "printImage";
    public final static String LINK_BACKGROUND_COLOR = "backgroundColor";
    public final static String LINK_BACKGROUND_DRAWABLE = "backgroundDrawable";
    public final static String LINK_BUTTON_COLOR = "buttonColor";
    public final static String LINK_BUTTON_TEXT_COLOR = "buttonTextColor";
    public final static String LINK_PAYMENT_COLOR = "paymentColor";
    public final static String LINK_STYLE = "style";
    public final static String LINK_EDIT_TEXT_UNDER = "editTextUnder";
    public final static String LINK_EDIT_TEXT_FONT_COLOR = "editTextFontColor";
    public final static String LINK_EDIT_TEXT_HINT_COLOR = "editTextHintColor";
    public final static String LINK_TEXT_VIEW_COLOR = "textViewColor";
    public final static String LINK_TEXT_VIEW_COLOR_TITLE = "textViewColorTitle";
    public final static String LINK_RETURN_CARD_HOLDER = "cardHolder";
    public final static String LINK_RETURN_CARD_NUMBER = "cardNumber";
    public final static String LINK_RETURN_CARD_TYPE = "cardType";
    public final static String LINK_RETURN_OPERATION_AUTH_NO = "operationAuthNo";
    public final static String LINK_RETURN_OPERATION_FOLIO = "operationFolio";
    public final static String KEY_APLICATION="key_aplication";

    public Global() {
    }

    public static Global getInstance() {
        if (instance == null)
            instance = new Global();

        return instance;
    }

    /**
     * Method that gets a value for a String shared preference key.
     *
     * @param context Context of the application.
     * @param key     Key for the value to know.
     * @return Value of the key.
     */
    public static int getIntKey(Context context, String key) {
        return context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).getInt(key, -1);
    }

    /**
     * Method for saving a string key.
     *
     * @param context Context of the application.
     * @param key     Key for the value to know.
     * @param value   Value of the key.
     */
    public static void setIntKey(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setBooleanPreference(Context context, String key, boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Method that gets a value for a String shared preference key.
     *
     * @param context Context of the application.
     * @param key     Key for the value to know.
     * @return Value of the key.
     */
    public static String getStringKey(Context context, String key) {
        return context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).getString(key, PixzelleDefinitions.STRING_NULL);
    }

    public static boolean getBooleanPreference(Context context, String key) {
        try {
            return context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).getBoolean(key, false);
        }catch (ClassCastException ex){
            try {
                return Boolean.parseBoolean(context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).getString(key, "false"));
            }catch (Exception ex2){
                clearPreference(context, key);
                return false;
            }
        }
    }

    public static void clearPreference(Context context, String key){
        if(context != null){
            SharedPreferences editor = context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE);
            if(editor != null){
                editor.edit().remove(key).apply();
            }
        }
    }

    /**
     * Method that gets a value for a String shared preference key.
     *
     * @param context Context of the application.
     * @param key     Key for the value to know.
     * @return Value of the key.
     */
    public static String getStringKey2(Context context, String key) {
        return context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).getString(key, null);
    }

    /**
     * Method for saving a string key.
     *
     * @param context Context of the application.
     * @param key     Key for the value to know.
     * @param value   Value of the key.
     */
    public static void setStringKey(Context context, String key, String value) {
        if(context != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.apply();
        }
    }


    /**
     * Method that clean all the app values, must be call in the log out and when the user finishes the combo register.
     *
     * @param context Context of the application.
     */
    public static void clearAll(Context context) {
        String device = getStringKey(context, Definitions.KEY_DEVICE_INFO);
        SharedPreferences.Editor editor = context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        if(device != PixzelleDefinitions.STRING_NULL) {
            setStringKey(context, Definitions.KEY_DEVICE_INFO, device);
        }
    }

    public static void clearDeviceInfo(Context context){
        //Global.device = getStringKey(context, Definitions.KEY_DEVICE_INFO);
        setStringKey(context, Definitions.KEY_DEVICE_INFO, Definitions.EMPTY());
    }

    public static void clearAddress(Context context){
        setStringKey(context, QposReader.ADDRESS, Definitions.EMPTY());
    }

    public static void restoreDevice(Context context){
        setStringKey(context, Definitions.KEY_DEVICE_INFO, Global.device);
    }

    public static void clearPayment(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Definitions.SR_PAGO_SDK(), Context.MODE_PRIVATE).edit();
        editor.putString(Definitions.KEY_EMV, PixzelleDefinitions.STRING_NULL);
        editor.putString(Definitions.KEY_EXT_TRANSACTION, PixzelleDefinitions.STRING_NULL);
        editor.putString(Definitions.KEY_TRANSACTION_TOKEN, PixzelleDefinitions.STRING_NULL);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context){
        /*return !Global.getStringKey(context, Definitions.KEY_USER).equals(PixzelleDefinitions.STRING_NULL) &&
                !Global.getStringKey(context, Definitions.KEY_PASS).equals(PixzelleDefinitions.STRING_NULL);*/
        return Global.getBooleanPreference(context,Definitions.IS_VERIFIED_PHONE);
    }

    public static boolean isLoggedInAndValidToken(Context context){
        boolean loggedIn = isLoggedIn(context);
        boolean validToken = false;

        String expiration = Global.getStringKey(context, Definitions.KEY_EXPIRATION_TOKEN());

        if (!expiration.equals(PixzelleDefinitions.STRING_NULL)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Definitions.TIMESTAMP(), Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Definitions.GMT()));
            try {
                Date date = simpleDateFormat.parse(expiration);
                Calendar today = Calendar.getInstance();
                Calendar expirationCalendar = Calendar.getInstance();
                expirationCalendar.setTime(date);

                if (today.compareTo(expirationCalendar) == -1) {
                    validToken = true;
                } else {
                    validToken = false;
                }
            } catch (Exception ex) {
                validToken = false;
            }
        }
        return validToken;
    }

    public static boolean isLoggedInAndValidated(Context context){
        return Global.isLoggedInAndValidToken(context) && Global.getBooleanPreference(context, Definitions.IS_VERIFIED_PHONE);
    }

    public static boolean isInDebugMode(Context context) {
        boolean isInDebugMode;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;

            isInDebugMode = !bundle.getBoolean(Definitions.SR_PAGO_RELEASE());
        } catch (Exception ex) {
            Logger.logError(ex);
            isInDebugMode = true;
        }

        return isInDebugMode;
    }

    public static String getNewRelicApiKey(Context context){
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getString("newrelicapykey");
        } catch (PackageManager.NameNotFoundException e) {
            Logger.logError(e);
        } catch (NullPointerException e) {
            Logger.logError(e);
        }
        return null;
    }
}
