package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardTypeRS extends Response{

    @SerializedName("result")
    @Expose
    private CardType result;


    public CardType getResult() {
        return result;
    }

    public void setResult(CardType result) {
        this.result = result;
    }
}
