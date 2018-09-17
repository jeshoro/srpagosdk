package sr.pago.sdk.model.requests.srpago;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Disperse implements Serializable{

    @SerializedName("user_affiliated")
    @Expose
    private String userAffiliated;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public String getUserAffiliated() {
        return userAffiliated;
    }

    public void setUserAffiliated(String userAffiliated) {
        this.userAffiliated = userAffiliated;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class User implements Serializable {

        @SerializedName("user")
        @Expose
        private String user;
        @SerializedName("fee")
        @Expose
        private int fee;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }
    }

}
