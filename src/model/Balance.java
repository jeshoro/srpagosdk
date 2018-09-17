package sr.pago.sdk.model;

/**
 * Created by Rodolfo on 24/07/2017.
 */

public class Balance {
    public double totalFunds;
    public String currency;
    public String timesStamp;

    public double getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(double totalFunds) {
        this.totalFunds = totalFunds;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimesStamp() {
        return timesStamp;
    }

    public void setTimesStamp(String timesStamp) {
        this.timesStamp = timesStamp;
    }
}
