package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.response.ValidationPhoneSendResult;

/**
 * Created by desarrolladorandroid on 27/01/17.
 */

public interface OnValidationPhoneSendListener extends SrPagoWebServiceListener{
    void onSuccess(ValidationPhoneSendResult result);
}
