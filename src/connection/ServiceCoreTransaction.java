package sr.pago.sdk.connection;

import org.json.JSONException;
import org.json.JSONObject;

import sr.pago.sdk.SrPagoActivity;
import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.PaymentListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.Global;

/**
 * Created by Rodolfo on 20/05/2016 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public class ServiceCoreTransaction extends ServiceCore {
    private double pendingAmount;


    public ServiceCoreTransaction(SrPagoActivity srPagoActivity, double pendingAmount){
        super(srPagoActivity);
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
        if(!isFromOtherApp()) {
            if (response != null)
                Global.setStringKey(this.getContext(), Definitions.OPERATION_SIGNATURE, response.getRaw());
            Global.setBooleanPreference(this.getContext(), Definitions.IS_SIGNATURE_COMPLETED, false);
        }
        switch (webService){
            case Definitions.PAYMENT:
                SrPagoTransaction srPagoTransaction = new SrPagoTransaction();
                try {
                    srPagoTransaction.setToken(new JSONObject(response.getRaw()).getJSONObject("result").getString("token"));
                    srPagoTransaction.setAmount(pendingAmount);
                }catch (JSONException ex){
                    if(response == null){
                        response = new SPResponse();
                    }
                    response.setMessage("Error returning transaction");
                    this.webServiceListener.onError(Pixzelle.BAD_REQUEST, response, webService);
                }

                break;
            case Definitions.PAYMENT_CARD:
                ((SrPagoActivity)(this.getContext())).validatePaymentDocuments(response);
                ((PaymentListener)webServiceListener).onPaymentSuccess(null);
                break;
        }

    }

    private boolean isFromOtherApp(){
        return Global.getBooleanPreference(this.getContext(), Definitions.IF_FROM_OTHER_APP);
    }
}
