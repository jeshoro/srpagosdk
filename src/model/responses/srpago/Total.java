package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dmorales on 3/26/18.
 */

public class Total implements Serializable {

    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

