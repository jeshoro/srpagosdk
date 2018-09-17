package sr.pago.sdk.readers.qpos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.dspread.xpos.QPOSService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.api.parsers.PixzelleParserResponseFactory;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.PixzelleDefinitions;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 21/08/2017.
 */

public class QposBluetooth extends QposReader {

    private String address;

    public QposBluetooth(Context context) {
        super(context);
        qposReader = this;
        String tmpAddress = Global.getStringKey(context, QposReader.ADDRESS);
        if (!tmpAddress.equals(PixzelleDefinitions.STRING_NULL)) {
            address = tmpAddress;
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1001:
                        sendMsg(1002);
                        break;
                    case 1002:
                        qposService.connectBluetoothDevice(true, 25, address);
                        break;
                    case 1003:
                        qposService.doTrade(30);
                        break;
                    case 8003:
                        Hashtable<String, String> h = qposService.getNFCBatchData();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public PixzelleParserResponseFactory buildResponseFactory() {
        return null;
    }

    @Override
    public void initReader(Context context) {
        Logger.logWarning("QposBluetooth", "initReader");

        String tmpAddress = Global.getStringKey(context, QposReader.ADDRESS);
        if (!tmpAddress.equals(PixzelleDefinitions.STRING_NULL)) {
            address = tmpAddress;
        }
        qposService = QPOSService.getInstance(QPOSService.CommunicationMode.BLUETOOTH_2Mode);
        if (qposService == null) {
            srPagoListener.onNoDeviceDetected();
        }
        qposService.setContext(context);
        Handler handler = new Handler(Looper.myLooper());
        qposService.initListener(handler, new MyQposControllerListener());
        sendMsg(1001);
    }

    @Override
    public void stopReader() {
        Logger.logWarning("QposBluetooth", "stopReader");
        qposService.disconnectBT();
    }

    @Override
    public void resetReader() {
        Logger.logWarning("QposBluetooth", "resetReader");

        qposService.resetPosStatus();
    }

    @Override
    public void onQposInfoResult(Hashtable<String, String> hashtable) {
        Logger.logWarning("QposBluetooth", "onQposInfoResult: " + hashtable.toString());
        if (qposService != null) {
            if (patchConfig) {
                Global.setBooleanPreference(context, Definitions.KEY_READER_IS_PATCH_CONFIG, true);
            }
            isReaderConnected = true;
            handleDeviceInfo(hashtable, "bluetooth");
        }
    }

    @Override
    public void onRequestQposConnected() {
        Logger.logWarning("QposBluetooth", "onRequestQposConnected");
        if (srPagoListener != null) {
            srPagoListener.onDevicePlugged();
            //srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.QPOS_CONNECTED);
        }
        if (qposService != null) {
            qposService.getQposId();
        }else
            Logger.logDebug("SrPagoQPOSReader", "qposService: NULL!!");
    }

    @Override
    public void onRequestQposDisconnected() {
        Logger.logWarning("QposBluetooth", "onRequestQposDisconnected");
        isReaderConnected = false;
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        batteryLow = true;
        if (alertMonths != null) {
            alertMonths.dismiss();
        }
        if (srPagoListener != null) {
            srPagoListener.onDeviceUnplugged();
        }
    }

    @Override
    public void onRequestNoQposDetected() {
        Logger.logWarning("QposBluetooth", "onRequestNoQposDetected");

        if (srPagoListener != null) {
            srPagoListener.onError(SrPagoDefinitions.Error.SQPOS_NO_DETECTED);
        }

    }

    @Override
    public void onBluetoothBonding() {
        Logger.logWarning("QposBluetooth", "onBluetoothBonding");
    }

    @Override
    public void onBluetoothBonded() {
        Logger.logWarning("QposBluetooth", "onBluetoothBonded");
    }

    @Override
    public void onBluetoothBondFailed() {
        Logger.logWarning("QposBluetooth", "onBluetoothBondFailed");
    }

    @Override
    public void onBluetoothBondTimeout() {
        Logger.logWarning("QposBluetooth", "onBluetoothBondTimeout");
    }

}
