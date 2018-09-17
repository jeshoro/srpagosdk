package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.OnCodeRedeemListener;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.Code;

/**
 * Created by Rodolfo on 12/07/2017.
 */

public class CodeRedeemerService {
    public static void redeemCode(Context context, String code, final OnCodeRedeemListener onCodeRedeemListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] body = {code};
        serviceCore.executeService(Definitions.CODE_REDEEM, new WebServiceListener<Code>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Code> response, int webService) {
                onCodeRedeemListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onCodeRedeemListener.onError(response.getMessage(), response.getError());
            }
        }, body, null);
    }

//    public static void getDistributor(Context context, String code, OnCodeRedeemListener onCodeRedeemListener) {
//        ServiceCore serviceCore = new ServiceCore(context);
//        String[] urlParams = {code};
//        serviceCore.executeService(Definitions.CODE_REDEEM, onCodeRedeemListener, null, urlParams);
//    }
}
