package sr.pago.sdk.object;

import android.content.Context;

import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.object.response.Response;

/**
 * Represents an operation made by the user.
 *
 * @author Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since 2015-03-30
 */
public class Operation extends BaseItem {
    private Card card;
    private String commerce;
    private String cardNumber;
    private String reference;
    private String date;
    private String hour;
    private String amount;
    private double amountF;
    private String currency;
    private String folio;
    private String idAffectedOperation;
    private String authNumber;
    private String cardType;
    private String type;
    private String operationType;
    private String emvResponse;
    private String transactionType;
    private String applicationIdentifier;
    private String applicationLabel;
    private String cryptogramType;
    private String cryptogramValue;
    private String signature;
    private String description;
    private boolean hasDevolution;

    public boolean hasDevolution() {
        return hasDevolution;
    }

    public void setHasDevolution(boolean hasDevolution) {
        this.hasDevolution = hasDevolution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Operation(Context context) {
        this.card = new Card(context);
        this.idAffectedOperation = "";
        this.emvResponse = "";
        this.reference = "";
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getReference() {
        if (reference.equals(""))
            return "Sin referencia";
        return reference;
    }

    public double getAmountF() {
        return amountF;
    }

    public void setAmountF(double amountF) {
        this.amountF = amountF;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount.replaceAll(",", ".");
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCommerce() {
        return commerce;
    }

    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }

    public String getIdAffectedOperation() {
        return idAffectedOperation;
    }

    public void setIdAffectedOperation(String idAffectedOperation) {
        this.idAffectedOperation = idAffectedOperation;
    }

    public String getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmvResponse() {
        return emvResponse;
    }

    public void setEmvResponse(String emvResponse) {
        this.emvResponse = emvResponse;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    public void setApplicationIdentifier(String applicationIdentifier) {
        this.applicationIdentifier = applicationIdentifier;
    }

    public String getApplicationLabel() {
        return applicationLabel;
    }

    public void setApplicationLabel(String applicationLabel) {
        this.applicationLabel = applicationLabel;
    }

    public String getCryptogramType() {

        String binary = Integer.toBinaryString(Integer.parseInt(String.valueOf(cryptogramType.charAt(0))));

        if (binary.equals("00") || binary.equals("0")) {
            return "AAC";
        } else if (binary.equals("01") || binary.equals("1")) {
            return "TC";
        } else if (binary.equals("10")) {
            return "ARQC";
        } else {
            return "";
        }
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
