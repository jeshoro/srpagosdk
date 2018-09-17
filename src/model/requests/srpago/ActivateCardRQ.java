package sr.pago.sdk.model.requests.srpago;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivateCardRQ {

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("second_last_name")
    @Expose
    private String secondLastName;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    @SerializedName("state")
    @Expose
    private String state;

    public ActivateCardRQ(String number, String name, String lastName, String secondLastName, String gender, String birthDate, String state) {
        this.number = number;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
