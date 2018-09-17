package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public class Notification extends PixzelleClass {
    public String description;
    public boolean email;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }
}
