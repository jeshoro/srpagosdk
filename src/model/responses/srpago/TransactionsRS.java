package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionsRS extends Response {

    @SerializedName("result")
    @Expose
    private Transactions result;


    public Transactions getResult() {
        return result;
    }

    public void setResult(Transactions result) {
        this.result = result;
    }
}
