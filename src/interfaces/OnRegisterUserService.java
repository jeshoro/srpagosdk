package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.response.RegisterResult;

/**
 * Created by desarrolladorandroid on 26/01/17.
 */

public interface OnRegisterUserService extends SrPagoWebServiceListener {
    void onSuccess(RegisterResult result);
}
