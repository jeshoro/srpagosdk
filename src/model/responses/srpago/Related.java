package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Related  implements Serializable {

    @SerializedName("transaction")
    @Expose
    private String transaction;

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

}