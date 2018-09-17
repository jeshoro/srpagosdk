package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivateCardRS extends Response {

    @SerializedName("result")
    @Expose
    private List<Card> result = null;

    public List<Card> getResult() {
        return result;
    }

    public void setResult(List<Card> result) {
        this.result = result;
    }
}
