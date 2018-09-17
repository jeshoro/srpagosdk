package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 15/10/2015.
 */
public class AccountCard extends PixzelleClass {
    public String bankName;
    public String alias;
    public String type;
    public int accountNumberSuffix;
    public Boolean active;
    public String authorized;
    public boolean isDefault;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAccountNumberSuffix() {
        return accountNumberSuffix;
    }

    public void setAccountNumberSuffix(int accountNumberSuffix) {
        this.accountNumberSuffix = accountNumberSuffix;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public void setDefault(boolean isDefault){
        this.isDefault = isDefault;
    }

    public boolean getDefault(){
        return isDefault;
    }
}
