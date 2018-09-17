package sr.pago.sdk.object.response;

import java.io.Serializable;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by desarrolladorandroid on 27/01/17.
 */

public class ValidationPhoneSendResult extends PixzelleClass implements Serializable {

    private static final long serialVersionUID = -1030559907150217034L;
    public String maxUserAttempts;
    public String currentUserAttempt;
    public String maxAttempts;
    public String currentAttempt;
    public String secondsToNextAttempt;
}
