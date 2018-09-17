package sr.pago.sdk.model;

import java.util.Date;

import sr.pago.sdk.SrPagoTransaction;

/**
 * Created by David Morales on 9/29/17.
 */

public class Ticket extends SrPagoTransaction {

    /*private String transaction;

    private String name;
    private String shortName;

    private String bankAccountNumber;
    private String bankName;
    private String paymentId;
    private String shortId;
    private String description;

    private String amount;
    private String currency;

    private String url;
    private String timestamp;
    private String expirationDate;
    private Integer status;
    private String statusCode;
    private String number;*/

    private String transaction;
//    private Store store;
    private String bankAccountNumber;
    private String bankName;
    private String paymentId;
    private String shortId;
//    private Total total;
    private String url;
    private Date expirationDate;
    private String statusCode;
//    private Phone phone;
    private String email;
    private String bankReferenceNumber;
    private String transactionId;
    private Date timestamp;
    private String status;
    private String reference;
    private String number;
    private String name;
    private String shortName;
    private double amount;
    private String currency;


    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankReferenceNumber() {
        return bankReferenceNumber;
    }

    public void setBankReferenceNumber(String bankReferenceNumber) {
        this.bankReferenceNumber = bankReferenceNumber;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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



    //    public class Phone {
//
//        private String number;
//
//        public Phone(String number) {
//            this.number=number;
//        }
//
//        public String getNumber() {
//            return number;
//        }
//
//        public void setNumber(String number) {
//            this.number = number;
//        }
//
//    }

//    public class Store {
//
//        private String name;
//        private String shortName;
//
//        public Store(String name, String shortName) {
//            this.name=name;
//            this.shortName=shortName;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getShortName() {
//            return shortName;
//        }
//
//        public void setShortName(String shortName) {
//            this.shortName = shortName;
//        }
//
//    }

//    public class Total {
//
//        private double amount;
//        private String currency;
//
//        public Total(double amount, String currency) {
//            this.amount=amount;
//            this.currency=currency;
//        }
//
//        public double getAmount() {
//            return amount;
//        }
//
//        public void setAmount(double amount) {
//            this.amount = amount;
//        }
//
//        public String getCurrency() {
//            return currency;
//        }
//
//        public void setCurrency(String currency) {
//            this.currency = currency;
//        }
//
//    }


}
