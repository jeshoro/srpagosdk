package sr.pago.sdk.model.responses.srpago;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by David Morales on 22/11/17.
 */

public class Beneficiary implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("birth_date")
    private Date birthDate;

    @SerializedName("relation")
    private Integer relation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

}