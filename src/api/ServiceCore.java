package sr.pago.sdk.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;

import sr.pago.sdk.api.parsers.ParserResponseFactory;
import sr.pago.sdk.api.parsers.PixzelleParserResponseFactory;
import sr.pago.sdk.connection.PixzelleNetworkHandler;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 08/12/2015 for TestIt.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */
@SuppressLint("ParcelCreator")
public class ServiceCore extends PixzelleNetworkHandler {
    protected WebServiceListener webServiceListener;
    private int service;
    private int method;
    private String customUrl;
    private WebServiceConnection myWebServiceConnection;

    public ServiceCore(Context context) {
        super(context);
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public void executeService(int service, final WebServiceListener listener, final Object[] bodyParams, final String[] urlParams) {

        this.webServiceListener = listener;
        this.service = service;
        this.method = getRESTMethod(service);

        Calendar expiration = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        try {
            callWebService(bodyParams, urlParams);
        } catch (Exception ex) {
            callWebService(bodyParams, urlParams);
        }
    }

    private void callWebService(final Object[] bodyParams, final String[] urlParams) {
        Logger.logDebug("WS execution", service + "");
        myWebServiceConnection = buildWebServiceConnection(this.getContext(), service, this.method);
        myWebServiceConnection.setListener(this);
        myWebServiceConnection.setUrlParams(urlParams);
        myWebServiceConnection.setCustomUrl(customUrl);
        myWebServiceConnection.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bodyParams);
    }

    public WebServiceConnection buildWebServiceConnection(Context context, int service, int method) {
        return new WebServiceConnection(context, service, method);
    }

    protected int getRESTMethod(int service) {
        return Definitions.getRESTType(service);
    }

    @Override
    public PixzelleParserResponseFactory buildResponseFactory() {
        return new ParserResponseFactory();
    }

    @Override
    public void onResponseOk(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        customUrl = null;
        if (webServiceListener != null) {
            webServiceListener.onSuccess(code, response, webService);
        }
    }


    @Override
    public void onResponseError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        customUrl = null;
        if (webServiceListener != null) {
            webServiceListener.onError(code, response, webService);
        }
    }

    public WebServiceListener getWebServiceListener() {
        return webServiceListener;
    }

    public void setWebServiceListener(WebServiceListener webServiceListener) {
        this.webServiceListener = webServiceListener;
    }
}
