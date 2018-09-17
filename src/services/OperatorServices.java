package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.AddOperatorListener;
import sr.pago.sdk.interfaces.OperatorAddListener;
import sr.pago.sdk.interfaces.OperatorDeleteListener;
import sr.pago.sdk.interfaces.OperatorsListener;
import sr.pago.sdk.model.Operator;
import sr.pago.sdk.model.SPResponse;

/**
 * Created by Rodolfo on 25/09/2015 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public class OperatorServices {
    public static void updateOperator(final Context context, final AddOperatorListener addOperatorListener, String name, String pwd, String url) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[2];
        data[0] = name;
        data[1] = pwd;
        String[] email = new String[1];
        email[0] = url;
        serviceCore.executeService(Definitions.UPDATE_OPERATOR, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                addOperatorListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                addOperatorListener.onError(response.getMessage(), response.getError());
            }
        }, data, email);
    }

    public static void deleteOperator(Context context, final OperatorDeleteListener operatorDeleteListener, String var){
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data=new String[1];
        data[0]=var+"";
        serviceCore.executeService(Definitions.OPERATOR_DELETE, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorDeleteListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorDeleteListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void addOperator(Context context, final OperatorAddListener operatorAddListener, String email, String name, String pwd){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data =new Object[3];
        data[0]=email;
        data[1]=name;
        data[2]=pwd;
        serviceCore.executeService(Definitions.ADD_OPERATOR, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorAddListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorAddListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void getOperators(Context context, final OperatorsListener operatorListener){
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.GET_OPERATORS, new WebServiceListener<Operator>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                operatorListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }
}
