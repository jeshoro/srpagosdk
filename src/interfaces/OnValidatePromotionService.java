package sr.pago.sdk.interfaces;

import sr.pago.sdk.model.responses.srpago.ValidatePromotionRS;
import sr.pago.sdk.object.response.RegisterResult;

/**
 * Created by desarrolladorandroid on 26/01/17.
 */

public interface OnValidatePromotionService extends SrPagoWebServiceListener {
    void onSuccess(ValidatePromotionRS result);
}
