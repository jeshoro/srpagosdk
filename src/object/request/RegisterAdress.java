package sr.pago.sdk.object.request;

import java.io.Serializable;

/**
 * Created by desarrolladorandroid on 31/01/17.
 */

public class RegisterAdress implements Serializable {

    private static final long serialVersionUID = 6777555765152574505L;
    public String calle;
    public String numeroExterior;
    public String numeroInterior;
    public String cp;
}
