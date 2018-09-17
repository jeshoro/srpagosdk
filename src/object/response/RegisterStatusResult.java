package sr.pago.sdk.object.response;

import java.io.Serializable;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by desarrolladorandroid on 25/01/17.
 */

public class RegisterStatusResult extends PixzelleClass implements Serializable {

    private static final long serialVersionUID = -7182222403413198032L;
    public boolean facebook;
    public boolean email;
    public boolean operator;
}
