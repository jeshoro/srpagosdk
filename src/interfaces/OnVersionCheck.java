package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.response.VersionCheckResult;

/**
 * Created by desarrolladorandroid on 06/12/16.
 */

public interface OnVersionCheck extends SrPagoWebServiceListener {
    void onSuccess(VersionCheckResult result);
}
