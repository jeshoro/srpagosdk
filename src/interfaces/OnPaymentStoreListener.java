package sr.pago.sdk.interfaces;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public interface OnPaymentStoreListener extends SrPagoWebServiceListener{
    void onSuccess(String token);
}
