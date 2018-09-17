package sr.pago.sdk.model.responses.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Properties implements Serializable {

    @SerializedName("login")
    @Expose
    private boolean login;

    @SerializedName("cardNumber")
    @Expose
    private boolean cardNumber;

    @SerializedName("balance")
    @Expose
    private boolean balance;

    @SerializedName("history")
    @Expose
    private boolean history;

    @SerializedName("cancelCard")
    @Expose
    private boolean cancelCard;

    @SerializedName("withdrawal")
    @Expose
    private boolean withdrawal;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(boolean cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isBalance() {
        return balance;
    }

    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isCancelCard() {
        return cancelCard;
    }

    public void setCancelCard(boolean cancelCard) {
        this.cancelCard = cancelCard;
    }

    public boolean isWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

}