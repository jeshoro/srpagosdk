package sr.pago.sdk.model.responses.srpago;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by David Morales on 22/11/17.
 */

public class Response {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("error")
    @Expose
    private Error error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
