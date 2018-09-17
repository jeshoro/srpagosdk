package sr.pago.sdk.readers.bbpos;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


import com.bbpos.bbdevice.BBDeviceController;
import com.bbpos.bbdevice.CAPK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import sr.pago.sdk.R;
import sr.pago.sdk.SignatureActivity;
import sr.pago.sdk.SrPagoActivity;
import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.enums.PixzelleDefinitions;
import sr.pago.sdk.interfaces.OnSignatureCompleteListener;
import sr.pago.sdk.interfaces.PaymentListener;
import sr.pago.sdk.interfaces.ServerTimeListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.Operation;
import sr.pago.sdk.object.PaymentPreferences;
import sr.pago.sdk.object.PixzelleClass;
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.readers.BaseReader;
import sr.pago.sdk.services.PaymentService;
import sr.pago.sdk.utils.Logger;


/**
 * Created by Rodolfo on 12/05/2015.
 */
public abstract class BbposReader extends BaseReader {

    BBDeviceController bbDeviceController;
    static BbposReader bbposReader;
    private static final String TAG = "BbposReader";


    private int deviceBusyCount = 0, timeoutCount = 0;


    @Deprecated
    public BbposReader(SrPagoActivity srPagoActivity) {
        super(srPagoActivity, srPagoActivity);
    }

    public BbposReader(Context context) {
        super(context);
    }

    protected void checkCard() {
        Logger.logDebug(TAG, "checkCard");

        Hashtable<String, Object> data = new Hashtable<>();
        data.put("checkCardMode", BBDeviceController.CheckCardMode.SWIPE_OR_INSERT);
        data.put("checkCardTimeout", 120);

        bbDeviceController.checkCard(data);
    }

    @Override
    public void retryPayment() {
        Logger.logDebug(TAG, "retryPayment");

        sendPayment(chipCard, paymentListener);
    }

    @Override
    public void getDeviceInfo() {
        Logger.logDebug(TAG, "getDeviceInfo");

        bbDeviceController.getDeviceInfo();
    }

    public abstract void onReturnDeviceInfo(Hashtable<String, String> deviceInfoData);

    protected void onError(BBDeviceController.Error errorState, String message) {
        Logger.logDebug(TAG, "onError: errorState ->" + errorState + "\tmessage ->" + message);

        isReading = false;
        if (srPagoListener == null) return;

        switch (errorState) {
            case TIMEOUT:
                timeoutCount++;
                if (timeoutCount > 3)
                    this.srPagoListener.onError(SrPagoDefinitions.Error.READER_TIMEOUT);
                break;
            case TAMPER:
                this.srPagoListener.onError(SrPagoDefinitions.Error.TAMPER);
                break;
            case UNKNOWN:
                this.srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
                break;
            case CRC_ERROR:
                this.srPagoListener.onError(SrPagoDefinitions.Error.COMM_ERROR);
                break;
            case PCI_ERROR:
                this.srPagoListener.onError(SrPagoDefinitions.Error.PCI_ERROR);
                break;
            case COMM_ERROR:
                this.srPagoListener.onError(SrPagoDefinitions.Error.COMM_ERROR);
                break;
            case DEVICE_BUSY:
                deviceBusyCount++;
                if (deviceBusyCount > 3)
                    this.srPagoListener.onError(SrPagoDefinitions.Error.READER_BUSY);
                break;
            case INPUT_INVALID:
                this.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_INVALID);
                break;
            case FAIL_TO_START_BT:
                if (message.contains("BLE status : 133")) {
//                    bbDeviceController.releaseBBDeviceController();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            srPagoListener.onError(SrPagoDefinitions.Error.FAIL_TO_START_BT);
                        }
                    }, 3000);
                }
                break;
            case CMD_NOT_AVAILABLE:
                this.srPagoListener.onError(SrPagoDefinitions.Error.CMD_NOT_AVAILABLE);
                break;
            case USB_NOT_SUPPORTED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.USB_NOT_SUPPORTED);
                break;
            case INPUT_OUT_OF_RANGE:
                this.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_OUT_OF_RANGE);
                break;
            case CHANNEL_BUFFER_FULL:
                this.srPagoListener.onError(SrPagoDefinitions.Error.CHANNEL_BUFFER_FULL);
                break;
            case FAIL_TO_START_AUDIO:
                this.srPagoListener.onError(SrPagoDefinitions.Error.FAIL_TO_START_AUDIO);
                break;
            case FAIL_TO_START_SERIAL:
                this.srPagoListener.onError(SrPagoDefinitions.Error.FAIL_TO_START_SERIAL);
                break;
            case INPUT_INVALID_FORMAT:
                this.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_INVALID_FORMAT);
                break;
            case USB_DEVICE_NOT_FOUND:
                this.srPagoListener.onError(SrPagoDefinitions.Error.USB_DEVICE_NOT_FOUND);
                break;
            case CASHBACK_NOT_SUPPORTED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.CASHBACK_NOT_SUPPORTED);
                break;
            case HARDWARE_NOT_SUPPORTED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.HARDWARE_NOT_SUPPORTED);
                break;
            case AUDIO_PERMISSION_DENIED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.AUDIO_PERMISSION_DENIED);
                break;
            case COMM_LINK_UNINITIALIZED:
                isReaderConnected = false;
                this.srPagoListener.onError(SrPagoDefinitions.Error.COMM_LINK_UNINITIALIZED);
                break;
            case SERIAL_PERMISSION_DENIED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.SERIAL_PERMISSION_DENIED);
                break;
            case BLUETOOTH_PERMISSION_DENIED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.BLUETOOTH_PERMISSION_DENIED);
                break;
            case VOLUME_WARNING_NOT_ACCEPTED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.WARNING_NOT_ACCEPTED);
                break;
            case USB_DEVICE_PERMISSION_DENIED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.USB_DEVICE_PERMISSION_DENIED);
                break;
            case BLUETOOTH_LOCATION_PERMISSION_DENIED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.BLUETOOTH_LOCATION_PERMISSION_DENIED);
                break;
            case INVALID_FUNCTION_IN_CURRENT_CONNECTION_MODE:
                if (message.equals("Cannot connect bluetooth while audio is started")) {
                    bbDeviceController.stopAudio();
//                    bbposReader.initReader(context);
                } else
                    this.srPagoListener.onError(SrPagoDefinitions.Error.INVALID_FUNCTION_IN_CURRENT_CONNECTION_MODE);
                break;
            case BTV4_NOT_SUPPORTED:
                this.srPagoListener.onError(SrPagoDefinitions.Error.BTV4_NOT_SUPPORTED);
                break;
        }
    }

    @Override
    public void makePayment(double amount, double tip, String reference, PaymentListener listener) {
        Logger.logDebug(TAG, "makePayment");

        this.paymentListener = listener;
        this.amount = amount;

        this.tip = tip;
        this.reference = reference;
        checkCard();
    }

    @Override
    public void makePayment(double amount, double tip, String reference, String affiliation, PaymentListener listener) {
        Logger.logDebug(TAG, "makePayment");

        this.paymentListener = listener;
        this.amount = amount;
        this.tip = tip;
        this.affiliation = affiliation;
        this.reference = reference;
        checkCard();
    }

    @Override
    public void handleEMV(String emv) throws Exception {
        Logger.logDebug(TAG, "transformEMV");

        String transformed;

        String stringSizeToCrop = emv.substring(0, 2);
        int intSizeToCrop = Integer.parseInt(stringSizeToCrop, 16);

        String firstTag = emv.substring(2, (intSizeToCrop * 2) + 2);
        String finalFourFirstTag = firstTag.substring(firstTag.length() - 4, firstTag.length());

        transformed = String.format(Definitions.EMV_1(), Definitions.EMV_FIRST(), finalFourFirstTag, Definitions.EMV_SECOND(), stringSizeToCrop, firstTag);

        if (emv.length() > (intSizeToCrop * 2) + 2) {
            String secondTag = emv.substring(firstTag.length() + 6, emv.length());
            transformed = String.format(Definitions.EMV_2(), transformed, Definitions.EMV_THIRD(), secondTag);
        }

        Logger.logMessage(Definitions.KEY_EMV(), transformed);
        bbDeviceController.sendOnlineProcessResult(transformed);
    }

    @Override
    public void cancelTransaction(SrPagoDefinitions.Status status) {
        Logger.logDebug(TAG, "cancelTransaction");

        cancelOperation = true;
        bbDeviceController.cancelCheckCard();
        /*bbDeviceController.stopAudio();
        bbDeviceController.startAudio();*/
    }

    @Override
    public void devolutionWithId(String transactionId) {
        Logger.logDebug(TAG, "devolutionWithId");

        sendDevolution(transactionId);

    }

    @Override
    public void startTransaction() {
        Logger.logDebug(TAG, "startTransaction");

        Global.clearPayment(context);
    }

    private String encryptAPDU(String apdu, String key) {
        Logger.logDebug(TAG, "encryptAPDU");

        String encrypted = null;
        return encrypted;
    }

    private String getFormattedNumber(int number) {
        Logger.logDebug(TAG, "getFormattedNumber");

        if (number < 10) {
            return String.format(Definitions.FORMATTED_NUMBER(), number);
        } else {
            return String.valueOf(number);
        }
    }

    private String getFormattedYear(int year) {
        Logger.logDebug(TAG, "getFormattedYear");

        return String.valueOf(year).substring(String.valueOf(year).length() - 2, String.valueOf(year).length());
    }

    private void openSignature() {
        Logger.logDebug(TAG, "openSignature");

        Intent intent = new Intent(context, SignatureActivity.class);
        intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
        intent.putExtra(Definitions.KEY_DATA_RECEIPT, Global.getStringKey(context, Definitions.KEY_DATA_RECEIPT));
        Global.setStringKey(context, Definitions.KEY_DATA_RECEIPT, PixzelleDefinitions.STRING_NULL);
        intent.putExtra(Definitions.KEY_CARD_HOLDER, Global.getInstance().holder);
        intent.putExtra(Definitions.KEY_AMOUNT, Global.getInstance().amount);
        intent.putExtra("listener", new OnSignatureCompleteListener() {
            @Override
            public void onSignatureComplete(Intent intent) {
                try {
                    paymentListener.onPaymentSuccess(TicketsResponse.parseTransaction(intent.getStringExtra(Definitions.KEY_DATA_RECEIPT)), null);
                } catch (Exception ex) {
                    paymentListener.onPaymentSuccess(null, null);
                }
            }
        });
        context.startActivity(intent);
    }

    /**
     * Build the loadAmexCAPKS
     */
    public void loadAmexCAPKS() {
        Logger.logDebug(TAG, "loadAmexCAPKS");

        if (bbDeviceController != null) {
            Logger.logDebug("SrPago", "loadAmexCAPKS");
            CAPK capk = new CAPK();
            capk.location = Definitions.CAPK_LOCATION();
            capk.rid = Definitions.CAPK_RID();
            capk.exponent = Definitions.CAPK_EXPONENT();
            capk.modulus = Definitions.CAPK_MODULUS();
            capk.checksum = Definitions.CAPK_CHECKSUM();
            capk.size = Definitions.CAPK_SIZE();
            capk.index = Definitions.CAPK_INDEX();
            bbDeviceController.updateCAPK(capk);
        }
    }

    // Methods from BBDeviceController

    public void readOfflineEMV() {
        Logger.logDebug(TAG, "readOfflineEMV");

        //TODO: VER QUE PEDO CON ESTE METODO
//        bbDeviceController.powerOnIcc();
    }

    public void sendRawAPDU(String apdu) {
        Logger.logDebug(TAG, "sendRawAPDU");

        oldAPDU = apdu;
        //TODO: VER QUE PEDO CON ESTE METODO
//        bbDeviceController.sendApdu(apdu, apdu.length());
    }

    public void sendEncryptedAPDU(final String apdu) {
        Logger.logDebug(TAG, "sendEncryptedAPDU");

        oldAPDU = apdu;
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(69, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                //TODO: VER QUE PEDO CON ESTE METODO
//                bbDeviceController.sendApdu(encryptAPDU(apdu, ((PixzelleClass)response.getItems().get(0)).getName()));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.APDU_INTERRUPTED);
                bbDeviceController.powerOffIcc();
            }
        }, null, null);

//        WebServiceConnection webServiceConnection = new WebServiceConnection(srPagoActivity, 69, PixzelleWebServiceConnection.POST);
//        webServiceConnection.setListener(new WebServiceListener(srPagoActivity) {
//            @Override
//            public void onResponseOk(Pixzelle.SERVER_CODES code, Response response, int webService) {
//                bbDeviceController.sendApduWithPkcs7Padding(encryptAPDU(apdu, response.getItems().get(0).getName()));
//            }
//
//            @Override
//            public void onResponseError(Pixzelle.SERVER_CODES code, Response response, int webService) {
//                if (srPagoListener != null)
//                    srPagoListener.onError(SrPagoDefinitions.Error.APDU_INTERRUPTED);
//                bbDeviceController.powerOffIcc();
//            }
//        });
//        webServiceConnection.execute();
    }

    public boolean isConnected() {
        Logger.logDebug(TAG, "isConnected: " + isReaderConnected);

        if (bbDeviceController == null) {
            /*if (bbDeviceController.getConnectionMode() == BBDeviceController.ConnectionMode.AUDIO)
                isReaderConnected = bbDeviceController.isAudioDevicePlugged();
            else
                bbDeviceController.isDeviceHere();
        } else {*/
            isReaderConnected = false;
        }
        return isReaderConnected;
    }

    protected void onReturnCheckCardResult(BBDeviceController.CheckCardResult checkCardResult, Hashtable<String, String> decodeData) {
        Logger.logDebug(TAG, "onReturnCheckCardResult -> " + checkCardResult);

        operation = new Operation(getContext());

        switch (checkCardResult) {
            case BAD_SWIPE:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                isReading = false;
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.CARD_READ);
                break;

            case NOT_ICC:
                isReading = false;
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.SOUND_INTERFERENCE_1);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                break;

            case MSR:
                chipCard = false;
                if (decodeData.get(Definitions.SERVICE_CODE()).equals(Definitions._221()) || decodeData.get(Definitions.SERVICE_CODE()).equals("201") || decodeData.get(Definitions.SERVICE_CODE()).startsWith("6")) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    isReading = false;
                    if (srPagoListener != null)
                        srPagoListener.onError(SrPagoDefinitions.Error.CARD_WITH_CHIP);
                } /*else if (decodeData.get(Definitions.ENC_TRACK_1()).equals(Definitions.EMPTY()) ||
                    decodeData.get(Definitions.ENC_TRACK_1()).substring(0, 4).equals(Definitions._0000())) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                isReading = false;
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
            } */ else {
                    double amount = this.amount;
                    if (amount >= 1) {
                        if (PaymentPreferences.getInstance(getContext()).isBandCardPossible()) {
                            handleBand(decodeData);
                            if (operation.getCard().getCardNumber().length() > 16) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                isReading = false;
                                if (srPagoListener != null)
                                    srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
                            } else {
                                if (validLocation()) {
                                    if (operation.getCard().getType().equals(Definitions.AMEX())) {
                                        setAmexPopUp();
                                    } else {
                                        if (srPagoListener != null)
                                            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_STARTED);
                                        setMonthsPopUp();
                                    }
                                } else {
                                    paymentListener.onError("Tu cobro no fue procesado debido a un error en los datos de ubicación.\n" +
                                            "Favor de verificar que se encuentren activos los servicios de ubicación.", SrPagoDefinitions.Error.UNKNOWN);
                                }
                            }
                        } else {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            isReading = false;
                            if (srPagoListener != null) {
                                srPagoListener.onError(SrPagoDefinitions.Error.MSR_NOT_ALLOWED);
                            }
                        }
                    } else {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        isReading = false;
                        if (srPagoListener != null) {
                            srPagoListener.onError(SrPagoDefinitions.Error.AMOUNT_LESS_THAN_MINIMUM);
                        }
                    }
                }
                break;

            case NO_CARD:
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.NO_CARD_DETECTED);
                isReading = false;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                break;

            case USE_ICC_CARD:
                chipCard = true;
                double amount = this.amount;
                if (amount >= 1) {
                    cancelOperation = false;
                    if (srPagoListener != null)
                        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_STARTED);
                    Logger.logMessage(Definitions.SR_PAGO(), context.getResources().getString(R.string.sr_pago_payment_step_2));
//                if (!isCustomLoading())
//                    progressDialog = ProgressDialog.show(srPagoActivity, Definitions.EMPTY(), Definitions.NO_CARD_QUIT(), true);
                    operation.getCard().setEmvFlag(Definitions.TRUE());
                    operation.getCard().setMsrFlag(Definitions.FALSE());
                    Logger.logMessage(Definitions.CARD(), Definitions.ICC());

                    //TODO: VER QUE PEDO CON ESTE METODO
//                bbDeviceController.startEmv(BBDeviceController.EmvOption.START);
                } else {
                    isReading = false;
                    if (srPagoListener != null)
                        srPagoListener.onError(SrPagoDefinitions.Error.AMOUNT_LESS_THAN_MINIMUM);
                }
                break;

            case TAP_CARD_DETECTED:
            case INSERTED_CARD:
                chipCard = true;
                if (this.amount >= 1) {
                    cancelOperation = false;
                    if (srPagoListener != null)
                        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_STARTED);
                    Logger.logMessage(Definitions.SR_PAGO(), context.getResources().getString(R.string.sr_pago_payment_step_2));
//                if (!isCustomLoading())
//                    progressDialog = ProgressDialog.show(srPagoActivity, Definitions.EMPTY(), Definitions.NO_CARD_QUIT(), true);
                    operation.getCard().setEmvFlag(Definitions.TRUE());
                    operation.getCard().setMsrFlag(Definitions.FALSE());

                    Hashtable<String, Object> data = new Hashtable<>();

                    String terminalTime = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());
                    BBDeviceController.CurrencyCharacter[] currencyCharacter = new BBDeviceController.CurrencyCharacter[]{BBDeviceController.CurrencyCharacter.M, BBDeviceController.CurrencyCharacter.X, BBDeviceController.CurrencyCharacter.N};

                    data.put("terminalTime", terminalTime);
                    data.put("currencyCharacters", currencyCharacter);
                    data.put("currencyCode", Definitions._484());
                    data.put("amount", this.amount);
                    data.put("transactionType", BBDeviceController.TransactionType.GOODS);


                    if (this instanceof BbposAudio || this instanceof BbposBluetooth)
                        data.put("checkCardMode", BBDeviceController.CheckCardMode.SWIPE_OR_INSERT);
                    else
                        data.put("checkCardMode", BBDeviceController.CheckCardMode.SWIPE_OR_INSERT_OR_TAP);

                    bbDeviceController.startEmv(data);

//                bbDeviceController.startEmv(BBDeviceController.EmvOption.START);
                } else {
                    isReading = false;
                    if (srPagoListener != null)
                        srPagoListener.onError(SrPagoDefinitions.Error.AMOUNT_LESS_THAN_MINIMUM);
                }
                break;

            case MAG_HEAD_FAIL:
                break;

            case MANUAL_PAN_ENTRY:
                break;

        }
    }

    public void onReturnCAPKList(List<CAPK> capkList) {
        Logger.logDebug(TAG, "onReturnCAPKList");

//        if (bbposReader != null)
//            bbposReader.onReturnCAPKList(list);
    }

    public void onReturnApduResult(boolean isSuccess, Hashtable<String, Object> data) {
        Logger.logDebug(TAG, "onReturnApduResult");

        Logger.logMessage(Definitions.SUCCESS(), Definitions.EMPTY() + isSuccess);
        //TODO: SACAR EL APDU DEL DATA, VER PRIMERO SI VIENE
//        if (srPagoListener != null)
        //            srPagoListener.onReturnAPDU(oldAPDU, apdu);
    }

    public void onReturnPowerOnIccResult(boolean isSuccess, String ksn, String atr, int atrLength) {
        Logger.logDebug(TAG, "onReturnPowerOnIccResult");

        Logger.logMessage(Definitions.SUCCESS_POWER_ON(), Definitions.EMPTY() + isSuccess);
        Logger.logMessage(Definitions.KSN(), ksn);
        Logger.logMessage(Definitions.ATR(), atr);
        //TODO: QUE PEDO CON EL KSN?
//        bbDeviceController.getKsn();
    }

    public void onReturnTransactionResult(BBDeviceController.TransactionResult transResult) {
        Logger.logDebug(TAG, "onReturnTransactionResult: TransactionResult -> " + transResult);

        Logger.logMessage(Definitions.RESULT(), transResult + Definitions.EMPTY());

        if (srPagoListener != null) {
            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_RETURN_TRANSACTION_RESULT);
        }

        switch (transResult) {
            case NOT_ICC:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.TRANSACTION_INTERRUPTED);
                break;

            case ICC_CARD_REMOVED:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.CARD_REMOVED);
                break;

            case TERMINATED:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.TERMINATED);
                break;

            case DECLINED:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.CARD_DECLINED);
                break;

            case CANCELED:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.TRANSACTION_INTERRUPTED);
                break;
            case APPROVED:
                Logger.logMessage(Definitions.TRANSACTION_RESULT(), Definitions.APPROVED());
                if (operation.getCard().getType().equals(Definitions.AMEX())) {
//                srPagoActivity.handler.sendMessage(new Message());
                    openSignature();
                }
                break;

            case TIMEOUT:
            case SELECT_APP_FAIL:
            case DEVICE_ERROR:
            case CARD_BLOCKED:
            case NO_EMV_APPS:
            case CAPK_FAIL:
            case CONDITION_NOT_SATISFIED:
            case CARD_SCHEME_NOT_MATCHED:
            case MISSING_MANDATORY_DATA:
            case CANCELED_OR_TIMEOUT:
            case APPLICATION_BLOCKED:
            case CARD_NOT_SUPPORTED:
            case INVALID_ICC_DATA:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (srPagoListener != null)
                    srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
                break;
        }
    }

    public void onReturnEmvCardDataResult(boolean isSuccess, String tlv) {
        Logger.logDebug(TAG, "onReturnEmvCardDataResult");
        Logger.logDebug(Definitions.TLV(), tlv);
    }

    protected void onRequestSelectApplication(ArrayList<String> appList) {
        Logger.logDebug(TAG, "onRequestSelectApplication");

        if (!cancelOperation) {
            if (srPagoListener != null)
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_SELECTED_APLICATION);
            bbDeviceController.selectApplication(0);
        }
    }

    protected void onRequestSetAmount() {
        Logger.logDebug(TAG, "onRequestSetAmount");

        double finalAmount = getFinalAmount();
        if (!cancelOperation) {
            if (srPagoListener != null)
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_SET_AMOUNT);

            boolean paymentSuccess = bbDeviceController.setAmount(String.format(Locale.US, Definitions._2f(), finalAmount), Definitions.EMPTY(), Definitions._484(), BBDeviceController.TransactionType.GOODS, null);
            Logger.logMessage(Definitions.SR_PAGO(), context.getResources().getString(R.string.sr_pago_payment_step_3));
            Logger.logMessage(Definitions.VALIDATION(), Definitions.EMPTY() + paymentSuccess);
        }
    }

    protected void onRequestOnlineProcess(String tlv) {
        Logger.logDebug(TAG, "onRequestOnlineProcess");

        if (srPagoListener != null)
            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_ONLINE_PROCESS);
        useCard = handleChip(tlv);
        if (operation.getCard().getType().equals(Definitions.AMEX())) {
            setAmexPopUp();
        } else {
            Logger.logMessage(Definitions.SR_PAGO(), context.getResources().getString(R.string.sr_pago_payment_step_5));
            if (tlv.length() > 0) {
                bbDeviceController.sendOnlineProcessResult(Definitions.ONLINE_PROCESS());
                if (operation.getCard().getMsrFlag().equals(Definitions.TRUE())) {
                    operation.getCard().setData(tlv);
                }
            }
        }
    }

    protected void onRequestTerminalTime() {
        Logger.logDebug(TAG, "onRequestTerminalTime");

        Calendar c = Calendar.getInstance();
        String seconds = getFormattedNumber(c.get(Calendar.SECOND));
        String minutes = getFormattedNumber(c.get(Calendar.MINUTE));
        String hour = getFormattedNumber(c.get(Calendar.HOUR_OF_DAY));
        String year = getFormattedYear(c.get(Calendar.YEAR));
        String month = getFormattedNumber(c.get(Calendar.MONTH) + 1);
        String day = getFormattedNumber(c.get(Calendar.DAY_OF_MONTH));
        final String finalTime = String.format(Definitions._6s(), year, month, day, hour, minutes, seconds);
        Logger.logMessage(Definitions.FINAL_TIME(), finalTime);
        Logger.logMessage("Cancel Operation", cancelOperation + "");
        if (!cancelOperation) {
            if (srPagoListener != null) {
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_TERMINAL_TIME);
                PaymentService paymentService = new PaymentService();
                paymentService.getServerTime(context, new ServerTimeListener() {
                    @Override
                    public void onSuccess(Object time) {
                        String serverTime = ((PixzelleClass) time).getName();
                        bbDeviceController.sendTerminalTime(finalTime);
                    }

                    @Override
                    public void onError(SrPagoDefinitions.Error error) {
                        bbDeviceController.sendTerminalTime(finalTime);
                    }

                    @Override
                    public void onError(String error, SrPagoDefinitions.Error errorCode) {
                        bbDeviceController.sendTerminalTime(finalTime);
                    }
                });
            }
        }
    }

    protected void onReturnBatchData(String tlv) {
        Logger.logDebug(TAG, "onReturnBatchData");

        if (srPagoListener != null)
            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_RETURN_BATCH_DATA);

        if (operation != null && operation.getCard() != null) {
            if (!operation.getCard().getType().equals(Definitions.AMEX())) {
                if (operation.getCard().getEmvFlag().equals(Definitions.TRUE())) {
                    if (useCard) {
                        setMonthsPopUp();
                    } else {
                        isReading = false;
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }
            }
        }
    }

    protected void onDeviceHere(boolean isHere) {
        Logger.logDebug(TAG, "onDeviceHere");

        isReaderConnected = isHere;

//        if (srPagoListener != null && isHere) {
//            srPagoListener.onDevicePlugged();
//        }
    }

    protected void onRequestFinalConfirm() {
        Logger.logDebug(TAG, "onRequestFinalConfirm");

        if (!cancelOperation) {
            if (srPagoListener != null)
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_FINAL_CONFIRM);
            bbDeviceController.sendFinalConfirmResult(true);
        }
    }

    protected void onWaitingForCard(BBDeviceController.CheckCardMode checkCardMode) {
        Logger.logDebug(TAG, "onWaitingForCard");

        cancelOperation = false;
        if (srPagoListener != null)
            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_WAITING);
    }

    protected void onWaitingReprintOrPrintNext() {
    }

    protected void onBTReturnScanResults(List<BluetoothDevice> foundDevices) {
    }

    protected void onBTScanTimeout() {
    }

    protected void onBTScanStopped() {
    }

    protected void onBTConnected(BluetoothDevice bluetoothDevice) {
    }

    protected void onBTDisconnected() {
    }

    protected void onUsbConnected() {
    }

    protected void onUsbDisconnected() {
    }

    protected void onSerialConnected() {
    }

    protected void onSerialDisconnected() {
    }

    protected void onReturnCancelCheckCardResult(boolean isSuccess) {
    }

    protected void onReturnReversalData(String tlv) {
    }

    protected void onReturnAmountConfirmResult(boolean isSuccess) {
    }

    protected void onReturnPinEntryResult(BBDeviceController.PinEntryResult pinEntryResult, Hashtable<String, String> data) {
    }

    protected void onReturnPrintResult(BBDeviceController.PrintResult printResult) {
    }

    protected void onReturnAccountSelectionResult(BBDeviceController.AccountSelectionResult result, int selectedAccountType) {
    }

    protected void onReturnAmount(Hashtable<String, String> data) {
    }

    protected void onReturnUpdateAIDResult(Hashtable<String, BBDeviceController.TerminalSettingStatus> data) {
    }

    protected void onReturnUpdateGprsSettingsResult(boolean isSuccess, Hashtable<String, BBDeviceController.TerminalSettingStatus> data) {
    }

    protected void onReturnUpdateTerminalSettingResult(BBDeviceController.TerminalSettingStatus terminalSettingStatus) {
    }

    protected void onReturnUpdateWiFiSettingsResult(boolean isSuccess, Hashtable<String, BBDeviceController.TerminalSettingStatus> data) {
    }

    protected void onReturnReadAIDResult(Hashtable<String, Object> data) {
    }

    protected void onReturnReadGprsSettingsResult(boolean isSuccess, Hashtable<String, Object> data) {
    }

    protected void onReturnReadTerminalSettingResult(Hashtable<String, Object> data) {
    }

    protected void onReturnReadWiFiSettingsResult(boolean isSuccess, Hashtable<String, Object> data) {
    }

    protected void onReturnEnableAccountSelectionResult(boolean isSuccess) {
    }

    protected void onReturnEnableInputAmountResult(boolean isSuccess) {
    }

    protected void onReturnCAPKDetail(CAPK capk) {
    }

    protected void onReturnCAPKLocation(String location) {
    }

    protected void onReturnUpdateCAPKResult(boolean isSuccess) {
    }

    protected void onReturnRemoveCAPKResult(boolean isSuccess) {
    }

    protected void onReturnEmvReportList(Hashtable<String, String> data) {
    }

    protected void onReturnEmvReport(String TLV) {
    }

    protected void onReturnDisableAccountSelectionResult(boolean isSuccess) {
    }

    protected void onReturnDisableInputAmountResult(boolean isSuccess) {
    }

    protected void onReturnPhoneNumber(BBDeviceController.PhoneEntryResult phoneEntryResult, String phoneNumber) {
    }

    protected void onReturnEmvCardNumber(boolean isSuccess, String cardNumber) {
    }

    protected void onReturnEncryptPinResult(boolean isSuccess, Hashtable<String, String> data) {
    }

    protected void onReturnEncryptDataResult(boolean isSuccess, Hashtable<String, String> data) {
    }

    protected void onReturnInjectSessionKeyResult(boolean isSuccess, Hashtable<String, String> data) {
    }

    protected void onReturnPowerOffIccResult(boolean isSuccess) {
    }

    protected void onRequestPinEntry(BBDeviceController.PinEntrySource pinEntrySource) {
        if (srPagoListener != null) {
            srPagoListener.onRequestPinEntry(false);
        }
    }

    protected void onRequestDisplayText(BBDeviceController.DisplayText displayText) {
        switch (displayText) {
            case TRY_AGAIN:
                if (srPagoListener != null) {
                    srPagoListener.onRequestPinEntry(true);
                }
                break;
        }
    }

    protected void onRequestDisplayAsterisk(int numOfAsterisk) {

        if (srPagoListener != null) {
            srPagoListener.onRequestDisplayAsterisk(numOfAsterisk);
        }
    }

    protected void onRequestDisplayLEDIndicator(BBDeviceController.ContactlessStatus contactlessStatus) {
    }

    protected void onRequestProduceAudioTone(BBDeviceController.ContactlessStatusTone contactlessStatusTone) {

    }

    protected void onRequestClearDisplay() {
    }

    protected void onRequestPrintData(int index, boolean isReprint) {
    }

    protected void onPrintDataCancelled() {
    }

    protected void onPrintDataEnd() {
    }

    protected void onBatteryLow(BBDeviceController.BatteryStatus batteryStatus) {
    }

    protected void onAudioDevicePlugged() {
    }

    protected void onAudioDeviceUnplugged() {
    }

    protected void onSessionInitialized() {
    }

    protected void onSessionError(BBDeviceController.SessionError sessionError, String errorMessage) {
    }

    protected void onAudioAutoConfigProgressUpdate(double percentage) {
    }

    protected void onAudioAutoConfigCompleted(boolean isDefaultSettings, String autoConfigSettings) {
    }

    protected void onAudioAutoConfigError(BBDeviceController.AudioAutoConfigError audioAutoConfigError) {
    }

    protected void onNoAudioDeviceDetected() {
    }

    protected void onPowerDown() {
    }

    protected void onPowerButtonPressed() {
    }

    protected void onDeviceReset() {
    }

    protected void onEnterStandbyMode() {
    }

    protected void onReturnNfcDataExchangeResult(boolean isSuccess, Hashtable<String, String> data) {
    }

    protected void onReturnNfcDetectCardResult(BBDeviceController.NfcDetectCardResult nfcDetectCardResult, Hashtable<String, Object> data) {
    }

    protected void onReturnControlLEDResult(boolean isSuccess, String errorMessage) {
    }

    protected void onReturnVasResult(BBDeviceController.VASResult result, Hashtable<String, Object> data) {
    }

    protected void onRequestStartEmv() {
    }

    protected void onDeviceDisplayingPrompt() {
    }

    protected void onRequestKeypadResponse() {
    }

    protected void onReturnDisplayPromptResult(BBDeviceController.DisplayPromptResult result) {
    }

    protected void onBarcodeReaderConnected() {
    }

    protected void onBarcodeReaderDisconnected() {
    }

    protected void onReturnBarcode(String barcode) {
    }

    static class MyBBDeviceControllerListener implements BBDeviceController.BBDeviceControllerListener {

        private static final String TAG = "MyBBDeviceControllerListene";

        @Override
        public void onWaitingForCard(BBDeviceController.CheckCardMode checkCardMode) {
            Logger.logDebug(TAG, "onWaitingForCard: " + checkCardMode.toString());
            if (bbposReader != null) bbposReader.onWaitingForCard(checkCardMode);
        }

        @Override
        public void onWaitingReprintOrPrintNext() {
            Logger.logDebug(TAG, "onWaitingReprintOrPrintNext");
            if (bbposReader != null) bbposReader.onWaitingReprintOrPrintNext();
        }

        @Override
        public void onBTReturnScanResults(List<BluetoothDevice> foundDevices) {
            Logger.logDebug(TAG, "onBTReturnScanResults");
            if (bbposReader != null) bbposReader.onBTReturnScanResults(foundDevices);
        }

        @Override
        public void onBTScanTimeout() {
            Logger.logDebug(TAG, "onBTScanTimeout");
            if (bbposReader != null) bbposReader.onBTScanTimeout();
        }

        @Override
        public void onBTScanStopped() {
            Logger.logDebug(TAG, "onBTScanStopped");
            if (bbposReader != null) bbposReader.onBTScanStopped();
        }

        @Override
        public void onBTConnected(BluetoothDevice bluetoothDevice) {
            Logger.logDebug(TAG, "onBTConnected");
            if (bbposReader != null) bbposReader.onBTConnected(bluetoothDevice);
        }

        @Override
        public void onBTDisconnected() {
            Logger.logDebug(TAG, "onBTDisconnected");
            if (bbposReader != null) bbposReader.onBTDisconnected();
        }

        @Override
        public void onUsbConnected() {
            Logger.logDebug(TAG, "onUsbConnected");
            if (bbposReader != null) bbposReader.onUsbConnected();
        }

        @Override
        public void onUsbDisconnected() {
            Logger.logDebug(TAG, "onUsbDisconnected");
            if (bbposReader != null) bbposReader.onUsbDisconnected();
        }

        @Override
        public void onSerialConnected() {
            Logger.logDebug(TAG, "onSerialConnected");
            if (bbposReader != null) bbposReader.onSerialConnected();
        }

        @Override
        public void onSerialDisconnected() {
            Logger.logDebug(TAG, "onSerialDisconnected");
            if (bbposReader != null) bbposReader.onSerialDisconnected();
        }

        @Override
        public void onReturnCheckCardResult(BBDeviceController.CheckCardResult checkCardResult, Hashtable<String, String> decodeData) {

            Logger.logDebug(TAG, "onReturnCheckCardResult: " + checkCardResult.name() + "\n ");
            if (decodeData == null) {
                Logger.logDebug(TAG, "onReturnCheckCardResult: decodeData = null");
            } else {
                Logger.logDebug(TAG, "onReturnCheckCardResult: decodeData -> " + decodeData.toString());
            }
            if (bbposReader != null)
                bbposReader.onReturnCheckCardResult(checkCardResult, decodeData);
        }

        @Override
        public void onReturnCancelCheckCardResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnCancelCheckCardResult");
            if (bbposReader != null) bbposReader.onReturnCancelCheckCardResult(isSuccess);
        }

        @Override
        public void onReturnDeviceInfo(Hashtable<String, String> deviceInfoTable) {
            Logger.logDebug(TAG, "onReturnDeviceInfo");
            if (bbposReader != null) bbposReader.onReturnDeviceInfo(deviceInfoTable);
        }

        @Override
        public void onReturnTransactionResult(BBDeviceController.TransactionResult transactionResult) {
            Logger.logDebug(TAG, "onReturnTransactionResult");
            if (bbposReader != null) bbposReader.onReturnTransactionResult(transactionResult);
        }

        @Override
        public void onReturnBatchData(String tlv) {
            Logger.logDebug(TAG, "onReturnBatchData");
            if (bbposReader != null) bbposReader.onReturnBatchData(tlv);
        }

        @Override
        public void onReturnReversalData(String tlv) {
            Logger.logDebug(TAG, "onReturnReversalData");
            if (bbposReader != null) bbposReader.onReturnReversalData(tlv);
        }

        @Override
        public void onReturnAmountConfirmResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnAmountConfirmResult");
            if (bbposReader != null) bbposReader.onReturnAmountConfirmResult(isSuccess);
        }

        @Override
        public void onReturnPinEntryResult(BBDeviceController.PinEntryResult pinEntryResult, Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnPinEntryResult");
            if (bbposReader != null) bbposReader.onReturnPinEntryResult(pinEntryResult, data);
        }

        @Override
        public void onReturnPrintResult(BBDeviceController.PrintResult printResult) {
            Logger.logDebug(TAG, "onReturnPrintResult");
            if (bbposReader != null) bbposReader.onReturnPrintResult(printResult);
        }

        @Override
        public void onReturnAccountSelectionResult(BBDeviceController.AccountSelectionResult result, int selectedAccountType) {
            Logger.logDebug(TAG, "onReturnAccountSelectionResult");
            if (bbposReader != null)
                bbposReader.onReturnAccountSelectionResult(result, selectedAccountType);
        }

        @Override
        public void onReturnAmount(Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnAmount");
            if (bbposReader != null) bbposReader.onReturnAmount(data);
        }

        @Override
        public void onReturnUpdateAIDResult(Hashtable<String, BBDeviceController.TerminalSettingStatus> data) {
            Logger.logDebug(TAG, "onReturnUpdateAIDResult");
            if (bbposReader != null) bbposReader.onReturnUpdateAIDResult(data);
        }

        @Override
        public void onReturnUpdateGprsSettingsResult(boolean isSuccess, Hashtable<String, BBDeviceController.TerminalSettingStatus> data) {
            Logger.logDebug(TAG, "onReturnUpdateGprsSettingsResult");
            if (bbposReader != null) bbposReader.onReturnUpdateGprsSettingsResult(isSuccess, data);
        }

        @Override
        public void onReturnUpdateTerminalSettingResult(BBDeviceController.TerminalSettingStatus terminalSettingStatus) {
            Logger.logDebug(TAG, "onReturnUpdateTerminalSettingResult");
            if (bbposReader != null)
                bbposReader.onReturnUpdateTerminalSettingResult(terminalSettingStatus);
        }

        @Override
        public void onReturnUpdateWiFiSettingsResult(boolean isSuccess, Hashtable<String, BBDeviceController.TerminalSettingStatus> data) {
            Logger.logDebug(TAG, "onReturnUpdateWiFiSettingsResult");
            if (bbposReader != null) bbposReader.onReturnUpdateWiFiSettingsResult(isSuccess, data);
        }

        @Override
        public void onReturnReadAIDResult(Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnReadAIDResult");
            if (bbposReader != null) bbposReader.onReturnReadAIDResult(data);
        }

        @Override
        public void onReturnReadGprsSettingsResult(boolean isSuccess, Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnReadGprsSettingsResult");
            if (bbposReader != null) bbposReader.onReturnReadGprsSettingsResult(isSuccess, data);
        }

        @Override
        public void onReturnReadTerminalSettingResult(Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnReadTerminalSettingResult");
            if (bbposReader != null) bbposReader.onReturnReadTerminalSettingResult(data);
        }

        @Override
        public void onReturnReadWiFiSettingsResult(boolean isSuccess, Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnReadWiFiSettingsResult");
            if (bbposReader != null) bbposReader.onReturnReadWiFiSettingsResult(isSuccess, data);
        }

        @Override
        public void onReturnEnableAccountSelectionResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnEnableAccountSelectionResult");
            if (bbposReader != null) bbposReader.onReturnEnableAccountSelectionResult(isSuccess);
        }

        @Override
        public void onReturnEnableInputAmountResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnEnableInputAmountResult");
            if (bbposReader != null) bbposReader.onReturnEnableInputAmountResult(isSuccess);
        }

        @Override
        public void onReturnCAPKList(List<CAPK> capkList) {
            Logger.logDebug(TAG, "onReturnCAPKList");
            if (bbposReader != null) bbposReader.onReturnCAPKList(capkList);
        }

        @Override
        public void onReturnCAPKDetail(CAPK capk) {
            Logger.logDebug(TAG, "onReturnCAPKDetail");
            if (bbposReader != null) bbposReader.onReturnCAPKDetail(capk);
        }

        @Override
        public void onReturnCAPKLocation(String location) {
            Logger.logDebug(TAG, "onReturnCAPKLocation");
            if (bbposReader != null) bbposReader.onReturnCAPKLocation(location);
        }

        @Override
        public void onReturnUpdateCAPKResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnUpdateCAPKResult");
            if (bbposReader != null) bbposReader.onReturnUpdateCAPKResult(isSuccess);
        }

        @Override
        public void onReturnRemoveCAPKResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnRemoveCAPKResult");
            if (bbposReader != null) bbposReader.onReturnRemoveCAPKResult(isSuccess);
        }

        @Override
        public void onReturnEmvReportList(Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnEmvReportList");
            if (bbposReader != null) bbposReader.onReturnEmvReportList(data);
        }

        @Override
        public void onReturnEmvReport(String tlv) {
            Logger.logDebug(TAG, "onReturnEmvReport");
            if (bbposReader != null) bbposReader.onReturnEmvReport(tlv);
        }

        @Override
        public void onReturnDisableAccountSelectionResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnDisableAccountSelectionResult");
            if (bbposReader != null) bbposReader.onReturnDisableAccountSelectionResult(isSuccess);
        }

        @Override
        public void onReturnDisableInputAmountResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnDisableInputAmountResult");
            if (bbposReader != null) bbposReader.onReturnDisableInputAmountResult(isSuccess);
        }

        @Override
        public void onReturnPhoneNumber(BBDeviceController.PhoneEntryResult phoneEntryResult, String phoneNumber) {
            Logger.logDebug(TAG, "onReturnPhoneNumber");
            if (bbposReader != null) bbposReader.onReturnPhoneNumber(phoneEntryResult, phoneNumber);
        }

        @Override
        public void onReturnEmvCardDataResult(boolean isSuccess, String tlv) {
            Logger.logDebug(TAG, "onReturnEmvCardDataResult");
            if (bbposReader != null) bbposReader.onReturnEmvCardDataResult(isSuccess, tlv);
        }

        @Override
        public void onReturnEmvCardNumber(boolean isSuccess, String cardNumber) {
            Logger.logDebug(TAG, "onReturnEmvCardNumber");
            if (bbposReader != null) bbposReader.onReturnEmvCardNumber(isSuccess, cardNumber);
        }

        @Override
        public void onReturnEncryptPinResult(boolean isSuccess, Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnEncryptPinResult");
            if (bbposReader != null) bbposReader.onReturnEncryptPinResult(isSuccess, data);
        }

        @Override
        public void onReturnEncryptDataResult(boolean isSuccess, Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnEncryptDataResult");
            if (bbposReader != null) bbposReader.onReturnEncryptDataResult(isSuccess, data);
        }

        @Override
        public void onReturnInjectSessionKeyResult(boolean isSuccess, Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnInjectSessionKeyResult");
            if (bbposReader != null) bbposReader.onReturnInjectSessionKeyResult(isSuccess, data);
        }

        @Override
        public void onReturnPowerOnIccResult(boolean isSuccess, String ksn, String atr, int atrLength) {
            Logger.logDebug(TAG, "onReturnPowerOnIccResult");
            if (bbposReader != null)
                bbposReader.onReturnPowerOnIccResult(isSuccess, ksn, atr, atrLength);
        }

        @Override
        public void onReturnPowerOffIccResult(boolean isSuccess) {
            Logger.logDebug(TAG, "onReturnPowerOffIccResult");
            if (bbposReader != null) bbposReader.onReturnPowerOffIccResult(isSuccess);
        }

        @Override
        public void onReturnApduResult(boolean isSuccess, Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnApduResult");
            if (bbposReader != null) bbposReader.onReturnApduResult(isSuccess, data);
        }

        @Override
        public void onRequestSelectApplication(ArrayList<String> appList) {
            Logger.logDebug(TAG, "onRequestSelectApplication");
            if (bbposReader != null) bbposReader.onRequestSelectApplication(appList);
        }

        @Override
        public void onRequestSetAmount() {
            Logger.logDebug(TAG, "onRequestSetAmount");
            if (bbposReader != null) bbposReader.onRequestSetAmount();
        }

        @Override
        public void onRequestPinEntry(BBDeviceController.PinEntrySource pinEntrySource) {
            Logger.logDebug(TAG, "onRequestPinEntry");
            if (bbposReader != null) bbposReader.onRequestPinEntry(pinEntrySource);
        }

        @Override
        public void onRequestOnlineProcess(String tlv) {
            Logger.logDebug(TAG, "onRequestOnlineProcess");
            if (bbposReader != null) bbposReader.onRequestOnlineProcess(tlv);
        }

        @Override
        public void onRequestTerminalTime() {
            Logger.logDebug(TAG, "onRequestTerminalTime");
            if (bbposReader != null) bbposReader.onRequestTerminalTime();
        }

        @Override
        public void onRequestDisplayText(BBDeviceController.DisplayText displayText) {
            Logger.logDebug(TAG, "onRequestDisplayText: " + displayText.toString());
            if (bbposReader != null) bbposReader.onRequestDisplayText(displayText);
        }

        @Override
        public void onRequestDisplayAsterisk(int numOfAsterisk) {
            Logger.logDebug(TAG, "onRequestDisplayAsterisk: " + numOfAsterisk);
            if (bbposReader != null) bbposReader.onRequestDisplayAsterisk(numOfAsterisk);
        }

        @Override
        public void onRequestDisplayLEDIndicator(BBDeviceController.ContactlessStatus contactlessStatus) {
            Logger.logDebug(TAG, "onRequestDisplayLEDIndicator");
            if (bbposReader != null) bbposReader.onRequestDisplayLEDIndicator(contactlessStatus);
        }

        @Override
        public void onRequestProduceAudioTone(BBDeviceController.ContactlessStatusTone contactlessStatusTone) {
            Logger.logDebug(TAG, "onRequestProduceAudioTone");
            if (bbposReader != null) bbposReader.onRequestProduceAudioTone(contactlessStatusTone);
        }

        @Override
        public void onRequestClearDisplay() {
            Logger.logDebug(TAG, "onRequestClearDisplay");
            if (bbposReader != null) bbposReader.onRequestClearDisplay();
        }

        @Override
        public void onRequestFinalConfirm() {
            Logger.logDebug(TAG, "onRequestFinalConfirm");
            if (bbposReader != null) bbposReader.onRequestFinalConfirm();
        }

        @Override
        public void onRequestPrintData(int index, boolean isReprint) {
            Logger.logDebug(TAG, "onRequestPrintData");
            if (bbposReader != null) bbposReader.onRequestPrintData(index, isReprint);
        }

        @Override
        public void onPrintDataCancelled() {
            Logger.logDebug(TAG, "onPrintDataCancelled");
            if (bbposReader != null) bbposReader.onPrintDataCancelled();
        }

        @Override
        public void onPrintDataEnd() {
            Logger.logDebug(TAG, "onPrintDataEnd");
            if (bbposReader != null) bbposReader.onPrintDataEnd();
        }

        @Override
        public void onBatteryLow(BBDeviceController.BatteryStatus batteryStatus) {
            Logger.logDebug(TAG, "onBatteryLow");
            if (bbposReader != null) bbposReader.onBatteryLow(batteryStatus);
        }

        @Override
        public void onAudioDevicePlugged() {
            Logger.logDebug(TAG, "onAudioDevicePlugged");
            if (bbposReader != null) bbposReader.onAudioDevicePlugged();
        }

        @Override
        public void onAudioDeviceUnplugged() {
            Logger.logDebug(TAG, "onAudioDeviceUnplugged");
            if (bbposReader != null) bbposReader.onAudioDeviceUnplugged();
        }

        @Override
        public void onError(BBDeviceController.Error Error, String errorMessage) {
            Logger.logDebug(TAG, "onError: { 'Error': '" + Error + "', 'errorMessage': '" + errorMessage + "}");
            if (bbposReader != null) bbposReader.onError(Error, errorMessage);
        }

        @Override
        public void onSessionInitialized() {
            Logger.logDebug(TAG, "onSessionInitialized");
            if (bbposReader != null) bbposReader.onSessionInitialized();
        }

        @Override
        public void onSessionError(BBDeviceController.SessionError sessionError, String errorMessage) {
            Logger.logDebug(TAG, "onSessionError");
            if (bbposReader != null) bbposReader.onSessionError(sessionError, errorMessage);
        }

        @Override
        public void onAudioAutoConfigProgressUpdate(double percentage) {
            Logger.logDebug(TAG, "onAudioAutoConfigProgressUpdate");
            if (bbposReader != null) bbposReader.onAudioAutoConfigProgressUpdate(percentage);
        }

        @Override
        public void onAudioAutoConfigCompleted(boolean isDefaultSettings, String autoConfigSettings) {
            Logger.logDebug(TAG, "onAudioAutoConfigCompleted");
            if (bbposReader != null)
                bbposReader.onAudioAutoConfigCompleted(isDefaultSettings, autoConfigSettings);
        }

        @Override
        public void onAudioAutoConfigError(BBDeviceController.AudioAutoConfigError audioAutoConfigError) {
            Logger.logDebug(TAG, "onAudioAutoConfigError");
            if (bbposReader != null) bbposReader.onAudioAutoConfigError(audioAutoConfigError);
        }

        @Override
        public void onNoAudioDeviceDetected() {
            Logger.logDebug(TAG, "onNoAudioDeviceDetected");
            if (bbposReader != null) bbposReader.onNoAudioDeviceDetected();
        }

        @Override
        public void onDeviceHere(boolean isSuccess) {
            Logger.logDebug(TAG, "isDeviceHere: " + isSuccess);
            if (bbposReader != null) bbposReader.onDeviceHere(isSuccess);
        }

        @Override
        public void onPowerDown() {
            Logger.logDebug(TAG, "onPowerDown");
            if (bbposReader != null) bbposReader.onPowerDown();
        }

        @Override
        public void onPowerButtonPressed() {
            Logger.logDebug(TAG, "onPowerButtonPressed");
            if (bbposReader != null) bbposReader.onPowerButtonPressed();
        }

        @Override
        public void onDeviceReset() {
            Logger.logDebug(TAG, "onDeviceReset");
            if (bbposReader != null) bbposReader.onDeviceReset();
        }

        @Override
        public void onEnterStandbyMode() {
            Logger.logDebug(TAG, "onEnterStandbyMode");
            if (bbposReader != null) bbposReader.onEnterStandbyMode();
        }

        @Override
        public void onReturnNfcDataExchangeResult(boolean isSuccess, Hashtable<String, String> data) {
            Logger.logDebug(TAG, "onReturnNfcDataExchangeResult");
            if (bbposReader != null) bbposReader.onReturnNfcDataExchangeResult(isSuccess, data);
        }

        @Override
        public void onReturnNfcDetectCardResult(BBDeviceController.NfcDetectCardResult nfcDetectCardResult, Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnNfcDetectCardResult");
            if (bbposReader != null)
                bbposReader.onReturnNfcDetectCardResult(nfcDetectCardResult, data);
        }

        @Override
        public void onReturnControlLEDResult(boolean isSuccess, String errorMessage) {
            Logger.logDebug(TAG, "onReturnControlLEDResult");
            if (bbposReader != null) bbposReader.onReturnControlLEDResult(isSuccess, errorMessage);
        }

        @Override
        public void onReturnVasResult(BBDeviceController.VASResult result, Hashtable<String, Object> data) {
            Logger.logDebug(TAG, "onReturnVasResult");
            if (bbposReader != null) bbposReader.onReturnVasResult(result, data);
        }

        @Override
        public void onRequestStartEmv() {
            Logger.logDebug(TAG, "onRequestStartEmv");
            if (bbposReader != null) bbposReader.onRequestStartEmv();
        }

        @Override
        public void onDeviceDisplayingPrompt() {
            Logger.logDebug(TAG, "onDeviceDisplayingPrompt");
            if (bbposReader != null) bbposReader.onDeviceDisplayingPrompt();
        }

        @Override
        public void onRequestKeypadResponse() {
            Logger.logDebug(TAG, "onRequestKeypadResponse");
            if (bbposReader != null) bbposReader.onRequestKeypadResponse();
        }

        @Override
        public void onReturnDisplayPromptResult(BBDeviceController.DisplayPromptResult result) {
            Logger.logDebug(TAG, "onReturnDisplayPromptResult");
            if (bbposReader != null) bbposReader.onReturnDisplayPromptResult(result);
        }

        @Override
        public void onBarcodeReaderConnected() {
            Logger.logDebug(TAG, "onBarcodeReaderConnected");
            if (bbposReader != null) bbposReader.onBarcodeReaderConnected();
        }

        @Override
        public void onBarcodeReaderDisconnected() {
            Logger.logDebug(TAG, "onBarcodeReaderDisconnected");
            if (bbposReader != null) bbposReader.onBarcodeReaderDisconnected();
        }

        @Override
        public void onReturnBarcode(String barcode) {
            Logger.logDebug(TAG, "onReturnBarcode");
            if (bbposReader != null) bbposReader.onReturnBarcode(barcode);
        }

    }
}