package sr.pago.sdk.model;

import java.io.Serializable;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 15/10/2015.
 */
public class Operator implements Serializable {

    public boolean operator;
    public String email;
    private String position;
    private String image;
    public boolean active;
    private String name;
    public int clave;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public Boolean getOperator() {
        return operator;
    }

    public void setOperator(Boolean operator) {
        this.operator = operator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
