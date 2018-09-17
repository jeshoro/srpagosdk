package sr.pago.sdk.interfaces;

import java.util.List;

import sr.pago.sdk.object.response.BusinessCatalogResult;

/**
 * Created by desarrolladorandroid on 25/01/17.
 */

public interface OnBusinessCatalogListener extends SrPagoWebServiceListener {
    void onSuccess(List<BusinessCatalogResult> result);
}
