package sr.pago.sdk.model.responses.srpago;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Rodolfo on 24/07/2017.
 */

public class SrPagoCard extends SrPagoCardRS implements Serializable {

    @SerializedName("active")
    private Boolean active;

    @SerializedName("client")
    private int client;

    @SerializedName("register_date")
    private String registerDate;

    @SerializedName("activation_date")
    private String activationDate;

    @SerializedName("activation_status")
    private int activationStatus;

    @SerializedName("status")
    private String status;

    @SerializedName("document_status")
    private List documentStatus;

    @SerializedName("cancelStatus")
    private int cancelStatus;

    @SerializedName("beneficiary")
    private Beneficiary beneficiary;

    @SerializedName("birth_date")
    private Date birthDate;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("curp")
    private String curp;

    @SerializedName("rfc")
    private String rfc;

    @SerializedName("income")
    private String income;

    @SerializedName("balance")
    private String balance;

    @SerializedName("transactions")
    private int transactions;

    @SerializedName("holder_name")
    private String holderName;

    @SerializedName("type")
    private String type;

    @SerializedName("number")
    private String number;

    @SerializedName("provider")
    @Expose
    private Provider provider;

    public int id;
    private String alias;
    private boolean blocked = false;

    public final static int NO_CARD = -2;
    public final static int ACTIVE = -1;
    public final static int NOT_CANCELLED = 0;
    public final static int CANCELLED_PROCESS = 1;
    public final static int CANCELLED_APPROVED = 2;

    private Documents documents = new Documents();


    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return active == null ? false : active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public int getStatus() {
        return getCancelStatus() != 0 ? getCancelStatus() : -1;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCurp() {
        return "XAXX010101HDFXXX00";
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return "XAXX010101000";
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getTransactions() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getClient() {
        return client;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public int getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(int activationStatus) {
        this.activationStatus = activationStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(List documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public class Documents implements Serializable {

        public final static int REJECTED_UNNECESARY = -2;
        public final static int REJECTED_RELOAD = -1;
        public final static int UNVERIFIED = 0;
        public final static int VERIFIED = 1;
        public final static int MISSING = 3;
        public final static int RECENTLY_UPDATED = 4;

        private int ineFront = MISSING;
        private int ineBack = MISSING;
        private int addressProof = MISSING;
        private int signature = MISSING;
        private int contractSignature = MISSING;
        private int status = MISSING;

        public int getIneFront() {
            return ineFront;
        }

        public void setIneFront(int ineFront) {
            this.ineFront = ineFront;
        }

        public int getIneBack() {
            return ineBack;
        }

        public void setIneBack(int ineBack) {
            this.ineBack = ineBack;
        }

        public int getAddressProof() {
            return addressProof;
        }

        public void setAddressProof(int addressProof) {
            this.addressProof = addressProof;
        }

        public int getSignature() {
            return signature;
        }

        public void setSignature(int signature) {
            this.signature = signature;
        }

        public int getContractSignature() {
            return contractSignature;
        }

        public void setContractSignature(int contractSignature) {
            this.contractSignature = contractSignature;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public int getDocumentSize() {
        return 5;
    }

    public int getDocuments(int document) {
        switch (document) {
            case 1:
                return this.documents.getIneFront();
            case 2:
                return this.documents.getIneBack();
            case 3:
                return this.documents.getAddressProof();
            case 4:
                return this.documents.getContractSignature();
            case 5:
                return this.documents.getSignature();
            default:
                return Documents.MISSING;
        }
    }

    public void setDocument(int document, int status) {

        switch (document) {
            case 1:
                this.documents.setIneFront(status);
                break;

            case 2:
                this.documents.setIneBack(status);
                break;

            case 3:
                this.documents.setAddressProof(status);
                break;

            case 4:
                this.documents.setContractSignature(status);
                break;

            case 5:
                this.documents.setSignature(status);
                break;
        }
    }
}

