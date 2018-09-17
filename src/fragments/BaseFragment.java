package sr.pago.sdk.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

import sr.pago.sdk.R;
import sr.pago.sdk.api.parsers.ParserResponseFactory;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.utils.Logger;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.object.response.Response;

/**
 * Created by Rodolfo on 21/09/2015.
 */
public abstract class BaseFragment extends PixzelleFragment {
    ProgressDialog progressDialog;
    private int webServiceRefresh;
    private Object[] paramsToRefresh;
    private String[] urlParamsToRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.logDebug("Fragment", this.getClass().toString());
    }

    @Override
    public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        try {
            if (response != null) {
                onResponseError(code, response, webService);
            } else {
                //onUnknownConnectionError(webService);
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            //onUnknownConnectionError(webService);
        }
    }

    @Override
    public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        try {
            if (response != null) {
                onResponseOk(code, response, webService);
            } else {
                //onUnknownConnectionError(webService);
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            //onUnknownConnectionError(webService);
        }
    }

    public abstract void onConnectionErrorYesClick(int webService);

    public abstract void onConnectionErrorNoClick(int webService);

    public abstract void onResponseOk(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService);

    public abstract void onResponseError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService);
}
