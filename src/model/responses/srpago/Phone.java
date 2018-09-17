package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone implements Serializable {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("number")
    @Expose
    private String number;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}