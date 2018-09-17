package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.StoresMethod;

/**
 * Created by Rodolfo on 20/10/2015.
 */
public interface StoreListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<StoresMethod> stores);
}
