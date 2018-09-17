package sr.pago.sdk.readers;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bbpos.bbdevice.BBDeviceController;

import java.security.Key;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sr.pago.sdk.LocationUpdateService;
import sr.pago.sdk.R;
import sr.pago.sdk.SrPagoActivity;
import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.PixzelleNetworkHandler;
import sr.pago.sdk.connection.ServiceCoreTransactionV2;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.PaymentListener;
import sr.pago.sdk.interfaces.PaymentValidateDocumentListener;
import sr.pago.sdk.interfaces.SrPagoListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.Operation;
import sr.pago.sdk.object.PaymentPreferences;
import sr.pago.sdk.object.SPPaymentType;
import sr.pago.sdk.readers.bbpos.BbposBluetooth;
import sr.pago.sdk.readers.bbpos.BbposReader;
import sr.pago.sdk.readers.qpos.QposReader;
import sr.pago.sdk.utils.Logger;


/**
 * Created by Rodolfo on 12/05/2015.
 */
public abstract class BaseReader extends PixzelleNetworkHandler {

    //New Vars
    protected boolean isReaderConnected = false;
    protected String oldAPDU;
    protected
    boolean useCard = false;


    protected Context context;
    protected Operation operation;
    public boolean isReading = false;
    boolean monthChose = false;
    public boolean isInitApp = false;
    protected String cp = Definitions.EMPTY();
    protected String cv = Definitions.EMPTY();
    protected boolean chipCard;
    protected boolean batteryLow = false;
    protected ProgressDialog progressDialog;
    //Cancel payment
    protected boolean cancelOperation = false;
    public boolean popupIsOpen = false;
    protected String reference;
    protected String affiliation;
    protected double amount;
    //Min 0 - 100 Max
    protected double tip;
    public static boolean walkerConnected = false;
    protected String PIN = Definitions.EMPTY();
    private static boolean DEBUG = false;
    public static SrPagoDefinitions.Reader activateReader;
    public AlertDialog alertMonths;
    protected SrPagoListener srPagoListener;
    protected PaymentListener paymentListener;
    protected PaymentValidateDocumentListener paymentValidateDocumentListener;
    //Custom Loading
    protected boolean customLoading;
    protected boolean customBluetooth;
    protected boolean customBluetoothSelection = false;
    @Deprecated
    protected SrPagoActivity srPagoActivity;
    public HashMap<String, Object> aditionalData;


    @Deprecated
    public BaseReader(Context context, SrPagoActivity srPagoActivity) {
        super(context);
        DEBUG = Global.isInDebugMode(context);
        this.context = context;
        this.srPagoActivity = srPagoActivity;

        cp = Definitions.EMPTY();
        cv = Definitions.EMPTY();
    }

    public BaseReader(Context context) {
        super(context);

        DEBUG = Global.isInDebugMode(context);
        this.context = context;

        cp = Definitions.EMPTY();
        cv = Definitions.EMPTY();
    }

    public void updateSrPagoActivity(SrPagoActivity srPagoActivity) {
        this.context = srPagoActivity;
        this.srPagoActivity = srPagoActivity;
    }

    /**
     * Connection methods.
     */
    public abstract void initReader(Context context);

    public abstract void stopReader();

    public abstract void getDeviceInfo();

    public abstract boolean isConnected();

    public abstract void resetReader();

    public abstract void startTransaction();

    @Deprecated
    public SrPagoActivity getSrPagoActivity() {
        return srPagoActivity;
    }

    @Deprecated
    public void setSrPagoActivity(SrPagoActivity srPagoActivity) {
        this.srPagoActivity = srPagoActivity;
    }

    public SrPagoListener getSrPagoListener() {
        return srPagoListener;
    }

    public void setSrPagoListener(SrPagoListener srPagoListener) {
        this.srPagoListener = srPagoListener;
    }

    /**
     * Payment processing methods.
     */
    public abstract void makePayment(double amount,
                                     double tip,
                                     String reference,
                                     PaymentListener listener);

    public abstract void makePayment(double amount,
                                     double tip,
                                     String reference,
                                     String affiliation,
                                     PaymentListener listener);

    public void setAdditionalData(HashMap<String, Object> additionalData) {
        this.aditionalData = additionalData;
    }

    protected void preparePayment(final boolean isChip) {
        if (progressDialog != null)
            progressDialog.dismiss();
//        AlertDialog.Builder builder = new AlertDialog.Builder(srPagoActivity);
        sendPayment(isChip, paymentListener);
    }

    protected boolean handleChip(String tlv) {
        operation.getCard().setData(tlv);

        Hashtable<String, String> info = BBDeviceController.decodeTlv(tlv);

        String stringName = Definitions.EMPTY();

        try {
            stringName = hexToAscii(info.get(Definitions._5F20()));
            stringName = stringName.replace(Definitions.EOF(), Definitions.EMPTY());
            stringName = stringName.trim();
        } catch (Exception ex) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            this.isReading = false;
//            this.srPagoListener.onError(SrPagoDefinitions.Error.CARD_READ);
            stringName = Definitions.EMPTY();
        }

        if (info.get(Definitions._5F24()) != null && info.get(Definitions._5F24()).length() >= 4) {
            operation.getCard().setCardMonth(info.get(Definitions._5F24()).substring(2, 4));
            operation.getCard().setCardYear(info.get(Definitions._5F24()).substring(0, 2));
        } else if (info.get("59") != null && info.get("59").length() >= 4) {
            operation.getCard().setCardMonth(info.get("59").substring(2, 4));
            operation.getCard().setCardYear(info.get("59").substring(0, 2));
        }

        operation.getCard().setCardHolderName(stringName);

        try {
            operation.getCard().setCardNumber(info.get(Definitions.MASKED_PAN()));
        } catch (NullPointerException ex) {
            operation.getCard().setCardNumber(info.get("5A"));
        }
        operation.getCard().setEmvFlag(Definitions.TRUE());
        operation.getCard().setMsrFlag(Definitions.FALSE());
        operation.getCard().setMsr1(Definitions.EMPTY());
        operation.getCard().setMsr2(Definitions.EMPTY());
        operation.getCard().setMsr3(Definitions.EMPTY());
        operation.getCard().setAffiliation(this.affiliation);
        operation.setReference(this.reference);
        operation.setAmount(String.valueOf(this.amount));

        //TODO Listener for returning the number and cardholder name to the app
        //lblStatus.setText(String.format("%s %s", operation.getCard().getFormattedCardNumber(), operation.getCard().getCardHolderName()));

        return true;
    }

    protected void handleBand(Hashtable<String, String> info) {
        chipCard = false;
        operation.getCard().setCardHolderName(info.get(Definitions.CARD_HOLDER_NAME()).trim());
        operation.getCard().setCardMonth(info.get(Definitions.EXPIRY_DATE()).substring(2, 4));
        operation.getCard().setCardYear(info.get(Definitions.EXPIRY_DATE()).substring(0, 2));
        operation.getCard().setCardNumber(info.get(Definitions.MASKED_PAN()));
        operation.getCard().setEmvFlag(Definitions.FALSE());
        operation.getCard().setMsrFlag(Definitions.TRUE());

        if (this instanceof QposReader) {
            operation.getCard().setMsr1(info.get("trackksn"));
            operation.getCard().setMsr2(info.get(Definitions.ENC_TRACK_1()));
            operation.getCard().setMsr3(info.get(Definitions.ENC_TRACK_2()));

        } else if (this instanceof BbposReader) {
            operation.getCard().setMsr1(info.get(Definitions.KSN()));
            operation.getCard().setMsr2(info.get(Definitions.ENC_TRACK_1()));
            operation.getCard().setMsr3(info.get(Definitions.ENC_TRACK_2()));
        } else {
            operation.getCard().setMsr1(info.get(Definitions.KSN()));
            operation.getCard().setMsr2(info.get(Definitions.ENC_TRACK_1()));
            operation.getCard().setMsr3(info.get(Definitions.ENC_TRACK_2()));
        }
        operation.setReference(this.reference);
        operation.getCard().setAffiliation(this.affiliation);
        operation.setAmount(String.valueOf(this.amount));

        //TODO Listener for returning the number and cardholder name to the app
    }

    protected void sendPayment(boolean chip, final PaymentListener paymentListener) {
        this.getSrPagoListener().onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT_CARD);
        if (!cancelOperation) {
            Logger.logMessage(Definitions.SEND_PAYMENT(), Definitions.SENDING());
            ServiceCore serviceCoreTransaction = new ServiceCoreTransactionV2(context, getFinalAmount());
            Object[] data = {context, String.valueOf(getFinalAmount()), String.valueOf(amount * tip / 100), reference, reference, this.getOperation().getCard(), String.valueOf(months), aditionalData};

            serviceCoreTransaction.executeService(Definitions.PAYMENT_CARD, new WebServiceListener<SrPagoTransaction>() {
                @Override
                public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                    //paymentListener.onPaymentSuccess(response.getItems().get(0));
                }

                @Override
                public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                    //paymentListener.onPaymentError(response.getMessage());
                    paymentListener.onPaymentError(response.getError(), response.getMessage());

//                    paymentListener.onError(response.getMessage(), response.getError());
                }
            }, data, null);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            ((ServiceCoreTransactionV2) serviceCoreTransaction).setPaymentListener(paymentListener);
//            if (!isCustomLoading())
//                progressDialog = ProgressDialog.show(srPagoActivity, Definitions.EMPTY(), Definitions.PROCESSING_PAYMENT(), true);
        }

    }

    protected void sendDevolution(String transactionId) {
//        WebServiceConnection webServiceConnection = new WebServiceConnection(srPagoActivity, Definitions.OPERATION, PixzelleWebServiceConnection.GET);
//        webServiceConnection.setListener(this);
//        String[] data = new String[1];
//        data[0] = transactionId;
//        webServiceConnection.setUrlParams(data);
//        webServiceConnection.execute(srPagoActivity, Definitions.EMPTY());

        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.OPERATION, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onResponseOk(code, response, webService);
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {

            }
        }, new Object[]{context, Definitions.EMPTY()}, new String[]{transactionId});
    }

    protected double getTipValue() {
        double tip;

        try {
            tip = this.tip;
        } catch (NumberFormatException ex) {
            tip = 0;
        }

        tip = tip / 100;

        double amount = this.amount;
        return amount * tip;
    }

    protected void clearChipBand() {
        this.amount = 0;
        this.tip = 0;
        this.reference = Definitions.EMPTY();
    }

    protected double getFinalAmount() {
        double tip;
        try {
            tip = this.tip;
        } catch (NumberFormatException ex) {
            tip = 0;
        }
        tip = tip / 100 + 1;
        double amount = this.amount;
        double amountWithTip = amount * tip;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("#.00");
        df.setDecimalFormatSymbols(symbols);
        double totalAmount = Double.valueOf(df.format(amountWithTip));
        return totalAmount;
    }

    protected double returnTip() {
        double tip;

        try {
            tip = this.tip;
        } catch (NumberFormatException ex) {
            tip = 0;
        }

        return tip;
    }

    public abstract void retryPayment();

    public abstract void cancelTransaction(SrPagoDefinitions.Status status);

    public abstract void devolutionWithId(String transactionId);


    /**
     * Months methods.
     */
    int months = 0;

    public void setMonthsPopUp() {
        Logger.logWarning("BaseReader", "setMonthsPopUp");
        if (validLocation()) {
            if (getFinalAmount() >= 0 && PaymentPreferences.getInstance(context).isMSIPossible()) {
                if (operation.getCard().getCardNumber() != null) {
                    if (operation.getCard().getCardNumber().length() > 10) {
                        if (!cancelOperation) {
                            ServiceCore serviceCore = new ServiceCore(context);
                            serviceCore.executeService(Definitions.SP_REQUEST_MONTHS, new WebServiceListener() {
                                @Override
                                public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                                    onResponseOk(code, response, webService);
                                }

                                @Override
                                public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                                    onResponseError(code, response, webService);
                                }
                            }, new Object[]{context, Definitions.EMPTY()}, new String[]{operation.getCard().getCardNumber().substring(0, 6), String.valueOf(getFinalAmount())});

//                            WebServiceConnection webServiceConnection = new WebServiceConnection(srPagoActivity, Definitions.SP_REQUEST_MONTHS, PixzelleWebServiceConnection.GET);
//                            webServiceConnection.setListener(this);
//                            webServiceConnection.setUrlParams(new String[]{operation.getCard().getCardNumber().substring(0, 6), String.valueOf(getFinalAmount())});
//                            webServiceConnection.execute(srPagoActivity, Definitions.EMPTY());
                        }
                    } else
                        this.srPagoListener.onError(SrPagoDefinitions.Error.CARD_READ);
                } else {
                    this.srPagoListener.onError(SrPagoDefinitions.Error.CARD_READ);
                }
            } else {
                preparePayment(false);
            }
        } else {
            paymentListener.onError("Tu cobro no fue procesado debido a un error en los datos de ubicación.\n" +
                    "Favor de verificar que se encuentren activos los servicios de ubicación.", SrPagoDefinitions.Error.UNKNOWN);
        }
        //pago
    }

    protected void setAmexPopUp() {
        Logger.logWarning("BaseReader", "setAmexPopUp");
        paymentListener.onAmexCvvStart();
        monthChose = false;
        final View container = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.view_amex_pop_up, null);
        final EditText txtCv = (EditText) container.findViewById(R.id.txt_cv);
        txtCv.getBackground().setColorFilter(context.getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        txtCv.setTextColor(context.getResources().getColor(android.R.color.black));

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context)
                .setMessage(Definitions.WRITE_CVV())
                .setView(container)
                .setPositiveButton(Definitions.ACCEPT(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            dialog.dismiss();
                            if (txtCv != null) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(txtCv.getWindowToken(), 0);
                            }
                            cp = encrypt(Definitions._00000().getBytes());
                            cv = encrypt(txtCv.getText().toString().getBytes());
                            operation.getCard().setCardSecurityCode(cv);
//                            if (getFinalAmount() >= 0 && PaymentPreferences.getInstance(context).isMSIPossible()) {
//                                setMonthsPopUp();
//                            } else {
//                                sendPayment(chipCard, paymentListener);
//                            }
                            paymentListener.onAmexCvvSuccess();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setMonthsPopUp();
                                }
                            }, 1400);

                            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_INIT_PERCENTAGE);
                        } catch (Exception ex) {
                            Logger.logError(ex);
                        }
                    }
                }).setNegativeButton(Definitions.CANCEL(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                        if (txtCv != null) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(txtCv.getWindowToken(), 0);
                        }
                        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_TRANSACTION_CANCELED);
                        dialog.dismiss();
                        dismissProgress();
                        paymentListener.onAmexCvvCancel();
                    }
                });

        if (!chipCard) {
            AlertDialog alertDialog = builder.show();

            final Button btnPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            btnPositive.setEnabled(false);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    btnPositive.setEnabled(txtCv.length() == 4);
                }
            };

            txtCv.addTextChangedListener(textWatcher);
        } else {
            try {
                cp = encrypt(Definitions._00000().getBytes());
                cv = encrypt(Definitions._000().getBytes());
                operation.getCard().setCardSecurityCode(cv);
                if (getFinalAmount() >= 0) {
                    setMonthsPopUp();
                } else {
                    sendPayment(chipCard, paymentListener);
                }
            } catch (Exception ex) {

            }
        }
    }

    public void activateMonths(int months) {
        this.months = months;
        preparePayment(false);
    }

    public void openPopupMoth(ArrayList<SPPaymentType> items) {
        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_MONTHS);
        paymentListener.onPaymentSelectMonths(items);
//        final View container = ((AppCompatActivity)context).getLayoutInflater().inflate(R.layout.view_months_pop_up, null);
//        alertMonths = new AlertDialog.Builder(context)
//                .setMessage(null)
//                .setView(container).show();
//        ListView listView = (ListView) container.findViewById(R.id.listview);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
//                    monthChose = true;
//                    months = Integer.parseInt(((PixzelleClass) parent.getAdapter().getItem(position)).getGuid());
//                    preparePayment(false);
//                } else {
//                    preparePayment(false);
//                }
//                alertMonths.dismiss();
//            }
//        });
//        ListViewAdapter adapter = new ListViewAdapter(context);
//        ArrayList<PixzelleClass> arrayData = new ArrayList<PixzelleClass>();
//        PixzelleClass item = new PixzelleClass();
//        item.setName("Pago de contado");
//        arrayData.add(item);
//        for (int i = 0; i < items.size(); i++) {
//            if (canPutMonth(items.get(i).getName())) {
//                item = new PixzelleClass();
//                item.setName((items.get(i)).getName() + " meses sin intereses");
//                item.setGuid((items.get(i)).getName());
//                arrayData.add(item);
//            }
//        }
//        adapter.setCollection(arrayData);
//        listView.setAdapter(adapter);
//        listView.setEnabled(true);
//        final TextView btnClose = (TextView) container.findViewById(R.id.btnCancel);
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertMonths.dismiss();
//                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_CANCEL_TRANSACTION);
//            }
//        });
//        final Button btnPlazos = alertMonths.getButton(DialogInterface.BUTTON_NEGATIVE);
//        alertMonths.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        btnPlazos.setEnabled(false);
//        alertMonths.setCanceledOnTouchOutside(false);
//
//        Resources r = getContext().getResources();
//        float pxWith = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());
//        float pxHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
//        alertMonths.getWindow().setLayout((int) pxWith, (int) pxHeight);
//        alertMonths.show();

    }

    private boolean canPutMonth(String month) {

        if (month.contains("3")) {
            return PaymentPreferences.getInstance(context).is3MSIPossible();
        }

        if (month.contains("6")) {
            return PaymentPreferences.getInstance(context).is6MSIPossible();
        }

        if (month.contains("9")) {
            return PaymentPreferences.getInstance(context).is9MSIPossible();
        }
        if (month.contains("12")) {
            return PaymentPreferences.getInstance(context).is12MSIPossible();
        }

        return true;
    }

    /**
     * Auxiliary methods.
     */
    public boolean validLocation() {

        try {
            String longitud = LocationUpdateService.lastLocation.getLongitude() + "";
            String latitud = LocationUpdateService.lastLocation.getLatitude() + "";

            if (latitud.length() > 4 && longitud.length() > 4) {
                return true;
            }
            return false;
        } catch (NullPointerException ex) {
            //TODO Handle this ina better way for wpos.
            Logger.logWarning("BaseReader", "validLocation failed!");
            return true;
        }

    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected String hexToAscii(String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder(n / 2);
        for (int i = 0; i < n; i += 2) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            sb.append((char) ((hexToInt(a) << 4) | hexToInt(b)));
        }
        return sb.toString();
    }

    protected static int hexToInt(char ch) {
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        throw new IllegalArgumentException(String.valueOf(ch));
    }

    public void setPinPopUp() {
        monthChose = false;
        final View container = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.view_amex_pin, null);
        final EditText txtPin = (EditText) container.findViewById(R.id.edittext);
        txtPin.getBackground().setColorFilter(context.getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        txtPin.setTextColor(context.getResources().getColor(android.R.color.black));

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(Definitions.WRITE_PIN())
                .setView(container)
                .setPositiveButton(Definitions.ACCEPT(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            PIN = txtPin.getText().toString();
                        } catch (Exception ex) {

                        }
                    }
                }).setNegativeButton(Definitions.CANCEL(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                        dialog.dismiss();
                        dismissProgress();
                    }
                }).show();

        final Button btnPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositive.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnPositive.setEnabled(txtPin.length() == 4);
            }
        };

        txtPin.addTextChangedListener(textWatcher);
    }

    protected String encrypt(byte[] message) throws Exception {
        byte[] key;

        if (DEBUG) {
            key = Definitions.KEY_AMEX_DEBUG().getBytes(Definitions.UTF());
        } else {
            key = Definitions.KEY_AMEX_RELEASE().getBytes(Definitions.UTF());
        }

        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Definitions.DES_3());
        Key desKey = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(Definitions.DES());
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        final byte[] plainText = cipher.doFinal(message);

        String semiFinal = Base64.encodeToString(plainText, Base64.DEFAULT);
        String encoded = Uri.encode(semiFinal.trim());

        return encoded;
    }

    public abstract void handleEMV(String emv) throws Exception;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void onResponseOk(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        try {
            if (response.getStatus()) {
                if (webService == Definitions.PAYMENT) {
                    this.srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_CALL_PAYMENT);
                    preparePayment(false);
                    //validatePaymentDocuments(res);
                }
                if (webService == Definitions.SP_REQUEST_MONTHS) {
                    dismissProgress();
                    if (response.getItems().size() > 0) {
                        for (int index = 0; index < response.getItems().size(); index++) {
                            ((SPPaymentType) response.getItems().get(index)).setRaw(getFinalAmount());
                        }
                        openPopupMoth(response.getItems());
                    } else
                        preparePayment(false);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(Definitions.SR_PAGO());
                builder.setMessage(response.getMessage());
                builder.setPositiveButton(context.getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissProgress();
                    }
                }).setNegativeButton(context.getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissProgress();
                    }
                });
                builder.create().show();
            }
        } catch (Exception ex) {
            Logger.logError(ex);
        }
    }

    @Override
    public void onResponseError(@Pixzelle.SERVER_CODES int code, SPResponse<?> response, int webService) {
        Logger.logWarning(Definitions.ERROR(), response.getMessage());

//        if(response.getCode() == null){
//            response.setCode("");
//            response.setError(SrPagoDefinitions.Error.NO_INTERNET);
//        }
        paymentListener.onError(response.getMessage(), response.getError());

//        if (response.getCode().equals("NotAuthorizedAccountException")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle(Definitions.SR_PAGO());
//            builder.setMessage(response.getMessage());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dismissProgress();
//                }
//            });
//            builder.create().show();
//        } else {
//            paymentListener.onPaymentError(response.getMessage());
////            AlertDialog.Builder builder = new AlertDialog.Builder(context);
////            builder.setTitle(Definitions.SR_PAGO());
////            builder.setMessage(Definitions.SIGN_UNKNOWN_ERROR());
////            builder.setPositiveButton(context.getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    preparePayment(false);
////                }
////            }).setNegativeButton(context.getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    dismissProgress();
////                }
////            });
////            builder.create().show();
//        }
    }

    @Override
    public void onUnknownConnectionError(final int var1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Definitions.SR_PAGO());
        builder.setMessage(Definitions.SIGN_UNKNOWN_ERROR());
        builder.setPositiveButton(context.getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preparePayment(false);
            }
        }).setNegativeButton(context.getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissProgress();
            }
        });
        builder.create().show();
        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_CANCEL_TRANSACTION);
    }

    @Override
    public void onTimeoutConnection(final int var1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Definitions.SR_PAGO());
        builder.setMessage(Definitions.SIGN_UNKNOWN_ERROR());
        builder.setPositiveButton(context.getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preparePayment(false);
            }
        }).setNegativeButton(context.getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissProgress();
            }
        });
        builder.create().show();
        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_CANCEL_TRANSACTION);

    }

    @Override
    public void onNoInternetConnection(final int var1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Definitions.SR_PAGO());
        builder.setMessage(Definitions.SIGN_UNKNOWN_ERROR());
        builder.setPositiveButton(context.getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preparePayment(false);
            }
        }).setNegativeButton(context.getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissProgress();
            }
        });
        builder.create().show();
        srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.SP_CANCEL_TRANSACTION);
    }

    /**
     * Custom views methods.
     */
    public boolean isCustomLoading() {
        return customLoading;
    }

    public void setCustomLoading(boolean customLoading) {
        this.customLoading = customLoading;
    }


    public boolean isCustomBluetooth() {
        return customBluetooth;
    }

    public void setCustomBluetooth(boolean customBluetooth) {
        this.customBluetooth = customBluetooth;
    }

    public boolean isCustomBluetoothSelection() {
        return customBluetoothSelection;
    }

    public void setCustomBluetoothSelection(boolean customBluetoothSelection) {
        this.customBluetoothSelection = customBluetoothSelection;
    }
}
