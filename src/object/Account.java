package sr.pago.sdk.object;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;

import sr.pago.sdk.model.SPPermissions;


/**
 * Created by Rodolfo on 09/08/2015.
 */
public class Account extends BaseItem {
    private String firstName;
    private String lastName;
    private String surname;
    private String email;
    private String username;
    private boolean active;
    private String rfc;
    private String createTime;
    private String birthday;
    private String avatar;
    private ArrayList<Address> addresses;
    private ArrayList<Pair<?, ?>> phones;
    private Company company;
    private boolean ecommerce;
    double commission;
    private String clabe;
    private boolean thirdPartyAccounts;
    private Integer automaticWithdrawal;
    private double variableCommission;

    public void setVariableCommission(double variableCommission) {
        this.variableCommission = variableCommission;
    }

    public double getVariableCommission() {
        return variableCommission;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public SPPermissions getPermissions() {
        return permissions;
    }

//    public void setPermissions(SPPermissions permissions) {
//        this.permissions = permissions;
//    }

    public SPPermissions permissions;

    public Account(Context context) {
        this.addresses = new ArrayList<>();
        this.phones = new ArrayList<>();
    }

    @Override
    public String getName() {
        return firstName + " " + lastName + " " + surname;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName == null ? "" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurname() {
        return surname == null ? "" : surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public ArrayList<Pair<?, ?>> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<Pair<?, ?>> phones) {
        this.phones = phones;
    }

    public boolean isEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(boolean ecommerce) {
        this.ecommerce = ecommerce;
    }

    public boolean isThirdPartyAccounts() {
        return thirdPartyAccounts;
    }

    public void setThirdPartyAccounts(boolean thirdPartyAccounts) {
        this.thirdPartyAccounts = thirdPartyAccounts;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public void setPermissions(SPPermissions permissions) {
        this.permissions = permissions;
    }

    public Integer getAutomaticWithdrawal() {
        return automaticWithdrawal;
    }

    public void setAutomaticWithdrawal(Integer automaticWithdrawal) {
        this.automaticWithdrawal = automaticWithdrawal;
    }
}
