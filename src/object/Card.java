package sr.pago.sdk.object;

import android.content.Context;

/**
 * The Card class represents a real debit/credit card in
 * the application.
 *
 * @author Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since 2015-04-09
 */
public class Card extends BaseItem {
    private String affiliation = "";
    private String cardMonth = "";
    private String cardYear = "";
    private String cardHolderName = "";
    private String cardSecurityCode = "000";
    private String finalCardNumber = "";
    private String startCardNumber = "";
    private String cardNumber = "";
    private String data = "";
    private String emvFlag = "false";
    private String msrFlag = "false";
    private String msr1 = "";
    private String msr2 = "";
    private String msr3 = "";
    private String reference = "";
    private String type = "";

    public Card(Context context) {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFinalCardNumber() {
        return finalCardNumber;
    }

    public void setFinalCardNumber(String finalCardNumber) {
        this.finalCardNumber = finalCardNumber;
    }

    public String getStartCardNumber() {
        return startCardNumber;
    }

    public void setStartCardNumber(String startCardNumber) {
        this.startCardNumber = startCardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardNumberToSend(boolean chip) {
        if (chip) {
            return startCardNumber;
        } else {
            return cardNumber;
        }
    }

    /**
     * Method that puts the card number for the card,
     * and also establishes the type of the card.
     *
     * @param cardNumber Number on the card.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        this.setStartCardNumber(cardNumber.substring(0, 4));
        this.setFinalCardNumber(cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
        if (cardNumber.charAt(0) == '4') {
            this.setType("VISA");
        } else if (cardNumber.charAt(0) == '5' || cardNumber.charAt(0) == '2') {
            this.setType("MAST");
        } else if (cardNumber.charAt(0) == '6') {
            this.setType("CRNT");
        } else if (cardNumber.charAt(0) == '3') {
            this.setType("AMEX");
        }
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsr1() {
        return msr1;
    }

    public void setMsr1(String msr1) {
        this.msr1 = msr1;
    }

    public String getMsr2() {
        return msr2;
    }

    public void setMsr2(String msr2) {
        this.msr2 = msr2;
    }

    public String getMsr3() {
        return msr3;
    }

    public void setMsr3(String msr3) {
        this.msr3 = msr3;
    }

    public String getCardMonth() {
        return cardMonth;
    }

    public void setCardMonth(String cardMonth) {
        this.cardMonth = cardMonth;
    }

    public String getCardYear() {
        return cardYear;
    }

    public void setCardYear(String cardYear) {
        this.cardYear = cardYear;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public String getEmvFlag() {
        return emvFlag;
    }

    public void setEmvFlag(String emvFlag) {
        this.emvFlag = emvFlag;
    }

    public String getMsrFlag() {
        return msrFlag;
    }

    public void setMsrFlag(String msrFlag) {
        this.msrFlag = msrFlag;
    }

    public String getFormattedCardNumber() {
        return String.format("XXXX-%s", this.finalCardNumber);
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
}
