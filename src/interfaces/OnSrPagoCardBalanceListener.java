package sr.pago.sdk.interfaces;


import sr.pago.sdk.model.Balance;

/**
 * Created by Rodolfo on 13/10/2015.
 */
public interface OnSrPagoCardBalanceListener extends SrPagoWebServiceListener {
    void onSuccess(Balance balance);
}
