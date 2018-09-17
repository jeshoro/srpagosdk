package sr.pago.sdk.api.parsers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import sr.pago.sdk.LocationUpdateService;
import sr.pago.sdk.SrPagoActivity;
import sr.pago.sdk.connection.Encryptor;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.model.responses.srpago.SrPagoCard;
import sr.pago.sdk.object.Card;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.PaymentPreferences;
import sr.pago.sdk.object.request.CardRequest;
import sr.pago.sdk.object.request.LoginRequest;
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 27/07/2015.
 */
public class ParserRequestFactory extends PixzelleParserRequestFactory {

    public ParserRequestFactory() {

    }

    /**
     * This method build the request object json.
     *
     * @param context
     * @param webService
     * @param params
     * @return
     * @throws Exception
     */
    public String convertToJSON(Context context, int webService, Object... params) throws Exception {
        switch (webService) {
            case Definitions.LOGIN:
                return LoginRequest.parse(params[0].toString(), params[1].toString(), params[2].toString());
            case Definitions.PAYMENT:
                return convertPaymentToJSON2(context, params);
            case Definitions.PAYMENT_CARD:
                return convertPaymentCardToJSON(context, params);
            case Definitions.PAYMENT_SIGNATURE:
                return convertPaymentSignatureToJSON(context, params);
            case Definitions.SP_TAIL_SIGNATURES:
                return tailSignatureToJSON(context, params);
            case Definitions.APPLICATION_LOGIN:
                return convertAppLoginToJSON(context);
            case Definitions.GET_OPERATORS:
            case Definitions.OPERATIONS_SEND:
                return TicketsResponse.parseIdTicket(params[0].toString(), params[1].toString());
            case Definitions.RECHARGE:
                return LoginRequest.parseRecharge(params[0].toString(), params[1].toString());
            case Definitions.RESET_PASSWORD:
                return LoginRequest.parseResetPassword(params[0].toString(), params[1].toString());
            case Definitions.ADD_OPERATOR:
                return LoginRequest.parseOperatorAdd(params[0].toString(), params[1].toString(), params[2].toString());
            case Definitions.NOTIFICATION_POST:
                return LoginRequest.parseNotificationPost(params[0].toString(), (boolean) params[1]);
            case Definitions.PAYMENT_STORE:
                return LoginRequest.parsePeymentStore(params[0].toString(), params[1].toString(), params[2].toString(), params[3].toString(), params[4].toString());
            case Definitions.PAYMENT_MOBILE:
                return LoginRequest.parsePaymentMethod(params[0].toString(), params[1].toString());
            case Definitions.PAYMENT_MOBILE_CONFIRMATION:
                return LoginRequest.parseMethodConfirmation(params[0].toString(), params[1].toString());
            case Definitions.UPLOAD_AVATAR:
                return LoginRequest.parseAccountImg(params[0].toString());
            case Definitions.BANK_ACCOUNT_POST:
                return LoginRequest.parseBankAccountPost(params[0].toString(), params[1].toString(), params[2].toString(), params[3].toString(), params[4].toString(), params[5].toString(), (boolean) params[6], (int) params[7]);
            case Definitions.SEND_TICKET:
                return LoginRequest.parseSendTicket(params[0].toString(), params[1].toString(), params[2].toString());
            case Definitions.TRANSFER_BALANCE:
                return LoginRequest.parseTransfer(params[0].toString(), (double) params[1], params[2].toString());
            case Definitions.UPDATE_OPERATOR:
                return LoginRequest.parseAddOperator(params[0].toString(), params[1].toString());
            case Definitions.CANCEL_CARD:
                return LoginRequest.parseCancel(params[0].toString(), params[1].toString(), params[2].toString());
            case Definitions.REPLACEMENT_CARD:
                return CardRequest.parseReplacementCard(params[0].toString());
            case Definitions.REGISTER_DEVICE:
                return registerDeviceToJson(context, params);
            case Definitions.BANK_ACCOUNT_DEFAULT:
                return setDefaultBankAccount(context, params);
            case Definitions.REGISTER_STATUS:
                return requestRegisterStatus(context, params);
            case Definitions.REGISTER:
                return requestRegisterUser(context, params);
            case Definitions.LINK_FB_ACCOUNT:
                return requestRegisterLinkFbAccount(context, params);
            case Definitions.LOGIN_FB:
                return requestLoginFacebook(context, params);
            case Definitions.VALIDATION_PHONE_SEND_CODE:
                return requestValidationPhonenumberSend(context, params);
            case Definitions.VALIDATION_PHONE_EDIT_PHONE_NUMBER:
                return requestValidationPhonenumberEdit(context, params);
            case Definitions.RECOVERY_PASSWORD:
                return requestRecoveryPassword(context, params);
            case Definitions.VALIDATE_DOCUMENTS_PAYMENT:
                return requestValidationPaymentDocuments(context, params);
            case Definitions.UPLOAD_DOCUMENTS_PAYMENT:
                return requestUploadPaymentDocuments(context, params);
            case Definitions.UPDATE_SETTINGS:
                return convertPreferencesToJSON(context);
            case Definitions.CONTRACT:
            case Definitions.CONTRACT_UPDATE:
                return parseContract((SrPagoCard) params[0]);
            case Definitions.UNREGISTER_DEVICE:
            case Definitions.OPERATOR_DELETE:
            case Definitions.GET_ACCOUNT:
            case Definitions.VERSION_CHECK:
            case Definitions.GET_ZIPCODE:
            case Definitions.GET_BUSINESS_CATALOG:
            case Definitions.VALIDATION_PHONE:
            case Definitions.TICKETS:

                return "";
            case Definitions.CODE_REDEEM:
            case Definitions.VALIDATE_PROMOTION:
                return convertCodeToJSON(params[0].toString());
            case Definitions.CONTRACT_UPLOAD_DOCUMENT:
                return parseUploadDocument(params[1].toString(), (SPTransactionDocument) params[0]);

            case Definitions.RESTORE_NIP:
                return parseRestoreNip(params[0].toString());

            case Definitions.SET_AUTOMATIC_WITHDRAWALS:
                return parseSetWithdrawal(params[0].toString());

            case Definitions.CARD_PREPAID:
                return params[0].toString();

            default:
                //NewRelic.setAttribute("ParserRequestFactoryConvertToJSON","Definicion de servicio de desconocido");
        }

        throw new JSONException("Error sending data to the web service.");
    }

    private static String parseSetWithdrawal(final String value) throws JSONException {
        JSONObject root = new JSONObject();

        root.put("automatic_withdrawal", value);
        return root.toString();
    }


    private static String parseRestoreNip(final String number) throws JSONException {
        JSONObject root = new JSONObject();

        root.put("number", number);
        return root.toString();
    }

    private static String parseUploadDocument(final String number, final SPTransactionDocument spTransactionDocument) throws JSONException {
        JSONObject root = new JSONObject();

        root.put("number", number);
        root.put("type", spTransactionDocument.getType());
        root.put("img", spTransactionDocument.getImage());

        return root.toString();
    }

    private static String parseContract(final SrPagoCard srPagoCard) throws JSONException {
        JSONObject root = new JSONObject();

        root.put("number", srPagoCard.getNumber());
        root.put("nationality", srPagoCard.getNationality());
        root.put("curp", srPagoCard.getCurp());
        root.put("rfc", srPagoCard.getRfc());
        root.put("birth_date", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(srPagoCard.getBirthDate()));
        root.put("income", srPagoCard.getIncome());
        root.put("balance", srPagoCard.getBalance());
        root.put("transactions", String.valueOf(srPagoCard.getTransactions()));

        JSONObject jsonBeneficiary = new JSONObject();
        jsonBeneficiary.put("name", srPagoCard.getBeneficiary().getName());
        jsonBeneficiary.put("relation", srPagoCard.getBeneficiary().getRelation());
        jsonBeneficiary.put("address", srPagoCard.getBeneficiary().getAddress());
        jsonBeneficiary.put("birth_date", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(srPagoCard.getBeneficiary().getBirthDate()));

        root.put("beneficiary", jsonBeneficiary);

        return root.toString();
    }

    private static String convertCodeToJSON(final String code) throws JSONException {
        JSONObject root = new JSONObject();
        root.put("code", code);

        return root.toString();
    }

    private static String convertPreferencesToJSON(final Context context) throws JSONException {
        return PaymentPreferences.getInstance(context).toJSON();
    }

    /**
     * This method build the request payment object to json string.
     *
     * @param context
     * @param params
     * @return
     * @throws JSONException
     * @throws PackageManager.NameNotFoundException
     */
    private static String convertPaymentToJSON2(Context context, Object... params) throws JSONException, PackageManager.NameNotFoundException {
        Context context1 = (Context) params[0];
        String amount = params[1].toString();
        String tip = params[2].toString();
        String referenceNumber;
        try {
            //referenceNumber = params[4].toString();
            referenceNumber = "";
        } catch (Exception ex) {
            referenceNumber = "";
            //NewRelic.setAttribute("ParserRequestFactoryConvertPaymentToJSON","Referencia vacia");
        }
        String referenceDetail = params[3].toString();
        String appKey;
        String ip;
        String currency;
        try {
            ApplicationInfo applicationInfo = context1.getPackageManager().getApplicationInfo(context1.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;

            if (Global.isInDebugMode(context)) {
                appKey = String.format("%s %s", bundle.getString("sr_pago_debug_application_token"), bundle.getString("sr_pago_debug_application_secret"));
            } else {
                appKey = String.format("%s %s", bundle.getString("sr_pago_release_application_token"), bundle.getString("sr_pago_release_application_secret"));
            }


            currency = bundle.getString("sr_pago_currency");
        } catch (PackageManager.NameNotFoundException ex) {
            throw new PackageManager.NameNotFoundException(ex.getMessage());
        }
        ip = "";
        JSONObject jsonPayment = new JSONObject();
        JSONObject jsonBody = new JSONObject();

        //json external
        JSONObject jsonExternal = new JSONObject();
        jsonExternal.put("application_key", appKey);
        jsonExternal.put("transaction", "");
        jsonPayment.put("external", jsonExternal);

        //json reference
        JSONObject jsonReference = new JSONObject();
        jsonReference.put("number", referenceNumber);
        jsonReference.put("description", referenceDetail);
        jsonPayment.put("reference", jsonReference);

        //json total
        JSONObject jsonTotal = new JSONObject();
        jsonTotal.put("amount", amount);
        jsonTotal.put("currency", currency);
        jsonPayment.put("total", jsonTotal);

        //json tip
        JSONObject jsonTip = new JSONObject();
        jsonPayment.put("amount", String.format(Locale.getDefault(), "%.2f", Double.parseDouble(amount) * (Double.parseDouble(tip) / 100)));
        jsonTip.put("currency", currency);
        jsonPayment.put("tip", jsonTip);

        //json origin
        JSONObject jsonOrigin = new JSONObject();

        try {
            jsonOrigin.put("device", new JSONObject(Global.getStringKey(context1, Definitions.KEY_DEVICE_INFO)));
        } catch (Exception ex) {
            jsonOrigin.put("device", new JSONObject());
            Logger.logError(ex);
        }
        jsonOrigin.put("ip", ip);

        //json location
        JSONObject jsonLocation = new JSONObject();
        try {
            jsonLocation.put("latitude", LocationUpdateService.lastLocation.getLatitude());
            jsonLocation.put("longitude", LocationUpdateService.lastLocation.getLatitude());
        } catch (Exception ex) {
            Logger.logError(ex);
            jsonLocation.put("latitude", "0.0");
            jsonLocation.put("longitude", "0.0");
        }

        jsonOrigin.put("location", jsonLocation);
        jsonPayment.put("origin", jsonOrigin);
        jsonBody.put("payment", jsonPayment);

        //json msr
        return jsonPayment.toString();
    }

    /**
     * This method build the request payment object to json string.
     *
     * @param context
     * @param params
     * @return
     * @throws JSONException
     * @throws PackageManager.NameNotFoundException
     */
    private static String convertPaymentToJSON(Context context, Object... params) throws JSONException, PackageManager.NameNotFoundException {
        SrPagoActivity srPagoActivity = (SrPagoActivity) params[0];
        String amount = params[1].toString();
        String tip = params[2].toString();
        String referenceNumber;
        try {
            referenceNumber = params[4].toString();
        } catch (Exception ex) {
            referenceNumber = "";
            //NewRelic.setAttribute("ParserRequestFactoryConvertPaymentToJSON","Referencia vacia");
        }
        String referenceDetail = params[3].toString();
        String appKey;
        String ip;
        String currency;
        try {
            ApplicationInfo applicationInfo = srPagoActivity.getPackageManager().getApplicationInfo(srPagoActivity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;

            if (Global.isInDebugMode(context)) {
                appKey = String.format("%s %s", bundle.getString("sr_pago_debug_application_token"), bundle.getString("sr_pago_debug_application_secret"));
            } else {
                appKey = String.format("%s %s", bundle.getString("sr_pago_release_application_token"), bundle.getString("sr_pago_release_application_secret"));
            }


            currency = bundle.getString("sr_pago_currency");
        } catch (PackageManager.NameNotFoundException ex) {
            throw new PackageManager.NameNotFoundException(ex.getMessage());
        }
        ip = "";
        JSONObject jsonPayment = new JSONObject();
        JSONObject jsonBody = new JSONObject();

        //json external
        JSONObject jsonExternal = new JSONObject();
        jsonExternal.put("application_key", appKey);
        jsonExternal.put("transaction", "");
        jsonPayment.put("external", jsonExternal);

        //json reference
        JSONObject jsonReference = new JSONObject();
        jsonReference.put("number", referenceNumber);
        jsonReference.put("description", referenceDetail);
        jsonPayment.put("reference", jsonReference);

        //json total
        JSONObject jsonTotal = new JSONObject();
        jsonTotal.put("amount", amount);
        jsonTotal.put("currency", currency);
        jsonPayment.put("total", jsonTotal);

        //json tip
        JSONObject jsonTip = new JSONObject();
        jsonPayment.put("amount", String.format(Locale.getDefault(), "%.2f", Double.parseDouble(amount) * (Double.parseDouble(tip) / 100)));
        jsonTip.put("currency", currency);
        jsonPayment.put("tip", jsonTip);

        //json origin
        JSONObject jsonOrigin = new JSONObject();

        try {
            jsonOrigin.put("device", new JSONObject(Global.getStringKey(srPagoActivity, Definitions.KEY_DEVICE_INFO)));
        } catch (Exception ex) {
            jsonOrigin.put("device", new JSONObject());
            Logger.logError(ex);
        }
        jsonOrigin.put("ip", ip);

        //json location
        JSONObject jsonLocation = new JSONObject();
        jsonLocation.put("latitude", srPagoActivity.getLatitude());
        jsonLocation.put("longitude", srPagoActivity.getLongitude());

        jsonOrigin.put("location", jsonLocation);
        jsonPayment.put("origin", jsonOrigin);
        jsonBody.put("payment", jsonPayment);

        //json msr
        return jsonPayment.toString();
    }

    /**
     * This method build the request paymentcard object to json string.
     *
     * @param context
     * @param params
     * @return
     * @throws Exception
     */
    private static String convertPaymentCardToJSON(Context context, Object... params) throws Exception {
        JSONObject jsonObject = new JSONObject();

        JSONObject jsonCard = new JSONObject();
        JSONObject jsonTotal = new JSONObject();

        //SrPagoActivity srPagoActivity = (SrPagoActivity) params[0];
        String amount = params[1].toString();
        String tip = params[2].toString();
        String referenceNumber;
        try {
            referenceNumber = "";
            //referenceNumber = params[4].toString();
        } catch (Exception ex) {
            referenceNumber = "";
            //NewRelic.setAttribute("ParserRequestFactoryConvertPaymentCardToJSON","Referencia vacia");
        }
        String referenceDetail = params[3].toString();
        Card card = (Card) params[5];
        String months = params[6].toString();

        String appKey;
        String ip;
        String currency;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;

            if (Global.isInDebugMode(context)) {
                appKey = String.format("%s %s", bundle.getString("sr_pago_debug_application_token"), bundle.getString("sr_pago_debug_application_secret"));
            } else {
                appKey = String.format("%s %s", bundle.getString("sr_pago_release_application_token"), bundle.getString("sr_pago_release_application_secret"));
            }

            currency = bundle.getString("sr_pago_currency");
        } catch (PackageManager.NameNotFoundException ex) {
            throw new PackageManager.NameNotFoundException(ex.getMessage());
        }
        ip = "";
        JSONObject jsonPayment = new JSONObject();
        JSONObject jsonBody = new JSONObject();

        //json external
        JSONObject jsonExternal = new JSONObject();
        jsonExternal.put("application_key", appKey);
        jsonExternal.put("transaction", "");
        jsonPayment.put("external", jsonExternal);

        //json disperse
        try {
            HashMap<String, Object> additionalData = (HashMap<String, Object>) params[7];
            JSONObject jsonDisperse = new JSONObject();

            if (additionalData != null) {

                if (additionalData.containsKey("connect_type")) {
                    jsonDisperse.put("type", additionalData.get("connect_type"));
                }

                if (additionalData.containsKey("connect_account")) {
                    jsonDisperse.put("user_affiliated", additionalData.get("connect_account"));

                    if (additionalData.containsKey("connect_fees")) {
                        JSONArray jsonUsers = new JSONArray();
                        for (HashMap<String, Object> objectHashMap : (ArrayList<HashMap<String, Object>>) additionalData.get("connect_fees")) {
                            JSONObject jsonUser = new JSONObject();

                            if (objectHashMap.get("account") != null) {
                                jsonUser.put("user", objectHashMap.get("account"));
                            }
                            if (objectHashMap.get("amount") != null) {
                                jsonUser.put("fee", objectHashMap.get("amount"));
                            }
                            if (objectHashMap.get("reference") != null) {
                                jsonUser.put("reference", objectHashMap.get("reference"));
                            }
                            if (objectHashMap.get("description") != null) {
                                jsonUser.put("description", objectHashMap.get("description"));
                            }

                            jsonUsers.put(jsonUser);
                        }
                        jsonDisperse.put("users", jsonUsers);
                    }
                }

                jsonPayment.put("disperse", jsonDisperse);
            }

        } catch (Exception ex) {

        }

        //json reference
        JSONObject jsonReference = new JSONObject();
        jsonReference.put("number", referenceNumber);
        jsonReference.put("description", referenceDetail);
        jsonPayment.put("reference", jsonReference);

        //json total
        jsonTotal.put("amount", amount);
        jsonTotal.put("currency", currency);
        jsonPayment.put("total", jsonTotal);
        jsonBody.put("total", jsonTotal);

        //json tip
        JSONObject jsonTip = new JSONObject();
        jsonTip.put("amount", tip);
        jsonTip.put("currency", currency);
        jsonPayment.put("tip", jsonTip);

        //json origin
        JSONObject jsonOrigin = new JSONObject();

        try {
            jsonOrigin.put("device", new JSONObject(Global.getStringKey(context, Definitions.KEY_DEVICE_INFO)));
//            jsonOrigin.put("device", new JSONObject());
        } catch (Exception ex) {
            jsonOrigin.put("device", new JSONObject());
            Logger.logError(ex);
        }
        jsonOrigin.put("ip", ip);

        //json location
        JSONObject jsonLocation = new JSONObject();
        try {
            jsonLocation.put("latitude", LocationUpdateService.lastLocation.getLatitude());
            jsonLocation.put("longitude", LocationUpdateService.lastLocation.getLongitude());
        } catch (Exception ex) {
            jsonLocation.put("latitude", 19.349274);
            jsonLocation.put("longitude", -99.190230);
        }
        jsonOrigin.put("location", jsonLocation);
        jsonPayment.put("origin", jsonOrigin);
        jsonBody.put("payment", jsonPayment);

        //json msr
        if (Boolean.valueOf(card.getEmvFlag())) {
            jsonBody.put("emv", card.getData());
        } else {
            JSONObject jsonMSR = new JSONObject();
            jsonMSR.put("msr_1", card.getMsr1());
            jsonMSR.put("msr_2", card.getMsr2());
            jsonMSR.put("msr_3", card.getMsr3());
            jsonBody.put("msr", jsonMSR);
        }

        if (card.getAffiliation() != null && !card.getAffiliation().equals("")) {
            JSONObject jsonAffiliated = new JSONObject();
            jsonAffiliated.put("user", card.getAffiliation());
            jsonBody.put("affiliated", jsonAffiliated);
        }

        //json Card
        jsonCard.put("holder_name", card.getCardHolderName());
        jsonCard.put("type", card.getType());
        jsonCard.put("number", card.getCardNumberToSend(Boolean.valueOf(card.getEmvFlag())));
        jsonCard.put("cvv", card.getCardSecurityCode());
        jsonCard.put("month", card.getCardMonth());
        jsonCard.put("year", card.getCardYear());
        jsonCard.put("zipcode", "");
        jsonBody.put("card", jsonCard);

        //json months
        jsonBody.put("months", jsonBody.equals("0") ? "1" : months);

        Global.getInstance().amount = String.format("%s %s", amount, currency);
        Global.getInstance().holder = card.getCardHolderName() == null ? "" : card.getCardHolderName();
        Logger.logDebug("Data", jsonBody.toString());

        int retry = 0;
        String data = "";
        do {
            String key;
            key = Encryptor.getRandomKey();
            try {
                jsonObject.put("key", Encryptor.rsaEncrypt(key).replaceAll("\t", "").replaceAll(" ", "").replaceAll("\r", ""));
                data = Encryptor.aesEncrypt(jsonBody.toString(), key).replaceAll("\t", "").replaceAll(" ", "").replaceAll("\r", "");
                jsonObject.put("data", data);
            } catch (Exception ex) {
                Logger.logError(ex);
            }
            retry++;
        } while (retry < 100 && (data == null || data.equals("")));
        return jsonObject.toString();
    }

    private static String convertPaymentSignatureToJSON(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("token", Global.getStringKey(context, Definitions.KEY_TRANSACTION_TOKEN));
        jsonObject.put("operation", Global.getStringKey(context, Definitions.KEY_EXT_TRANSACTION));
        jsonObject.put("image", Base64.encodeToString((byte[]) params[0], Base64.DEFAULT));

        return jsonObject.toString();
    }

    private static String convertAppLoginToJSON(Context context) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("application_bundle", context.getPackageName());

        return jsonObject.toString();
    }

    private static String tailSignatureToJSON(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("token", params[0]);
        jsonObject.put("operation", params[1]);
        jsonObject.put("image", params[2]);

        return jsonObject.toString();
    }

    private static String registerDeviceToJson(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", params[0]);
        jsonObject.put("token", params[1]);
        jsonObject.put("detail", params[2]);
        return jsonObject.toString();
    }

    private static String setDefaultBankAccount(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("default", params[0]);
        jsonObject.put("type", params[1]);
        return jsonObject.toString();
    }

    private static String requestRegisterStatus(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String facebookID = (String) params[1];
        jsonObject.put("email", params[0]);
        if (facebookID != null && !facebookID.equals(""))
            jsonObject.put("facebook_id", params[1]);
        return jsonObject.toString();
    }

    private static String requestRegisterUser(Context context, Object... params) throws JSONException {
        String email, password, tokenFB;
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonCompany = new JSONObject();
        JSONObject jsonUser = new JSONObject();
        JSONObject jsonphone = new JSONObject();
        JSONObject jsonFacebook = new JSONObject();

        jsonObject.put("name", params[0]);
        jsonObject.put("sur_name", params[1]);
        jsonObject.put("last_name", params[2]);
        jsonObject.put("area", params[3]);
        jsonObject.put("city", params[4]);
        jsonObject.put("state", params[5]);
        jsonObject.put("street", params[6]);
        jsonObject.put("zip_code", params[7]);
        jsonObject.put("num_ext", params[8]);
        jsonObject.put("num_int", params[9]);
        jsonObject.put("label", "Personal");
        jsonObject.put("ip", params[15]);

        jsonCompany.put("bussines_id", params[10]);
        jsonCompany.put("name", params[11]);
        jsonObject.put("company", jsonCompany);

        email = (String) params[12];
        password = (String) params[13];
        tokenFB = (String) params[16];
        if (email != null && password != null) {
            jsonUser.put("email", params[12]);
            jsonUser.put("password", params[13]);
            jsonObject.put("user", jsonUser);
        } else if (tokenFB != null) {
            jsonFacebook.put("fb_user_token", params[16]);
            jsonObject.put("facebook", jsonFacebook);
        }

        jsonphone.put("number", params[14]);
        jsonphone.put("label", "celular");
        jsonObject.put("phone", jsonphone);

        return jsonObject.toString();
    }

    private static String requestRegisterLinkFbAccount(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", params[0]);
        jsonObject.put("facebook_id", params[1]);
        jsonObject.put("fb_user_token", params[2]);
        jsonObject.put("password", params[3]);
        return jsonObject.toString();
    }

    private static String requestLoginFacebook(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fb_user_token", params[0]);
        return jsonObject.toString();
    }

    private static String requestValidationPhonenumberSend(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", params[0]);
        return jsonObject.toString();
    }

    private static String requestValidationPhonenumberEdit(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", params[0]);
        return jsonObject.toString();
    }

    private static String requestRecoveryPassword(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", params[0]);
        return jsonObject.toString();
    }

    private static String requestValidationPaymentDocuments(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transaction_token", params[0]);
        return jsonObject.toString();
    }

    private static String requestUploadPaymentDocuments(Context context, Object... params) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", params[1]);
        jsonObject.put("image", params[2]);
        jsonObject.put("type", params[3]);
        return jsonObject.toString();
    }
}
