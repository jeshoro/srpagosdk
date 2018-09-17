package sr.pago.sdk.model.responses.srpago;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmorales on 3/26/18.
 */

public class WithdrawalRS {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("result")
    @Expose
    private Result result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("operations")
        @Expose
        private List<Withdrawal> operations = null;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<Withdrawal> getOperations() {
            return operations;
        }

        public void setOperations(List<Withdrawal> operations) {
            this.operations = operations;
        }

    }


}
