package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Permissions implements Serializable {

    @SerializedName("/card")
    @Expose
    private List<Object> card = null;

    @SerializedName("/balance")
    @Expose
    private List<Object> balance = null;

    @SerializedName("/withdrawal")
    @Expose
    private List<Object> withdrawal = null;

    @SerializedName("/transfer")
    @Expose
    private List<Object> transfer = null;

    @SerializedName("/reader")
    @Expose
    private List<Object> reader = null;

    @SerializedName("/notifications")
    @Expose
    private List<Object> notifications = null;

    @SerializedName("/operators")
    @Expose
    private List<Object> operators = null;

    @SerializedName("/bank-accounts")
    @Expose
    private List<Object> bankAccounts = null;

    @SerializedName("/invoice")
    @Expose
    private List<Object> invoice = null;

    @SerializedName("/operations")
    @Expose
    private List<Object> operations = null;

    @SerializedName("/payment/convenience-store")
    @Expose
    private List<Object> paymentConvenienceStore = null;

    @SerializedName("/payment/card")
    @Expose
    private List<Object> paymentCard = null;

    @SerializedName("/account/avatar")
    @Expose
    private List<String> accountAvatar = null;

    @SerializedName("/client/application")
    @Expose
    private List<String> clientApplication = null;

    @SerializedName("/payment/ecommerce")
    @Expose
    private List<Object> paymentEcommerce = null;

    public List<Object> getCard() {
        return card;
    }

    public void setCard(List<Object> card) {
        this.card = card;
    }

    public List<Object> getBalance() {
        return balance;
    }

    public void setBalance(List<Object> balance) {
        this.balance = balance;
    }

    public List<Object> getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(List<Object> withdrawal) {
        this.withdrawal = withdrawal;
    }

    public List<Object> getTransfer() {
        return transfer;
    }

    public void setTransfer(List<Object> transfer) {
        this.transfer = transfer;
    }

    public List<Object> getReader() {
        return reader;
    }

    public void setReader(List<Object> reader) {
        this.reader = reader;
    }

    public List<Object> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Object> notifications) {
        this.notifications = notifications;
    }

    public List<Object> getOperators() {
        return operators;
    }

    public void setOperators(List<Object> operators) {
        this.operators = operators;
    }

    public List<Object> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<Object> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<Object> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Object> invoice) {
        this.invoice = invoice;
    }

    public List<Object> getOperations() {
        return operations;
    }

    public void setOperations(List<Object> operations) {
        this.operations = operations;
    }

    public List<Object> getPaymentConvenienceStore() {
        return paymentConvenienceStore;
    }

    public void setPaymentConvenienceStore(List<Object> paymentConvenienceStore) {
        this.paymentConvenienceStore = paymentConvenienceStore;
    }

    public List<Object> getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(List<Object> paymentCard) {
        this.paymentCard = paymentCard;
    }

    public List<String> getAccountAvatar() {
        return accountAvatar;
    }

    public void setAccountAvatar(List<String> accountAvatar) {
        this.accountAvatar = accountAvatar;
    }

    public List<String> getClientApplication() {
        return clientApplication;
    }

    public void setClientApplication(List<String> clientApplication) {
        this.clientApplication = clientApplication;
    }

    public List<Object> getPaymentEcommerce() {
        return paymentEcommerce;
    }

    public void setPaymentEcommerce(List<Object> paymentEcommerce) {
        this.paymentEcommerce = paymentEcommerce;
    }

}