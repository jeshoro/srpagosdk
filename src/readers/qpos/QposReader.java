package sr.pago.sdk.readers.qpos;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.dspread.xpos.QPOSService;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import sr.pago.sdk.R;
import sr.pago.sdk.SignatureActivity;
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
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.readers.BaseReader;
import sr.pago.sdk.services.PaymentService;
import sr.pago.sdk.utils.Logger;


/**
 * Created by Rodolfo on 21/08/2017.
 */

public abstract class QposReader extends BaseReader {

    QPOSService qposService;
    static QposReader qposReader;
    Handler mHandler;
    private String qposID;

    boolean patchConfig = false;
    public static String ADDRESS = "addressForQPOS";

    public QposReader(Context context) {
        super(context);
        try {
            patchConfig = Global.getBooleanPreference(context, Definitions.KEY_READER_IS_PATCH_CONFIG);
        } catch (ClassCastException ex) {
            try {
                patchConfig = Boolean.parseBoolean(Global.getStringKey(context, Definitions.KEY_READER_IS_PATCH_CONFIG));
            } catch (Exception ex2) {
                patchConfig = false;
            }
        }
    }

    public boolean isPatchConfig() {
        return patchConfig;
    }

    public void setPatchConfig(boolean patchConfig) {
        this.patchConfig = patchConfig;
    }

    private void readCard() {
        if (qposService != null) {
            qposService.doTrade(30);
        }
    }

    @Override
    public void startTransaction() {
        Global.clearPayment(context);
        cancelOperation = false;
    }

    void sendMsg(int what) {
        Message msg = new Message();
        msg.what = what;
        mHandler.sendMessage(msg);
    }

    private String getFormattedNumber(int number) {
        if (number < 10) {
            return String.format(Definitions.FORMATTED_NUMBER(), number);
        } else {
            return String.valueOf(number);
        }
    }

    private String getFormattedYear(int year) {
        Logger.logDebug("SrPagoQPOSReader", "getFormattedYear");
        return String.valueOf(year);
    }

    void handleDeviceInfo(Hashtable<String, String> deviceInfoData, String type) {
        isReaderConnected = true;
        JSONObject jsonDevice = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : deviceInfoData.entrySet()) {
                if (!entry.getKey().equals(Definitions.BATTERY_PERCENTAGE())) {
                    jsonDevice.put(entry.getKey(), entry.getValue());
                }
            }

            jsonDevice.put("type", type);
            jsonDevice.put("brand", "QPOS");
            jsonDevice.put("uid", qposID);
            jsonDevice.put(Definitions.BATTERY_PERCENTAGE(), Integer.parseInt(deviceInfoData.get(Definitions.BATTERY_PERCENTAGE()).replace("%", "")));
            deviceInfoData.remove(Definitions.BATTERY_PERCENTAGE());
            deviceInfoData.put(Definitions.BATTERY_PERCENTAGE(), String.valueOf(jsonDevice.getInt(Definitions.BATTERY_PERCENTAGE())));

        } catch (Exception ex) {
            Logger.logError(ex);
            try {
                jsonDevice.put(Definitions.BATTERY_PERCENTAGE(), 0);
            } catch (Exception e) {
                Logger.logError(e);
            }
        }
        Global.setStringKey(context, Definitions.KEY_DEVICE_INFO, jsonDevice.toString());
        if (srPagoListener != null)
            srPagoListener.onReturnDeviceInfo(deviceInfoData);
        try {
            int battery = Integer.parseInt(deviceInfoData.get(Definitions.BATTERY_PERCENTAGE()).replace("%", ""));
            String firmware = deviceInfoData.get(Definitions.FIRMWARE_VERSION());
            Logger.logMessage(Definitions.FIRMWARE_VERSION(), firmware);
            if (srPagoListener != null) {
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.QPOS_CONFIG);
                srPagoListener.deviceBaterry(battery);
            }

        } catch (Exception ex) {
            Logger.logError(ex);
        }
    }

    private void openSignature() {
        Intent intent = new Intent(qposReader.context, SignatureActivity.class);
        intent.putExtra(Definitions.KEY_SIGNATURE(), 1);
        intent.putExtra(Definitions.KEY_DATA_RECEIPT, Global.getStringKey(qposReader.context, Definitions.KEY_DATA_RECEIPT));
        Global.setStringKey(qposReader.context, Definitions.KEY_DATA_RECEIPT, PixzelleDefinitions.STRING_NULL);
        intent.putExtra(Definitions.KEY_CARD_HOLDER, Global.getInstance().holder);
        intent.putExtra(Definitions.KEY_AMOUNT, Global.getInstance().amount);
        intent.putExtra("listener", new OnSignatureCompleteListener() {
            @Override
            public void onSignatureComplete(Intent intent) {
                try {
                    qposReader.paymentListener.onPaymentSuccess(TicketsResponse.parseTransaction(intent.getStringExtra(Definitions.KEY_DATA_RECEIPT)), null);
                } catch (Exception ex) {
                    qposReader.paymentListener.onPaymentSuccess(null, null);
                }
            }
        });
        qposReader.context.startActivity(intent);
    }

    /**
     * Methods from BaseReader.
     */
    @Override
    public void getDeviceInfo() {
        Logger.logDebug("SrPagoQPOSReader", "getDeviceInfo");
        if (qposService != null) {
            qposService.getQposInfo();
        }
    }

    @Override
    public boolean isConnected() {
        Logger.logWarning("QposReader", "isConnected");

        if (qposService != null) {
            Logger.logWarning("QposReader", "isConnected: " + qposService.isQposPresent());
            return qposService.isQposPresent();
        }
        Logger.logWarning("QposReader", "isConnected: False");
        return false;
    }

    @Override
    public void makePayment(double amount, double tip, String reference, PaymentListener listener) {
        this.paymentListener = listener;
        this.amount = amount;
        this.tip = tip;
        this.reference = reference;
        readCard();
    }

    @Override
    public void makePayment(double amount, double tip, String reference, String affiliation, PaymentListener listener) {
        this.paymentListener = listener;
        this.amount = amount;
        this.tip = tip;
        this.reference = reference;
        this.affiliation = affiliation;
        readCard();
    }

    @Override
    public void handleEMV(String emv) {
        String transformed;

        String stringSizeToCrop = emv.substring(0, 2);
        int intSizeToCrop = Integer.parseInt(stringSizeToCrop, 16);

        String firstTag = emv.substring(2, (intSizeToCrop * 2) + 2);
        String finalFourFirstTag = firstTag.substring(firstTag.length() - 4, firstTag.length());

        transformed = String.format(Definitions.EMV_1(), Definitions.EMV_FIRST(), finalFourFirstTag, Definitions.EMV_SECOND(), stringSizeToCrop, firstTag);

        if (emv.length() > (intSizeToCrop * 2) + 2) {
            String stringSizeSecondTag = emv.substring(firstTag.length() + 2, firstTag.length() + 4);
            int intSizeSecondTag = Integer.parseInt(stringSizeSecondTag, 16);
            String secondTag = emv.substring(firstTag.length() + 6, emv.length());

            transformed = String.format(Definitions.EMV_2(), transformed, Definitions.EMV_THIRD(), secondTag);
        }

        Logger.logMessage(Definitions.KEY_EMV(), transformed);
        qposService.sendOnlineProcessResult(transformed);
    }

    @Override
    public void retryPayment() {
        sendPayment(chipCard, paymentListener);
    }

    @Override
    public void devolutionWithId(String transactionId) {
        Logger.logDebug("SrPagoQPOSReader", "devolutionWithId");
        sendDevolution(transactionId);
    }

    @Override
    public void cancelTransaction(SrPagoDefinitions.Status status) {
        Logger.logDebug("SrPagoQPOSReader", "cancelTransaction");
        cancelOperation = true;
        if (qposService != null) {
            qposService.resetPosStatus();
        }
    }

    public void readOfflineEMV() {
        //TODO: VER QUE PEDO CON ESTE METODO
//        bbDeviceController.powerOnIcc();
    }

    public void sendRawAPDU(String apdu) {
        oldAPDU = apdu;
        //TODO: VER QUE PEDO CON ESTE METODO
//        bbDeviceController.sendApdu(apdu, apdu.length());
    }

    public void sendEncryptedAPDU(final String apdu) {
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

//                qposReader.powerOffIcc();
            }
        }, null, null);
    }

    /**
     * Methods from QPos SDK.
     */
    protected void onRequestWaitingUser() {
        Logger.logWarning("QposReader", "onRequestWaitingUser");

        if (qposReader != null) {
            qposReader.cancelOperation = false;
            if (qposReader.srPagoListener != null) {
                qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_WAITING);
            }
        }
    }

    protected void onQposIdResult(Hashtable<String, String> posIdTable) {
        Logger.logWarning("QposReader", "onQposIdResult: posIdTable -> " + posIdTable.toString());

        if (qposReader != null) {
            qposReader.qposID = posIdTable.get("posId");
            if (!posIdTable.isEmpty()) {
                qposReader.qposID = posIdTable.get("posId");
            }
            if (qposReader.qposService != null) {
                qposReader.qposService.getQposInfo();
            }
        }
    }

    protected void onQposKsnResult(Hashtable<String, String> ksnResult) {
    }

    protected void onQposIsCardExist(boolean isCardExists) {
    }

    protected void onRequestDeviceScanFinished() {
    }

    protected void onQposInfoResult(Hashtable<String, String> posIdTable) {

    }

    protected void onQposGenerateSessionKeysResult(Hashtable<String, String> sessionResults) {
    }

    protected void onQposDoSetRsaPublicKey(boolean isRsaPublic) {
    }

    protected void onDoTradeResult(QPOSService.DoTradeResult doTradeResult, Hashtable<String, String> decodeData) {
        Logger.logWarning("QposReader", "onDoTradeResult: doTradeResult -> " + doTradeResult + ", decodeData -> " + decodeData);

        if (qposReader != null) {
            qposReader.operation = new Operation(qposReader.context);


            switch (doTradeResult) {
                case NONE:
                    if (qposReader.srPagoListener != null)
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.NO_CARD_DETECTED);
                    qposReader.isReading = false;
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    break;

                case MCR:
                    qposReader.chipCard = false;
                    String code = decodeData.get(Definitions.SERVICE_CODE());

                    if (code.equals(Definitions._221()) || code.equals("201") || code.charAt(0) == '6') {
//                    if (decodeData.get(Definitions.SERVICE_CODE()).equals(Definitions._221()) || decodeData.get(Definitions.SERVICE_CODE()).equals("201")) {
                        if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                            qposReader.progressDialog.dismiss();
                        }
                        qposReader.isReading = false;
                        if (qposReader.srPagoListener != null)
                            qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CARD_WITH_CHIP);
                    } else if (decodeData.get(Definitions.ENC_TRACK_1()).equals(Definitions.EMPTY()) ||
                            decodeData.get(Definitions.ENC_TRACK_1()).substring(0, 4).equals(Definitions._0000())) {
                        if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                            qposReader.progressDialog.dismiss();
                        }
                        qposReader.isReading = false;
                        if (qposReader.srPagoListener != null)
                            qposReader.srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
                    } else {
                        if (qposReader.amount >= 1) {
                            if (PaymentPreferences.getInstance(qposReader.context).isBandCardPossible()) {
                                qposReader.handleBand(decodeData);
                                if (qposReader.operation.getCard().getCardNumber().length() > 16) {
                                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                                        qposReader.progressDialog.dismiss();
                                    }
                                    qposReader.isReading = false;
                                    if (qposReader.srPagoListener != null)
                                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
                                } else {
                                    if (qposReader.validLocation()) {
                                        if (qposReader.operation.getCard().getType().equals(Definitions.AMEX())) {
                                            qposReader.setAmexPopUp();
                                        } else {
                                            if (qposReader.srPagoListener != null)
                                                qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_STARTED);
                                            qposReader.setMonthsPopUp();
                                        }
                                    } else {
                                        qposReader.paymentListener.onError("Tu cobro no fue procesado debido a un error en los datos de ubicación.\n" +
                                                "Favor de verificar que se encuentren activos los servicios de ubicación.", SrPagoDefinitions.Error.UNKNOWN);
                                    }
                                }
                            } else {
                                if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                                    qposReader.progressDialog.dismiss();
                                }
                                qposReader.isReading = false;
                                if (qposReader.srPagoListener != null) {
                                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.MSR_NOT_ALLOWED);
                                }
                            }
                        } else {
                            if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                                qposReader.progressDialog.dismiss();
                            }
                            qposReader.isReading = false;
                            if (qposReader.srPagoListener != null) {
                                qposReader.srPagoListener.onError(SrPagoDefinitions.Error.AMOUNT_LESS_THAN_MINIMUM);
                            }
                        }
                    }
                    break;

                case ICC:
                    qposReader.chipCard = true;
                    if (qposReader.amount >= 1) {
                        if (qposReader.srPagoListener != null)
                            qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.ON_CARD_STARTED);
                        Logger.logMessage(Definitions.SR_PAGO(), qposReader.context.getResources().getString(R.string.sr_pago_payment_step_2));
                        if (!qposReader.isCustomLoading())
                            qposReader.progressDialog = ProgressDialog.show(qposReader.context, Definitions.EMPTY(), Definitions.NO_CARD_QUIT(), true);
                        qposReader.operation.getCard().setEmvFlag(Definitions.TRUE());
                        qposReader.operation.getCard().setMsrFlag(Definitions.FALSE());
                        Logger.logMessage(Definitions.CARD(), Definitions.ICC());
                        qposReader.qposService.doEmvApp(QPOSService.EmvOption.START);
                    } else {
                        qposReader.isReading = false;
                        if (qposReader.srPagoListener != null)
                            qposReader.srPagoListener.onError(SrPagoDefinitions.Error.AMOUNT_LESS_THAN_MINIMUM);
                    }
                    break;

                case NOT_ICC:
                    qposReader.isReading = false;
                    if (qposReader.srPagoListener != null)
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.SOUND_INTERFERENCE_1);
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    break;

                case BAD_SWIPE:
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    qposReader.isReading = false;
                    if (qposReader.srPagoListener != null)
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CARD_READ);
                    break;

                case NO_RESPONSE:
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    qposReader.isReading = false;
                    if (qposReader.srPagoListener != null)
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.READER_TIMEOUT);
                    break;

                case NO_UPDATE_WORK_KEY:
                    break;

                case NFC_ONLINE:
                    break;

                case NFC_OFFLINE:
                    break;

                case NFC_DECLINED:
                    break;
            }
        }
    }

    protected void onSearchMifareCardResult(Hashtable<String, String> mifareResult) {
    }

    protected void onFinishMifareCardResult(boolean isVerified) {
    }

    protected void onVerifyMifareCardResult(boolean isVerified) {
    }

    protected void onReadMifareCardResult(Hashtable<String, String> mifareResult) {
    }

    protected void onWriteMifareCardResult(boolean isWriteSuccess) {
    }

    protected void onOperateMifareCardResult(Hashtable<String, String> mifareData) {
    }

    protected void getMifareCardVersion(Hashtable<String, String> mifareData) {
    }

    protected void getMifareReadData(Hashtable<String, String> mifareData) {
    }

    protected void getMifareFastReadData(Hashtable<String, String> mifareData) {
    }

    protected void writeMifareULData(String mifareUlData) {
    }

    protected void verifyMifareULData(Hashtable<String, String> mifareData) {
    }

    protected void transferMifareData(String mifareData) {
    }

    protected void onRequestSetAmount() {
        Logger.logWarning("QposReader", "onRequestSetAmount");

        if (qposReader != null) {
            double finalAmount = qposReader.getFinalAmount();
            if (!qposReader.cancelOperation) {
                if (qposReader.srPagoListener != null)
                    qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_SET_AMOUNT);
                if (qposReader.qposService != null)
                    qposReader.qposService.setAmount(String.format(Locale.US, Definitions._2f(), finalAmount).replace(".", "").replace(",", ""), Definitions.EMPTY(), Definitions._484(), QPOSService.TransactionType.GOODS);
                Logger.logMessage(Definitions.SR_PAGO(), qposReader.context.getResources().getString(R.string.sr_pago_payment_step_3));
            }
        }
    }

    protected void onRequestSelectEmvApp(ArrayList<String> appList) {
    }

    protected void onRequestIsServerConnected() {
        Logger.logWarning("QposReader", "onRequestIsServerConnected");

        if (qposReader != null) {
            if (qposReader.srPagoListener != null) {
                qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_CHECK_SERVER_CONNECTIVITY);
            }
            if (qposReader.qposService != null) {
                qposReader.qposService.isServerConnected(true);
            }
        }
    }

    protected void onRequestFinalConfirm() {
        Logger.logWarning("QposReader", "onRequestFinalConfirm");

        if (qposReader != null) {
            if (!qposReader.cancelOperation) {
                if (qposReader.srPagoListener != null) {
                    qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_FINAL_CONFIRM);
                }
                if (qposReader.qposService != null) {
                    qposReader.qposService.finalConfirm(true);
                }
            }
        }
    }

    protected void onRequestOnlineProcess(String tlv) {
        Logger.logWarning("QposReader", "onRequestOnlineProcess: tlv -> " + tlv);

        if (qposReader != null) {
            if (qposReader.srPagoListener != null) {
                qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_ONLINE_PROCESS);
            }
            qposReader.useCard = qposReader.handleChip(tlv);
            if (qposReader.operation.getCard().getType().equals(Definitions.AMEX())) {
                qposReader.setAmexPopUp();
            } else {
                Logger.logMessage(Definitions.SR_PAGO(), qposReader.context.getResources().getString(R.string.sr_pago_payment_step_5));
                if (tlv.length() > 0) {
                    qposReader.qposService.sendOnlineProcessResult(Definitions.ONLINE_PROCESS());
                    if (qposReader.operation.getCard().getMsrFlag().equals(Definitions.TRUE())) {
                        qposReader.operation.getCard().setData(tlv);
                    }
                }
            }
        }
    }

    protected void onRequestTime() {
        Logger.logWarning("QposReader", "onRequestTime");

        if (qposReader != null) {
            Calendar c = Calendar.getInstance();
            String seconds = qposReader.getFormattedNumber(c.get(Calendar.SECOND));
            String minutes = qposReader.getFormattedNumber(c.get(Calendar.MINUTE));
            String hour = qposReader.getFormattedNumber(c.get(Calendar.HOUR_OF_DAY));
            String year = qposReader.getFormattedYear(c.get(Calendar.YEAR));
            String month = qposReader.getFormattedNumber(c.get(Calendar.MONTH) + 1);
            String day = qposReader.getFormattedNumber(c.get(Calendar.DAY_OF_MONTH));

            final String finalTime = String.format(Definitions._6s(), year, month, day, hour, minutes, seconds);
            String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(Calendar.getInstance().getTime());

            Logger.logMessage("Terminaltime", terminalTime);
            Logger.logMessage(Definitions.FINAL_TIME(), finalTime);
            if (!qposReader.cancelOperation) {
                if (qposReader.srPagoListener != null) {
                    qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_TERMINAL_TIME);
                    PaymentService.getServerTime(qposReader.context, new ServerTimeListener() {
                        @Override
                        public void onSuccess(Object time) {
                            qposReader.qposService.sendTime(finalTime);
                        }

                        @Override
                        public void onError(SrPagoDefinitions.Error error) {
                            qposReader.qposService.sendTime(finalTime);
                        }

                        @Override
                        public void onError(String error, SrPagoDefinitions.Error errorCode) {
                            qposReader.qposService.sendTime(finalTime);
                        }
                    });

                }

            }
            Logger.logMessage(Definitions.SR_PAGO(), qposReader.context.getResources().getString(R.string.sr_pago_payment_step_4));
        }
    }

    protected void onRequestTransactionResult(QPOSService.TransactionResult transactionResult) {
        Logger.logWarning("QposReader", "onRequestTransactionResult: transactionResult ->" + transactionResult);

        if (qposReader != null && qposReader.srPagoListener != null) {

            qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_RETURN_TRANSACTION_RESULT);

            switch (transactionResult) {
                case APPROVED:
                    if (qposReader.operation.getCard().getType().equals(Definitions.AMEX())) {
                        openSignature();
                    }
                    break;

                case TERMINATED:
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }

                    if (qposReader.srPagoListener != null) {
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.TERMINATED);
                    }
                    break;

                case DECLINED:

                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }

                    if (qposReader.srPagoListener != null) {
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CARD_DECLINED);
                    }

                    /*  CODIGO PARA HACER DEVOLUCION AMEX BORRAR
                    if (qposReader.operation.getCard().getType().equals(Definitions.AMEX())) {

                        qposReader.isReading = false;
                        OperationsServices.cancelOperation(qposReader.context, new CancelOperationListener() {
                            @Override
                            public void onSuccess() {
                                if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                                    qposReader.progressDialog.dismiss();
                                }
                                if (qposReader.srPagoListener != null) {
                                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CARD_DECLINED);
                                }
                                qposReader.isReading = false;
                            }

                            @Override
                            public void onError(SrPagoDefinitions.Error error) {
                                if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                                    qposReader.progressDialog.dismiss();
                                }
                                if (qposReader.srPagoListener != null) {
                                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.UNKNOWN);
                                }
                                qposReader.isReading = false;
                            }

                            @Override
                            public void onError(String error, SrPagoDefinitions.Error errorCode) {
                                if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                                    qposReader.progressDialog.dismiss();
                                }
                                if (qposReader.srPagoListener != null) {
                                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.UNKNOWN);
                                }
                                qposReader.isReading = false;
                            }
                        }, Global.getStringKey(qposReader.context, Definitions.KEY_EXT_TRANSACTION));

                    }*/
                    break;

                case NOT_ICC:
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CARD_REMOVED);
                    break;

                case CANCEL:
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.TRANSACTION_INTERRUPTED);
                    break;

                default:
                    if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                        qposReader.progressDialog.dismiss();
                    }
                    if (qposReader.srPagoListener != null) {
                        qposReader.srPagoListener.onError(SrPagoDefinitions.Error.READER_UNKNOWN);
                    }
                    break;
            }
        }
    }

    protected void onRequestTransactionLog(String tlv) {
    }

    protected void onRequestBatchData(String tlv) {
        Logger.logWarning("QposReader", "onRequestBatchData: tlv ->" + tlv);

        if (qposReader != null) {
            qposReader.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_RETURN_BATCH_DATA);
            if (!qposReader.operation.getCard().getType().equals(Definitions.AMEX())) {
                if (qposReader.operation.getCard().getEmvFlag().equals(Definitions.TRUE())) {
                    if (qposReader.useCard) {
                        qposReader.setMonthsPopUp();
                    } else {
                        qposReader.isReading = false;
                        if (qposReader.progressDialog != null && qposReader.progressDialog.isShowing()) {
                            qposReader.progressDialog.dismiss();
                        }
                    }
                }
            }
        }

    }

    protected void onRequestQposConnected() {
    }

    protected void onRequestQposDisconnected() {
    }

    protected void onRequestNoQposDetected() {
    }

    protected void onRequestNoQposDetectedUnbond() {
    }

    protected void onError(QPOSService.Error error) {
        Logger.logWarning("QposReader", "onError: " + error.name());

        if (qposReader != null && qposReader.srPagoListener != null) {
            qposReader.isReading = false;

            switch (error) {
                case TIMEOUT:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.TIMEOUT);
                    break;
                case INPUT_INVALID_FORMAT:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_INVALID_FORMAT);
                    break;
                case INPUT_OUT_OF_RANGE:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_OUT_OF_RANGE);
                    break;
                case CMD_NOT_AVAILABLE:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CMD_NOT_AVAILABLE);
                    break;
                case INPUT_INVALID:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_INVALID);
                    break;
                case DEVICE_BUSY:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.DEVICE_BUSY);
                    break;
                case COMM_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.COMM_ERROR);
                    break;
                case CRC_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CRC_ERROR);
                    break;
                case UNKNOWN:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.UNKNOWN);
                    break;
                case MAC_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.MAC_ERROR);
                    break;
                case APDU_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.APDU_ERROR);
                    break;
                case CMD_TIMEOUT:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CMD_TIMEOUT);
                    break;
                case DEVICE_RESET:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.DEVICE_RESET);
                    break;
                case WR_DATA_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.WR_DATA_ERROR);
                    break;
                case EMV_APP_CFG_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.EMV_APP_CFG_ERROR);
                    break;
                case INPUT_ZERO_VALUES:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.INPUT_ZERO_VALUES);
                    break;
                case EMV_CAPK_CFG_ERROR:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.EMV_CAPK_CFG_ERROR);
                    break;
                case ICC_ONLINE_TIMEOUT:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.ICC_ONLINE_TIMEOUT);
                    break;
                case AMOUNT_OUT_OF_LIMIT:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.AMOUNT_OUT_OF_LIMIT);
                    break;
                case CASHBACK_NOT_SUPPORTED:
                    qposReader.srPagoListener.onError(SrPagoDefinitions.Error.CASHBACK_NOT_SUPPORTED);
                    break;
            }
        }
    }

    protected void onRequestDisplay(QPOSService.Display display) {
    }

    protected void onReturnReversalData(String tlv) {
    }

    protected void onReturnGetPinResult(Hashtable<String, String> result) {
    }

    protected void onReturnPowerOnIccResult(boolean result, String ksn, String atr, int atrLen) {
    }

    protected void onReturnPowerOffIccResult(boolean result) {
    }

    protected void onReturnApduResult(boolean isSuccess, String apdu, int apduLen) {
    }

    protected void onReturnSetSleepTimeResult(boolean isSuccess) {
    }

    protected void onGetCardNoResult(String result) {
    }

    protected void onRequestSignatureResult(byte[] signature) {
    }

    protected void onRequestCalculateMac(String calMac) {
    }

    protected void onRequestUpdateWorkKeyResult(QPOSService.UpdateInformationResult updateInformationResult) {
    }

    protected void onReturnCustomConfigResult(boolean isSuccess, String result) {
    }

    protected void onRequestSetPin() {
    }

    protected void onReturnSetMasterKeyResult(boolean isSuccess) {
    }

    protected void onRequestUpdateKey(String result) {
    }

    protected void onReturnUpdateIPEKResult(boolean b) {
    }

    protected void onReturnRSAResult(String s) {
    }

    protected void onReturnUpdateEMVResult(boolean isSuccess) {
    }

    protected void onReturnGetQuickEmvResult(boolean isSuccess) {
    }

    protected void onReturnGetEMVListResult(String s) {
    }

    protected void onReturnUpdateEMVRIDResult(boolean isSuccess) {
    }

    protected void onDeviceFound(BluetoothDevice bluetoothDevice) {
    }

    protected void onReturnBatchSendAPDUResult(LinkedHashMap<Integer, String> batchAPDUResult) {
    }

    protected void onBluetoothBonding() {
    }

    protected void onBluetoothBonded() {
    }

    protected void onWaitingforData(String s) {
    }

    protected void onBluetoothBondFailed() {
    }

    protected void onBluetoothBondTimeout() {
    }

    protected void onReturniccCashBack(Hashtable<String, String> result) {
    }

    protected void onLcdShowCustomDisplay(boolean showCustomDisplay) {
    }

    protected void onUpdatePosFirmwareResult(QPOSService.UpdateInformationResult result) {
    }

    protected void onBluetoothBoardStateResult(boolean b) {
    }

    protected void onReturnDownloadRsaPublicKey(HashMap<String, String> map) {
    }

    protected void onGetPosComm(int i, String s, String s1) {
    }

    protected void onUpdateMasterKeyResult(boolean result, Hashtable<String, String> resultTable) {
    }

    protected void onPinKey_TDES_Result(String result) {
    }

    protected void onEmvICCExceptionData(String tlv) {
    }

    protected void onSetParamsResult(boolean b, Hashtable<String, Object> hashtable) {
    }

    protected void onGetInputAmountResult(boolean b, String s) {
    }

    protected void onReturnNFCApduResult(boolean b, String s, int i) {
    }

    protected void onReturnPowerOnNFCResult(boolean b, String s, String s1, int i) {
    }

    protected void onReturnPowerOffNFCResult(boolean b) {
    }

    protected void onCbcMacResult(String s) {
    }

    protected void onReadBusinessCardResult(boolean b, String s) {
    }

    protected void onWriteBusinessCardResult(boolean b) {
    }

    protected void onConfirmAmountResult(boolean b) {
    }

    protected void onSetManagementKey(boolean b) {
    }

    protected void onSetSleepModeTime(boolean isSuccess) {
    }

    protected void onGetSleepModeTime(String s) {
    }

    protected void onGetShutDownTime(String s) {
    }

    protected void onEncryptData(String s) {
    }

    protected void onAddKey(boolean b) {
    }

    protected void onSetBuzzerResult(boolean b) {
    }

    protected void onSetBuzzerTimeResult(boolean b) {
    }

    protected void onSetBuzzerStatusResult(boolean b) {
    }

    protected void onGetBuzzerStatusResult(String s) {
    }

    protected void onQposDoTradeLog(boolean b) {
    }

    protected void onQposDoGetTradeLogNum(String s) {
    }

    protected void onQposDoGetTradeLog(String s, String s1) {
    }

    static class MyQposControllerListener implements QPOSService.QPOSServiceListener {

        private static final String TAG = "MyQposControllerListener";

        @Override
        public void onRequestWaitingUser() {
            Logger.logWarning(TAG, "onRequestWaitingUser");
            if (qposReader != null) qposReader.onRequestWaitingUser();
        }

        @Override
        public void onQposIdResult(Hashtable<String, String> posIdTable) {
            Logger.logWarning(TAG, "onQposIdResult");
            if (qposReader != null) qposReader.onQposIdResult(posIdTable);
        }

        @Override
        public void onQposKsnResult(Hashtable<String, String> ksnResult) {
            Logger.logWarning(TAG, "onQposKsnResult");
            if (qposReader != null) qposReader.onQposKsnResult(ksnResult);
        }

        @Override
        public void onQposIsCardExist(boolean isCardExists) {
            Logger.logWarning(TAG, "onQposIsCardExist");
            if (qposReader != null) qposReader.onQposIsCardExist(isCardExists);
        }

        @Override
        public void onRequestDeviceScanFinished() {
            Logger.logWarning(TAG, "onRequestDeviceScanFinished");
            if (qposReader != null) qposReader.onRequestDeviceScanFinished();
        }

        @Override
        public void onQposInfoResult(Hashtable<String, String> posIdTable) {
            Logger.logWarning(TAG, "onQposInfoResult");
            if (qposReader != null) qposReader.onQposInfoResult(posIdTable);
        }

        @Override
        public void onQposGenerateSessionKeysResult(Hashtable<String, String> sessionResults) {
            Logger.logWarning(TAG, "onQposGenerateSessionKeysResult");
            if (qposReader != null) qposReader.onQposGenerateSessionKeysResult(sessionResults);
        }

        @Override
        public void onQposDoSetRsaPublicKey(boolean isRsaPublic) {
            Logger.logWarning(TAG, "onQposDoSetRsaPublicKey");
            if (qposReader != null) qposReader.onQposDoSetRsaPublicKey(isRsaPublic);
        }

        @Override
        public void onDoTradeResult(QPOSService.DoTradeResult doTradeResult, Hashtable<String, String> decodeData) {
            Logger.logWarning(TAG, "onDoTradeResult");
            if (qposReader != null) qposReader.onDoTradeResult(doTradeResult, decodeData);
        }

        @Override
        public void onSearchMifareCardResult(Hashtable<String, String> mifareResult) {
            Logger.logWarning(TAG, "onSearchMifareCardResult");
            if (qposReader != null) qposReader.onSearchMifareCardResult(mifareResult);
        }

        @Override
        public void onFinishMifareCardResult(boolean isVerified) {
            Logger.logWarning(TAG, "onFinishMifareCardResult");
            if (qposReader != null) qposReader.onFinishMifareCardResult(isVerified);
        }

        @Override
        public void onVerifyMifareCardResult(boolean isVerified) {
            Logger.logWarning(TAG, "onVerifyMifareCardResult");
            if (qposReader != null) qposReader.onVerifyMifareCardResult(isVerified);
        }

        @Override
        public void onReadMifareCardResult(Hashtable<String, String> mifareResult) {
            Logger.logWarning(TAG, "onReadMifareCardResult");
            if (qposReader != null) qposReader.onReadMifareCardResult(mifareResult);
        }

        @Override
        public void onWriteMifareCardResult(boolean isWriteSuccess) {
            Logger.logWarning(TAG, "onWriteMifareCardResult");
            if (qposReader != null) qposReader.onWriteMifareCardResult(isWriteSuccess);
        }

        @Override
        public void onOperateMifareCardResult(Hashtable<String, String> mifareData) {
            Logger.logWarning(TAG, "onOperateMifareCardResult");
            if (qposReader != null) qposReader.onOperateMifareCardResult(mifareData);
        }

        @Override
        public void getMifareCardVersion(Hashtable<String, String> mifareData) {
            Logger.logWarning(TAG, "getMifareCardVersion");
            if (qposReader != null) qposReader.getMifareCardVersion(mifareData);
        }

        @Override
        public void getMifareReadData(Hashtable<String, String> mifareData) {
            Logger.logWarning(TAG, "getMifareReadData");
            if (qposReader != null) qposReader.getMifareReadData(mifareData);
        }

        @Override
        public void getMifareFastReadData(Hashtable<String, String> mifareData) {
            Logger.logWarning(TAG, "getMifareFastReadData");
            if (qposReader != null) qposReader.getMifareFastReadData(mifareData);
        }

        @Override
        public void writeMifareULData(String mifareUlData) {
            Logger.logWarning(TAG, "writeMifareULData");
            if (qposReader != null) qposReader.writeMifareULData(mifareUlData);
        }

        @Override
        public void verifyMifareULData(Hashtable<String, String> mifareData) {
            Logger.logWarning(TAG, "verifyMifareULData");
            if (qposReader != null) qposReader.verifyMifareULData(mifareData);
        }

        @Override
        public void transferMifareData(String mifareData) {
            Logger.logWarning(TAG, "transferMifareData");
            if (qposReader != null) qposReader.transferMifareData(mifareData);
        }

        @Override
        public void onRequestSetAmount() {
            Logger.logWarning(TAG, "onRequestSetAmount");
            if (qposReader != null) qposReader.onRequestSetAmount();
        }

        @Override
        public void onRequestSelectEmvApp(ArrayList<String> appList) {
            Logger.logWarning(TAG, "onRequestSelectEmvApp");
            if (qposReader != null) qposReader.onRequestSelectEmvApp(appList);
        }

        @Override
        public void onRequestIsServerConnected() {
            Logger.logWarning(TAG, "onRequestIsServerConnected");
            if (qposReader != null) qposReader.onRequestIsServerConnected();
        }

        @Override
        public void onRequestFinalConfirm() {
            Logger.logWarning(TAG, "onRequestFinalConfirm");
            if (qposReader != null) qposReader.onRequestFinalConfirm();
        }

        @Override
        public void onRequestOnlineProcess(String tlv) {
            Logger.logWarning(TAG, "onRequestOnlineProcess");
            if (qposReader != null) qposReader.onRequestOnlineProcess(tlv);
        }

        @Override
        public void onRequestTime() {
            Logger.logWarning(TAG, "onRequestTime");
            if (qposReader != null) qposReader.onRequestTime();
        }

        @Override
        public void onRequestTransactionResult(QPOSService.TransactionResult transactionResult) {
            Logger.logWarning(TAG, "onRequestTransactionResult");
            if (qposReader != null) qposReader.onRequestTransactionResult(transactionResult);
        }

        @Override
        public void onRequestTransactionLog(String tlv) {
            Logger.logWarning(TAG, "onRequestTransactionLog");
            if (qposReader != null) qposReader.onRequestTransactionLog(tlv);
        }

        @Override
        public void onRequestBatchData(String tlv) {
            Logger.logWarning(TAG, "onRequestBatchData");
            if (qposReader != null) qposReader.onRequestBatchData(tlv);
        }

        @Override
        public void onRequestQposConnected() {
            Logger.logWarning(TAG, "onRequestQposConnected");
            if (qposReader != null) qposReader.onRequestQposConnected();
        }

        @Override
        public void onRequestQposDisconnected() {
            Logger.logWarning(TAG, "onRequestQposDisconnected");
            if (qposReader != null) qposReader.onRequestQposDisconnected();
        }

        @Override
        public void onRequestNoQposDetected() {
            Logger.logWarning(TAG, "onRequestNoQposDetected");
            if (qposReader != null) qposReader.onRequestNoQposDetected();
        }

        @Override
        public void onRequestNoQposDetectedUnbond() {
            Logger.logWarning(TAG, "onRequestNoQposDetectedUnbond");
            if (qposReader != null) qposReader.onRequestNoQposDetectedUnbond();
        }

        @Override
        public void onError(QPOSService.Error error) {
            Logger.logWarning(TAG, "onError");
            if (qposReader != null) qposReader.onError(error);
        }

        @Override
        public void onRequestDisplay(QPOSService.Display display) {
            Logger.logWarning(TAG, "onRequestDisplay");
            if (qposReader != null) qposReader.onRequestDisplay(display);
        }

        @Override
        public void onReturnReversalData(String tlv) {
            Logger.logWarning(TAG, "onReturnReversalData");
            if (qposReader != null) qposReader.onReturnReversalData(tlv);
        }

        @Override
        public void onReturnGetPinResult(Hashtable<String, String> result) {
            Logger.logWarning(TAG, "onReturnGetPinResult");
            if (qposReader != null) qposReader.onReturnGetPinResult(result);
        }

        @Override
        public void onReturnPowerOnIccResult(boolean result, String ksn, String atr, int atrLen) {
            Logger.logWarning(TAG, "onReturnPowerOnIccResult");
            if (qposReader != null) qposReader.onReturnPowerOnIccResult(result, ksn, atr, atrLen);
        }

        @Override
        public void onReturnPowerOffIccResult(boolean result) {
            Logger.logWarning(TAG, "onReturnPowerOffIccResult");
            if (qposReader != null) qposReader.onReturnPowerOffIccResult(result);
        }

        @Override
        public void onReturnApduResult(boolean isSuccess, String apdu, int apduLen) {
            Logger.logWarning(TAG, "onReturnApduResult");
            if (qposReader != null) qposReader.onReturnApduResult(isSuccess, apdu, apduLen);
        }

        @Override
        public void onReturnSetSleepTimeResult(boolean isSuccess) {
            Logger.logWarning(TAG, "onReturnSetSleepTimeResult");
            if (qposReader != null) qposReader.onReturnSetSleepTimeResult(isSuccess);
        }

        @Override
        public void onGetCardNoResult(String result) {
            Logger.logWarning(TAG, "onGetCardNoResult");
            if (qposReader != null) qposReader.onGetCardNoResult(result);
        }

        @Override
        public void onRequestSignatureResult(byte[] signature) {
            Logger.logWarning(TAG, "onRequestSignatureResult");
            if (qposReader != null) qposReader.onRequestSignatureResult(signature);
        }

        @Override
        public void onRequestCalculateMac(String calMac) {
            Logger.logWarning(TAG, "onRequestCalculateMac");
            if (qposReader != null) qposReader.onRequestCalculateMac(calMac);
        }

        @Override
        public void onRequestUpdateWorkKeyResult(QPOSService.UpdateInformationResult updateInformationResult) {
            Logger.logWarning(TAG, "onRequestUpdateWorkKeyResult");
            if (qposReader != null)
                qposReader.onRequestUpdateWorkKeyResult(updateInformationResult);
        }

        @Override
        public void onReturnCustomConfigResult(boolean isSuccess, String result) {
            Logger.logWarning(TAG, "onReturnCustomConfigResult");
            if (qposReader != null) qposReader.onReturnCustomConfigResult(isSuccess, result);
        }

        @Override
        public void onRequestSetPin() {
            Logger.logWarning(TAG, "onRequestSetPin");
            if (qposReader != null) qposReader.onRequestSetPin();
        }

        @Override
        public void onReturnSetMasterKeyResult(boolean isSuccess) {
            Logger.logWarning(TAG, "onReturnSetMasterKeyResult");
            if (qposReader != null) qposReader.onReturnSetMasterKeyResult(isSuccess);
        }

        @Override
        public void onRequestUpdateKey(String result) {
            Logger.logWarning(TAG, "onRequestUpdateKey");
            if (qposReader != null) qposReader.onRequestUpdateKey(result);
        }

        @Override
        public void onReturnUpdateIPEKResult(boolean b) {
            Logger.logWarning(TAG, "onReturnUpdateIPEKResult");
            if (qposReader != null) qposReader.onReturnUpdateIPEKResult(b);
        }

        @Override
        public void onReturnRSAResult(String s) {
            Logger.logWarning(TAG, "onReturnRSAResult");
            if (qposReader != null) qposReader.onReturnRSAResult(s);
        }

        @Override
        public void onReturnUpdateEMVResult(boolean isSuccess) {
            Logger.logWarning(TAG, "onReturnUpdateEMVResult");
            if (qposReader != null) qposReader.onReturnUpdateEMVResult(isSuccess);
        }

        @Override
        public void onReturnGetQuickEmvResult(boolean isSuccess) {
            Logger.logWarning(TAG, "onReturnGetQuickEmvResult");
            if (qposReader != null) qposReader.onReturnGetQuickEmvResult(isSuccess);
        }

        @Override
        public void onReturnGetEMVListResult(String s) {
            Logger.logWarning(TAG, "onReturnGetEMVListResult");
            if (qposReader != null) qposReader.onReturnGetEMVListResult(s);
        }

        @Override
        public void onReturnUpdateEMVRIDResult(boolean isSuccess) {
            Logger.logWarning(TAG, "onReturnUpdateEMVRIDResult");
            if (qposReader != null) qposReader.onReturnUpdateEMVRIDResult(isSuccess);
        }

        @Override
        public void onDeviceFound(BluetoothDevice bluetoothDevice) {
            Logger.logWarning(TAG, "onDeviceFound");
            if (qposReader != null) qposReader.onDeviceFound(bluetoothDevice);
        }

        @Override
        public void onReturnBatchSendAPDUResult(LinkedHashMap<Integer, String> batchAPDUResult) {
            Logger.logWarning(TAG, "onReturnBatchSendAPDUResult");
            if (qposReader != null) qposReader.onReturnBatchSendAPDUResult(batchAPDUResult);
        }

        @Override
        public void onBluetoothBonding() {
            Logger.logWarning(TAG, "onBluetoothBonding");
            if (qposReader != null) qposReader.onBluetoothBonding();
        }

        @Override
        public void onBluetoothBonded() {
            Logger.logWarning(TAG, "onBluetoothBonded");
            if (qposReader != null) qposReader.onBluetoothBonded();
        }

        @Override
        public void onWaitingforData(String s) {
            Logger.logWarning(TAG, "onWaitingforData");
            if (qposReader != null) qposReader.onWaitingforData(s);
        }

        @Override
        public void onBluetoothBondFailed() {
            Logger.logWarning(TAG, "onBluetoothBondFailed");
            if (qposReader != null) qposReader.onBluetoothBondFailed();
        }

        @Override
        public void onBluetoothBondTimeout() {
            Logger.logWarning(TAG, "onBluetoothBondTimeout");
            if (qposReader != null) qposReader.onBluetoothBondTimeout();
        }

        @Override
        public void onReturniccCashBack(Hashtable<String, String> result) {
            Logger.logWarning(TAG, "onReturniccCashBack");
            if (qposReader != null) qposReader.onReturniccCashBack(result);
        }

        @Override
        public void onLcdShowCustomDisplay(boolean showCustomDisplay) {
            Logger.logWarning(TAG, "onLcdShowCustomDisplay");
            if (qposReader != null) qposReader.onLcdShowCustomDisplay(showCustomDisplay);
        }

        @Override
        public void onUpdatePosFirmwareResult(QPOSService.UpdateInformationResult result) {
            Logger.logWarning(TAG, "onUpdatePosFirmwareResult");
            if (qposReader != null) qposReader.onUpdatePosFirmwareResult(result);
        }

        @Override
        public void onBluetoothBoardStateResult(boolean b) {
            Logger.logWarning(TAG, "onBluetoothBoardStateResult");
            if (qposReader != null) qposReader.onBluetoothBoardStateResult(b);
        }

        @Override
        public void onReturnDownloadRsaPublicKey(HashMap<String, String> map) {
            Logger.logWarning(TAG, "onReturnDownloadRsaPublicKey");
            if (qposReader != null) qposReader.onReturnDownloadRsaPublicKey(map);
        }

        @Override
        public void onGetPosComm(int i, String s, String s1) {
            Logger.logWarning(TAG, "onGetPosComm");
            if (qposReader != null) qposReader.onGetPosComm(i, s, s1);
        }

        @Override
        public void onUpdateMasterKeyResult(boolean result, Hashtable<String, String> resultTable) {
            Logger.logWarning(TAG, "onUpdateMasterKeyResult");
            if (qposReader != null) qposReader.onUpdateMasterKeyResult(result, resultTable);
        }

        @Override
        public void onPinKey_TDES_Result(String result) {
            Logger.logWarning(TAG, "onPinKey_TDES_Result");
            if (qposReader != null) qposReader.onPinKey_TDES_Result(result);
        }

        @Override
        public void onEmvICCExceptionData(String tlv) {
            Logger.logWarning(TAG, "onEmvICCExceptionData");
            if (qposReader != null) qposReader.onEmvICCExceptionData(tlv);
        }

        @Override
        public void onSetParamsResult(boolean b, Hashtable<String, Object> hashtable) {
            Logger.logWarning(TAG, "onSetParamsResult");
            if (qposReader != null) qposReader.onSetParamsResult(b, hashtable);
        }

        @Override
        public void onGetInputAmountResult(boolean b, String s) {
            Logger.logWarning(TAG, "onGetInputAmountResult");
            if (qposReader != null) qposReader.onGetInputAmountResult(b, s);
        }

        @Override
        public void onReturnNFCApduResult(boolean b, String s, int i) {
            Logger.logWarning(TAG, "onReturnNFCApduResult");
            if (qposReader != null) qposReader.onReturnNFCApduResult(b, s, i);
        }

        @Override
        public void onReturnPowerOnNFCResult(boolean b, String s, String s1, int i) {
            Logger.logWarning(TAG, "onReturnPowerOnNFCResult");
            if (qposReader != null) qposReader.onReturnPowerOnNFCResult(b, s, s1, i);
        }

        @Override
        public void onReturnPowerOffNFCResult(boolean isSuccess) {
            Logger.logWarning(TAG, "onReturnPowerOffNFCResult");
            if (qposReader != null) qposReader.onReturnPowerOffNFCResult(isSuccess);
        }

        @Override
        public void onCbcMacResult(String s) {
            Logger.logWarning(TAG, "onCbcMacResult");
            if (qposReader != null) qposReader.onCbcMacResult(s);
        }

        @Override
        public void onReadBusinessCardResult(boolean b, String s) {
            Logger.logWarning(TAG, "onReadBusinessCardResult");
            if (qposReader != null) qposReader.onReadBusinessCardResult(b, s);
        }

        @Override
        public void onWriteBusinessCardResult(boolean b) {
            Logger.logWarning(TAG, "onWriteBusinessCardResult");
            if (qposReader != null) qposReader.onWriteBusinessCardResult(b);
        }

        @Override
        public void onConfirmAmountResult(boolean b) {
            Logger.logWarning(TAG, "onConfirmAmountResult");
            if (qposReader != null) qposReader.onConfirmAmountResult(b);
        }

        @Override
        public void onSetManagementKey(boolean b) {
            Logger.logWarning(TAG, "onSetManagementKey");
            if (qposReader != null) qposReader.onSetManagementKey(b);
        }

        @Override
        public void onSetSleepModeTime(boolean isSuccess) {
            Logger.logWarning(TAG, "onSetSleepModeTime");
            if (qposReader != null) qposReader.onSetSleepModeTime(isSuccess);
        }

        @Override
        public void onGetSleepModeTime(String s) {
            Logger.logWarning(TAG, "onGetSleepModeTime");
            if (qposReader != null) qposReader.onGetSleepModeTime(s);
        }

        @Override
        public void onGetShutDownTime(String s) {
            Logger.logWarning(TAG, "onGetShutDownTime");
            if (qposReader != null) qposReader.onGetShutDownTime(s);
        }

        @Override
        public void onEncryptData(String s) {
            Logger.logWarning(TAG, "onEncryptData");
            if (qposReader != null) qposReader.onEncryptData(s);
        }

        @Override
        public void onAddKey(boolean b) {
            Logger.logWarning(TAG, "onAddKey");
            if (qposReader != null) qposReader.onAddKey(b);
        }

        @Override
        public void onSetBuzzerResult(boolean b) {
            Logger.logWarning(TAG, "onSetBuzzerResult");
            if (qposReader != null) qposReader.onSetBuzzerResult(b);
        }

        @Override
        public void onSetBuzzerTimeResult(boolean b) {
            Logger.logWarning(TAG, "onSetBuzzerTimeResult");
            if (qposReader != null) qposReader.onSetBuzzerTimeResult(b);
        }

        @Override
        public void onSetBuzzerStatusResult(boolean b) {
            Logger.logWarning(TAG, "onSetBuzzerStatusResult");
            if (qposReader != null) qposReader.onSetBuzzerStatusResult(b);
        }

        @Override
        public void onGetBuzzerStatusResult(String s) {
            Logger.logWarning(TAG, "onGetBuzzerStatusResult");
            if (qposReader != null) qposReader.onGetBuzzerStatusResult(s);
        }

        @Override
        public void onQposDoTradeLog(boolean b) {
            Logger.logWarning(TAG, "onQposDoTradeLog");
            if (qposReader != null) qposReader.onQposDoTradeLog(b);
        }

        @Override
        public void onQposDoGetTradeLogNum(String s) {
            Logger.logWarning(TAG, "onQposDoGetTradeLogNum");
            if (qposReader != null) qposReader.onQposDoGetTradeLogNum(s);
        }

        @Override
        public void onQposDoGetTradeLog(String s, String s1) {
            Logger.logWarning(TAG, "onQposDoGetTradeLog");
            if (qposReader != null) qposReader.onQposDoGetTradeLog(s, s1);
        }
    }
}
