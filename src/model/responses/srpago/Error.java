package sr.pago.sdk.model.responses.srpago;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by David Morales on 22/11/17.
 */

public class Error  implements Serializable {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private Error message;

    @SerializedName("description")
    @Expose
    private Boolean description;

    @SerializedName("http_status_code")
    @Expose
    private Error httpStatusCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Error getMessage() {
        return message;
    }

    public void setMessage(Error message) {
        this.message = message;
    }

    public Boolean getDescription() {
        return description;
    }

    public void setDescription(Boolean description) {
        this.description = description;
    }

    public Error getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Error httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
