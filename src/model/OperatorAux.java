package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 25/09/2015 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public class OperatorAux extends PixzelleClass {

    public String holderName;
    public String type;
    public String number;
    public Boolean activo;
    public  int client;
    public double totalFounds;
    public String currency;
    public String timesTemp;
    public String cancelStatus;


    public String isCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }


    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public double getTotalFounds() {
        return totalFounds;
    }

    public void setTotalFounds(double totalFounds) {
        this.totalFounds = totalFounds;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimesTemp() {
        return timesTemp;
    }

    public void setTimesTemp(String timesTemp) {
        this.timesTemp = timesTemp;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
