package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.AbmListener;
import sr.pago.sdk.interfaces.OnBusinessCatalogListener;
import sr.pago.sdk.interfaces.OnRegisterDevice;
import sr.pago.sdk.interfaces.OnVersionCheck;
import sr.pago.sdk.interfaces.OnZipCodeResponse;
import sr.pago.sdk.model.Abm;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.response.BusinessCatalogResult;
import sr.pago.sdk.object.response.VersionCheckResult;
import sr.pago.sdk.object.response.ZipCodeResult;

/**
 * Created by Rodolfo on 25/07/2017.
 */

public class UtilitiesServices {
    public static void getBusinessCatalogs(Context context, final OnBusinessCatalogListener onBusinessCatalogListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.GET_BUSINESS_CATALOG, new WebServiceListener<BusinessCatalogResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<BusinessCatalogResult> response, int webService) {
                onBusinessCatalogListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onBusinessCatalogListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void getColony(final Context context, final OnZipCodeResponse onZipCodeResponse, final String zip) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = zip;
        serviceCore.executeService(Definitions.GET_ZIPCODE, new WebServiceListener<ZipCodeResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<ZipCodeResult> response, int webService) {
                onZipCodeResponse.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onZipCodeResponse.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void getBanks(final Context context, final AbmListener abmListener){
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.ABM, new WebServiceListener<Abm>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Abm> response, int webService) {
                abmListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {

            }
        }, null, null);
    }

    public static void registerDevice(Context context, final OnRegisterDevice registerDeviceListener, String uuid, String token, String detail){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[3];
        data[0] = uuid;
        data[1] = token;
        data[2] = detail;
        serviceCore.executeService(Definitions.REGISTER_DEVICE, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                registerDeviceListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                registerDeviceListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void unregisterDevice(Context context, final OnRegisterDevice registerDeviceListener, String uuid) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = uuid;
        serviceCore.executeService(Definitions.UNREGISTER_DEVICE, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                registerDeviceListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                registerDeviceListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }

    public static void versionCheck(final Context context, final OnVersionCheck onVersionCheckListener, final String version) {
        ServiceCore serviceCore = new ServiceCore(context);
        String[] data = new String[1];
        data[0] = version;
        serviceCore.executeService(Definitions.VERSION_CHECK, new WebServiceListener<VersionCheckResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<VersionCheckResult> response, int webService) {
                onVersionCheckListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onVersionCheckListener.onError(response.getMessage(), response.getError());
            }
        }, null, data);
    }
}
