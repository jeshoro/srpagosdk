package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 30/10/2015.
 */
public class Abm extends PixzelleClass {

    public String  number;
    public String shortName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
