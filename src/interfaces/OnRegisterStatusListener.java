package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.response.RegisterStatusResult;

/**
 * Created by desarrolladorandroid on 25/01/17.
 */

public interface OnRegisterStatusListener extends SrPagoWebServiceListener {
    void onSuccess(RegisterStatusResult result);
}
