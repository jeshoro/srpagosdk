package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WSErrorHandler;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.ActivatePrevivaleListener;
import sr.pago.sdk.interfaces.BankAccountListener;
import sr.pago.sdk.interfaces.CardTypeListener;
import sr.pago.sdk.interfaces.OnAccountInfoListener;
import sr.pago.sdk.interfaces.OnBankAccountAddListener;
import sr.pago.sdk.interfaces.OnBankAccountDeleteListener;
import sr.pago.sdk.interfaces.OnCancelCardListener;
import sr.pago.sdk.interfaces.OnRestoreNipListener;
import sr.pago.sdk.interfaces.OnSetDefaultBankAccount;
import sr.pago.sdk.interfaces.OnSignaturePendingUploadListener;
import sr.pago.sdk.interfaces.OnSrPagoCardBalanceListener;
import sr.pago.sdk.interfaces.OnSrPagoCardListener;
import sr.pago.sdk.interfaces.SrPagoCardListener;
import sr.pago.sdk.interfaces.SrPagoDocumentsListener;
import sr.pago.sdk.interfaces.TransactionsListener;
import sr.pago.sdk.interfaces.TransferListener;
import sr.pago.sdk.model.Balance;
import sr.pago.sdk.model.BankAccount;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.model.responses.srpago.AccountInfoRS;
import sr.pago.sdk.model.responses.srpago.Card;
import sr.pago.sdk.model.responses.srpago.CardTypeRS;
import sr.pago.sdk.model.responses.srpago.SrPagoCard;
import sr.pago.sdk.model.Transaction;
import sr.pago.sdk.object.Account;

/**
 * Created by Rodolfo on 15/10/2015.
 */
public class AccountService {
    public static void callBankAccount(Context context, final BankAccountListener bankAccountListener) {
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.BANK_ACCOUNTS, new WebServiceListener<BankAccount>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<BankAccount> response, int webService) {
                bankAccountListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                bankAccountListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void callSrPagoCard(Context context, final OnSrPagoCardListener onSrPagoCardListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.SR_PAGO_CARD, new WebServiceListener<SrPagoCard>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoCard> response, int webService) {
                onSrPagoCardListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                if (response.getError() == null) {
                    try {
                        response.setError(WSErrorHandler.errorHandling(response.getCode(), response.getMessage()));
                    } catch (Exception ex) {

                    }
                }
                onSrPagoCardListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void restoreSrPagoCardNip(Context context, final OnRestoreNipListener onRestoreNipListener, String number) {
        Object[] data = new Object[1];
        data[0] = number;

        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.RESTORE_NIP, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRestoreNipListener.onSuccess(response.getBody());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                if (response.getError() == null) {
                    try {
                        response.setError(WSErrorHandler.errorHandling(response.getCode(), response.getMessage()));
                    } catch (Exception ex) {

                    }
                }
                onRestoreNipListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void addContract(Context context, final SrPagoCard srPagoCard, final SrPagoCardListener srPagoCardListener) {
        Object[] data = {srPagoCard};

        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.CONTRACT, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoCardListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoCardListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void updateContract(Context context, final SrPagoCard srPagoCard, final SrPagoCardListener srPagoCardListener) {
        Object[] data = {srPagoCard};
        String[] url = {srPagoCard.getNumber()};

        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.CONTRACT_UPDATE, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoCardListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoCardListener.onError(response.getMessage(), response.getError());
            }
        }, data, url);
    }

    public static void getSrPagoCardDocumentsStatus(Context context, final String number, final SrPagoDocumentsListener srPagoDocumentsListener) {
        final String[] urlParams = {number};

        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.CONTRACT_DOCUMENTS_STATUS, new WebServiceListener<SPTransactionDocument>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SPTransactionDocument> response, int webService) {
                srPagoDocumentsListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoDocumentsListener.onError(response.getMessage(), response.getError());
            }
        }, null, urlParams);
    }

    public static void uploadSrPagoCardDocument(Context context, final SPTransactionDocument spTransactionDocument, final String number, final SrPagoCardListener srPagoCardListener) {
        final Object[] params = {spTransactionDocument, number};
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.CONTRACT_UPLOAD_DOCUMENT, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoCardListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                srPagoCardListener.onError(response.getMessage(), response.getError());
            }
        }, params, null);
    }

    public static void callSrPagoTransactions(Context context, final TransactionsListener transactionsListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.TRANSACTIONS, new WebServiceListener<Transaction>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Transaction> response, int webService) {
                transactionsListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                transactionsListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void callAccountInfo(final Context context,
                                       final OnAccountInfoListener onAccountInfoListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.GET_ACCOUNT, new WebServiceListener<AccountInfoRS>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<AccountInfoRS> response, int webService) {
                onAccountInfoListener.onSuccess(response.getItems().get(0).getResult());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onAccountInfoListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void uploadPendingSignature(final Context context,
                                              final OnSignaturePendingUploadListener onSignaturePendingUploadListener,
                                              final String token,
                                              final String operationId,
                                              final String data) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] dataSender = new Object[3];
        dataSender[0] = token;
        dataSender[1] = operationId;
        dataSender[2] = data;
        serviceCore.executeService(Definitions.SP_TAIL_SIGNATURES, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onSignaturePendingUploadListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onSignaturePendingUploadListener.onError(response.getMessage(), response.getError());
            }
        }, dataSender, null);

    }

    public static void cancelCard(final Context context,
                                  final OnCancelCardListener onCancelCardListener,
                                  final String number,
                                  final String reason,
                                  final String obs) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[3];
        data[0] = number;
        data[1] = reason;
        data[2] = obs;
        serviceCore.executeService(Definitions.CANCEL_CARD, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onCancelCardListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onCancelCardListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void transferBalance(Context context, final TransferListener transferListener, String id, double amount, String description) {
        ServiceCore serviceCore = new ServiceCore(context);

        Object[] data = new Object[3];
        data[0] = id;
        data[1] = amount;
        data[2] = description;
        serviceCore.executeService(Definitions.TRANSFER_BALANCE, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                transferListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                transferListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void setDefaultAccount(Context context, final OnSetDefaultBankAccount onSetDefaultBankAccount, String id, String type, boolean status) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        Object[] requestData = new Object[2];
        requestData[0] = status;
        requestData[1] = type;
        data[0] = id;
        serviceCore.executeService(Definitions.BANK_ACCOUNT_DEFAULT, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onSetDefaultBankAccount.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onSetDefaultBankAccount.onError(response.getMessage(), response.getError());
            }
        }, requestData, data);
    }

    public static void setAutomaticWithdrawal(Context context, final OnSetDefaultBankAccount onSetDefaultBankAccount, String automatic_withdrawal) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] requestData = new Object[1];
        requestData[0] = automatic_withdrawal;
        serviceCore.executeService(Definitions.SET_AUTOMATIC_WITHDRAWALS, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onSetDefaultBankAccount.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onSetDefaultBankAccount.onError(response.getMessage(), response.getError());
            }
        }, requestData, null);
    }

    public static void addBankAccount(final Context context,
                                      final OnBankAccountAddListener onBankAccountAddListener,
                                      final String alias,
                                      final String type,
                                      final String number,
                                      final String img,
                                      final String imgIfe,
                                      final String imgIfeBack,
                                      boolean isDefaultAccount) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[8];
        data[0] = alias;
        data[1] = type;
        data[2] = number;
        data[3] = img;
        data[4] = imgIfe;
        data[5] = imgIfeBack;
        data[6] = isDefaultAccount;
        data[7] = 1;
        serviceCore.executeService(Definitions.BANK_ACCOUNT_POST, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBankAccountAddListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBankAccountAddListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void addBankAccount(final Context context,
                                      final OnBankAccountAddListener onBankAccountAddListener,
                                      final String alias,
                                      final String type,
                                      final String number,
                                      final String img,
                                      final String imgIfe,
                                      final String imgIfeBack,
                                      boolean isDefaultAccount,
                                      int origin) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[8];
        data[0] = alias;
        data[1] = type;
        data[2] = number;
        data[3] = img;
        data[4] = imgIfe;
        data[5] = imgIfeBack;
        data[6] = isDefaultAccount;
        data[7] = origin;
        serviceCore.executeService(Definitions.BANK_ACCOUNT_POST, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBankAccountAddListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBankAccountAddListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void deleteBankAccount(final Context context, final OnBankAccountDeleteListener onBankAccountDeleteListener, int id) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = id + "";
        serviceCore.executeService(Definitions.DELETE_BANK_ACCOUNT, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBankAccountDeleteListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBankAccountDeleteListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void callBankAccounts(Context context, final BankAccountListener bankAccountListener) {
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.BANK_ACCOUNTS, new WebServiceListener<BankAccount>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<BankAccount> response, int webService) {
                bankAccountListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                bankAccountListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void getSrPagoCardBalance(Context context, final OnSrPagoCardBalanceListener operatorListener) {
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.BALANCE, new WebServiceListener<Balance>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Balance> response, int webService) {
                operatorListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void validateCardNumber(Context context, final CardTypeListener cardTypeListener, String cardNumber) {
        String[] data = new String[1];
        data[0] = cardNumber;
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.CARD_TYPE, new WebServiceListener<CardTypeRS>() {
            @Override
            public void onSuccess(int code, SPResponse<CardTypeRS> response, int webService) {
                cardTypeListener.onCardTypeSuccess(response.getItems().get(0).getResult());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                cardTypeListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void activatePrevivaleCard(Context context, final ActivatePrevivaleListener previvaleListener, String json) {
        String[] data = new String[1];
        data[0] = json;
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.CARD_PREPAID, new WebServiceListener<Card>() {
            @Override
            public void onSuccess(int code, SPResponse<Card> response, int webService) {
                previvaleListener.onPrevivaleSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                previvaleListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }
}
