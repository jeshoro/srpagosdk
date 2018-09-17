package sr.pago.sdk.readers.bbpos;

import android.content.Context;
import android.os.Environment;

import com.bbpos.bbdevice.BBDeviceController;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.api.parsers.PixzelleParserResponseFactory;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.utils.Logger;
import sr.pago.sdk.utils.PixzelleUtilities;

/**
 * Created by David Morales on 07/11/2018.
 * <p>
 * Class to manage connection with the BBPos BT reader.
 */
public class BbposAudio extends BbposReader {

    private final String TAG = "BbposAudio";

    @Override
    public PixzelleParserResponseFactory buildResponseFactory() {
        return null;
    }

    /**
     * Public constructor class BBpos Audio.
     *
     * @param context context needed for super class
     */
    public BbposAudio(Context context) {
        super(context);
        bbposReader = this;
    }

    /**
     * This method initialize the BBPos Audio reader.
     *
     * @param context context to get preferences.
     */
    @Override
    public void initReader(Context context) {
        Logger.logWarning(TAG, "initReader");
        /*if (!isReaderConnected) {
            if (srPagoListener != null)
                srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.INIT_READER);

            if (bbDeviceController == null) {
                BBDeviceController.setDebugLogEnabled(false);
                bbDeviceController = BBDeviceController.getInstance(context, new BbposReader.MyBBDeviceControllerListener());
                bbDeviceController.stopAudio();
                bbDeviceController.startAudio();
                bbDeviceController.setDetectAudioDevicePlugged(true);
            }

           *//* if (PixzelleUtilities.getDevice().toLowerCase(Locale.US).contains("sony"))
                bbDeviceController.setAudioAutoConfig("2b0dce5c3b3c4f7854a21073b3a46410bdda2359a4eb95b617603790872946a30f7457c883146aaf05a75b768aae9b025742700f4609e443d4fb39eec2103be9e82ae047c585feaec4ccf72063a75278230e60fe356874a581120982e2ec78a3");
            else
//                bbDeviceController.setAudioAutoConfig("640b5c7dea87fb4c6c6233f7dd2cf8443fcb3511f41be1ed31923519a6e5b9805d351673bfe172cc53959b890753470b3f3de416bff1703f73f99a62bdca3a27c407abffe472113fb2e1ee649d6f96a160bbf699dbdfe01a22a32f59d89c8d48");

            bbDeviceController.startAudioAutoConfig()*//*;
        }*/

        Logger.logDebug("SrPagoReader", "Iniciando clase reader Anywhere");
        if (srPagoListener != null)
            srPagoListener.onUpdateMessage(SrPagoDefinitions.Status.INIT_READER);
        if (bbDeviceController == null) {
            BBDeviceController.setDebugLogEnabled(true);
            bbDeviceController = BBDeviceController.getInstance(context, new BbposReader.MyBBDeviceControllerListener());
            bbDeviceController.stopAudio();
            bbDeviceController.startAudio();
            bbDeviceController.setDetectAudioDevicePlugged(true);
        }
        //bbDeviceController.startAutoConfig();
        if (PixzelleUtilities.getDevice().toLowerCase(Locale.US).contains("sony"))
            bbDeviceController.setAudioAutoConfig("2b0dce5c3b3c4f7854a21073b3a46410bdda2359a4eb95b617603790872946a30f7457c883146aaf05a75b768aae9b025742700f4609e443d4fb39eec2103be9e82ae047c585feaec4ccf72063a75278230e60fe356874a581120982e2ec78a3");
        else
            bbDeviceController.setAudioAutoConfig("640b5c7dea87fb4c6c6233f7dd2cf8443fcb3511f41be1ed31923519a6e5b9805d351673bfe172cc53959b890753470b3f3de416bff1703f73f99a62bdca3a27c407abffe472113fb2e1ee649d6f96a160bbf699dbdfe01a22a32f59d89c8d48");
        if (bbDeviceController.isAudioDevicePlugged()) {
            onAudioDevicePlugged();
        }
    }

    /**
     * this method stop the BBPos Audio reader.
     */
    @Override
    public void stopReader() {
        Logger.logWarning(TAG, "stopReader");
        if (isReaderConnected) {
            if (bbDeviceController != null) {
                bbDeviceController.stopAudio();
                bbDeviceController = null;
            }
        }
    }

    /**
     * Method to reset the BBBPos Audio reader.
     */
    @Override
    public void resetReader() {
        Logger.logWarning(TAG, "resetReader");

        if (bbDeviceController != null) {
            bbDeviceController.stopAudio();
            bbDeviceController.startAudio();

            Logger.logWarning(TAG, "resetReader: reseted");
        }
    }

    /**
     * Method that fires when the reader is plugged.
     */
    @Override
    public void onAudioDevicePlugged() {
        Logger.logWarning(TAG, "onAudioDevicePlugged");

        batteryLow = false;
        if (bbDeviceController != null) {
            bbDeviceController.getDeviceInfo();

            if (srPagoListener != null) {
                srPagoListener.onDevicePlugged();
            }
        }
    }

    /**
     * Method that fires when the reader is unplugged.
     */
    @Override
    public void onAudioDeviceUnplugged() {
        Logger.logWarning(TAG, "onAudioDeviceUnplugged");

        isReaderConnected = false;
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        batteryLow = true;
        if (srPagoListener != null)
            srPagoListener.onDeviceUnplugged();
        if (alertMonths != null)
            alertMonths.dismiss();
    }

    /**
     * Method that fires when the reader is not detected.
     */
    @Override
    public void onNoAudioDeviceDetected() {
        Logger.logWarning(TAG, "onNoAudioDeviceDetected");

        this.srPagoListener.onNoDeviceDetected();
    }

    /**
     * Method that fires when there's a change in progress auto configuring the reader.
     */
    @Override
    public void onAudioAutoConfigProgressUpdate(double percentage) {
        Logger.logWarning(TAG, "onAudioAutoConfigProgressUpdate: " + percentage + "%");
    }

    /**
     * Method that fires when the auto config process has completed.
     */
    @Override
    public void onAudioAutoConfigCompleted(boolean isDefaultSettings, String autoConfigSettings) {
        Logger.logWarning(TAG, "onAudioAutoConfigCompleted: isDefaultSettings ->" + isDefaultSettings + ", autoConfigSettings-> " + autoConfigSettings);

        String outputDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + Definitions.CONFIG_LOCATION();
        String filename = Definitions.CONFIG_FILE();

        if (isDefaultSettings) {
            new File(outputDirectory + filename).delete();
        } else {
            try {
                File directory = new File(outputDirectory);
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(outputDirectory + filename, false);
                fos.write(autoConfigSettings.getBytes());
                fos.flush();
                fos.close();

            } catch (Exception e) {
                Logger.logError(e);
            }
        }
    }

    /**
     * Method that fires when there is a error configuring the audio.
     *
     * @param audioAutoConfigError represents the type of error.
     */
    @Override
    public void onAudioAutoConfigError(BBDeviceController.AudioAutoConfigError audioAutoConfigError) {
        Logger.logWarning(TAG, "onAudioAutoConfigError: " + audioAutoConfigError.name());

        //TODO: notify view about this error.
        switch (audioAutoConfigError) {
            case PHONE_NOT_SUPPORTED:
                break;
            case INTERRUPTED:
                break;
        }
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
            jsonDevice.put("type", "Audio");
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

        deviceInfoData.put("type", "Audio");
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

    /*@Override
    public void onReturnStartEmvResult(BBDeviceController.StartEmvResult result, String ksn) {
        Logger.logMessage(Definitions.SR_PAGO(), getContext().getResources().getString(R.string.sr_pago_payment_step_3));
    }*/

    /*@Override
    public void onReturnApduResultWithPkcs7Padding(boolean isSuccess, String apdu) {
        Logger.logMessage(Definitions.SUCCESS(), Definitions.EMPTY() + isSuccess);
        if (isSuccess) {
            if (srPagoListener != null)
                srPagoListener.onReturnAPDU(oldAPDU, apdu);
        } else {
            if (srPagoListener != null)
                srPagoListener.onError(SrPagoDefinitions.Error.BAD_APDU);
        }
        bbDeviceController.powerOffIcc();
    }*/

}
