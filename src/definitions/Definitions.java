package sr.pago.sdk.definitions;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;

import sr.pago.sdk.connection.PixzelleWebServiceConnection;

/**
 * Created by Rodolfo on 09/08/2015.
 */
public class Definitions {
    public final static String PREF_MSI_ACTIVATED = "key_msi_activated";
    public final static String PREF_MSI_3 = "key_msi_3";
    public final static String PREF_MSI_6 = "key_msi_6";
    public final static String PREF_MSI_9 = "key_msi_9";
    public final static String PREF_MSI_12 = "key_msi_12";
    public final static String PREF_MSI_FROM = "key_msi_from";
    public final static String PREF_BAND = "key_band_card";
    public final static String PREF_TIP = "key_tip";
    public final static String PREF_REFERENCE = "key_reference";
    public final static String PREF_AUTOMATIC_WITHDRAWALS = "key_automatic_withdrawals";
    public final static String PREF_SHOW_AUTOMATIC_WITHDRAWALS = "key_show_automatic_withdrawals";
    public final static String PREF_SHOW_WHATS_NEW = "key_show_whats_new";
    public final static String PREF_SEARCHED = "key_searched";

    public final static String KEY_V4_OPERATOR = "key_operator_v4";
    public final static String KEY_V3_NAME = "key_name_v3";
    public final static String KEY_V3_COMPLETE_NAME = "key_complete_name_v3";
    public final static String KEY_V3_ADDRESS = "key_address_v3";
    public final static String KEY_V3_COMPANY = "key_company_v3";
    public final static String KEY_V3_RFC = "key_rfc_v3";
    public final static String KEY_V3_PHONE = "key_phone_v3";
    public final static String KEY_V3_AVATAR = "key_avatar_v3";
    public final static String KEY_V3_THIRD_PARTY_ACCOUNTS = "key_third_party_accounts_v3";
    public static final String KEY_V3_VAR_COMMISSION = "key_variable_commission";
    public static final String KEY_V3_FIX_COMMISSION = "key_fixed_commission";

    private static String credentials;

    public static String getCredentials() {
        return credentials;
    }

    public static void setCredentials(String key, String secret) {
        String token = "Basic " + Base64.encodeToString((key + ":" + secret).getBytes(), Base64.DEFAULT);
        Definitions.credentials = token.substring(0, token.length() - 1);
    }

    public static String ACCEPT() {
        return obfuscated(new byte[]{65, 99, 101, 112, 116, 97, 114});
    }

    public static String AES() {
        return obfuscated(new byte[]{65, 69, 83});
    }

    public static String AES_KEY() {
        return obfuscated(new byte[]{77, 73, 73, 67, 73, 106, 65, 78, 66, 103, 107, 113, 104, 107, 105, 71, 57, 119, 48, 66, 65, 81, 69, 70, 65, 65, 79, 67, 65, 103, 56, 65, 77, 73, 73, 67, 67, 103, 75, 67, 65, 103, 69, 65, 118, 48, 117, 116, 76, 70, 106, 119, 72, 81, 107, 43, 49, 97, 76, 106, 120, 108, 57, 116, 79, 106, 118, 116, 47, 113, 70, 68, 49, 72, 102, 77, 70, 122, 106, 89, 97, 52, 100, 51, 105, 70, 75, 114, 81, 116, 118, 120, 97, 87, 77, 47, 66, 47, 54, 108, 116, 80, 110, 54, 43, 80, 101, 122, 43, 100, 79, 100, 53, 57, 122, 70, 109, 122, 78, 72, 103, 51, 51, 104, 56, 83, 48, 112, 97, 90, 54, 119, 109, 78, 118, 51, 109, 119, 112, 52, 104, 67, 74, 116, 116, 71, 122, 70, 118, 108, 50, 104, 104, 119, 56, 90, 43, 79, 85, 57, 75, 119, 71, 83, 88, 103, 81, 43, 53, 70, 78, 121, 82, 121, 68, 76, 112, 48, 113, 116, 55, 53, 97, 121, 118, 86, 48, 118, 86, 56, 111, 88, 48, 80, 103, 117, 98, 100, 47, 78, 84, 72, 122, 82, 75, 107, 48, 117, 98, 88, 79, 56, 87, 86, 87, 107, 78, 104, 77, 100, 115, 118, 48, 72, 71, 114, 104, 73, 77, 68, 88, 65, 87, 76, 65, 81, 66, 122, 68, 101, 119, 109, 73, 67, 86, 72, 57, 77, 73, 74, 122, 106, 111, 90, 121, 109, 82, 55, 65, 117, 78, 112, 101, 102, 68, 52, 104, 111, 86, 75, 56, 99, 66, 77, 106, 90, 48, 120, 82, 75, 83, 80, 121, 100, 51, 122, 73, 54, 117, 74, 121, 69, 82, 99, 82, 51, 43, 78, 57, 110, 120, 118, 103, 52, 103, 117, 83, 104, 80, 50, 55, 99, 110, 68, 57, 113, 112, 76, 116, 52, 76, 54, 89, 116, 85, 48, 66, 85, 43, 104, 117, 115, 70, 88, 111, 72, 76, 54, 89, 50, 67, 115, 120, 121, 122, 120, 84, 57, 109, 116, 111, 114, 65, 71, 101, 53, 111, 82, 105, 84, 67, 55, 90, 47, 83, 57, 117, 55, 112, 120, 71, 78, 52, 105, 111, 122, 103, 109, 65, 101, 105, 48, 77, 90, 86, 98, 75, 111, 119, 115, 47, 113, 97, 57, 47, 113, 48, 80, 80, 122, 98, 70, 47, 80, 72, 83, 90, 75, 111, 117, 49, 68, 74, 118, 115, 74, 50, 80, 75, 89, 51, 90, 80, 89, 65, 84, 55, 47, 117, 52, 120, 56, 78, 82, 105, 74, 47, 54, 99, 115, 115, 117, 122, 115, 73, 80, 85, 100, 81, 57, 72, 66, 122, 65, 49, 90, 66, 77, 72, 107, 112, 79, 109, 107, 105, 112, 117, 49, 71, 55, 107, 115, 47, 71, 119, 84, 102, 81, 74, 107, 72, 80, 87, 53, 120, 72, 117, 49, 69, 79, 89, 118, 103, 118, 47, 80, 72, 114, 51, 66, 74, 110, 67, 77, 78, 89, 75, 70, 118, 102, 53, 99, 52, 81, 100, 48, 67, 79, 110, 110, 85, 51, 106, 68, 101, 108, 49, 79, 75, 108, 55, 108, 85, 122, 114, 43, 105, 111, 113, 85, 101, 100, 88, 51, 57, 51, 68, 47, 102, 115, 122, 100, 75, 52, 104, 106, 118, 116, 85, 106, 111, 54, 84, 104, 84, 82, 78, 109, 51, 121, 52, 97, 118, 89, 47, 114, 109, 43, 111, 76, 117, 56, 115, 90, 87, 112, 121, 66, 109, 52, 80, 102, 78, 50, 120, 71, 79, 110, 70, 99, 111, 57, 83, 105, 121, 67, 84, 48, 51, 88, 79, 69, 117, 79, 88, 111, 107, 105, 100, 54, 66, 68, 77, 105, 48, 97, 117, 101, 57, 76, 75, 74, 97, 81, 82, 43, 75, 71, 86, 99, 47, 72, 50, 112, 50, 100, 50, 89, 117, 52, 71, 100, 103, 88, 83, 49, 118, 113, 49, 115, 121, 97, 102, 55, 86, 48, 81, 80, 79, 109, 97, 109, 84, 79, 121, 74, 82, 90, 52, 53, 85, 111, 76, 102, 66, 82, 66, 56, 110, 89, 66, 71, 68, 111, 48, 109, 80, 82, 55, 71, 73, 111, 110, 54, 77, 56, 83, 109, 71, 71, 115, 84, 111, 51, 86, 48, 76, 43, 78, 105, 57, 98, 78, 74, 72, 97, 56, 67, 65, 119, 69, 65, 65, 81, 61, 61});
    }

    public static String ALREADY() {
        return obfuscated(new byte[]{65, 108, 114, 101, 97, 100, 121, 32, 97, 117, 116, 104, 101, 110, 116, 105, 99, 97, 116, 101, 100});
    }

    public static String AMEX() {
        return obfuscated(new byte[]{65, 77, 69, 88});
    }

    public static String APPROVED() {
        return obfuscated(new byte[]{65, 112, 112, 114, 111, 118, 101, 100});
    }

    public static String APP_JSON() {
        return obfuscated(new byte[]{97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 47, 106, 115, 111, 110});
    }

    public static String APP_KEY_FORMAT() {
        return obfuscated(new byte[]{97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 95, 107, 101, 121, 32, 110, 111, 116, 32, 114, 101, 113, 117, 105, 114, 101, 100, 32, 102, 111, 114, 109, 97, 116});
    }

    public static String ATR() {
        return obfuscated(new byte[]{97, 116, 114});
    }

    public static String AUTH() {
        return obfuscated(new byte[]{65, 117, 116, 104});
    }

    public static String AUTH_TOKEN() {
        return obfuscated(new byte[]{65, 117, 116, 104, 111, 114, 105, 122, 97, 116, 105, 111, 110, 84, 111, 107, 101, 110});
    }

    public static String BATTERY_OUT() {
        return obfuscated(new byte[]{76, 97, 32, 98, 97, 116, 101, 114, -61, -83, 97, 32, 100, 101, 32, 116, 117, 32, 108, 101, 99, 116, 111, 114, 32, 115, 101, 32, 101, 115, 116, -61, -95, 32, 97, 103, 111, 116, 97, 110, 100, 111, 46});
    }

    public static String BATTERY_PERCENTAGE() {
        return obfuscated(new byte[]{98, 97, 116, 116, 101, 114, 121, 80, 101, 114, 99, 101, 110, 116, 97, 103, 101});
    }

    public static String BEARER() {
        return obfuscated(new byte[]{66, 101, 97, 114, 101, 114});
    }

    public static String CANCEL() {
        return obfuscated(new byte[]{67, 97, 110, 99, 101, 108, 97, 114});
    }

    public static String CAPK_CHECKSUM() {
        return obfuscated(new byte[]{65, 55, 50, 54, 54, 65, 66, 65, 69, 54, 52, 66, 52, 50, 65, 51, 54, 54, 56, 56, 53, 49, 49, 57, 49, 68, 52, 57, 56, 53, 54, 69, 49, 55, 70, 56, 70, 66, 67, 68});
    }

    public static String CAPK_EXPONENT() {
        return obfuscated(new byte[]{48, 51});
    }

    public static String CAPK_INDEX() {
        return obfuscated(new byte[]{48, 69});
    }

    public static String CAPK_LOCATION() {
        return obfuscated(new byte[]{51, 48});
    }

    public static String CAPK_MODULUS() {
        return obfuscated(new byte[]{65, 65, 57, 52, 65, 56, 67, 54, 68, 65, 68, 50, 52, 70, 57, 66, 65, 53, 54, 65, 50, 55, 67, 48, 57, 66, 48, 49, 48, 50, 48, 56, 49, 57, 53, 54, 56, 66, 56, 49, 65, 48, 50, 54, 66, 69, 57, 70, 68, 48, 65, 51, 52, 49, 54, 67, 65, 57, 65, 55, 49, 49, 54, 54, 69, 68, 53, 48, 56, 52, 69, 68, 57, 49, 67, 69, 68, 52, 55, 68, 68, 52, 53, 55, 68, 66, 55, 69, 54, 67, 66, 67, 68, 53, 51, 69, 53, 54, 48, 66, 67, 53, 68, 70, 52, 56, 65, 66, 67, 51, 56, 48, 57, 57, 51, 66, 54, 68, 53, 52, 57, 70, 53, 49, 57, 54, 67, 70, 65, 55, 55, 68, 70, 66, 50, 48, 65, 48, 50, 57, 54, 49, 56, 56, 69, 57, 54, 57, 65, 50, 55, 55, 50, 69, 56, 67, 52, 49, 52, 49, 54, 54, 53, 70, 56, 66, 66, 50, 53, 49, 54, 66, 65, 50, 67, 55, 66, 53, 70, 67, 57, 49, 70, 56, 68, 65, 48, 52, 69, 56, 68, 53, 49, 50, 69, 66, 48, 70, 54, 52, 49, 49, 53, 49, 54, 70, 66, 56, 54, 70, 67, 48, 50, 49, 67, 69, 55, 69, 57, 54, 57, 68, 65, 57, 52, 68, 51, 51, 57, 51, 55, 57, 48, 57, 65, 53, 51, 65, 53, 55, 70, 57, 48, 55, 67, 52, 48, 67, 50, 50, 48, 48, 57, 68, 65, 55, 53, 51, 50, 67, 66, 51, 66, 69, 53, 48, 57, 65, 69, 49, 55, 51, 66, 51, 57, 65, 68, 54, 65, 48, 49, 66, 65, 53, 66, 66, 56, 53});
    }

    public static String CAPK_RID() {
        return obfuscated(new byte[]{65, 48, 48, 48, 48, 48, 48, 48, 50, 53});
    }

    public static String CAPK_SIZE() {
        return obfuscated(new byte[]{57, 48});
    }

    public static String CARD() {
        return obfuscated(new byte[]{67, 97, 114, 100});
    }

    public static String CARD_HOLDER_NAME() {
        return obfuscated(new byte[]{99, 97, 114, 100, 104, 111, 108, 100, 101, 114, 78, 97, 109, 101});
    }

    public static String CIPHER() {
        return obfuscated(new byte[]{82, 83, 65, 47, 69, 67, 66, 47, 80, 75, 67, 83, 49, 80, 65, 68, 68, 73, 78, 71});
    }

    public static String COMISSIONS() {
        return obfuscated(new byte[]{42, 67, 111, 109, 105, 115, 105, 111, 110, 101, 115, 32, 109, -61, -95, 115, 32, 73, 86, 65});
    }

    public static String COMPLETED() {
        return obfuscated(new byte[]{67, 111, 109, 112, 108, 101, 116, 101, 100});
    }

    public static String CONFIG_COMPLETED() {
        return obfuscated(new byte[]{67, 111, 110, 102, 105, 103, 32, 99, 111, 109, 112, 108, 101, 116, 101, 100});
    }

    public static String CONFIG_FILE() {
        return obfuscated(new byte[]{115, 101, 116, 116, 105, 110, 103, 115, 46, 116, 120, 116});
    }

    public static String CONFIG_LOCATION() {
        return obfuscated(new byte[]{47, 65, 110, 100, 114, 111, 105, 100, 47, 100, 97, 116, 97, 47, 99, 111, 109, 46, 98, 98, 112, 111, 115, 46, 101, 109, 118, 115, 119, 105, 112, 101, 46, 117, 105, 47});
    }

    public static String CONFIRM() {
        return obfuscated(new byte[]{67, 111, 110, 102, 105, 114, 109, 97, 114});
    }

    public static String CONTENT_TYPE() {
        return obfuscated(new byte[]{67, 111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101});
    }

    public static String DEBUG_APP_SECRET() {
        return obfuscated(new byte[]{115, 114, 95, 112, 97, 103, 111, 95, 100, 101, 98, 117, 103, 95, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 95, 115, 101, 99, 114, 101, 116});
    }

    public static String DEBUG_APP_TOKEN() {
        return obfuscated(new byte[]{115, 114, 95, 112, 97, 103, 111, 95, 100, 101, 98, 117, 103, 95, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 95, 116, 111, 107, 101, 110});
    }

    public static String DELETE() {
        return obfuscated(new byte[]{68, 69, 76, 69, 84, 69});
    }

    public static String DES() {
        return obfuscated(new byte[]{100, 101, 115, 101, 100, 101, 47, 69, 67, 66, 47, 80, 75, 67, 83, 55, 80, 97, 100, 100, 105, 110, 103});
    }

    public static String DES_3() {
        return obfuscated(new byte[]{100, 101, 115, 101, 100, 101});
    }

    public static String DEVICE_PLUGGED() {
        return obfuscated(new byte[]{68, 101, 118, 105, 99, 101, 32, 112, 108, 117, 103, 103, 101, 100});
    }

    public static String obfuscated(byte[] bytes) {
        return new String(bytes);
    }

    public static String DPIS() {
        return obfuscated(new byte[]{68, 80, 73, 83});
    }

    public static String EMPTY() {
        return obfuscated(new byte[]{});
    }

    public static String EMV_ERROR() {
        return obfuscated(new byte[]{69, 77, 86, 32, 69, 114, 114, 111, 114});
    }

    public static String EMV_1() {
        return obfuscated(new byte[]{37, 115, 37, 115, 37, 115, 37, 115, 37, 115});
    }

    public static String EMV_2() {
        return obfuscated(new byte[]{37, 115, 37, 115, 37, 115});
    }

    public static String EMV_FIRST() {
        return obfuscated(new byte[]{56, 65, 48, 50});
    }

    public static String EMV_KSN() {
        return obfuscated(new byte[]{101, 109, 118, 75, 115, 110});
    }

    public static String EMV_SECOND() {
        return obfuscated(new byte[]{57, 49});
    }

    public static String EMV_THIRD() {
        return obfuscated(new byte[]{55, 50});
    }

    public static String ENC_TRACK_1() {
        return obfuscated(new byte[]{101, 110, 99, 84, 114, 97, 99, 107, 49});
    }

    public static String ENC_TRACK_2() {
        return obfuscated(new byte[]{101, 110, 99, 84, 114, 97, 99, 107, 50});
    }

    public static String EOF() {
        return obfuscated(new byte[]{26});
    }

    public static String ERROR() {
        return obfuscated(new byte[]{69, 114, 114, 111, 114});
    }

    public static String ERROR_READING() {
        return obfuscated(new byte[]{69, 114, 114, 111, 114, 32, 114, 101, 97, 100, 105, 110, 103, 32, 115, 111, 109, 101, 116, 104, 105, 110, 103, 32, 105, 110, 32, 83, 114, 46, 80, 97, 103, 111});
    }

    public static String ERROR_READING_AMEX() {
        return obfuscated(new byte[]{69, 114, 114, 111, 114, 32, 114, 101, 97, 100, 105, 110, 103, 32, 65, 77, 69, 88});
    }

    public static String EXPIRY_DATE() {
        return obfuscated(new byte[]{101, 120, 112, 105, 114, 121, 68, 97, 116, 101});
    }

    public static String FALSE() {
        return obfuscated(new byte[]{102, 97, 108, 115, 101});
    }

    public static String FINAL_TIME() {
        return obfuscated(new byte[]{70, 105, 110, 97, 108, 32, 116, 105, 109, 101});
    }

    public static String FIRMWARE() {
        return obfuscated(new byte[]{70, 105, 114, 109, 119, 97, 114, 101, 32, 86, 101, 114, 115, 105, 111, 110});
    }

    public static String FIRMWARE_VERSION() {
        return obfuscated(new byte[]{102, 105, 114, 109, 119, 97, 114, 101, 86, 101, 114, 115, 105, 111, 110});
    }

    public static String FORMATTED_NUMBER() {
        return obfuscated(new byte[]{48, 37, 100});
    }

    public static String GET() {
        return obfuscated(new byte[]{71, 69, 84});
    }

    public static String GMT() {
        return obfuscated(new byte[]{71, 77, 84});
    }

    public static String GOTHAM() {
        return obfuscated(new byte[]{103, 111, 116, 104, 97, 109, 95, 108, 105, 103, 104, 116, 46, 111, 116, 102});
    }

    public static String GPS_DISACTIVATED() {
        return obfuscated(new byte[]{69, 108, 32, 103, 112, 115, 32, 115, 101, 32, 101, 110, 99, 117, 101, 110, 116, 114, 97, 32, 100, 101, 115, 97, 99, 116, 105, 118, 97, 100, 111, 44, 32, 112, 111, 114, 32, 102, 97, 118, 111, 114, 32, 101, 110, 99, 105, -61, -87, 110, 100, 97, 108, 111, 46});
    }

    public static String HEADER_CONTENT() {
        return obfuscated(new byte[]{37, 115, 32, 37, 115});
    }

    public static String HTTP() {
        return obfuscated(new byte[]{104, 116, 116, 112});
    }

    public static String HTTPS() {
        return obfuscated(new byte[]{104, 116, 116, 112, 115});
    }

    public static String ICC() {
        return obfuscated(new byte[]{73, 67, 67});
    }

    public static String INTERRUPTED() {
        return obfuscated(new byte[]{73, 110, 116, 101, 114, 114, 117, 112, 116, 101, 100});
    }

    public static String INVALID_APP_EXCEPTION() {
        return obfuscated(new byte[]{73, 110, 118, 97, 108, 105, 100, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 69, 120, 99, 101, 112, 116, 105, 111, 110});
    }

    public static String IS_NULL() {
        return obfuscated(new byte[]{73, 115, 32, 110, 117, 108, 108});
    }

    public static String KEY_AMEX_DEBUG() {
        return obfuscated(new byte[]{63, 109, 78, 52, 94, 97, 50, 61, 101, 98, 37, 72, 88, 51, 86, 56, 65, 82, 87, 115, 61, 86, 54, 115});
    }

    public static String KEY_AMEX_RELEASE() {
        return obfuscated(new byte[]{40, 55, 51, 121, 94, 106, 120, 84, 65, 113, 65, 43, 67, 87, 104, 115, 81, 43, 50, 94, 111, 51, 68, 56});
    }

    public static String KEY_AMOUNT() {
        return obfuscated(new byte[]{97, 109, 111, 117, 110, 116});
    }

    public static String KEY_CARD_HOLDER() {
        return obfuscated(new byte[]{99, 97, 114, 100, 72, 111, 108, 100, 101, 114});
    }

    public static String KEY_DATA_RECEIPT() {
        return obfuscated(new byte[]{114, 101, 99, 101, 105, 112, 116});
    }

    public static String KEY_DEVICE_INFO() {
        return obfuscated(new byte[]{100, 101, 118, 105, 99, 101, 73, 110, 102, 111});
    }

    public static String KEY_EMV() {
        return obfuscated(new byte[]{101, 109, 118});
    }

    public static String KEY_EXPIRATION_TOKEN() {
        return obfuscated(new byte[]{101, 120, 112, 105, 114, 97, 116, 105, 111, 110, 84, 111, 107, 101, 110});
    }

    public static String KEY_EXT_TRANSACTION() {
        return obfuscated(new byte[]{101, 120, 116, 101, 114, 110, 97, 108, 84, 114, 97, 110, 115, 97, 99, 116, 105, 111, 110});
    }

    public static String KEY_PASS() {
        return obfuscated(new byte[]{112, 97, 115, 115});
    }

    public static String KEY_RECEIPT() {
        return obfuscated(new byte[]{114, 101, 99, 101, 105, 112, 116});
    }

    public static String KEY_REGISTER_TOKEN() {
        return obfuscated(new byte[]{114, 101, 103, 105, 115, 116, 101, 114, 84, 111, 107, 101, 110});
    }

    public static String SP_KEY_IS_OPERATOR() {
        return "srpagosdk_is_operator";
    }

    public static String KEY_NAME_OPERATOR() {
        return "nameOperator";
    }


    public static String KEY_EMAIL_OPERATOR() {
        return "emailOperator";
    }

    public static String KEY_EMAIL_ADMIN() {
        return "emailAdmin";
    }


    public static String KEY_PHONE_ADMIN() {
        return "phoneAdmin";
    }


    public static String KEY_FIRMWARE_DEVICE() {
        return "firmwareDevice";
    }


    public static String KEY_SIGNATURE() {
        return obfuscated(new byte[]{115, 105, 103, 110, 97, 116, 117, 114, 101});
    }

    public static String KEY_TRANSACTION_TOKEN() {
        return obfuscated(new byte[]{116, 114, 97, 110, 115, 97, 99, 116, 105, 111, 110, 84, 111, 107, 101, 110});
    }

    public static String KEY_USER() {
        return obfuscated(new byte[]{117, 115, 101, 114});
    }

    public static String KSN() {
        return obfuscated(new byte[]{107, 115, 110});
    }

    public static String MASKED_PAN() {
        return obfuscated(new byte[]{109, 97, 115, 107, 101, 100, 80, 65, 78});
    }

    public static String MONTHS() {
        return obfuscated(new byte[]{109, 101, 115, 101, 115});
    }

    public static String MONTHS_APPLY() {
        return obfuscated(new byte[]{65, 112, 108, 105, 99, 97, 114, 32, 112, 108, 97, 122, 111, 115});
    }

    public static String NFC() {
        return obfuscated(new byte[]{78, 70, 67});
    }

    public static String NO() {
        return obfuscated(new byte[]{78, 111});
    }

    public static String NO_CARD_QUIT() {
        return obfuscated(new byte[]{78, 111, 32, 114, 101, 116, 105, 114, 101, 32, 108, 97, 32, 116, 97, 114, 106, 101, 116, 97, 44, 32, 101, 115, 112, 101, 114, 97, 32, 108, 97, 32, 99, 111, 110, 102, 105, 114, 109, 97, 99, 105, -61, -77, 110, 32, 102, 105, 110, 97, 108, 46});
    }

    public static String OK() {
        return obfuscated(new byte[]{79, 75});
    }

    public static String OK_BASE() {
        return obfuscated(new byte[]{79, 107, 32, 66, 97, 115, 101});
    }

    public static String ONE_PAYMENT() {
        return obfuscated(new byte[]{85, 110, 32, 115, -61, -77, 108, 111, 32, 112, 97, 103, 111, 46});
    }

    public static String ONLINE_PROCESS() {
        return obfuscated(new byte[]{56, 65, 48, 50, 51, 48, 51, 48});
    }

    public static String PAYMENT_FINAL() {
        return obfuscated(new byte[]{37, 115, 32, 37, 100, 32, 37, 115, 32, 40, 37, 46, 50, 102, 37, 37, 32, 43, 32, 37, 46, 50, 102, 37, 37, 41, 10, 37, 115, 32, 36, 37, 46, 50, 102, 10, 37, 115});
    }

    public static String PAY_TO() {
        return obfuscated(new byte[]{80, 97, 103, 111, 32, 97});
    }

    public static String PHONE_NOT_SUPPORTED() {
        return obfuscated(new byte[]{80, 104, 111, 110, 101, 32, 110, 111, 116, 32, 115, 117, 112, 112, 111, 114, 116, 101, 100});
    }

    public static String POST() {
        return obfuscated(new byte[]{80, 79, 83, 84});
    }

    public static String PROCESSING_PAYMENT() {
        return obfuscated(new byte[]{80, 114, 111, 99, 101, 115, 97, 110, 100, 111, 32, 80, 97, 103, 111, 46});
    }

    public static String PUT() {
        return obfuscated(new byte[]{80, 85, 84});
    }

    public static String READER_ERROR() {
        return obfuscated(new byte[]{82, 101, 97, 100, 101, 114, 32, 101, 114, 114, 111, 114});
    }

    public static String REALEASE_APP_SECRET() {
        return obfuscated(new byte[]{115, 114, 95, 112, 97, 103, 111, 95, 114, 101, 108, 101, 97, 115, 101, 95, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 95, 115, 101, 99, 114, 101, 116});
    }

    public static String RELEASE_APP_TOKEN() {
        return obfuscated(new byte[]{115, 114, 95, 112, 97
                , 103, 111, 95, 114, 101, 108, 101, 97, 115, 101, 95, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 95, 116, 111, 107, 101, 110});
    }

    public static String oneTime = "oneTime";
    public final static int LOGIN = 0;
    public final static int GET_ACCOUNT = 1;
    public final static int PAYMENT = 2;
    public final static int PAYMENT_CARD = 3;
    public final static int PAYMENT_SIGNATURE = 4;
    public final static int APPLICATION_LOGIN = 5;
    public final static int DEVOLUTION = 6;
    public final static int SR_PAGO_CARD = 7;  //7
    public final static int OPERATIONS = 8;
    public final static int TICKETS_ID = 9;
    public final static int OPERATIONS_SEND = 10;
    public final static int OPERATIONS_FUNDS = 11;
    public final static int BALANCE = 12;
    public final static int TRANSACTIONS = 13;
    public final static int GET_OPERATORS = 14;
    public final static int ACCOUNTS = 15;
    public final static int SERVICES = 16;
    public final static int RECHARGE = 17;
    public final static int STORE = 18;
    public final static int AUTH_LOGOUT = 19;
    public final static int BANK_ACCOUNTS = 20;
    public final static int TRANSFER_ID = 21;
    public final static int DELETE_BANK_ACCOUNT = 22;
    public final static int RESET_PASSWORD = 23;
    public final static int OPERATOR_DELETE = 24;
    public final static int ADD_OPERATOR = 25;
    public final static int NOTIFICATIONS = 26;
    public final static int NOTIFICATION_POST = 27;
    public final static int PAYMENT_STORE = 28;
    public final static int OPERATIONS_FOUNTS_BALANCE = 29;
    public final static int TRANSFER_BALANCE = 30;
    // public final static int ACCOUNT_IMG = 31;
    public final static int UPLOAD_AVATAR = 31;
    public final static int AVATAR = 32;
    public final static int ABM = 33;
    public final static int BANK_ACCOUNT_POST = 34;
    public final static int SEND_TICKET = 35;
    public final static int OPERATION = 36;
    public final static int UPDATE_OPERATOR = 37;
    public final static int CANCEL_CARD = 38;
    public final static int SP_ACCOUNT_INFO = 39;
    public final static int SP_REQUEST_MONTHS = 40;
    public final static int SP_CANCEL_OPERATION = 41;
    public final static int SP_TAIL_SIGNATURES = 42;
    public final static int REPLACEMENT_CARD = 43;
    public final static int PAYMENT_MOBILE = 44;
    public final static int PAYMENT_MOBILE_CONFIRMATION = 45;
    public final static int OPERATIONS_BALANCE = 46;

    public final static int REGISTER_DEVICE = 47;
    public final static int UNREGISTER_DEVICE = 48;
    public final static int VERSION_CHECK = 49;
    public final static int BANK_ACCOUNT_DEFAULT = 50;
    public final static int GET_ZIPCODE = 51;
    public final static int REGISTER_STATUS = 52;
    public final static int GET_BUSINESS_CATALOG = 53;
    public final static int REGISTER = 54;
    public final static int LINK_FB_ACCOUNT = 55;
    public final static int LOGIN_FB = 56;
    public final static int VALIDATION_PHONE = 57;
    public final static int VALIDATION_PHONE_SEND_CODE = 58;
    public final static int VALIDATION_PHONE_EDIT_PHONE_NUMBER = 59;
    public final static int RECOVERY_PASSWORD = 60;
    public final static int VALIDATE_DOCUMENTS_PAYMENT = 61;
    public final static int UPLOAD_DOCUMENTS_PAYMENT = 62;
    public final static int GET_SERVER_TIME = 63;
    public final static int UPDATE_SETTINGS = 64;
    public final static int GET_USER_SETTINGS = 65;
    public final static int CODE_REDEEM = 66;
    public final static int CODE_DISTRIBUTOR = 67;
    public static final int CONTRACT = 68;
    public static final int CONTRACT_UPDATE = 69;
    public static final int CONTRACT_DOCUMENTS_STATUS = 100;
    public static final int CONTRACT_UPLOAD_DOCUMENT = 101;

    public final static int TICKETS = 102;
    public final static int RESTORE_NIP = 103;
    public final static int WITHDRAWALS = 104;
    public final static int SET_AUTOMATIC_WITHDRAWALS = 105;
    public final static int VALIDATE_PROMOTION = 106;
    public final static int CARD_TYPE = 107;
    public final static int CARD_PREPAID = 108;

    public final static int CALCULATOR_SET_AMOUNT = 601;
    public final static int QPOSREADER_CONNECT = 71;
    public final static int QPOSREADER_INIT = 72;
    public final static int SETTINGS = 600;

    public static String getRELEASE_URLS(int which) {
        String baseURL = "https://api.srpago.com/";
        return getFormatedURL(which, baseURL);
    }

    public static String getDEBUG_URLS(int which) {
        String baseURL = "https://sandbox-api.srpago.com/";
//        String baseURL = "http://192.168.0.115/";
        return getFormatedURL(which, baseURL);
    }

    private static String getFormatedURL(int which, String baseURL) {
        switch (which) {
            case LOGIN:
                return baseURL + "v1/auth/login-my";
            case GET_ACCOUNT:
            case SP_ACCOUNT_INFO:
            case SET_AUTOMATIC_WITHDRAWALS:
                return baseURL + "v1/account/info";
            case PAYMENT:
                return baseURL + "v1/payment";
            case PAYMENT_CARD:
                return baseURL + "v1/payment/card";
            case PAYMENT_SIGNATURE:
            case SP_TAIL_SIGNATURES:
                return baseURL + "v1/payment/signature";
            case APPLICATION_LOGIN:
                return baseURL + "v1/auth/login/application";
            case DEVOLUTION:
                return baseURL + "v1/operations/apply-reversal/%s";
            case SR_PAGO_CARD:
                return baseURL + "v1/card";
            case OPERATIONS:
                return baseURL + "v1/operations%s";
            case TICKETS_ID:
                return baseURL + "v1/operations";
            case OPERATIONS_SEND:
                return baseURL + "v1/operations/send";
            case OPERATIONS_FUNDS:
                return baseURL + "v1/operations/founds";
            case BALANCE:
                return baseURL + "v1/card/balance";
            case TRANSACTIONS:
                return baseURL + "v1/card/transactions";
            case GET_OPERATORS:
                return baseURL + "v1/operator";
            case ACCOUNTS:
                return baseURL + "v1/bank-accounts";
            case SERVICES:
            case RECHARGE:
                return baseURL + "v1/card/services/tae";
            case STORE:
                return baseURL + "v1/payment/method";
            case AUTH_LOGOUT:
                return baseURL + "v1/auth/logout";
            case BANK_ACCOUNTS:
                return baseURL + "v1/bank-accounts";
            case TRANSFER_ID:
                return baseURL + "v1/transfer/";
            case DELETE_BANK_ACCOUNT:
                return baseURL + "v1/bank-accounts/%s";
            case RESET_PASSWORD:
                return baseURL + "v1/account/reset-password";
            case OPERATOR_DELETE:
                return baseURL + "v1/operator/%s";
            case ADD_OPERATOR:
                return baseURL + "v1/operator";
            case NOTIFICATIONS:
                return baseURL + "v1/notifications";
            case NOTIFICATION_POST:
                return baseURL + "v1/notifications/%s";
            case PAYMENT_STORE:
                return baseURL + "v1/payment/convenience-store";
            case OPERATIONS_FOUNTS_BALANCE:
                return baseURL + "v1/operations/founds";
            case TRANSFER_BALANCE:
                return baseURL + "v1/transfer";
            case UPLOAD_AVATAR:
                return baseURL + "v1/account";
            case AVATAR:
                return baseURL + "v1/account/avatar";
            case ABM:
                return baseURL + "v1//abm";
            case BANK_ACCOUNT_POST:
                return baseURL + "v1/bank-accounts";
            case SEND_TICKET:
                return baseURL + "v1/operations/send";
            case OPERATION:
                return baseURL + "v1/operations/%s";
            case UPDATE_OPERATOR:
                return baseURL + "v1/operator/%s";
            case CANCEL_CARD:
                return baseURL + "v1/card/cancel";
            case SP_REQUEST_MONTHS:
                return baseURL + "v1/partial-payment/promotion/%s/%s";
            case SP_CANCEL_OPERATION:
                return baseURL + "v1/operations/apply-reversal/%s";
            case REPLACEMENT_CARD:
                return baseURL + "v1/card/replacement";
            case PAYMENT_MOBILE:
                return baseURL + "v1/payment/mobile";
            case PAYMENT_MOBILE_CONFIRMATION:
                return baseURL + "v1/payment/mobile/confirm";
            case OPERATIONS_BALANCE:
                return baseURL + "v1/operations";
            case REGISTER_DEVICE:
                return baseURL + "v1/device/register";
            case UNREGISTER_DEVICE:
                return baseURL + "v1/device/register/%s";
            case VERSION_CHECK:
                return baseURL + "v1/app/version-check/%s";
            case BANK_ACCOUNT_DEFAULT:
                return baseURL + "v1/bank-accounts/%s";
            case GET_ZIPCODE:
                return baseURL + "v1/zipcode/%s";
            case REGISTER_STATUS:
                return baseURL + "v1/user/register/status";
            case GET_BUSINESS_CATALOG:
                return baseURL + "v1/catalog/business";
            case REGISTER:
                return baseURL + "v1/register";
            case LINK_FB_ACCOUNT:
                return baseURL + "v1/account/link";
            case LOGIN_FB:
                return baseURL + "v1/auth/login/facebook";
            case VALIDATION_PHONE:
                return baseURL + "v1/user/phone/validation/%s";
            case VALIDATION_PHONE_SEND_CODE:
                return baseURL + "v1/user/phone/validation";
            case VALIDATION_PHONE_EDIT_PHONE_NUMBER:
                return baseURL + "v1/user/phone/validation/%s";
            case RECOVERY_PASSWORD:
                return baseURL + "v1/account/reset-password";
            case VALIDATE_DOCUMENTS_PAYMENT:
                return baseURL + "v1/payment/%s/document";
            case UPLOAD_DOCUMENTS_PAYMENT:
                return baseURL + "v1/payment/document";
            case GET_SERVER_TIME:
                return baseURL + "v1/configuration?reader=%s";
            case UPDATE_SETTINGS:
            case GET_USER_SETTINGS:
                return baseURL + "v1/account/payment/settings";
            case CODE_REDEEM:
                return baseURL + "v1/user/code";
            case CODE_DISTRIBUTOR:
                return baseURL + "v1/distribuitor/code/%s";
            case TICKETS:
                return baseURL + "v1/payment%s";
            case CONTRACT:
                return baseURL + "v1/card/contract";
            case CONTRACT_UPDATE:
                return baseURL + "v1/card/contract/%s";
            case CONTRACT_DOCUMENTS_STATUS:
                return baseURL + "v1/card/document/%s";
            case CONTRACT_UPLOAD_DOCUMENT:
                return baseURL + "v1/card/document";
            case RESTORE_NIP:
                return baseURL + "v1/card/restore-nip";
            case WITHDRAWALS:
                return baseURL + "v1/withdrawal%s";
            case VALIDATE_PROMOTION:
                return baseURL + "v1/user/promotion";
            case CARD_TYPE:
                return baseURL + "v1/card/type/%s";
            case CARD_PREPAID:
                return baseURL + "v1/card/prepaid";
            default:
                return "";
        }
    }

    public static int getRESTType(final int service) {
        switch (service) {
            case APPLICATION_LOGIN:
            case LOGIN:
            case PAYMENT:
            case PAYMENT_CARD:
            case OPERATIONS_SEND:
            case RECHARGE:
            case RESET_PASSWORD:
            case ADD_OPERATOR:
            case PAYMENT_STORE:
            case TRANSFER_BALANCE:
            case UPLOAD_AVATAR:
            case BANK_ACCOUNT_POST:
            case SEND_TICKET:
            case CANCEL_CARD:
            case SP_TAIL_SIGNATURES:
            case REPLACEMENT_CARD:
            case PAYMENT_MOBILE:
            case PAYMENT_MOBILE_CONFIRMATION:
            case REGISTER_DEVICE:
            case REGISTER_STATUS:
            case REGISTER:
            case LINK_FB_ACCOUNT:
            case LOGIN_FB:
            case VALIDATION_PHONE_SEND_CODE:
            case RECOVERY_PASSWORD:
            case UPLOAD_DOCUMENTS_PAYMENT:
            case UPDATE_SETTINGS:
            case CODE_REDEEM:
            case PAYMENT_SIGNATURE:
            case CONTRACT:
            case CONTRACT_UPLOAD_DOCUMENT:
            case RESTORE_NIP:
            case VALIDATE_PROMOTION:
            case CARD_PREPAID:
                return PixzelleWebServiceConnection.POST;

            case DELETE_BANK_ACCOUNT:
            case OPERATOR_DELETE:
            case UNREGISTER_DEVICE:
                return PixzelleWebServiceConnection.DELETE;

            case NOTIFICATION_POST:
            case UPDATE_OPERATOR:
            case BANK_ACCOUNT_DEFAULT:
            case VALIDATION_PHONE_EDIT_PHONE_NUMBER:
            case CONTRACT_UPDATE:
            case SET_AUTOMATIC_WITHDRAWALS:
                return PixzelleWebServiceConnection.PUT;

            default:
                return PixzelleWebServiceConnection.GET;
        }
    }

    public static String REQUEST_PARAMS() {
        return obfuscated(new byte[]{82, 101, 113, 117, 101, 115, 116, 32, 112, 97, 114, 97, 109, 115});
    }

    public static String RESPONSE() {
        return obfuscated(new byte[]{82, 69, 83, 80, 79, 78, 83, 69});
    }

    public static String RESPONSE_CODE() {
        return obfuscated(new byte[]{82, 101, 115, 112, 111, 110, 115, 101, 32, 99, 111, 100, 101});
    }

    public static String RESULT() {
        return obfuscated(new byte[]{82, 101, 115, 117, 108, 116});
    }

    public static String RSA() {
        return obfuscated(new byte[]{82, 83, 65});
    }

    public static String SENDING() {
        return obfuscated(new byte[]{83, 101, 110, 100, 105, 110, 103});
    }

    public static String SEND_PAYMENT() {
        return obfuscated(new byte[]{83, 101, 110, 100, 32, 112, 97, 121, 109, 101, 110, 116});
    }

    public static String SERVICE_CODE() {
        return obfuscated(new byte[]{115, 101, 114, 118, 105, 99, 101, 67, 111, 100, 101});
    }

    public static String SIGNATURE_JPG1() {
        return obfuscated(new byte[]{115, 105, 103, 110, 97, 116, 117, 114, 101, 49, 46, 106, 112, 103});
    }

    public static String SIGNATURE_JPG2() {
        return obfuscated(new byte[]{115, 105, 103, 110, 97, 116, 117, 114, 101, 50, 46, 106, 112, 103});
    }

    public static String SIGN_NO_INTERNET() {
        return obfuscated(new byte[]{76, 97, 32, 99, 111, 110, 101, 120, 105, -61, -77, 110, 32, 97, 32, 73, 110, 116, 101, 114, 110, 101, 116, 32, 112, 97, 114, 101, 99, 101, 32, 101, 115, 116, 97, 114, 32, 100, 101, 115, 97, 99, 116, 105, 118, 97, 100, 97, 46, 10, -62, -65, 68, 101, 115, 101, 97, 115, 32, 114, 101, 105, 110, 116, 101, 110, 116, 97, 114, 108, 111, 63});
    }

    public static String SIGN_PLEASE() {
        return obfuscated(new byte[]{70, 105, 114, 109, 101, 32, 112, 111, 114, 32, 102, 97, 118, 111, 114, 46});
    }

    public static String SIGN_SERVER_OCCUPIED() {
        return obfuscated(new byte[]{69, 108, 32, 115, 101, 114, 118, 105, 100, 111, 114, 32, 112, 97, 114, 101, 99, 101, 32, 111, 99, 117, 112, 97, 100, 111, 46, 10, -62, -65, 68, 101, 115, 101, 97, 115, 32, 114, 101, 105, 110, 116, 101, 110, 116, 97, 114, 108, 111, 63});
    }

    public static String SIGN_UNKNOWN_ERROR() {
        return obfuscated(new byte[]{72, 97, 32, 111, 99, 117, 114, 114, 105, 100, 111, 32, 117, 110, 32, 101, 114, 114, 111, 114, 32, 105, 110, 101, 115, 112, 101, 114, 97, 100, 111, 46, 10, -62, -65, 68, 101, 115, 101, 97, 115, 32, 114, 101, 105, 110, 116, 101, 110, 116, 97, 114, 108, 111, 63});
    }

    public static String SIGN_UPLOADED() {
        return obfuscated(new byte[]{70, 105, 114, 109, 97, 32, 115, 117, 98, 105, 100, 97, 32, 99, 111, 114, 114, 101, 99, 116, 97, 109, 101, 110, 116, 101, 46});
    }

    public static String SIGN_UPLOADING() {
        return obfuscated(new byte[]{83, 117, 98, 105, 101, 110, 100, 111, 32, 102, 105, 114, 109, 97, 46});
    }

    public static String SPAGO() {
        return obfuscated(new byte[]{115, 112, 97, 103, 111});
    }

    public static String SR_PAGO() {
        return obfuscated(new byte[]{83, 114, 46, 80, 97, 103, 111});
    }

    public static String SR_PAGO_RELEASE() {
        return obfuscated(new byte[]{115, 114, 95, 112, 97, 103, 111, 95, 114, 101, 108, 101, 97, 115, 101, 95, 109, 111, 100, 101});
    }

    public static String SR_PAGO_SDK() {
        return obfuscated(new byte[]{83, 82, 80, 65, 71, 79, 83, 68, 75});
    }

    public static String SUCCESS() {
        return obfuscated(new byte[]{83, 117, 99, 99, 101, 115, 115});
    }

    public static String SUCCESS_POWER_ON() {
        return obfuscated(new byte[]{83, 117, 99, 99, 101, 115, 115, 32, 80, 111, 119, 101, 114, 32, 79, 110});
    }

    public static String TIMESTAMP() {
        return obfuscated(new byte[]{121, 121, 121, 121, 45, 77, 77, 45, 100, 100, 39, 84, 39, 72, 72, 58, 109, 109, 58, 115, 115, 122});
    }

    public static String TLV() {
        return obfuscated(new byte[]{116, 108, 118});
    }

    public static String TOTAL() {
        return obfuscated(new byte[]{84, 111, 116, 97, 108, 58});
    }

    public static String TRACK_2_ONLY() {
        return obfuscated(new byte[]{84, 114, 97, 99, 107, 50, 79, 110, 108, 121});
    }

    public static String TRANSACTION_RESULT() {
        return obfuscated(new byte[]{84, 114, 97, 110, 115, 97, 99, 116, 105, 111, 110, 32, 114, 101, 115, 117, 108, 116});
    }

    public static String TRUE() {
        return obfuscated(new byte[]{116, 114, 117, 101});
    }

    public static String UDI() {
        return obfuscated(new byte[]{117, 100, 105});
    }

    public static String URL() {
        return obfuscated(new byte[]{85, 82, 76});
    }

    public static String USER_NOT_FOUND_KEY() {
        return obfuscated(new byte[]{85, 115, 101, 114, 32, 110, 111, 116, 32, 102, 111, 117, 110, 100, 32, 98, 97, 115, 101, 100, 32, 111, 110, 32, 112, 117, 98, 108, 105, 99, 32, 107, 101, 121});
    }

    public static String USE_ICC_CARD() {
        return obfuscated(new byte[]{85, 83, 69, 95, 73, 67, 67, 95, 67, 65, 82, 68});
    }

    public static String UTF() {
        return obfuscated(new byte[]{85, 84, 70, 45, 56});
    }

    public static String VALIDATION() {
        return obfuscated(new byte[]{86, 97, 108, 105, 100, 97, 116, 105, 111, 110});
    }

    public static String WRITE_CVV() {
        return obfuscated(new byte[]{69, 115, 99, 114, 105, 98, 101, 32, 101, 108, 32, 99, 118, 118, 32, 100, 101, 32, 108, 97, 32, 116, 97, 114, 106, 101, 116, 97, 46});
    }

    public static String WRITE_PIN() {
        return obfuscated(new byte[]{69, 115, 99, 114, 105, 98, 101, 32, 101, 108, 32, 112, 105, 110, 32, 100, 101, 32, 108, 97, 32, 116, 97, 114, 106, 101, 116, 97, 46});
    }

    public static String YES() {
        return obfuscated(new byte[]{83, -61, -83});
    }

    public static String _000() {
        return obfuscated(new byte[]{48, 48, 48});
    }

    public static String _0000() {
        return obfuscated(new byte[]{48, 48, 48, 48});
    }

    public static String _00000() {
        return obfuscated(new byte[]{48, 48, 48, 48, 48});
    }

    public static String _221() {
        return obfuscated(new byte[]{50, 50, 49});
    }

    public static String _2f() {
        return obfuscated(new byte[]{37, 46, 50, 102});
    }

    public static String _484() {
        return obfuscated(new byte[]{52, 56, 52});
    }

    public static String _5F20() {
        return obfuscated(new byte[]{53, 102, 50, 48});
    }

    public static String _5F24() {
        return obfuscated(new byte[]{53, 102, 50, 52});
    }

    public static String _6s() {
        return obfuscated(new byte[]{37, 115, 37, 115, 37, 115, 37, 115, 37, 115, 37, 115});
    }

    public final static String KEY_REGISTER_TOKEN = "registerToken";
    public final static String KEY_EXPIRATION_TOKEN = "expirationToken";
    public final static String KEY_TRANSACTION_TOKEN = "transactionToken";
    public final static String KEY_EXT_TRANSACTION = "externalTransaction";
    public final static String KEY_DEVICE_INFO = "deviceInfo";
    public final static String KEY_EMV = "emv";
    public final static String KEY_CARD_HOLDER = "cardHolder";
    public final static String KEY_AMOUNT = "amount";
    public final static String KEY_DATA_RECEIPT = "receipt";

    public final static String KEY_NAME = "name";
    public final static String KEY_ADDRESS = "address";
    public final static String KEY_PHONE = "phone";
    public final static String TOKEN_FCM = "token_fcm";

    public final static String KEY_USER = "user";
    public final static String KEY_PASS = "pass";

    public final static String KEY_READER_DEFAULT = "reader_default";
    public final static String KEY_READER_IS_PATCH_CONFIG = "sp_reader_is_patch_config";

    public final static String KEY_PERMISSION_CARD = "permission_card";
    public final static String KEY_PERMISSION_CONVENIENCESTORE = "permission_convenience_store";
    public final static String KEY_PERMISSION_PAYMENT_CARD = "permission_payment_card";
    public final static String KEY_PERMISSION_BALANCE = "permission_balance";
    public final static String KEY_PERMISSION_WITHDRAWAL = "permission_withdrawal";
    public final static String KEY_PERMISSION_TRANSFER = "permission_transfer";
    public final static String KEY_PERMISSION_OPERATIONS = "permission_operations";
    public final static String KEY_PERMISSION_READER = "permission_reader";
    public final static String KEY_PERMISSION_NOTIFICATIONS = "permission_notifications";
    public final static String KEY_PERMISSION_OPERATORS = "permission_operators";
    public final static String KEY_PERMISSION_BANK_ACCOUNTS = "permission_bank_accounts";
    public final static String KEY_PERMISSION_ECOMMERCE = "permission_ecommerce";
    public final static String KEY_PERMISSION_INVOICE = "permission_invoice";
    public final static String KEY_PERMISSION_AVATAR = "permission_avatar";
    public final static String KEY_PERMISSION_AVATAR_EDIT = "permission_avatar_edit";

    public final static String KEY_READER_FIRMWARE = "reader_firmware";

    public final static String CALCULATOR = "calculatorComission";
    public final static String IF_FROM_OTHER_APP = "fromotherapp";
    public final static String OPERATION_SIGNATURE = "operationsignature";
    public final static String IS_SIGNATURE_COMPLETED = "issiganutecompleted";
    public final static String CAN_SHOW_SMS_TICKET = "canshowsms";
    public final static String NOTIFICATION = "notification";
    public final static String NOTIFICATIONS_ID = "notificationid";
    public final static String LINK_RETURN_CARD_HOLDER = "cardHolder";
    public final static String LINK_RETURN_CARD_NUMBER = "cardNumber";
    public final static String LINK_RETURN_CARD_TYPE = "cardType";
    public final static String LINK_RETURN_OPERATION_AUTH_NO = "operationAuthNo";
    public final static String LINK_RETURN_OPERATION_FOLIO = "operationFolio";
    public final static String LINK_TIP = "tip";
    public final static String LINK_AMOUNT = "amount";
    public final static String IS_DEVOLUTION_SUCCESS = "isDevolutionSuccess";

    public final static String FIRST_NAME = "name";
    public final static String MIDDLE_NAME = "last_name";
    public final static String LAST_NAME = "sur_name";
    public final static String PHONE_NUMBER = "number";
    public final static String BUSSINES_ID = "bussines_id";
    public final static String BUSSINES_NAME = "name_bussines";
    public final static String EMAIL_REGISTER = "emailregister";
    public final static String PASSWORD_REGISTER = "passwordregister";
    public final static String FBTOKEN = "tokenfb";
    public final static String IS_VERIFIED_PHONE = "verifiedPhone";


    public class ChatAttributes {
        public final static String NAME = "name";
        public final static String IS_OPERATOR = "isOperator";
        public final static String PHONE = "phone";
        public final static String ARE_NOTIFICATIONS_ENABLED = "areNotificationsEnabled";
        public final static String TYPE_DEVICE = "typeDevice";
        public final static String FIRMWARE = "firmware";

    }

}
