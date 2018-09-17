package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountInfoRS extends Response {

    @SerializedName("result")
    @Expose
    private AccountInfo result;

    public AccountInfo getResult() {
        return result;
    }

    public void setResult(AccountInfo result) {
        this.result = result;
    }


}
