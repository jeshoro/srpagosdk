package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.Code;
import sr.pago.sdk.object.response.Response;

/**
 * Created by Rodolfo on 12/07/2017.
 */

public interface OnCodeRedeemListener extends SrPagoWebServiceListener {
    void onSuccess(Code code);
}
