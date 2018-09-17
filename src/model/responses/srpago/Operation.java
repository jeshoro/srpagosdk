package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operation implements Serializable{

    @SerializedName("transaction")
    @Expose
    private String transaction;

    @SerializedName("timestamp")
    @Expose
    private Date timestamp;

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;

    @SerializedName("authorization_code")
    @Expose
    private String authorizationCode;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reference")
    @Expose
    private Reference reference;

    @SerializedName("card")
    @Expose
    private Card card;

    @SerializedName("total")
    @Expose
    private Total total;

    @SerializedName("tip")
    @Expose
    private Tip tip;

    @SerializedName("fee")
    @Expose
    private Fee fee;

    @SerializedName("fee_details")
    @Expose
    private List<FeeDetail> feeDetails = null;

    @SerializedName("origin")
    @Expose
    private Origin origin;

    @SerializedName("affiliation")
    @Expose
    private String affiliation;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("related")
    @Expose
    private Related related;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("hasDevolution")
    @Expose
    private boolean hasDevolution;

    @SerializedName("commission")
    @Expose
    private Commission commission;

    @SerializedName("ARQC")
    @Expose
    private String aRQC;

    @SerializedName("cryptogram_type")
    @Expose
    private String cryptogramType;

    @SerializedName("AID")
    @Expose
    private String aID;

    @SerializedName("transaction_type")
    @Expose
    private String transactionType;

    @SerializedName("affiliated_name")
    @Expose
    private String affiliatedName;

    @SerializedName("token")
    @Expose
    private String token;

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public List<FeeDetail> getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(List<FeeDetail> feeDetails) {
        this.feeDetails = feeDetails;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Related getRelated() {
        return related;
    }

    public void setRelated(Related related) {
        this.related = related;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasDevolution() {
        return hasDevolution;
    }

    public void setHasDevolution(boolean hasDevolution) {
        this.hasDevolution = hasDevolution;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public String getARQC() {
        return aRQC;
    }

    public void setARQC(String aRQC) {
        this.aRQC = aRQC;
    }

    public String getCryptogramType() {
        return cryptogramType;
    }

    public void setCryptogramType(String cryptogramType) {
        this.cryptogramType = cryptogramType;
    }

    public String getAID() {
        return aID;
    }

    public void setAID(String aID) {
        this.aID = aID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAffiliatedName() {
        return affiliatedName;
    }

    public void setAffiliatedName(String affiliatedName) {
        this.affiliatedName = affiliatedName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}