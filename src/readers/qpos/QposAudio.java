package sr.pago.sdk.readers.qpos;


import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.dspread.xpos.QPOSService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import sr.pago.sdk.api.parsers.PixzelleParserResponseFactory;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.utils.Logger;

/**
 * Created by David Morales on 07/11/2018.
 * <p>
 * Class to manage connection with the QPos Audio reader.
 */
public class QposAudio extends QposReader {

    AudioManager audioManager;

    @Override
    public PixzelleParserResponseFactory buildResponseFactory() {
        return null;
    }

    /**
     * Public constructor class QPos Audio.
     *
     * @param context context needed for super class
     */
    public QposAudio(Context context) {
        super(context);

        Logger.logWarning("QposAudio", "Creating instance");

        qposReader = this;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
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

    /**
     * This method initialize the QPos Audio reader.
     *
     * @param context context to get preferences.
     */
    @Override
    public void initReader(Context context) {
        Logger.logWarning("QposAudio", "initReader");

        Logger.logWarning("QposAudio", "initReader: getting instance");
        qposService = QPOSService.getInstance(QPOSService.CommunicationMode.AUDIO);

        if (qposService == null) {
            Logger.logWarning("QposAudio", "initReader: NoQposDetected");
            qposReader.onRequestNoQposDetected();
            return;
        }

        qposService.setContext(context);
        Handler handler = new Handler(Looper.myLooper());
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        Logger.logWarning("QposAudio", "initReader: volume");
        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

        Logger.logWarning("QposAudio", "initReader: qposservice init");
        qposService.initListener(handler, new MyQposControllerListener());

        qposService.setVolumeFlag(false);
        Logger.logWarning("QposAudio", "initReader: opening audio");
        qposService.openAudio();

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (patchConfig) {
            Logger.logWarning("QposAudio", "Patch: true");
            audioManager.setMode(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
    }

    /**
     * this method stop the QPos Audio reader.
     */
    @Override
    public void stopReader() {
        Logger.logWarning("QposAudio", "stopReader");

        if (qposService != null) {
            try {
                qposService.closeAudio();
            } catch (IllegalArgumentException ex) {
                try {
                    qposService.resetQPOS();
                } catch (Exception e) {

                }
            }
            try {
                qposService.onDestroy();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * Method to reset the QPos Audio reader.
     */
    @Override
    public void resetReader() {
        Logger.logWarning("QposAudio", "resetReader");

        if (qposService != null) {

            qposService.closeAudio();
            qposService.openAudio();
        }
    }

    /**
     * Method that fires when the QPos Audio reader returns the device info.
     */
    @Override
    public void onQposInfoResult(Hashtable<String, String> hashtable) {
        Logger.logWarning("QposAudio", "onQposInfoResult: " + hashtable.toString());

        if (qposService != null) {
            if (patchConfig) {
                Global.setBooleanPreference(context, Definitions.KEY_READER_IS_PATCH_CONFIG, true);
            }
            isReaderConnected = true;
            handleDeviceInfo(hashtable, "audio");
        }
    }

    /**
     * Method that fires when the QPos Audio reader is connected.
     */
    @Override
    public void onRequestQposConnected() {
        Logger.logWarning("QposAudio", "onRequestQposConnected");

        if (qposService != null) {
            qposService.getQposId();
            if (srPagoListener != null) {
                srPagoListener.onDevicePlugged();
            }
        }
    }

    /**
     * Method that fires when the QPos Audio reader is disconnected.
     */
    @Override
    public void onRequestQposDisconnected() {
        Logger.logWarning("QposAudio", "onRequestQposDisconnected");

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
}
