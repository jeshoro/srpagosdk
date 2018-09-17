package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.SPTransactionDocument;

/**
 * Created by Pixzelle on 29/06/16.
 */
public interface SrPagoDocumentsListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<SPTransactionDocument> documents);

}
