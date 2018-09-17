package sr.pago.sdk.services;

import java.util.List;

import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.interfaces.SrPagoWebServiceListener;
import sr.pago.sdk.model.SPTransactionDocument;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public interface PaymentListener extends SrPagoWebServiceListener {
    void onPaymentSuccess(SrPagoTransaction  operation, List<SPTransactionDocument> documents);
    void onFinishTransaction();
}
