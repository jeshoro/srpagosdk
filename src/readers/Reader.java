package sr.pago.sdk.readers;

import android.content.Context;

/**
 * Created by desarrolladorandroid on 12/01/17.
 */

public interface Reader {

    void initReader();
    void stopReader();
    void getDeviceInfo();
    boolean isConnected();
    void resetReader();
    void startTransaction();


    void onDeviceReturnCheckCardResult();
    void onDeviceReturnEmvCardDataResult();
    void onDeviceReturnStartEmvResult();
    void onDeviceRequestSetAmount();
    void onDeviceRequestSelectApplication();
    void onDeviceRequestCheckServerConnectivity();
    void onDeviceRequestFinalConfirm();
    void onDeviceRequestOnlineProcess();
    void onDeviceRequestTerminalTime();
    void onDeviceRequestDisplayText();
    void onDeviceReturnTransactionResult();
    void onDeviceReturnBatchData();
    void onDeviceReturnReversalData();
    void onDevicePlugged();
    void onDeviceUnplugged();
    void onDeviceNoDeviceDetected();
    void onDeviceError();
}
