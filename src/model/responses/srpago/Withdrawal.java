package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dmorales on 3/26/18.
 */

public class Withdrawal implements Serializable {

    @SerializedName("total")
    @Expose
    private Total total;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("bank_account")
    @Expose
    private BankAccount bankAccount;

    @SerializedName("commission")
    @Expose
    private Commission commission;

    @SerializedName("date_request")
    @Expose
    private Date dateRequest;

    @SerializedName("date_apply")
    @Expose
    private Date dateApply;

    @SerializedName("date_cancel")
    @Expose
    private Date dateCancel;

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Date getDateApply() {
        return dateApply;
    }

    public void setDateApply(Date dateApply) {
        this.dateApply = dateApply;
    }

    public Date getDateCancel() {
        return dateCancel;
    }

    public void setDateCancel(Date dateCancel) {
        this.dateCancel = dateCancel;
    }
}