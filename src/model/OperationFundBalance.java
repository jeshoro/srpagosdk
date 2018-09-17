package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 27/10/2015.
 */
public class OperationFundBalance extends PixzelleClass {

    public double totalFounds;
    public double availableFounds;
    public double totalRedrawal;
    public double pendingFunds;
    public double amount;
    public double iva;
    public double total;
    public String currency;
    public String timestamp;

    public double getTotalFounds() {
        return totalFounds;
    }

    public void setTotalFounds(double totalFounds) {
        this.totalFounds = totalFounds;
    }

    public double getAvailableFounds() {
        return availableFounds;
    }

    public void setAvailableFounds(double availableFounds) {
        this.availableFounds = availableFounds;
    }

    public double getTotalRedrawal() {
        return totalRedrawal;
    }

    public void setTotalRedrawal(double totalRedrawal) {
        this.totalRedrawal = totalRedrawal;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public double getPendingFunds() {
        return pendingFunds;
    }

    public void setPendingFunds(double pendingFunds) {
        this.pendingFunds = pendingFunds;
    }
}
