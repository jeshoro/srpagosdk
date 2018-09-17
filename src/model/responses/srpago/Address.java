package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Serializable {

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("town")
    @Expose
    private String town;

    @SerializedName("zip_code")
    @Expose
    private String zipCode;

    @SerializedName("city")
    @Expose
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}