package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.CancelOperationListener;
import sr.pago.sdk.interfaces.OperationFundBalanceListener;
import sr.pago.sdk.interfaces.OperationListener;
import sr.pago.sdk.interfaces.SendTicketListener;
import sr.pago.sdk.interfaces.OnGetOperationsListener;
import sr.pago.sdk.interfaces.WithdrawalsListener;
import sr.pago.sdk.model.OperationFundBalance;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.responses.srpago.Withdrawal;

/**
 * Created by Rodolfo on 25/07/2017.
 */

public class OperationsServices {

    public static void cancelOperation(Context context, final CancelOperationListener operationListener, String id) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = id;
        serviceCore.executeService(Definitions.SP_CANCEL_OPERATION, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operationListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operationListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void getFunds(Context context, final OperationFundBalanceListener operationsFoundsListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.OPERATIONS_FOUNTS_BALANCE, new WebServiceListener<OperationFundBalance>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<OperationFundBalance> response, int webService) {
                operationsFoundsListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operationsFoundsListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void getWithdrawals(Context context, final WithdrawalsListener withdrawalsListener, String urlParams) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data=new String[1];
        data[0] = urlParams;
        serviceCore.executeService(Definitions.WITHDRAWALS, new WebServiceListener<Withdrawal>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Withdrawal> response, int webService) {
                withdrawalsListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                withdrawalsListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void getOperation(Context context, final OperationListener operationListener, String id) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = id;

        serviceCore.executeService(Definitions.OPERATION, new WebServiceListener<sr.pago.sdk.object.Operation>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<sr.pago.sdk.object.Operation> response, int webService) {
                operationListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operationListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void sendOperationToUser(final Context context, final SendTicketListener sendTicketListener, final String id, final String email, final String phone){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data =new Object[3];
        data[0] = id;
        data[1] = email;
        data[2] = phone;
        serviceCore.executeService(Definitions.SEND_TICKET, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                sendTicketListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                sendTicketListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void getOperations(Context context, final OnGetOperationsListener onGetOperationsListener, String urlParams){
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data=new String[1];
        data[0] = urlParams;
        serviceCore.executeService(Definitions.OPERATIONS, new WebServiceListener<SrPagoTransaction>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
                onGetOperationsListener.onSuccesss(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onGetOperationsListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

//    public void getOperationsById(Context context, OnGetOperationsListener onGetOperationsListener, String id){
//        ServiceCore serviceCore = new ServiceCore(context);
//        String[] data =new String[1];
//        data[0]=id;
//        serviceCore.executeService(Definitions.TICKETS_ID, new WebServiceListener<SrPagoTransaction>() {
//            @Override
//            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<SrPagoTransaction> response, int webService) {
//
//            }
//
//            @Override
//            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
//
//            }
//        }, null, data);
//    }
}
