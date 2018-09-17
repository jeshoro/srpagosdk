package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountInfo implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("company")
    @Expose
    private Company company;

    @SerializedName("active")
    @Expose
    private boolean active;

    @SerializedName("rfc")
    @Expose
    private String rfc;

    @SerializedName("create_time")
    @Expose
    private String createTime;

    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    @SerializedName("phones")
    @Expose
    private List<Phone> phones = null;

    @SerializedName("ecommerce")
    @Expose
    private boolean ecommerce;

    @SerializedName("trustLevel")
    @Expose
    private int trustLevel;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("permissions")
    @Expose
    private Permissions permissions;

    @SerializedName("ecom_id")
    @Expose
    private String ecomId;

    @SerializedName("fullProfile")
    @Expose
    private boolean fullProfile;

    @SerializedName("emailSent")
    @Expose
    private boolean emailSent;

    @SerializedName("fixedCommission")
    @Expose
    private String fixedCommission;

    @SerializedName("variableCommission")
    @Expose
    private String variableCommission;

    @SerializedName("clabe")
    @Expose
    private String clabe;

    @SerializedName("thirdparty_accounts")
    @Expose
    private boolean thirdpartyAccounts;

    @SerializedName("automatic_withdrawal")
    @Expose
    private Integer automaticWithdrawal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return firstName + " " + lastName + " " + surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurname() {
        return surname;
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

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public boolean isEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(boolean ecommerce) {
        this.ecommerce = ecommerce;
    }

    public int getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(int trustLevel) {
        this.trustLevel = trustLevel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public String getEcomId() {
        return ecomId;
    }

    public void setEcomId(String ecomId) {
        this.ecomId = ecomId;
    }

    public boolean isFullProfile() {
        return fullProfile;
    }

    public void setFullProfile(boolean fullProfile) {
        this.fullProfile = fullProfile;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public String getFixedCommission() {
        return fixedCommission;
    }

    public void setFixedCommission(String fixedCommission) {
        this.fixedCommission = fixedCommission;
    }

    public String getVariableCommission() {
        return variableCommission;
    }

    public void setVariableCommission(String variableCommission) {
        this.variableCommission = variableCommission;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public boolean isThirdpartyAccounts() {
        return thirdpartyAccounts;
    }

    public void setThirdpartyAccounts(boolean thirdpartyAccounts) {
        this.thirdpartyAccounts = thirdpartyAccounts;
    }

    public Integer getAutomaticWithdrawal() {
        return automaticWithdrawal;
    }

    public void setAutomaticWithdrawal(Integer automaticWithdrawal) {
        this.automaticWithdrawal = automaticWithdrawal;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
