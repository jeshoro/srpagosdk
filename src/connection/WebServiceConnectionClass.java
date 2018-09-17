package sr.pago.sdk.connection;

import android.content.Context;

import java.util.List;
import java.util.Map;

import sr.pago.sdk.enums.Pixzelle;

/**
 * Created by Rodolfo on 08/12/2015 for Jyb.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */
public abstract class WebServiceConnectionClass implements PixzelleWebServiceListener {
    private Context context;
    protected int webServiceRefresh;
    protected Object[] paramsToRefresh;
    protected String[] urlParamsToRefresh;

    public WebServiceConnectionClass(){

    }

    public WebServiceConnectionClass(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void result(String result, int webService) {

    }

    @Override
    public void result(@Pixzelle.SERVER_CODES int code, String result, Map<String, List<String>> headers, String url, String method, String body, int webService, Object[] params, String[] urlParams) {
        switch (code) {
            case Pixzelle.OK:
            case Pixzelle.CREATED:
            case Pixzelle.NO_CONTENT:
                onOkConnection(code, result, headers, webService);
                break;

            case Pixzelle.NO_INTERNET:
                webServiceRefresh = webService;
                paramsToRefresh = params;
                urlParamsToRefresh = urlParams;
                onNoInternetConnection(webService);
                break;

            case Pixzelle.TIME_OUT:
                webServiceRefresh = webService;
                paramsToRefresh = params;
                urlParamsToRefresh = urlParams;
                onTimeoutConnection(webService);
                break;

            case Pixzelle.UNKNOWN_CODE:
                webServiceRefresh = webService;
                paramsToRefresh = params;
                urlParamsToRefresh = urlParams;
                onServerError(code, result, headers,url, body, method, webService, params, urlParams);
                //onUnknownConnectionError(webService);
                break;

            default:
                webServiceRefresh = webService;
                paramsToRefresh = params;
                urlParamsToRefresh = urlParams;
                onServerError(code, result, headers, url, body, method, webService, params, urlParams);
                break;
        }
    }

    public abstract void onNoInternetConnection(int webService);

    public abstract void onTimeoutConnection(int webService);

    public abstract void onUnknownConnectionError(int webService);

    public abstract void onServerError(@Pixzelle.SERVER_CODES int code, String error, Map<String, List<String>> headers, String url, String body, String method, int webService, Object[] params, String[] urlParams);

    public abstract void onOkConnection(@Pixzelle.SERVER_CODES int code, String result, Map<String, List<String>> headers, int webService);
}
