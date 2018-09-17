package sr.pago.sdk.interfaces;

import java.util.List;

import sr.pago.sdk.object.response.ZipCodeResult;

/**
 * Created by desarrolladorandroid on 24/01/17.
 */

public interface OnZipCodeResponse extends SrPagoWebServiceListener {
    void onSuccess(List<ZipCodeResult> result);
}
