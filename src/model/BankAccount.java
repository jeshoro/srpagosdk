package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 24/10/2015.
 */
public class BankAccount extends PixzelleClass {

    public static final int SR_PAGO = 0;
    public static final int PERSONAL = 1;
    public static final int THIRD = 2;
    public static final int PERSONAL_AND_THIRD = 3;
    public static final int PERSONAL_AND_SRPAGO = 4;
    public static final int ALL = -1;

    private String bankName;
    private String alias;
    private String type;
    private String accountNumberSuffix;
    private int origin;
    private Boolean active;
    private String authorized;
    private int clave;
    private boolean isDefault;
    private int viewType;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

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

    public String getAccountNumberSuffix() {
        return accountNumberSuffix;
    }

    public void setAccountNumberSuffix(String accountNumberSuffix) {
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
