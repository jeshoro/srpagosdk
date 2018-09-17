package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.SrPagoTransaction;

/**
 * Created by Rodolfo on 25/09/2015 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public interface OnGetOperationsListener extends SrPagoWebServiceListener {
    void onSuccesss(ArrayList<SrPagoTransaction> operations);

}
