package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardType {

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("prepaid")
    @Expose
    private boolean prepaid;

    @SerializedName("provider")
    @Expose
    private Provider provider;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isPrepaid() {
        return prepaid;
    }

    public void setPrepaid(boolean prepaid) {
        this.prepaid = prepaid;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}