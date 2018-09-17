package sr.pago.sdk.connection;

import android.content.Context;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.api.parsers.PixzelleParserResponseFactory;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.response.Response;
import sr.pago.sdk.utils.Logger;


/**
 * Created by Rodolfo on 10/08/2015.
 */
public abstract class PixzelleNetworkHandler extends WebServiceConnectionClass {
    public PixzelleNetworkHandler(Context context) {
        super(context);
    }

    @Override
    public void onNoInternetConnection(final int webService) {
        SPResponse spResponse = new SPResponse();
        spResponse.setError(SrPagoDefinitions.Error.NO_INTERNET);
        onResponseError(Pixzelle.NO_INTERNET, spResponse, webService);
    }

    @Override
    public void onTimeoutConnection(final int webService) {
        SPResponse spResponse = new SPResponse();
        spResponse.setError(SrPagoDefinitions.Error.TIMEOUT);
        onResponseError(Pixzelle.TIME_OUT, spResponse, webService);
    }

    @Override
    public void onUnknownConnectionError(final int webService) {
        SPResponse spResponse = new SPResponse();
        spResponse.setError(SrPagoDefinitions.Error.UNKNOWN);
        onResponseError(Pixzelle.UNKNOWN_CODE, spResponse, webService);
    }

    @Override
    public void onServerError(@Pixzelle.SERVER_CODES int code, String error, Map<String, List<String>> headers, String url, String body, String method, int webService, Object[] params, String[] urlParams) {
        Logger.logWarning("Error code " + webService, code + "");
        Logger.logWarning("Error response " + webService, error == null ? "" : error);
        try {
            if (code != Pixzelle.NOT_FOUND) {
                if (code == Pixzelle.MAINTENANCE) {
                    onUnknownConnectionError(webService);
                } else {
                    PixzelleParserResponseFactory pixzelleParserResponseFactory = buildResponseFactory();
                    SPResponse response;
                    try {
                        response = pixzelleParserResponseFactory.parseError(error);
                    } catch (JSONException ex) {
                        response = new SPResponse();
                    }
                    if (response != null) {
                        response.setHeader(headers);
                        response.setUrl(url);
                        response.setBody(body);
                        response.setMethod(method);

                        if (code == Pixzelle.SERVER_ERROR && response.getMessage() == null) {
                            response.setMessage("No se ha podido establecer la conexión con el servidor, favor de intentar más tarde.");
//                            onUnknownConnectionError(webService);
                        }

                        onResponseError(code, response, webService);
                    } else {
                        onUnknownConnectionError(webService);
                    }
                }
            } else {
                onResponseError(code, null, webService);
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            onUnknownConnectionError(webService);
        }
    }

    @Override
    public void onOkConnection(@Pixzelle.SERVER_CODES int code, String result, Map<String, List<String>> headers, int webService) {
        Logger.logMessage("Ok code " + webService, code + "");
        if (webService != 32)
            Logger.logMessage("Ok response" + webService, result);
        try {
            PixzelleParserResponseFactory pixzelleParserResponseFactory = buildResponseFactory();
            SPResponse response = pixzelleParserResponseFactory.parseResponse(getContext(), webService, result);
            if (response != null) {
                response.setHeader(headers);
                onResponseOk(code, response, webService);
            } else {
                onUnknownConnectionError(webService);
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            onUnknownConnectionError(webService);
        }
    }

    public abstract void onResponseOk(@Pixzelle.SERVER_CODES int code, SPResponse<?> response, int webService);

    public abstract void onResponseError(@Pixzelle.SERVER_CODES int code, SPResponse<?> response, int webService);

    public abstract PixzelleParserResponseFactory buildResponseFactory();
}
