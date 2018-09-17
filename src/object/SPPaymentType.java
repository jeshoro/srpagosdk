package sr.pago.sdk.object;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 29/08/2017.
 */

public class SPPaymentType extends BaseItem implements Serializable {
    private int months;
    private double commission;
    private double raw;
    private double net;
    private double perMonth;

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public double getCommission() {
        return commission;
    }

    public double getFullComission() {
        return commission + 16;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getRaw() {
        return raw;
    }

    public void setRaw(double raw) {
        this.raw = raw;
    }

    public double getNet() {
//        return getRaw() * ((100 - getCommission()) / 100);
        return getRaw() - ((getRaw() * (getCommission() / 100)) * 1.16);
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getMoneyPerMonth() {
        return getRaw() / getMonths();
    }

    public double getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(double perMonth) {
        this.perMonth = perMonth;
    }

    public static String toJSON(ArrayList<SPPaymentType> paymentTypes) {
        try {
            JSONArray root = new JSONArray();


            for (SPPaymentType type : paymentTypes) {
                JSONObject item = new JSONObject();
                item.put("months", type.months);
                item.put("commission", type.commission);
                item.put("perMonth", type.perMonth);
                item.put("raw", type.raw);

                root.put(item);
            }

            return root.toString();
        } catch (Exception ex) {
            return "[]";
        }
    }

    public static ArrayList<SPPaymentType> fromJSON(String json) {
        ArrayList<SPPaymentType> items = new ArrayList<>();

        try {
            JSONArray root = new JSONArray(json);

            for (int index = 0; index < root.length(); index++) {
                SPPaymentType type = new SPPaymentType();
                type.setMonths(root.getJSONObject(index).getInt("months"));
                type.setCommission(root.getJSONObject(index).getDouble("commission"));
                type.setPerMonth(root.getJSONObject(index).getDouble("perMonth"));
                type.setRaw(root.getJSONObject(index).getDouble("raw"));

                items.add(type);
            }

        } catch (Exception ex) {
            Logger.logError(ex);
            items = new ArrayList<>();
        }

        return items;
    }
}
