package sr.pago.sdk.connection;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import sr.pago.sdk.SignatureActivity;
import sr.pago.sdk.SrPagoActivity;
import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.SrPagoV2;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.enums.PixzelleDefinitions;
import sr.pago.sdk.interfaces.OnSignatureCompleteListener;
import sr.pago.sdk.interfaces.PaymentListener;
import sr.pago.sdk.interfaces.PaymentValidateDocumentListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.services.PaymentService;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 20/05/2016 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public class ServiceCoreTransactionV2 extends ServiceCore {
    private double pendingAmount;
    private List<SPTransactionDocument> paymentDocuments;

    public static PaymentListener paymentListener;

    private static ServiceCoreTransactionV2 serviceCoreTransactionV2;

    public void setPaymentListener(PaymentListener paymentListener) {
        ServiceCoreTransactionV2.paymentListener = paymentListener;
    }

    @Deprecated
    public ServiceCoreTransactionV2(SrPagoActivity srPagoActivity, double pendingAmount) {
        super(srPagoActivity);
        pendingAmount = pendingAmount;
    }

    public ServiceCoreTransactionV2(Context srPagoActivity, double pendingAmount) {
        super(srPagoActivity);
        ServiceCoreTransactionV2.serviceCoreTransactionV2 = this;
        this.pendingAmount = pendingAmount;
    }

    @Override
    public void onResponseError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        if (webServiceListener != null) {
            webServiceListener.onError(code, response, webService);
        }
    }


    @Override
    public void onResponseOk(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        if (!isFromOtherApp()) {
            if (response != null)
                Global.setStringKey(getContext(), Definitions.OPERATION_SIGNATURE, response.getRaw());
            Global.setBooleanPreference(getContext(), Definitions.IS_SIGNATURE_COMPLETED, false);
        }
        switch (webService) {
            case Definitions.PAYMENT:
                SrPagoTransaction srPagoTransaction = new SrPagoTransaction();
                try {
                    srPagoTransaction.setToken(new JSONObject(response.getRaw()).getJSONObject("result").getString("token"));
                    srPagoTransaction.setAmount(pendingAmount);
                } catch (JSONException ex) {
                    if (response == null) {
                        response = new SPResponse();
                    }
                    response.setMessage("Error returning transaction");
                    webServiceListener.onError(Pixzelle.BAD_REQUEST, response, webService);
                }

                break;
            case Definitions.PAYMENT_CARD:
                validatePaymentDocuments(response);
                webServiceListener.onSuccess(code, response, webService);
                break;
        }

    }

    public void validatePaymentDocuments(final SPResponse response) {
        final SrPagoTransaction srPagoTransaction = (SrPagoTransaction) response.getItems().get(0);
        PaymentValidateDocumentListener paymentValidateDocumentListener = new PaymentValidateDocumentListener() {
            @Override
            public void onSuccess(List<SPTransactionDocument> documents) {
                Logger.logDebug("success", "Documents:" + documents);
                paymentDocuments = documents;
                openSignature(response, srPagoTransaction);
            }

            @Override
            public void onError(SrPagoDefinitions.Error error) {
                Logger.logWarning("error", error.toString());
                openSignature(response, srPagoTransaction);
            }

            @Override
            public void onError(String error, SrPagoDefinitions.Error errorCode) {
                Logger.logWarning("error", error);
                openSignature(response, srPagoTransaction);
            }
        };

        String operation = response.getRaw();
        String transactionToken = "";
        try {
            Logger.logDebug("JSONTOKEN", operation);
            JSONObject jsonObject = new JSONObject(operation);
            transactionToken = jsonObject.getJSONObject("result").getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PaymentService.callValidateDocumentPaymentService(getContext(), paymentValidateDocumentListener, transactionToken);
    }

    public void openSignature(SPResponse response, SrPagoTransaction srPagoTransaction) {
        if (Global.getStringKey(getContext(), Definitions.KEY_EMV).equals(PixzelleDefinitions.STRING_NULL) ||
                Global.getStringKey(getContext(), Definitions.KEY_EMV).equals("")) {
            if (SrPagoV2.getInstance().getReader() != null)
                SrPagoV2.getInstance().getReader().getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT_CARD);
            Intent intent = new Intent(getContext(), paymentListener.getSignatureActivity() == null ? SignatureActivity.class : paymentListener.getSignatureActivity());
            intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
            //intent.putExtra("listener", onSignatureCompleteListener);
            intent.putExtra("sale", srPagoTransaction);
            intent.putExtra(Definitions.KEY_DATA_RECEIPT, response.getRaw());
            if (SrPagoV2.getInstance().getReader() != null)
                SrPagoV2.getInstance().getReader().getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_SIGNATURE);
            if (intent.getExtras().getString(Global.LINK_PACKAGE) != null) {
                paymentListener.onFinishTransaction();
                getContext().startActivity(intent);
            } else {
                paymentListener.onFinishTransaction();
                getContext().startActivity(intent);
            }
        } else {
            Global.setStringKey(getContext(), Definitions.KEY_DATA_RECEIPT, response.getRaw());
            try {
                SrPagoV2.getInstance().getReader().handleEMV(Global.getStringKey(getContext(), Definitions.KEY_EMV));
            } catch (Exception e) {
                //e.printStackTrace();
                SrPagoV2.getInstance().getReader().getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT_CARD);
                Intent intent = new Intent(getContext(), paymentListener.getSignatureActivity() == null ? SignatureActivity.class : paymentListener.getSignatureActivity());
                //intent.putExtra("listener", onSignatureCompleteListener);
                intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
                intent.putExtra("sale", srPagoTransaction);
                intent.putExtra(Definitions.KEY_DATA_RECEIPT, response.getRaw());
                intent.putExtra(Definitions.KEY_CARD_HOLDER, Global.getInstance().holder);
                intent.putExtra(Definitions.KEY_AMOUNT, Global.getInstance().amount);
                SrPagoV2.getInstance().getReader().getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_SIGNATURE);
                if (intent.getExtras().getString(Global.LINK_PACKAGE) != null) {
                    paymentListener.onFinishTransaction();
                    getContext().startActivity(intent);
                } else {
                    paymentListener.onFinishTransaction();
                    getContext().startActivity(intent);
                }
            }
        }
    }

    public static OnSignatureCompleteListener onSignatureCompleteListener = new OnSignatureCompleteListener() {
        @Override
        public void onSignatureComplete(Intent intent) {
            try {
                Logger.logDebug("SignatureD", "Correct");
                ServiceCoreTransactionV2.paymentListener.onPaymentSuccess((SrPagoTransaction) intent.getSerializableExtra("sale"), serviceCoreTransactionV2.paymentDocuments);
            } catch (Exception ex) {
                Logger.logError(ex);
                ServiceCoreTransactionV2.paymentListener.onPaymentSuccess(null, null);
            }
        }
    };

    private boolean isFromOtherApp() {
        return Global.getBooleanPreference(getContext(), Definitions.IF_FROM_OTHER_APP);
    }


}
