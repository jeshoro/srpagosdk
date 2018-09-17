package sr.pago.sdk.model;

/**
 * Created by Pixzelle on 28/01/16.
 */
public class SPPermissions {
    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public boolean isAvatar_edit() {
        return avatar_edit;
    }

    public void setAvatar_edit(boolean avatar_edit) {
        this.avatar_edit = avatar_edit;
    }

    public boolean isAvatar_show() {
        return avatar_show;
    }

    public void setAvatar_show(boolean avatar_show) {
        this.avatar_show = avatar_show;
    }

    public boolean isInvoice() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    public boolean isEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(boolean ecommerce) {
        this.ecommerce = ecommerce;
    }

    public boolean isOperators() {
        return operators;
    }

    public void setOperators(boolean operators) {
        this.operators = operators;
    }

    public boolean isBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(boolean bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public boolean isReader() {
        return reader;
    }

    public void setReader(boolean reader) {
        this.reader = reader;
    }

    public boolean isOperations() {
        return operations;
    }

    public void setOperations(boolean operations) {
        this.operations = operations;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public boolean isWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    public boolean isBalance() {
        return balance;
    }

    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    public boolean isPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(boolean paymentCard) {
        this.paymentCard = paymentCard;
    }

    public boolean isConvenienceStore() {
        return convenienceStore;
    }

    public void setConvenienceStore(boolean convenienceStore) {
        this.convenienceStore = convenienceStore;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean card;
    public boolean convenienceStore;
    public boolean paymentCard;
    public boolean balance;
    public boolean withdrawal;
    public boolean transfer;
    public boolean operations;
    public boolean reader;
    public boolean notifications;
    public boolean operators;
    public boolean bankAccounts;
    public boolean ecommerce;
    public boolean invoice;
    public boolean avatar;
    public boolean avatar_edit;
    public boolean avatar_show;
}
