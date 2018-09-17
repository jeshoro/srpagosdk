package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.SPAccountInfo;

/**
 * Created by Eduardo on 22/12/15.
 */
public interface AcountInfoListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<SPAccountInfo> acountInfos);
}
