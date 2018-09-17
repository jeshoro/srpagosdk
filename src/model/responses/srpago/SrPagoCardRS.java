package sr.pago.sdk.model.responses.srpago;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David Morales on 22/11/17.
 */

public class SrPagoCardRS extends Response {

    @SerializedName("result")
    @Expose
    private List<SrPagoCard> result = null;

    public List<SrPagoCard> getResult() {
        return result;
    }

    public void setResult(List<SrPagoCard> result) {
        this.result = result;
    }
}
