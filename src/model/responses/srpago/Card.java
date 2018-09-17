package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Card implements Serializable {

    @SerializedName("holder_name")
    @Expose
    private String holderName;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("raw")
    @Expose
    private String raw;

    @SerializedName("active")
    @Expose
    private boolean active;

    @SerializedName("beneficiary")
    @Expose
    private Beneficiary beneficiary;

    @SerializedName("rfc")
    @Expose
    private String rfc;

    @SerializedName("income")
    @Expose
    private String income;

    @SerializedName("balance")
    @Expose
    private String balance;

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}