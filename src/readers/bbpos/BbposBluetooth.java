package sr.pago.sdk.readers.bbpos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bbpos.bbdevice.BBDeviceController;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.api.parsers.PixzelleParserResponseFactory;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.readers.qpos.QposReader;
import sr.pago.sdk.utils.Logger;

/**
 * Created by David Morales on 07/11/2018.
 * <p>
 * Class to manage connection with the BBPos BT reader.
 */
public class BbposBluetooth extends BbposReader {

    private BluetoothDevice device;
    static BbposBluetooth instance;
    private final String TAG = "BbposBluetooth";

    @Override
    public PixzelleParserResponseFactory buildResponseFactory() {
        return null;
    }

    public static BbposBluetooth getInstance(Context context) {
        if (instance == null)
            instance = new BbposBluetooth(context);

        return instance;
    }

    /**
     * Public constructor class BBpos BT.
     *
     * @param context context needed for super class
     */
    public BbposBluetooth(Context context) {
        super(context);
        bbposReader = this;
    }

    /**
     * This method initialize the BBPos BT reader.
     *
     * @param context context to get preferences.
     */
    @Override
    public void initReader(Context context) {
        Logger.logWarning(TAG, "initReader");
        BBDeviceController.setDebugLogEnabled(true);

        if (!isReaderConnected) {

            String tmpAddress = Global.getStringKey(context, QposReader.ADDRESS);
            BluetoothAdapter btAdapter;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
                assert bluetoothManager != null;
                btAdapter = bluetoothManager.getAdapter();
            } else {
                btAdapter = BluetoothAdapter.getDefaultAdapter();
            }

            BluetoothDevice device = btAdapter.getRemoteDevice(tmpAddress);

            if (srPagoListener != null) {
                srPagoListener.onDevicePlugged();
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.INIT_READER);
            }

            if (bbDeviceController == null)
                bbDeviceController = BBDeviceController.getInstance(context, new BbposReader.MyBBDeviceControllerListener());

            this.device = device;
            bbDeviceController.connectBT(device);

        } else {
            if (bbDeviceController != null) {
                bbDeviceController.getDeviceInfo();
            }
        }
    }

    /**
     * this method stop the BBPos BT reader.
     */
    @Override
    public void stopReader() {
        Logger.logWarning(TAG, "stopReader");

        if (bbDeviceController != null && isReaderConnected) {
            bbDeviceController.disconnectBT();

            //No need to stop it or null it
            //bbDeviceController.releaseBBDeviceController();
            //bbDeviceController = null;
        }
    }

    /**
     * Method to reset the BBBPos BT reader.
     */
    @Override
    public void resetReader() {
        Logger.logWarning(TAG, "resetReader");

        if (bbDeviceController != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initReader(context);
                }
            }, 1000);

            /*try {
                stopReader();

                if (!isReaderConnected) {
                    bbDeviceController.connectBT(device);
                    Logger.logWarning(TAG, "resetReader: starting");
                }

            } catch (Exception ex) {
                Log.e("BBposReader", "resetPos: ", ex);
            }*/
        }
    }

    /**
     * Method that fires when the reader is connected.
     *
     * @param bluetoothDevice Device to connect.
     */
    @Override
    protected void onBTConnected(BluetoothDevice bluetoothDevice) {
        Logger.logWarning(TAG, "onBTConnected");

        isReaderConnected = true;

        if (bbDeviceController != null) {
            bbDeviceController.getDeviceInfo();
        }
    }

    /**
     * Method that fires when the reader is disconnected.
     * <p>
     * sets the flag isReaderConnected to false, and hides the dialog.
     */
    @Override
    protected void onBTDisconnected() {
        Logger.logWarning(TAG, "onBTDisconnected");

        isReaderConnected = false;

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        batteryLow = true;


        if (srPagoListener != null)
            srPagoListener.onDeviceUnplugged();

        if (alertMonths != null)
            alertMonths.dismiss();

//        initReader(context);
    }

    @Override
    public void onReturnDeviceInfo(Hashtable<String, String> deviceInfoData) {
        Logger.logWarning(TAG, "onReturnDeviceInfo: " + deviceInfoData.toString());

        isReaderConnected = true;

        try {
            JSONObject jsonDevice = new JSONObject();
            for (Map.Entry<String, String> entry : deviceInfoData.entrySet()) {
                jsonDevice.put(entry.getKey(), entry.getValue());
            }
            jsonDevice.put("type", "Bluetooth");
            jsonDevice.put("brand", "Bbpos");

            Global.setStringKey(getContext(), Definitions.KEY_DEVICE_INFO, jsonDevice.toString());
        } catch (Exception ex) {
            paymentListener.onError(SrPagoDefinitions.Error.READER_TIMEOUT);
            Logger.logError(ex);
            return;
        }
        if (Integer.parseInt(String.valueOf(deviceInfoData.get(Definitions.FIRMWARE_VERSION()).charAt(0))) >= 8) {
//            loadAmexCAPKS();
        }

        deviceInfoData.put("type", "Bluetooth");
        deviceInfoData.put("brand", "Bbpos");
        if (srPagoListener != null)
            srPagoListener.onReturnDeviceInfo(deviceInfoData);
        else {
            initReader(context);
            srPagoListener.onError(SrPagoDefinitions.Error.READER_TIMEOUT);
        }

        try {
            int battery = Integer.parseInt(deviceInfoData.get(Definitions.BATTERY_PERCENTAGE()));
            if (srPagoListener != null)
                srPagoListener.deviceBaterry(battery);
        } catch (Exception ex) {
            Logger.logError(ex);
        }
    }
}
