package sr.pago.sdk.object;

import java.io.Serializable;

/**
 * Created by desarrolladorandroid on 24/01/17.
 */

public class Colonia extends PixzelleClass implements Serializable {

    private static final long serialVersionUID = 2750925089221837121L;
    public String town;
    public String typeTown;
    public String zip_code;
    public String city;
    public String country;
    public String state;
}
