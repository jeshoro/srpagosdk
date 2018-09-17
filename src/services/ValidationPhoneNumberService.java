package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.OnValidationPhoneEditListener;
import sr.pago.sdk.interfaces.OnValidationPhoneListener;
import sr.pago.sdk.interfaces.OnValidationPhoneSendListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.response.ValidationPhoneSendResult;

/**
 * Created by desarrolladorandroid on 27/01/17.
 */

public class ValidationPhoneNumberService {
    public static void validateCode(Context context, final OnValidationPhoneListener onValidationPhoneListener, String code){
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = code;
        serviceCore.executeService(Definitions.VALIDATION_PHONE, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onValidationPhoneListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onValidationPhoneListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void resendCode(Context context, final OnValidationPhoneSendListener onValidationPhoneSendListener, String ip){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] requestData = new Object[1];
        requestData[0] = ip;
        serviceCore.executeService(Definitions.VALIDATION_PHONE_SEND_CODE, new WebServiceListener<ValidationPhoneSendResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<ValidationPhoneSendResult> response, int webService) {
                onValidationPhoneSendListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onValidationPhoneSendListener.onError(response.getMessage(), response.getError());
            }
        }, requestData, null);
    }

    public static void editPhoneNumber(Context context, final OnValidationPhoneEditListener onValidationPhoneEditListener, String ip, String phoneNumber){
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        Object[] requestData = new Object[1];
        data[0] = phoneNumber;
        requestData[0] = ip;
        serviceCore.executeService(Definitions.VALIDATION_PHONE_EDIT_PHONE_NUMBER, new WebServiceListener<ValidationPhoneSendResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<ValidationPhoneSendResult> response, int webService) {
                onValidationPhoneEditListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onValidationPhoneEditListener.onError(response.getMessage(), response.getError());
            }
        }, requestData, data);
    }
}
