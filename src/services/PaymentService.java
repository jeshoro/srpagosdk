package sr.pago.sdk.services;

import android.content.Context;
import android.content.Intent;

import sr.pago.sdk.LocationUpdateService;
import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.OnGetTicketsListener;
import sr.pago.sdk.interfaces.OnPaymentStoreListener;
import sr.pago.sdk.interfaces.OnRechargeListener;
import sr.pago.sdk.interfaces.PaymentValidateDocumentListener;
import sr.pago.sdk.interfaces.ServerTimeListener;
import sr.pago.sdk.interfaces.ServicesListener;
import sr.pago.sdk.interfaces.SrPagoCardListener;
import sr.pago.sdk.interfaces.StoreListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.model.Service;
import sr.pago.sdk.model.StoresMethod;
import sr.pago.sdk.model.Ticket;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.PixzelleClass;
import sr.pago.sdk.readers.BaseReader;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public class PaymentService {
    private static boolean timeout = false;

    public static void payFromMobile(final Context context, final PaymentListener paymentListener, final String amount, final double tip, final String reference, final boolean fromDevice) {
        //context.startService(new Intent(context, LocationUpdateService.class));
        /*AsyncTask asyncTask = new AsyncTask<Object, Void, Void>() {
            @Override
            protected Void doInBackground(Object... params) {
                Logger.logDebug("Status", LocationUpdateService.status + "");
                while (LocationUpdateService.status == -1) {
                    Logger.logDebug("WHILE", LocationUpdateService.status + "");
                }
                Logger.logDebug("Status", LocationUpdateService.status + "");

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Logger.logDebug("Status POST", LocationUpdateService.status + "");

                if (LocationUpdateService.status == LocationSettingsStatusCodes.SUCCESS) {
                    ServiceCore serviceCore = new ServiceCore(context);
                    Object[] data = new Object[4];
                    data[0] = context;
                    data[1] = amount.replaceAll("\\$", "").replaceAll(",", "");
                    data[2] = tip;
                    data[3] = reference;

                    if (!fromDevice) {
                        Global.clearDeviceInfo(context);
                    }

                    serviceCore.executeService(Definitions.PAYMENT, new WebServiceListener<SrPagoTransaction>() {
                        @Override
                        public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                            timeout = false;
                            context.stopService(new Intent(context, LocationUpdateService.class));
                            paymentListener.onPaymentSuccess(response.getItems().get(0), null);
                        }

                        @Override
                        public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                            timeout = false;
                            context.stopService(new Intent(context, LocationUpdateService.class));
                            paymentListener.onError(response.getMessage(), response.getError());
                        }
                    }, data, null);
                } else {
                    timeout = false;
                    SPResponse response = new SPResponse();
                    response.setMessage("Parece que tú gps tiene problemas");
                    paymentListener.onError(response.getMessage(), response.getError());
                }
            }
        };

        asyncTask.execute();*/

        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[4];
        data[0] = context;
        data[1] = amount.replaceAll("\\$", "").replaceAll(",", "");
        data[2] = tip;
        data[3] = reference;

        if (!fromDevice) {
            Global.clearDeviceInfo(context);
        }

        serviceCore.executeService(Definitions.PAYMENT, new WebServiceListener<SrPagoTransaction>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                timeout = false;
                context.stopService(new Intent(context, LocationUpdateService.class));
                paymentListener.onPaymentSuccess(response.getItems().get(0), null);
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                timeout = false;
                context.stopService(new Intent(context, LocationUpdateService.class));
                paymentListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);

    }

    public static void payFromStore(Context context, final OnPaymentStoreListener onPaymentStoreListener, String email, String amount, String store, String phone, String token) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[5];
        data[0] = email == null ? "" : email;
        data[1] = amount;
        data[2] = store == null || store.equals("") ? "OXXO" : store;
        data[3] = phone == null ? "" : phone;
        data[4] = token;
        serviceCore.executeService(Definitions.PAYMENT_STORE, new WebServiceListener<SrPagoTransaction>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                onPaymentStoreListener.onSuccess(response.getItems().get(0).getToken());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPaymentStoreListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void sendSmsService(Context context, final OnPaymentStoreListener onPaymentStoreListener, String phone, String token) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[5];
        data[0] = phone == null ? "" : phone;
        data[1] = token;
        serviceCore.executeService(Definitions.PAYMENT_MOBILE, new WebServiceListener<SrPagoTransaction>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                onPaymentStoreListener.onSuccess(response.getItems().get(0).getToken());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPaymentStoreListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void sendOTP(Context context, final OnPaymentStoreListener onPaymentStoreListener, String otp, String token) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[5];
        data[0] = otp == null ? "" : otp;
        data[1] = token;
        serviceCore.executeService(Definitions.PAYMENT_MOBILE_CONFIRMATION, new WebServiceListener<SrPagoTransaction>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                onPaymentStoreListener.onSuccess(response.getItems().get(0).getToken());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPaymentStoreListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void callValidateDocumentPaymentService(Context context, final PaymentValidateDocumentListener paymentValidateDocumentListener, String transactionToken) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = transactionToken;

        serviceCore.executeService(Definitions.VALIDATE_DOCUMENTS_PAYMENT, new WebServiceListener<SPTransactionDocument>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SPTransactionDocument> response, int webService) {
                paymentValidateDocumentListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                paymentValidateDocumentListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void callUploadDocumentPaymentService(Context context, String transactionToken, String image, int type, final PaymentValidateDocumentListener paymentValidateDocumentListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[4];
        data[0] = context;
        data[1] = transactionToken;
        data[2] = image;
        data[3] = type;

        serviceCore.executeService(Definitions.UPLOAD_DOCUMENTS_PAYMENT, new WebServiceListener<SPTransactionDocument>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SPTransactionDocument> response, int webService) {
                paymentValidateDocumentListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                paymentValidateDocumentListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void getServerTime(Context context, final ServerTimeListener serverTimeListener) {
        int reader = 1;
        switch (BaseReader.activateReader) {
            case BBPOS_AUDIO_READER:
                reader = 1;
                break;
            case QPOS_AUDIO_READER:
                reader = 2;
                break;
            case QPOS_BLUETOOTH_READER:
                reader = 3;
                break;

        }
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = "" + reader;
        serviceCore.executeService(Definitions.GET_SERVER_TIME, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                serverTimeListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                serverTimeListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void recharge(Context context, final OnRechargeListener onRechargeListener, String number, String sku) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[2];
        data[0] = number;
        data[1] = sku;
        serviceCore.executeService(Definitions.RECHARGE, new WebServiceListener<PixzelleClass>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<PixzelleClass> response, int webService) {
                onRechargeListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRechargeListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void getServices(Context context, final ServicesListener servicesListener) {
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.SERVICES, new WebServiceListener<Service>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Service> response, int webService) {
                servicesListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                servicesListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void getTickets(Context context, final OnGetTicketsListener onGetTicketsListener, String urlParams) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = urlParams;
        serviceCore.executeService(Definitions.TICKETS, new WebServiceListener<Ticket>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Ticket> response, int webService) {
                onGetTicketsListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onGetTicketsListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void getStores(Context context, final StoreListener storeListener) {
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.STORE, new WebServiceListener<StoresMethod>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<StoresMethod> response, int webService) {
                storeListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                storeListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void replaceSrPagoCard(Context context, final SrPagoCardListener listener, String numberCard) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[1];
        data[0] = numberCard;
        serviceCore.executeService(Definitions.REPLACEMENT_CARD, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                listener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                listener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }
}
