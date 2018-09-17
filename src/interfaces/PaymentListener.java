package sr.pago.sdk.interfaces;

import java.util.ArrayList;
import java.util.List;

import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.object.SPPaymentType;

/**
 * Created by Rodolfo on 20/05/2016 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public interface PaymentListener extends SrPagoWebServiceListener {
    void onPaymentSuccess(SrPagoTransaction transaction);
    void onPaymentSuccess(SrPagoTransaction srPagoTransaction, List<SPTransactionDocument> documents);
    void onPaymentError(SrPagoDefinitions.Error code, String error);
    void onPaymentError(String error);
    void onAmexCvvStart();
    void onAmexCvvSuccess();
    void onAmexCvvCancel();
    void onPaymentSelectMonths(ArrayList<SPPaymentType> paymentTypes);
    Class<?> getSignatureActivity();
    void onFinishTransaction();
}