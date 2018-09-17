package sr.pago.sdk;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import sr.pago.sdk.model.responses.srpago.FeeDetail;
import sr.pago.sdk.object.PixzelleClass;
import sr.pago.sdk.object.Transference;

/**
 * Created by Rodolfo on 24/11/2015 for SrPago.
 * Sr. Pago All rights reserved.
 */
public class SrPagoTransaction extends PixzelleClass {

    private String token;
    private String method;
    private String authorizationCode;
    private String cryptogramType;
    private String cryptogramValue;
    private String transactionId;
    private int monthlyInstallments;
    private Date timestamp;
    private String paymentMethod;
    private String status;
    private String reference;
    private String cardHolderName;
    private String cardType;
    private String cardNumber;
    private String cardLabel;
    private double amount;
    private String currency;
    private double latitude;
    private double longitude;
    private String affiliation;
    private String arqc;
    private String aid;
    private String transactionType;
    private String url;
    private double tip;
    private double commission;
    private boolean hasDevolution;
    private String operator = null;
    private String transferenceSource = "";
    private String transferenceDestination = "";
    private String location;
    private List<FeeDetail> feeDetails = null;

    private Transference transference;

    public Transference getTransference() {
        return transference;
    }

    public void setTransference(Transference transference) {
        this.transference = transference;
    }

    public String toJSON(){
        String json = "";

        try{
            JSONObject root = new JSONObject();
            root.put("token", token);
            root.put("method", method);
            root.put("authorizationCode", authorizationCode);
            root.put("cryptogramType", cryptogramType);
            root.put("cryptogramValue", cryptogramValue);
            root.put("transactionId", transactionId);
            root.put("monthlyInstallments", monthlyInstallments);
            root.put("timestamp", timestamp);
            root.put("paymentMethod", paymentMethod);
            root.put("status", status);
            root.put("reference", reference);
            root.put("cardHolderName", cardHolderName);
            root.put("cardType", cardType);
            root.put("cardNumber", cardNumber);
            root.put("cardLabel", cardLabel);
            root.put("amount", amount);
            root.put("currency", currency);
            root.put("latitude", latitude);
            root.put("longitude", longitude);
            root.put("affiliation", affiliation);
            root.put("arqc", arqc);
            root.put("aid", aid);
            root.put("transactionType", transactionType);
            root.put("url", url);
            root.put("commision", commission);
            root.put("tip", tip);
            root.put("operator", operator);
            root.put("hasDevolution", hasDevolution);
            root.put("location", location);

            json = root.toString();
        }catch (Exception ex){
            json = "";
        }

        return json;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getCryptogramType() {
        return cryptogramType;
    }

    public void setCryptogramType(String cryptogramType) {
        this.cryptogramType = cryptogramType;
    }

    public String getCryptogramValue() {
        return cryptogramValue;
    }

    public void setCryptogramValue(String cryptogramValue) {
        this.cryptogramValue = cryptogramValue;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardLabel() {
        return cardLabel;
    }

    public void setCardLabel(String cardLabel) {
        this.cardLabel = cardLabel;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getArqc() {
        return arqc;
    }

    public void setArqc(String arqc) {
        this.arqc = arqc;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public boolean isHasDevolution() {
        return hasDevolution;
    }

    public void setHasDevolution(boolean hasDevolution) {
        this.hasDevolution = hasDevolution;
    }

    public int getMonthlyInstallments() {
        return monthlyInstallments;
    }

    public void setMonthlyInstallments(int monthlyInstallments) {
        this.monthlyInstallments = monthlyInstallments;
    }

    public String getTransferenceSource() {
        return transferenceSource;
    }

    public void setTransferenceSource(String transferenceSource) {
        this.transferenceSource = transferenceSource;
    }

    public String getTransferenceDestination() {
        return transferenceDestination;
    }

    public void setTransferenceDestination(String transferenceDestination) {
        this.transferenceDestination = transferenceDestination;
    }


    public List<FeeDetail> getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(List<FeeDetail> feeDetails) {
        this.feeDetails = feeDetails;
    }
}
