package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.PaymentPreferences;

/**
 * Created by Rodolfo on 11/07/2017.
 */

public interface OnPreferencesEditListener extends SrPagoWebServiceListener {
    void onSuccess(PaymentPreferences preferences);
}
