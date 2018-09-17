package sr.pago.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import sr.pago.sdk.activities.GPSActivity;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.enums.PixzelleDefinitions;
import sr.pago.sdk.interfaces.PaymentValidateDocumentListener;
import sr.pago.sdk.interfaces.SrPagoFinishListener;
import sr.pago.sdk.interfaces.SrPagoWebServiceListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.readers.BaseReader;
import sr.pago.sdk.services.PaymentService;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.utils.Logger;


/**
 * Created by Rodolfo on 23/07/2015.
 */
public abstract class SrPagoActivity extends GPSActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, WebServiceListener{


    private SrPagoWebServiceListener srPagoWebServiceListener;
    private SrPagoFinishListener srPagoFinishListener;
    private List<SPTransactionDocument> paymentDocuments;

    private BaseReader reader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*if(reader != null && reader instanceof AnywhereCommerce) {
            ((AnywhereCommerce) reader).destroy();
        }else if(reader != null && reader instanceof QPOSReader){
            //((QPOSReader) reader).disconnectBT();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @Override
//    protected void onNoInternetConnection(int i) {
//        reader.dismissProgress();
//        reader.isReading = false;
//        reader.getSrPagoListener().onError(SrPagoDefinitions.Error._400);
//    }
//
//    @Override
//    protected void onTimeoutConnection(int i) {
//        reader.dismissProgress();
//        reader.isReading = false;
//        reader.getSrPagoListener().onError(SrPagoDefinitions.Error._400);
//    }
//
//    @Override
//    protected void onUnknownConnectionError(int i) {
//        reader.dismissProgress();
//        reader.isReading = false;
//        reader.getSrPagoListener().onError(SrPagoDefinitions.Error.UNKNOWN);
//    }


    @Override
    public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        try {
            switch (webService) {
                case Definitions.APPLICATION_LOGIN:
                    reader.dismissProgress();
                    break;
                case Definitions.PAYMENT_CARD:
                    if(Global.getStringKey(this, Definitions.KEY_EMV).equals(PixzelleDefinitions.STRING_NULL) ||
                            Global.getStringKey(this, Definitions.KEY_EMV).equals("")) {
                        this.reader.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT_CARD);
                        Intent intent = new Intent(SrPagoActivity.this, SignatureActivity.class);
                        if(getIntent().getExtras() != null) {
                            intent.putExtras(getIntent().getExtras());
                        }
                        intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
                        intent.putExtra(Definitions.KEY_DATA_RECEIPT, response.getRaw());
                        intent.putExtra(Definitions.KEY_CARD_HOLDER, Global.getInstance().holder);
                        intent.putExtra(Definitions.KEY_AMOUNT, Global.getInstance().amount);
                        this.reader.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_SIGNATURE);
                        if(intent.getExtras().getString(Global.LINK_PACKAGE) != null){
                            onFinishTransaction();
                            startActivityForResult(intent, 69);
                        }
                        else {
                            onFinishTransaction();
                            startActivityForResult(intent, 69);
                        }
                    }else{
                        Global.setStringKey(this, Definitions.KEY_DATA_RECEIPT,response.getRaw());
                        reader.handleEMV(Global.getStringKey(this, Definitions.KEY_EMV));
                    }
                    break;
            }
        }catch (Exception ex){
            Logger.logError(ex);
            response.setStatus(false);
            reader.isReading = false;
            reader.getSrPagoListener().onError(SrPagoDefinitions.Error.S500);
            reader.dismissProgress();
            //PixzelleErrors.printException(ex);
        }
    }

    @Override
    public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        reader.dismissProgress();
        if (response.getMessage().equalsIgnoreCase(""))
            reader.getSrPagoListener().onError(SrPagoDefinitions.Error.S500);
        else {
            String message = "";
            if(response.getCode() == null)
                message = response.getMessage();
            else{
                message = response.getCode()+ "\n" + response.getMessage();
            }
            reader.getSrPagoListener().onError(message, SrPagoDefinitions.Error.S500);
        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            signatureActivity();
        }
    };

    public void signatureActivity(){
        reader.dismissProgress();
        Intent intent = new Intent(SrPagoActivity.this, SignatureActivity.class);
        intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
        intent.putExtra(Definitions.KEY_DATA_RECEIPT, Global.getStringKey(this, Definitions.KEY_DATA_RECEIPT));
        Global.setStringKey(this, Definitions.KEY_DATA_RECEIPT, PixzelleDefinitions.STRING_NULL);
        intent.putExtra(Definitions.KEY_CARD_HOLDER, Global.getInstance().holder);
        intent.putExtra(Definitions.KEY_AMOUNT, Global.getInstance().amount);
        startActivityForResult(intent, 69);
    }


    public SrPagoWebServiceListener getSrPagoWebServiceListener() {
        return srPagoWebServiceListener;
    }

    public void setSrPagoWebServiceListener(SrPagoWebServiceListener srPagoWebServiceListener) {
        this.srPagoWebServiceListener = srPagoWebServiceListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(reader != null)
            reader.isReading = false;
        Logger.logDebug("result",resultCode+" request code" +requestCode);
        setIntent(data);
        if (requestCode != Definitions.CALCULATOR_SET_AMOUNT && requestCode != Definitions.QPOSREADER_CONNECT && requestCode != Definitions.QPOSREADER_INIT) {
            if (resultCode == Activity.RESULT_OK) {
                if(requestCode != 89) {
                    if (resultCode == 10001 || resultCode == 1618 || resultCode == 70) {
                    } else {
                        setIntent(data);
                        try {
                            onPaymentSuccess(TicketsResponse.parseTransaction(data.getStringExtra(Definitions.KEY_DATA_RECEIPT)), paymentDocuments);
                        } catch (Exception ex) {
                            onPaymentSuccess(null, null);
                        }
                    }
                } else {

                }

            }
        } /*else if (Definitions.QPOSREADER_CONNECT == requestCode && resultCode == Activity.RESULT_OK) {
            if (reader instanceof QPOSReader) {
                ((QPOSReader)reader).configuringQPOS = false;
                ((QPOSReader) reader).connect();
            }
        }else if(Definitions.QPOSREADER_INIT == requestCode  && resultCode == Activity.RESULT_OK){
            if(reader instanceof QPOSReader){
                ((QPOSReader)reader).configuringQPOS = false;
                ((QPOSReader) reader).initReader(false, "Activity");
            }
        }

        if(reader != null && reader instanceof QPOSReader && Definitions.QPOSREADER_CONNECT == requestCode){
            ((QPOSReader)reader).configuringQPOS = false;
        }*/
    }

    public abstract void onPaymentSuccess(SrPagoTransaction srPagoTransaction, List<SPTransactionDocument> documents);

    public abstract void onFinishTransaction();


    public SrPagoFinishListener getSrPagoFinishListener() {
        return srPagoFinishListener;
    }

    public void setSrPagoFinishListener(SrPagoFinishListener srPagoFinishListener) {
        this.srPagoFinishListener = srPagoFinishListener;
    }

    void setReader(BaseReader reader){
        this.reader = reader;
    }

//    @Override
//    protected void onConnectionErrorYesClick(int webService) {
//        if(webService == Definitions.PAYMENT){
//            reader.dismissProgress();
//            reader.retryPayment();
//        }
//    }
//
//    @Override
//    protected void onConnectionErrorNoClick(int webService) {
//        reader.dismissProgress();
//    }

    @Override
    public void onPause() {
        super.onPause();
        /*Logger.logDebug("Pause", "ON PAUSE");
        if(needReaderInActivityDialog()) return;
        if(reader != null) {
            if(reader instanceof AnywhereCommerce) {
                ((AnywhereCommerce) reader).destroy();
            }else if(reader instanceof QPOSReader){
                //((QPOSReader) reader).disconnectBT();
            }
        }*/
    }

    public boolean needReaderInActivityDialog(){
        return false;
    }

    public void openSignature(SPResponse response){
        if(Global.getStringKey(this, Definitions.KEY_EMV).equals(PixzelleDefinitions.STRING_NULL) ||
                Global.getStringKey(this, Definitions.KEY_EMV).equals("")) {
            if(this.reader != null)
                this.reader.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT_CARD);
            Intent intent = new Intent(SrPagoActivity.this, SignatureActivity.class);
            intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
            intent.putExtra(Definitions.KEY_DATA_RECEIPT, response.getRaw());
            if(this.reader != null)
                this.reader.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_SIGNATURE);
            if(intent.getExtras().getString(Global.LINK_PACKAGE) != null){
                onFinishTransaction();
                startActivityForResult(intent, 69);
            }
            else {
                onFinishTransaction();
                startActivityForResult(intent, 69);
            }
        }else{
            Global.setStringKey(this, Definitions.KEY_DATA_RECEIPT,response.getRaw());
            try {
                reader.handleEMV(Global.getStringKey(this, Definitions.KEY_EMV));
            } catch (Exception e) {
                //e.printStackTrace();
                this.reader.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT_CARD);
                Intent intent = new Intent(SrPagoActivity.this, SignatureActivity.class);
                intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
                intent.putExtra(Definitions.KEY_DATA_RECEIPT, response.getRaw());
                intent.putExtra(Definitions.KEY_CARD_HOLDER, Global.getInstance().holder);
                intent.putExtra(Definitions.KEY_AMOUNT, Global.getInstance().amount);
                this.reader.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_SIGNATURE);
                if(intent.getExtras().getString(Global.LINK_PACKAGE) != null){
                    onFinishTransaction();
                    startActivityForResult(intent, 69);
                }
                else {
                    onFinishTransaction();
                    startActivityForResult(intent, 69);
                }
            }
        }
    }

    public void validatePaymentDocuments(final SPResponse response){
        PaymentValidateDocumentListener paymentValidateDocumentListener = new PaymentValidateDocumentListener() {
            @Override
            public void onSuccess(List<SPTransactionDocument> documents) {
                Logger.logDebug("success","Documents:"+documents);
                paymentDocuments = documents;
                openSignature(response);
            }

            @Override
            public void onError(SrPagoDefinitions.Error error) {
                Logger.logWarning("error", error.toString());
                openSignature(response);
            }

            @Override
            public void onError(String error, SrPagoDefinitions.Error errorCode) {
                Logger.logWarning("error", error);
                openSignature(response);

            }
        };
        PaymentService paymentService = new PaymentService();
        String operation = response.getRaw();
        String transactionToken = "";
        try {
            JSONObject jsonObject = new JSONObject(operation);
            transactionToken = jsonObject.getJSONObject("result").getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        paymentService.callValidateDocumentPaymentService(this, paymentValidateDocumentListener, transactionToken);
    }


}
