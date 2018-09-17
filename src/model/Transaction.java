package sr.pago.sdk.model;

import java.util.Date;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 14/10/2015.
 */
public class Transaction extends PixzelleClass {


    public String holderName;
    public String typeCatd;
    public int number;
    public Boolean active;
    public int client;
    public Date timestamp;
    public String descriptionTrans;
    public double amount;
    public  String currency;
    public String type;

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getTypeCatd() {
        return typeCatd;
    }

    public void setTypeCatd(String typeCatd) {
        this.typeCatd = typeCatd;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescriptionTrans() {
        return descriptionTrans;
    }

    public void setDescriptionTrans(String descriptionTrans) {
        this.descriptionTrans = descriptionTrans;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
