package sr.pago.sdk.connection;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;

import sr.pago.sdk.api.parsers.ParserRequestFactory;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.PixzelleDefinitions;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.utils.Logger;

public class WebServiceConnection extends PixzelleWebServiceConnection<Object, Void> {

    private String[] urlParams = null;

    public WebServiceConnection(final Context context, final int webService, final int method) {
        super(context, webService, method);
    }

    @Override
    public String buildUrl(Object... params) {
        return getURL(this.getContext(), this.getWebService());
    }

    @Override
    public String[] getUrlParams() {
        return this.urlParams;
    }

    public void setUrlParams(String[] urlParams) {
        this.urlParams = urlParams;

    }

    @Override
    public ArrayList<Pair<String, String>> buildHeader() {
        ArrayList<Pair<String, String>> headers = new ArrayList<>();
        headers.add(new Pair<>(Definitions.CONTENT_TYPE(), Definitions.APP_JSON()));

        if (!Global.getStringKey(
                this.getContext(), Definitions.KEY_REGISTER_TOKEN()).equals(PixzelleDefinitions.STRING_NULL) &&
                this.getWebService() != Definitions.LOGIN &&
                this.getWebService() != Definitions.LOGIN_FB &&
                this.getWebService() != Definitions.SERVICES &&
                this.getWebService() != Definitions.STORE &&
                this.getWebService() != Definitions.RESET_PASSWORD &&
                this.getWebService() != Definitions.ABM &&
                this.getWebService() != Definitions.VERSION_CHECK &&
                this.getWebService() != Definitions.RECOVERY_PASSWORD &&
                this.getWebService() != Definitions.REGISTER_STATUS &&
                this.getWebService() != Definitions.LINK_FB_ACCOUNT &&
                this.getWebService() != Definitions.REGISTER &&
                this.getWebService() != Definitions.GET_ZIPCODE &&
                this.getWebService() != Definitions.GET_BUSINESS_CATALOG
                && this.getWebService() != Definitions.GET_SERVER_TIME) {
            headers.add(new Pair<>(Definitions.AUTH_TOKEN(), String.format(Definitions.HEADER_CONTENT(), Definitions.BEARER(), Global.getStringKey(this.getContext(), Definitions.KEY_REGISTER_TOKEN()))));
        } else {
            try {
                ApplicationInfo applicationInfo = this.getContext().getPackageManager().getApplicationInfo(this.getContext().getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = applicationInfo.metaData;
                if (Global.isInDebugMode(this.getContext())) {
                    headers.add(new Pair<>(Definitions.AUTH_TOKEN(), String.format(Definitions.HEADER_CONTENT(), bundle.getString(Definitions.DEBUG_APP_TOKEN()), bundle.getString(Definitions.DEBUG_APP_SECRET()))));
                } else {
                    headers.add(new Pair<>(Definitions.AUTH_TOKEN(), String.format(Definitions.HEADER_CONTENT(), bundle.getString(Definitions.RELEASE_APP_TOKEN()), bundle.getString(Definitions.REALEASE_APP_SECRET()))));
                }
                headers.add(new Pair<>("X-User-Agent", "{\"agent\":\"" + getApplicationName(this.getContext()) + " Android" + "/" + this.getContext().getPackageManager().getPackageInfo(this.getContext().getPackageName(), 0).versionName + "\"}"));
            } catch (Exception ex) {
                Logger.logError(ex);
            }
            Logger.logDebug("token", Global.getStringKey(this.getContext(), Definitions.KEY_REGISTER_TOKEN()));
        }
        return headers;
    }

    public static String getURL(Context context, int urlKey) {
        if (Global.isInDebugMode(context)) {
            return Definitions.getDEBUG_URLS(urlKey);
        } else {
            return Definitions.getRELEASE_URLS(urlKey);
        }
    }

    @Override
    public byte[] buildBody(Object... params) {

        try {
            ParserRequestFactory parserRequestFactory = new ParserRequestFactory();
            String str = parserRequestFactory.convertToJSON(this.getContext(), this.getWebService(), params);

            Logger.logMessage(Definitions.REQUEST_PARAMS(), str);
            return str.getBytes(Definitions.UTF());
        } catch (Exception ex) {

            Logger.logError(ex);
        }
        return null;
    }

    @Override
    public int wsType() {
        return OAUTH;
    }

    @Override
    public boolean cancelWS(boolean mayInterrupt) {
        return super.cancelWS(true);
    }

    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }
}