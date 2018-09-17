package sr.pago.sdk.interfaces;

import sr.pago.sdk.SrPagoTransaction;

/**
 * Created by Pixzelle on 06/07/16.
 */
public interface PaymentMobileListener extends SrPagoWebServiceListener {
    void onSuccess(SrPagoTransaction transaction);
}
