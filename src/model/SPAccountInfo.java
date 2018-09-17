package sr.pago.sdk.model;

import java.util.ArrayList;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Eduardo on 22/12/15.
 */
public class SPAccountInfo extends PixzelleClass {
    public String lastName;
    public String surName;
    public String email;
    public String username;
    public boolean active;
    public String rfc;
    public String createTime;
    public String avatar;
    public boolean ecommerce;
    public ArrayList<SPAddress> addres;
    public ArrayList<PixzelleClass> phones;

    public SPPermissions getPermissions() {
        return permissions;
    }

//    public void setPermissions(SPPermissions permissions) {
//        this.permissions = permissions;
//    }

    public SPPermissions permissions;
    public String getLastName() {
        return lastName;
    }

    public ArrayList getAdress() { return addres; }

    public ArrayList getPhone(){ return phones; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(boolean ecommerce) {
        this.ecommerce = ecommerce;
    }
}
