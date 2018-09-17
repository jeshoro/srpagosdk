package sr.pago.sdk.interfaces;

import java.util.Hashtable;

import sr.pago.sdk.SrPagoDefinitions;

/**
 * <h1>SrPagoListener</h1>
 * Interface for hearing success or errors for authenticating. The onError is called in SrPagoWebServiceListener
 * <h4>Version 1.0</h4>
 * <ul>
 * <li>First Version</li>
 * <li>Date: 23/07/2015</li>
 * </ul>
 * <h4>Version 1.1</h4>
 * <ul>
 * <li>Offline methods added.</li>
 * <li>Date: 15/09/2015</li>
 * </ul>
 *
 * @author Rodolfo Peña - * Sr. Pago All rights reserved.
 * @version 1.1
 * @see SrPagoWebServiceListener
 * @since 23/07/2015
 */
public interface SrPagoListener extends SrPagoWebServiceListener {
    /**
     * Called when there is no device connected.
     *
     * @since Version 1.0
     */
    void onNoDeviceDetected();

    /**
     * Called when the device is connected.
     *
     * @since Version 1.0
     */
    void onDevicePlugged();

    /**
     * Called when the device is disconnected.
     *
     * @since Version 1.0
     */
    void onDeviceUnplugged();

    /**
     * When the device is connected this method is called letting the developer know that the reader is compatible with the phone.
     *
     * @param batteryLevel percentage that the reader has.
     * @since Version 1.0
     */
    void deviceBaterry(int batteryLevel);

    /**
     * Notifies an important message of the reader, actually it calls when the reader is waiting for the card.
     *
     * @param status status of the reader
     * @see SrPagoDefinitions.Status
     * @since Version 1.0
     */
    void onUpdateMessage(SrPagoDefinitions.Status status);

    /**
     * Notifies when a display asterisk is needed
     *
     * @param numOfAsterisk number of asterisks to display
     * @since Version 4.0.5
     */
    void onRequestDisplayAsterisk(int numOfAsterisk);

    /**
     * Notifies when a display asterisk is needed
     *
     * @since Version 4.0.5
     * @param isRetry
     */
    void onRequestPinEntry(boolean isRetry);

    /**
     * Returns the UID and KSN obtained by the offline process of reading the chip.
     *
     * @param uid UID obtained by the reader
     * @since Version 1.1
     */
    void onReturnUIDAndKSN(String uid, String ksn);

    /**
     * Return the APDU obtained by the offline process
     *
     * @param oldApdu raw APDU sent
     * @param newApdu ADPU encrypted
     * @since Version 1.1
     */
    void onReturnAPDU(String oldApdu, String newApdu);

    void onReturnDeviceInfo(Hashtable<String, String> deviceInfoData);
}
