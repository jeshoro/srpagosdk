package sr.pago.sdk.object;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rodolfo on 08/09/2017.
 */

public class Contract {
    private String cardNumber;
    private String nationality;
    private String curp;
    private String rfc;
    private Date birthDate;
    private int monthlyIncome;
    private int expectedBalance;
    private int expectedTransactions;
    private String beneficiaryName;
    private int beneficiaryRelation;
    private String beneficiaryAddress;
    private Date beneficiaryBirthDate;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public int getExpectedBalance() {
        return expectedBalance;
    }

    public void setExpectedBalance(int expectedBalance) {
        this.expectedBalance = expectedBalance;
    }

    public int getExpectedTransactions() {
        return expectedTransactions;
    }

    public void setExpectedTransactions(int expectedTransactions) {
        this.expectedTransactions = expectedTransactions;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public int getBeneficiaryRelation() {
        return beneficiaryRelation;
    }

    public void setBeneficiaryRelation(int beneficiaryRelation) {
        this.beneficiaryRelation = beneficiaryRelation;
    }

    public String getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(String beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }

    public Date getBeneficiaryBirthDate() {
        return beneficiaryBirthDate;
    }

    public void setBeneficiaryBirthDate(Date beneficiaryBirthDate) {
        this.beneficiaryBirthDate = beneficiaryBirthDate;
    }

    public String toJson() {
        try {
            JSONObject root = new JSONObject();
            root.put("number", cardNumber);
            root.put("nationality", nationality);
            root.put("curp", curp);
            root.put("rfc", rfc);
            root.put("birth_date", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(birthDate));
            root.put("income", monthlyIncome);
            root.put("balance", expectedBalance);
            root.put("transactions", expectedTransactions);

            JSONObject jsonBeneficiary = new JSONObject();
            jsonBeneficiary.put("name", beneficiaryName);
            jsonBeneficiary.put("relation", beneficiaryRelation);
            jsonBeneficiary.put("address", beneficiaryAddress);
            jsonBeneficiary.put("birth_date", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(beneficiaryBirthDate));
            root.put("beneficiary", jsonBeneficiary);

            return root.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
