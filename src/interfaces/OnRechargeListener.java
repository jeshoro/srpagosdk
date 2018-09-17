package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 20/10/2015.
 */
public interface OnRechargeListener extends SrPagoWebServiceListener{
    void onSuccess(PixzelleClass item);
}
