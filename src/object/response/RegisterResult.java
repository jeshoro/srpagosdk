package sr.pago.sdk.object.response;

import java.io.Serializable;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by desarrolladorandroid on 26/01/17.
 */

public class RegisterResult extends PixzelleClass implements Serializable {

    private static final long serialVersionUID = 8328308156916848124L;
    public String token;
    public String expires;
}
