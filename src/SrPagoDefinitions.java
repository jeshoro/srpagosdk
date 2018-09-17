package sr.pago.sdk;

/**
 * Created by Rodolfo on 23/07/2015.
 */

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
 * <li>BAD_APDU added</li>
 * <li>Date: 15/09/2015</li>
 * </ul>
 *
 * @author Rodolfo Peña - * Sr. Pago All rights reserved.
 * @version 1.1
 * @since 23/07/2015
 */
public class SrPagoDefinitions {
    /**
     * Enumeration for the different types os error that the sdk can throw
     *
     * @since Version 1.1
     */
    public enum Error {
        /**
         * When the amount passed for the payment is less than the minimum permitted from Sr.Pago.
         *
         * @since Version 1.0
         */
        AMOUNT_LESS_THAN_MINIMUM,
        /**
         * When the authentication failed because of a bad bundle identifier.
         *
         * @since Version 1.0
         */
        INVALID_BUNDLE_IDENTIFIER,
        /**
         * When the part of the payment is tried to be use without the reader connected.
         *
         * @since Version 1.0
         */
        DEVICE_NOT_PRESENT,
        /**
         * When the card used for the payment was declined.
         *
         * @since Version 1.0
         */
        CARD_DECLINED,
        /**
         * When there was no card detected when the reader was waiting for one.
         *
         * @since Version 1.0
         */
        NO_CARD_DETECTED,
        /**
         * When the error ocurred reading the card.
         *
         * @since Version 1.0
         */
        CARD_READ,
        /**
         * Class 1 sound interference.
         *
         * @since Version 1.0
         */
        SOUND_INTERFERENCE_1,
        /**
         * Class 2 sound interference
         *
         * @since Version 1.0
         */
        SOUND_INTERFERENCE_2,
        /**
         * When a card with chip was swiped.
         *
         * @since Version 1.0
         */
        CARD_WITH_CHIP,
        /**
         * When a timeout has occurred communicating with the reader.
         *
         * @since Version 1.0
         */
        READER_TIMEOUT,
        /**
         * When an unknown error has occurred with the reader.
         *
         * @since Version 1.0
         */
        READER_UNKNOWN,
        /**
         * When an unknown error has occurred with Sr.Pago sdk.
         *
         * @since Version 1.0
         */
        UNKNOWN,
        /**
         * When a 400 error has occurred with Sr.Pago services.
         *
         * @since Version 1.0
         */
        S400,
        /**
         * When a 500 error has occurred with Sr.Pago services.
         *
         * @since Version 1.0
         */
        S500,
        /**
         * When a the card tried to be read is blocked.
         *
         * @since Version 1.0
         */
        CARD_BLOCKED,
        /**
         * When the card that is going to be read is not supported.
         *
         * @since Version 1.0
         */
        CARD_NOT_SUPPORTED,
        /**
         * When the card was removed in the read process.
         *
         * @since Version 1.0
         */
        CARD_REMOVED,
        /**
         * When there was an error regarding the authorization of the card.
         *
         * @since Version 1.0
         */
        CARD_SERVER_ERROR,
        /**
         * When the reader is occupied making other task.
         *
         * @since Version 1.0
         */
        READER_BUSY,
        /**
         * When there was an error produced by the reader.
         *
         * @since Version 1.0
         */
        WARNING_NOT_ACCEPTED,
        /**
         * When the read process was terminated.
         *
         * @since Version 1.0
         */
        TERMINATED,
        /**
         * When ther is no internet and the developer was trying to get some info.
         *
         * @since Version 1.0
         */
        NO_INTERNET,
        /**
         * When the APDU exchange was a failure
         *
         * @since Version 1.1
         */
        BAD_APDU,
        /**
         * When there has not been a token registered.
         *
         * @since Version 1.1
         */
        NO_TOKEN,
        /**
         * When the token has expired.
         *
         * @since Version 1.1
         */
        TOKEN_EXPIRED,
        /**
         * When the user trying to using the sdk has no permissions to use it.
         *
         * @since Version 1.1
         */
        SQPOS_NO_DETECTED,
        USER_NOT_FOUND,
        TIMEOUT,
        APDU_INTERRUPTED,
        APP_KEY_INVALID,
        EMAIL_INVALID,
        ERROR_CARD,
        SERVICE_UNAVAILABLE,
        SERVER_ERROR,
        BAD_REQUEST,
        AMOUNT_GREATER,
        CARD_INFO_NOT_FOUND,
        INVALID_ACCOUNT,
        USERNAME_REQUIRES,
        ALIAS_INVALID,
        BANK_ACCOUNT,
        TOTAL_MUST,
        DATA,
        CANCEL_CARD,
        SERVICE_CURRENTLY,
        TRANSACTION_INTERRUPTED,
        COMM_ERROR,
        DEVICE_RESET,
        MAC_ADDRESS_MISSING,
        CARD_REQUIRED_PIN,
        CMD_NOT_AVAILABLE,
        TAMPER, PCI_ERROR, INPUT_INVALID, FAIL_TO_START_BT, USB_NOT_SUPPORTED, INPUT_OUT_OF_RANGE, CHANNEL_BUFFER_FULL, FAIL_TO_START_AUDIO, FAIL_TO_START_SERIAL, INPUT_INVALID_FORMAT, USB_DEVICE_NOT_FOUND, CASHBACK_NOT_SUPPORTED, HARDWARE_NOT_SUPPORTED, AUDIO_PERMISSION_DENIED, COMM_LINK_UNINITIALIZED, SERIAL_PERMISSION_DENIED, BLUETOOTH_PERMISSION_DENIED, USB_DEVICE_PERMISSION_DENIED, BLUETOOTH_LOCATION_PERMISSION_DENIED, INVALID_FUNCTION_IN_CURRENT_CONNECTION_MODE, BTV4_NOT_SUPPORTED, DEVICE_BUSY, CRC_ERROR, MAC_ERROR, APDU_ERROR, CMD_TIMEOUT, WR_DATA_ERROR, EMV_APP_CFG_ERROR, INPUT_ZERO_VALUES, EMV_CAPK_CFG_ERROR, ICC_ONLINE_TIMEOUT, AMOUNT_OUT_OF_LIMIT, MSR_NOT_ALLOWED
    }

    public enum Status {
        NONE,
        INIT_READER,
        ON_CARD_WAITING,
        ON_CARD_STARTED,
        QPOS_CONFIG,
        QPOS_CONNECTED,
        SP_INIT_READER,
        SP_CANCEL_TRANSACTION,
        SP_INIT_PERCENTAGE,
        SP_SET_AMOUNT,
        SP_TERMINAL_TIME,
        SP_SELECTED_APLICATION,
        SP_FINAL_CONFIRM,
        SP_CHECK_SERVER_CONNECTIVITY,
        SP_ONLINE_PROCESS,
        SP_RETURN_BATCH_DATA,
        SP_RETURN_TRANSACTION_RESULT,
        SP_CALL_PAYMENT,
        SP_CALL_PAYMENT_CARD,
        SP_MONTHS,
        SP_CALL_SIGNATURE,
        SP_TRANSACTION_CANCELED,
        SP_PIN_ENTRY, SP_PIN_SUCCESS, SP_PIN_FAILURE, SP_CONNECTING_BLUETOOTH
    }

    public enum Reader {
        QPOS_AUDIO_READER,
        QPOS_BLUETOOTH_READER,
        BBPOS_AUDIO_READER,
        BBPOS_BLUETOOTH_READER,
        BLUETOOTH_READER,
        WPOS_MINI_READER,
        BBPOS_SERIAL_READER,
        SPOS_I9000

    }

    public enum DialogStatus {
        STATUS_INIT_BLUETOOTH,
        STATUS_BATTERY,
        STATUS_UNPLUGGED
    }
}
