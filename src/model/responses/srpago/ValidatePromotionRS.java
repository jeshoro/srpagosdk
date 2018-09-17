package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidatePromotionRS extends Response {

    @SerializedName("result")
    @Expose
    private Promotion result;

    public Promotion getResult() {
        return result;
    }

    public void setResult(Promotion result) {
        this.result = result;
    }
}
