package sr.pago.sdk.interfaces;

import java.util.List;

import sr.pago.sdk.model.SPTransactionDocument;

/**
 * Created by dherrera on 2/15/17.
 */

public interface PaymentValidateDocumentListener extends SrPagoWebServiceListener {
    void onSuccess(List<SPTransactionDocument> documents);
}
