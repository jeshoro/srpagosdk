package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dmorales on 3/26/18.
 */

public class BankAccount implements Serializable {

    @SerializedName("bank_name")
    @Expose
    private String bankName;

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("account_number_suffix")
    @Expose
    private String accountNumberSuffix;


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAccountNumberSuffix() {
        return accountNumberSuffix;
    }

    public void setAccountNumberSuffix(String accountNumberSuffix) {
        this.accountNumberSuffix = accountNumberSuffix;
    }

}