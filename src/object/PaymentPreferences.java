package sr.pago.sdk.object;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import sr.pago.sdk.definitions.Definitions;

/**
 * Created by Rodolfo on 11/07/2017.
 */

public class PaymentPreferences extends PixzelleClass {
    private static PaymentPreferences instance;
    private Context context;

    private boolean tmpMSIPossible = true;
    private boolean tmpBandCardPossible = true;
    private boolean tmpTipPossible = true;
    private String tmpReferenceSelected = "1";
    private boolean tmpMSI3Possible = true;
    private boolean tmpMSI6Possible = true;
    private boolean tmpMSI9Possible = true;
    private boolean tmpMSI12Possible = true;
    private String tmpMinimum = "500";

    public static PaymentPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new PaymentPreferences();
        }

        instance.context = context;
        return instance;
    }

    public boolean isMSIPossible() {
        return getBooleanPreference(context, Definitions.PREF_MSI_ACTIVATED);
    }

    public boolean isBandCardPossible() {
        return getBooleanPreference(context, Definitions.PREF_BAND);
    }

    public boolean isTipPossible() {
        return getBooleanPreference(context, Definitions.PREF_TIP);
    }

    public String getReferenceSelected() {
        String reference = getStringPreference(context, Definitions.PREF_REFERENCE, "1");

        switch (reference) {
            case "0":
                return "desactivado";
            case "2":
                return "requerido";
            default:
                return "opcional";
        }
    }

    public String getReferenceDescription(){
        String reference = getStringPreference(context, Definitions.PREF_REFERENCE, "1");

        switch (reference) {
            case "0": return "No se solicitará ninguna referencia al momento del cobro.";
            case "2": return "La referencia al momento del cobro será obligatoria.";
            default: return "Tú decides si ingresas referencia a tus ventas.";
        }
    }

    public String getReferenceIdSelected() {
        return getStringPreference(context, Definitions.PREF_REFERENCE, "1");
    }

    public boolean is3MSIPossible() {
        return getBooleanPreference(context, Definitions.PREF_MSI_3);
    }

    public boolean is6MSIPossible() {
        return getBooleanPreference(context, Definitions.PREF_MSI_6);
    }

    public boolean is9MSIPossible() {
        return getBooleanPreference(context, Definitions.PREF_MSI_9);
    }

    public boolean is12MSIPossible() {
        return getBooleanPreference(context, Definitions.PREF_MSI_12);
    }

    public double getMinimumForMSI() {
        return Double.parseDouble(getStringPreference(context, Definitions.PREF_MSI_FROM, "0.00"));
    }

    private boolean getBooleanPreference(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, true);
    }

    private String getStringPreference(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public void setMSIFrom(double from) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(Definitions.PREF_MSI_FROM, String.valueOf(from));
        editor.commit();
    }

    public void fromJSON(String json) {
        try {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            JSONObject root = new JSONObject(json);

            if (root.getJSONObject("monthly_installments").has("selected")) {
                JSONArray monthlySelected = root.getJSONObject("monthly_installments").getJSONArray("selected");

                ArrayList<Integer> monthsSelected = new ArrayList<>();

                for (int index = 0; index < monthlySelected.length(); index++) {
                    monthsSelected.add(Integer.parseInt(monthlySelected.getString(index)));
                }

                editor.putBoolean(Definitions.PREF_MSI_3, monthsSelected.contains(3));
                editor.putBoolean(Definitions.PREF_MSI_6, monthsSelected.contains(6));
                editor.putBoolean(Definitions.PREF_MSI_9, monthsSelected.contains(9));
                editor.putBoolean(Definitions.PREF_MSI_12, monthsSelected.contains(12));
            } else {
                editor.putBoolean(Definitions.PREF_MSI_3, root.getJSONObject("monthly_installments").getBoolean("enabled"));
                editor.putBoolean(Definitions.PREF_MSI_6, root.getJSONObject("monthly_installments").getBoolean("enabled"));
                editor.putBoolean(Definitions.PREF_MSI_9, root.getJSONObject("monthly_installments").getBoolean("enabled"));
                editor.putBoolean(Definitions.PREF_MSI_12, root.getJSONObject("monthly_installments").getBoolean("enabled"));
            }

            editor.putBoolean(Definitions.PREF_MSI_ACTIVATED, root.getJSONObject("monthly_installments").getBoolean("enabled"));
            if(root.getJSONObject("monthly_installments").has("min_amount")){
                editor.putString(Definitions.PREF_MSI_FROM, root.getJSONObject("monthly_installments").getString("selected_amount"));
            }else{
                editor.putString(Definitions.PREF_MSI_FROM, "500.00");
            }

            try {
                editor.putBoolean(Definitions.PREF_BAND, root.getJSONObject("payment_methods").getBoolean("swipe"));
            }catch (Exception ex){
                editor.putBoolean(Definitions.PREF_BAND, true);
            }

            editor.putString(Definitions.PREF_REFERENCE, root.getJSONObject("reference").getString("selected"));
            editor.putBoolean(Definitions.PREF_TIP, root.getJSONObject("tip").getBoolean("enabled"));

            editor.commit();
        } catch (Exception ex) {
            sr.pago.sdk.utils.Logger.logError(ex);
        }
    }

    public void initTmpVariables(){
        tmpMSIPossible = getBooleanPreference(context, Definitions.PREF_MSI_ACTIVATED);
        tmpMSI3Possible = getBooleanPreference(context, Definitions.PREF_MSI_3);
        tmpMSI6Possible = getBooleanPreference(context, Definitions.PREF_MSI_6);
        tmpMSI9Possible = getBooleanPreference(context, Definitions.PREF_MSI_9);
        tmpMSI12Possible = getBooleanPreference(context, Definitions.PREF_MSI_12);
        tmpBandCardPossible = getBooleanPreference(context, Definitions.PREF_BAND);
        tmpTipPossible = getBooleanPreference(context, Definitions.PREF_TIP);
        tmpReferenceSelected = getStringPreference(context, Definitions.PREF_REFERENCE, "1");
        tmpMinimum = getStringPreference(context, Definitions.PREF_MSI_FROM, "500");
    }

    public void restartPreferences(){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean(Definitions.PREF_MSI_ACTIVATED, tmpMSIPossible);
        editor.putBoolean(Definitions.PREF_MSI_3, tmpMSI3Possible);
        editor.putBoolean(Definitions.PREF_MSI_6, tmpMSI6Possible);
        editor.putBoolean(Definitions.PREF_MSI_9, tmpMSI9Possible);
        editor.putBoolean(Definitions.PREF_MSI_12, tmpMSI12Possible);
        editor.putBoolean(Definitions.PREF_BAND, tmpBandCardPossible);
        editor.putBoolean(Definitions.PREF_TIP, tmpTipPossible);
        editor.putString(Definitions.PREF_REFERENCE, tmpReferenceSelected);
        editor.putString(Definitions.PREF_MSI_FROM, tmpMinimum);

        editor.commit();
    }

    public String toJSON() {
        String json;

        try {
            JSONObject root = new JSONObject();
            JSONObject monthlyInstallmentsJSON = new JSONObject();
            JSONArray monthlySelectedArray = new JSONArray();

            if (is3MSIPossible()) {
                monthlySelectedArray.put(3);
            }

            if (is6MSIPossible()) {
                monthlySelectedArray.put(6);
            }

            if (is9MSIPossible()) {
                monthlySelectedArray.put(9);
            }

            if (is12MSIPossible()) {
                monthlySelectedArray.put(12);
            }

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            symbols.setDecimalSeparator('.');
            symbols.setGroupingSeparator(',');
            NumberFormat formatter = new DecimalFormat("#0.00", symbols);
            monthlyInstallmentsJSON.put("enabled", isMSIPossible());
            monthlyInstallmentsJSON.put("selected", monthlySelectedArray);
            monthlyInstallmentsJSON.put("selected_amount", String.valueOf(formatter.parse(String.valueOf(getMinimumForMSI()))));

            JSONObject ifeJSON = new JSONObject();
            ifeJSON.put("enabled", false);
            ifeJSON.put("min_selected_amount", "4000");

            JSONObject tipJSON = new JSONObject();
            tipJSON.put("enabled", isTipPossible());

            JSONObject referenceJSON = new JSONObject();
            referenceJSON.put("selected", Integer.parseInt(getReferenceIdSelected()));

            JSONObject methodsJSON = new JSONObject();
            methodsJSON.put("swipe", isBandCardPossible());

            root.put("monthly_installments", monthlyInstallmentsJSON);
            root.put("ife", ifeJSON);
            root.put("tip", tipJSON);
            root.put("reference", referenceJSON);
            root.put("payment_methods", methodsJSON);

            json = root.toString();
        } catch (Exception ex) {
            sr.pago.sdk.utils.Logger.logError(ex);
            json = "";
        }

        return json;
    }
}
