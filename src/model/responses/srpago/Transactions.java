package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Transactions  implements Serializable {

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("sales")
    @Expose
    private String sales;

    @SerializedName("transferences")
    @Expose
    private String transferences;

    @SerializedName("commissions")
    @Expose
    private Commissions commissions;

    @SerializedName("operations")
    @Expose
    private List<Operation> operations = null;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getTransferences() {
        return transferences;
    }

    public void setTransferences(String transferences) {
        this.transferences = transferences;
    }

    public Commissions getCommissions() {
        return commissions;
    }

    public void setCommissions(Commissions commissions) {
        this.commissions = commissions;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

}
