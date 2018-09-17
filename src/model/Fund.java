package sr.pago.sdk.model;

import java.util.ArrayList;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 06/10/2015.
 */
public class Fund extends PixzelleClass {

    public double totalFounds;
    public String availableFounds;
    public String currency;
    public String timestamp;
    public ArrayList<PixzelleClass> result;

    public Fund(){
        super();
        result=new ArrayList<>();
    }

    public ArrayList<PixzelleClass> getResult() {
        return result;
    }

    public void setResult(ArrayList<PixzelleClass> result) {
        this.result = result;
    }

    public double getTotalFounds() {
        return totalFounds;
    }

    public void setTotalFounds(double totalFounds) {
        this.totalFounds = totalFounds;
    }

    public String getAvailableFounds() {
        return availableFounds;
    }

    public void setAvailableFounds(String availableFounds) {
        this.availableFounds = availableFounds;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
