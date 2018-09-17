package sr.pago.sdk.api.parsers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.model.Abm;
import sr.pago.sdk.model.Avatar;
import sr.pago.sdk.model.Balance;
import sr.pago.sdk.model.BankAccount;
import sr.pago.sdk.model.Fund;
import sr.pago.sdk.model.Notification;
import sr.pago.sdk.model.OperationFundBalance;
import sr.pago.sdk.model.Operator;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.SPTransactionDocument;
import sr.pago.sdk.model.Service;
import sr.pago.sdk.model.StoresMethod;
import sr.pago.sdk.model.Ticket;
import sr.pago.sdk.model.Transaction;
import sr.pago.sdk.model.responses.srpago.CardTypeRS;
import sr.pago.sdk.model.responses.srpago.AccountInfoRS;
import sr.pago.sdk.model.responses.srpago.SrPagoCard;
import sr.pago.sdk.model.responses.srpago.ValidatePromotionRS;
import sr.pago.sdk.model.responses.srpago.Withdrawal;
import sr.pago.sdk.object.Account;
import sr.pago.sdk.object.Card;
import sr.pago.sdk.object.Code;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.Operation;
import sr.pago.sdk.object.PaymentPreferences;
import sr.pago.sdk.object.PixzelleClass;
import sr.pago.sdk.object.SPPaymentType;
import sr.pago.sdk.object.response.BusinessCatalogResult;
import sr.pago.sdk.object.response.OperationsResponse;
import sr.pago.sdk.object.response.OperatorResponse;
import sr.pago.sdk.object.response.PaymentMobileResponse;
import sr.pago.sdk.object.response.RegisterResult;
import sr.pago.sdk.object.response.RegisterStatusResult;
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.object.response.ValidationPhoneSendResult;
import sr.pago.sdk.object.response.VersionCheckResult;
import sr.pago.sdk.object.response.ZipCodeResult;

/**
 * Created by Rodolfo on 07/08/2015.
 */
public class ParserResponseFactory extends PixzelleParserResponseFactory {

    @Override
    public SPResponse parseResponse(Context context, int method, String json) throws JSONException {
        SPResponse<?> spResponse = null;

        switch (method) {
            case Definitions.APPLICATION_LOGIN:
            case Definitions.LOGIN:
                spResponse = new SPResponse<>();
                parseLogin(spResponse, context, json, true);
                break;
            case Definitions.GET_ACCOUNT:
                spResponse = new SPResponse<AccountInfoRS>();
                parseAccountInfo(spResponse, context, json);
                break;
            case Definitions.PAYMENT:
                spResponse = new SPResponse<SrPagoTransaction>();
                parsePayment(spResponse, context, json);
                break;
            case Definitions.PAYMENT_CARD:
                spResponse = new SPResponse<SrPagoTransaction>();
                parsePaymentCard(spResponse, context, json);
                break;
            case Definitions.PAYMENT_SIGNATURE:
                spResponse = new SPResponse<>();
                parsePaymentSignature(spResponse, json);
                break;
            case Definitions.OPERATIONS_FUNDS:
                spResponse = new SPResponse<Fund>();
                OperationsResponse.parseTicketsFunds(spResponse, json);
                break;
            case Definitions.WITHDRAWALS:
                spResponse = new SPResponse<Withdrawal>();
                OperationsResponse.parseWithdrawals(spResponse, json);
                break;
            case Definitions.OPERATIONS:
                spResponse = new SPResponse<SrPagoTransaction>();
                TicketsResponse.parseOperations(spResponse, json);
                break;
            case Definitions.TICKETS:
                spResponse = new SPResponse<Ticket>();
                TicketsResponse.parseTickets(spResponse, json);
                break;
            case Definitions.SR_PAGO_CARD:
                spResponse = new SPResponse<SrPagoCard>();
                OperatorResponse.parseSrPagoCard(spResponse, json);
                break;
            case Definitions.BALANCE:
                spResponse = new SPResponse<Balance>();
                OperatorResponse.parseBalance(spResponse, json);
                break;
            case Definitions.TRANSACTIONS:
                spResponse = new SPResponse<Transaction>();
                OperatorResponse.parseSPCardTransactions(spResponse, json);
                break;
            case Definitions.GET_OPERATORS:
                spResponse = new SPResponse<Operator>();
                OperatorResponse.parseOperators(spResponse, json);
                break;
            case Definitions.SERVICES:
                spResponse = new SPResponse<Service>();
                OperatorResponse.parseService(spResponse, json);
                break;
            case Definitions.RECHARGE:
                spResponse = new SPResponse<PixzelleClass>();
                parseTaeResponse(spResponse, context, json);
                break;
            case Definitions.STORE:
                spResponse = new SPResponse<StoresMethod>();
                OperatorResponse.parseStore(spResponse, json);
                break;
            case Definitions.BANK_ACCOUNTS:
                spResponse = new SPResponse<BankAccount>();
                OperatorResponse.parseBankAccount(spResponse, json);
                break;
            case Definitions.RESET_PASSWORD:
                spResponse = new SPResponse<>();
                parseLogin(spResponse, context, json, false);
                break;
            case Definitions.ADD_OPERATOR:
                spResponse = new SPResponse<>();
                parseLogin(spResponse, context, json, false);
                break;
            case Definitions.NOTIFICATIONS:
                spResponse = new SPResponse<Notification>();
                OperatorResponse.parseNotification(spResponse, json);
                break;
            case Definitions.NOTIFICATION_POST:
                spResponse = new SPResponse<>();
                parseLogin(spResponse, context, json, false);
                break;
            case Definitions.OPERATIONS_FOUNTS_BALANCE:
                spResponse = new SPResponse<OperationFundBalance>();
                OperatorResponse.parseOperationFountBalance(spResponse, json);
                break;
            case Definitions.PAYMENT_STORE:
                spResponse = new SPResponse<SrPagoTransaction>();
                OperatorResponse.parsePaymentStore(spResponse, json);
                break;
            case Definitions.AVATAR:
                spResponse = new SPResponse<Avatar>();
                OperatorResponse.parseAvatar(spResponse, json);
                break;
            case Definitions.ABM:
                spResponse = new SPResponse<Abm>();
                OperatorResponse.parseAbm(spResponse, json);
                break;
            case Definitions.OPERATION:
                spResponse = new SPResponse<Operation>();
                OperatorResponse.parseOperation(spResponse, json);
                break;
            case Definitions.UPDATE_OPERATOR:
            case Definitions.CANCEL_CARD:
            case Definitions.BANK_ACCOUNT_POST:
            case Definitions.SEND_TICKET:
            case Definitions.UPLOAD_AVATAR:
            case Definitions.UPDATE_SETTINGS:
            case Definitions.OPERATOR_DELETE:
            case Definitions.DELETE_BANK_ACCOUNT:
            case Definitions.TRANSFER_ID:
            case Definitions.AUTH_LOGOUT:
            case Definitions.SP_CANCEL_OPERATION:
                spResponse = new SPResponse<>();
                parseNothing(spResponse, json);
                break;
            case Definitions.TRANSFER_BALANCE:
                spResponse = new SPResponse<>();
                parseTransfer(spResponse, json);
                break;
            case Definitions.SP_REQUEST_MONTHS:
                spResponse = new SPResponse<SPPaymentType>();
                OperatorResponse.monthsInterestResponse(spResponse, json);
                break;
            case Definitions.SP_TAIL_SIGNATURES:
            case Definitions.REPLACEMENT_CARD:
                spResponse = new SPResponse<>();
                tailSignature(spResponse, json);
                break;
            case Definitions.PAYMENT_MOBILE:
            case Definitions.PAYMENT_MOBILE_CONFIRMATION:
                spResponse = new SPResponse<SrPagoTransaction>();
                PaymentMobileResponse.parseTransactions(spResponse, json);
                break;
            case Definitions.REGISTER_DEVICE:
            case Definitions.UNREGISTER_DEVICE:
                spResponse = new SPResponse<>();
                registerDevice(spResponse, json);
                break;
            case Definitions.VERSION_CHECK:
                spResponse = new SPResponse<VersionCheckResult>();
                versionCheck(spResponse, json);
                break;
            case Definitions.BANK_ACCOUNT_DEFAULT:
                spResponse = new SPResponse<>();
                defaultBankAccount(spResponse, json);
                break;
            case Definitions.GET_ZIPCODE:
                spResponse = new SPResponse<ZipCodeResult>();
                getColonias(spResponse, json);
                break;
            case Definitions.GET_BUSINESS_CATALOG:
                spResponse = new SPResponse<BusinessCatalogResult>();
                getBusinessCatalog(spResponse, json);
                break;
            case Definitions.REGISTER_STATUS:
                spResponse = new SPResponse<RegisterStatusResult>();
                responseStatus(spResponse, json);
                break;
            case Definitions.REGISTER:
                spResponse = new SPResponse<RegisterResult>();
                responseRegister(spResponse, json);
                break;
            case Definitions.LINK_FB_ACCOUNT:
                spResponse = new SPResponse<>();
                responseRegisterLinkFbAccount(spResponse, context, json);
                break;
            case Definitions.LOGIN_FB:
                spResponse = new SPResponse<>();
                responseLoginFacebook(spResponse, context, json);
                break;
            case Definitions.VALIDATION_PHONE:
                spResponse = new SPResponse<>();
                responseValidationPhone(spResponse, context, json);
                break;
            case Definitions.VALIDATION_PHONE_SEND_CODE:
            case Definitions.VALIDATION_PHONE_EDIT_PHONE_NUMBER:
                spResponse = new SPResponse<ValidationPhoneSendResult>();
                responseValidationPhoneSend(spResponse, json);
                break;
            case Definitions.RECOVERY_PASSWORD:
                spResponse = new SPResponse<>();
                responseRecoveryPassword(spResponse, json);
                break;
            case Definitions.VALIDATE_DOCUMENTS_PAYMENT:
                spResponse = new SPResponse<SPTransactionDocument>();
                responseValidateDocumentsPayment(spResponse, json);
                break;
            case Definitions.UPLOAD_DOCUMENTS_PAYMENT:
                spResponse = new SPResponse<>();
                responseUploadDocumentsPayment(spResponse, json);
                break;
            case Definitions.GET_SERVER_TIME:
                spResponse = new SPResponse<PixzelleClass>();
                responseGetServerTime(spResponse, json);
                break;
            case Definitions.GET_USER_SETTINGS:
                spResponse = new SPResponse<PaymentPreferences>();
                parsePreferences(spResponse, context, json);
                break;
            case Definitions.CODE_DISTRIBUTOR:
                spResponse = new SPResponse<Code>();
                parseCode(spResponse, json);
                break;
            case Definitions.CONTRACT_DOCUMENTS_STATUS:
                spResponse = new SPResponse<SPTransactionDocument>();
                parseDocuments(spResponse, json);
                break;
            case Definitions.VALIDATE_PROMOTION:
                spResponse = new SPResponse<ValidatePromotionRS>();
                parseValidatePromotion(spResponse, json);
                break;
            case Definitions.CARD_TYPE:
                spResponse = new SPResponse<CardTypeRS>();
                parseCardType(spResponse, json);
                break;
            case Definitions.CARD_PREPAID:
                spResponse = new SPResponse<Card>();
                parseCardPrepaid(spResponse, json);
                break;
        }

        if (spResponse == null) {
            spResponse = new SPResponse<>();
        }

        spResponse.setRaw(json);

        return spResponse;
    }

    private void parseCardPrepaid(SPResponse spResponse, String json) {
        try {
            sr.pago.sdk.model.responses.srpago.Card response1 = Parser.parse(json, sr.pago.sdk.model.responses.srpago.Card.class);
            spResponse.getItems().add(response1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseCardType(SPResponse spResponse, String json) {
        try {
            CardTypeRS response1 = Parser.parse(json, CardTypeRS.class);
            spResponse.getItems().add(response1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseValidatePromotion(SPResponse response, String json) {
        try {
            ValidatePromotionRS response1 = Parser.parse(json, ValidatePromotionRS.class);
            response.getItems().add(response1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static void parseDocuments(SPResponse response, final String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        if (root.has("success")) {
            response.setStatus(true);

            final JSONArray result = root.getJSONArray("result");

            for (int index = 0; index < result.length(); index++) {
                SPTransactionDocument spTransactionDocument = new SPTransactionDocument();
                spTransactionDocument.setType(result.getJSONObject(index).getInt("type"));
                spTransactionDocument.setProcess(result.getJSONObject(index).getInt("process"));
                response.getItems().add(spTransactionDocument);
            }
        } else {
            response.setStatus(false);
        }
    }

    private static void parseTransfer(SPResponse response, final String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        if (root.has("success")) {
            response.setStatus(true);
        } else {
            response.setStatus(false);
        }
    }

    @SuppressWarnings("unchecked")
    private static void parseCode(SPResponse response, final String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        if (root.has("success")) {
            response.setStatus(root.getBoolean("success"));
            if (response.getStatus()) {
                JSONObject codeJSON = root.getJSONObject("result");
                Code code = new Code();
                code.setName(codeJSON.getString("code"));
                code.setUsername(codeJSON.getString("username"));
                code.setType(codeJSON.getInt("type"));
                code.setPercentage(codeJSON.getDouble("percentage"));
                code.setActive(codeJSON.getBoolean("active"));
                code.setImage(codeJSON.getString("image"));

                response.setItems(new ArrayList<Code>());
                response.getItems().add(code);
            }
        } else {
            response.setStatus(false);
        }
    }

    private static void parseNothing(final SPResponse response, String json) {
        try {
            JSONObject root = new JSONObject(json);

            if (root.has("success")) {
                response.setStatus(root.getBoolean("success"));
            }
        } catch (Exception ex) {
            response.setStatus(true);
        }
    }

    @SuppressWarnings("unchecked")
    private static void parsePreferences(final SPResponse response, final Context context, final String json) throws JSONException {
        PaymentPreferences paymentPreferences = PaymentPreferences.getInstance(context);

        JSONObject root = new JSONObject(json);

        if (root.has("success")) {
            response.setStatus(root.getBoolean("success"));
            if (response.getStatus()) {
                paymentPreferences.fromJSON(root.getJSONObject("result").toString());
                response.setItems(new ArrayList<>());
                response.getItems().add(paymentPreferences);
            }
        } else {
            response.setStatus(false);
        }
    }

    @SuppressWarnings("unchecked")
    private static void parseReversal(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        response.setStatus(jsonObject.getBoolean("success"));
    }

//    @Override
//    public SPResponse parseError(String json) throws JSONException {
//        Logger.logWarning("Error", json);
//        SPResponse response = new SPResponse();
//
//        JSONObject jsonObject = new JSONObject(json);
//
//        response.setStatus(false);
//        if (jsonObject.getJSONObject("error").has("detail")) {
//            Pair<?, ?> detail = new Pair<>(jsonObject.getJSONObject("error").getJSONObject("detail").getString("message"), "message");
//            response.set(detail);
//        }
//        if (jsonObject.getJSONObject("error").has("code"))
//            response.setCode(jsonObject.getJSONObject("error").getString("code"));
//        else
//            response.setCode("");
//        response.setMessage(jsonObject.getJSONObject("error").getString("message"));
//
//        return response;
//    }

//    private static SPResponse parseError(JSONObject jsonError, SPResponse response) throws JSONException {
//        response.setCode(jsonError.getString("code"));
//        response.setMessage(jsonError.getString("message"));
//
//        if (jsonError.has("detail")) {
//            response.setMessage(jsonError.getJSONObject("detail").getString("message"));
//        }
//
//        return response;
//    }

    private static void parseError(JSONObject jsonError, SPResponse response) throws JSONException {
        response.setCode(jsonError.getString("code"));
        response.setMessage(jsonError.getString("message"));

        if (jsonError.has("detail")) {
//            response.setMessage(new Pair<>(jsonError.getJSONObject("detail").getString("code"),
//                    jsonError.getJSONObject("detail").getString("message")));

            response.setMessage(jsonError.getJSONObject("detail").getString("message"));
        }
    }


    @Override
    public SPResponse parseError(String json) throws JSONException {
        SPResponse response = new SPResponse();

        JSONObject jsonObject = new JSONObject(json);

        response.setStatus(false);
        response.setRaw(json);
        try {
            response.setCode(jsonObject.getString("error"));
            response.setMessage(jsonObject.getString("error_description"));
        } catch (Exception ex) {
            try {
                response.setCode(jsonObject.getJSONObject("error").getString("code"));
                String message = jsonObject.getJSONObject("error").getString("description") + "\n";

                JSONObject causes = jsonObject.getJSONObject("error").getJSONObject("messages");

                Iterator<?> keys = causes.keys();

                while (keys.hasNext()) {
                    JSONArray descriptions = causes.getJSONArray(keys.next().toString());

                    for (int index = 0; index < descriptions.length(); index++) {
                        message = String.format("%s-%s\n", message, descriptions.getString(index));
                    }
                }

                response.setMessage(message.substring(0, message.length() - 1));
            } catch (Exception ex2) {
                try {
                    response.setCode(jsonObject.getJSONObject("error").getString("code"));
                    //FIXME ASK WHY description and Message
                    String message = jsonObject.getJSONObject("error").getString("message") + "\n";
                    if (jsonObject.getJSONObject("error").has("detail")) {
                        message = message + jsonObject.getJSONObject("error").getJSONObject("detail").get("message") + "\n";
                    }
                    response.setMessage(message);
                } catch (Exception ex3) {
                    String message = jsonObject.getString("error");

                    response.setMessage(message);
                }
            }
        }

        return response;
    }

    @SuppressWarnings("unchecked")
    private static void parseLogin(final SPResponse response, Context context, String json, boolean self) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("connection") && jsonObject.getJSONObject("connection").has("token")) {
            response.setStatus(true);
            Global.setStringKey(context, Definitions.KEY_REGISTER_TOKEN(), jsonObject.getJSONObject("connection").getString("token"));
            Global.setStringKey(context, Definitions.KEY_EXPIRATION_TOKEN(), jsonObject.getJSONObject("connection").getString("expires"));
            Global.getStringKey(context, Definitions.KEY_REGISTER_TOKEN());
        }

        if (self) {
            if (jsonObject.has("result")) {
                Global.setStringKey(context, Definitions.KEY_EMAIL_ADMIN(), jsonObject.getJSONObject("result").getString("email"));
                Global.setStringKey(context, Definitions.KEY_PHONE_ADMIN(), jsonObject.getJSONObject("result").getString("phone"));
                if (jsonObject.getJSONObject("result").has("operator")) {
                    Global.setBooleanPreference(context, Definitions.KEY_V4_OPERATOR, true);
                    Global.setStringKey(context, Definitions.SP_KEY_IS_OPERATOR(), jsonObject.getJSONObject("result").getJSONObject("operator").getString("operator"));
                    Global.setStringKey(context, Definitions.KEY_NAME_OPERATOR(), jsonObject.getJSONObject("result").getJSONObject("operator").getString("name"));
                    Global.setStringKey(context, Definitions.KEY_EMAIL_OPERATOR(), jsonObject.getJSONObject("result").getJSONObject("operator").getString("email"));
                }
            } else {
                Global.setStringKey(context, Definitions.SP_KEY_IS_OPERATOR(), "false");
            }
        }

        if (jsonObject.has("result") && jsonObject.getJSONObject("result").has("verifiedPhone")) {
            boolean verifiedPhone = jsonObject.getJSONObject("result").getBoolean("verifiedPhone");
            Global.setBooleanPreference(context, Definitions.IS_VERIFIED_PHONE, verifiedPhone);
        }
    }

    @SuppressWarnings("unchecked")
    private static void parseTaeResponse(final SPResponse response, Context context, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("success")) {
            PixzelleClass item = new PixzelleClass();
            item.setName(jsonObject.getString("result"));
            response.getItems().add(item);
        } else {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void parseAccountInfo(final SPResponse response, Context context, String json) throws JSONException {

        response.getItems().add(Parser.parse(json, AccountInfoRS.class));

        /*JSONObject jsonObject = new JSONObject(json);

        response.setStatus(jsonObject.getBoolean("success"));

        if (response.getStatus()) {
            Account account = new Account(context);
            JSONObject accountJSON = jsonObject.getJSONObject("result");

            Iterator<String> accountIterator = accountJSON.keys();
            while (accountIterator.hasNext()) {
                String key = accountIterator.next();
                switch (key) {
                    case "first_name":
                        account.setFirstName(accountJSON.getString(key));
                        break;
                    case "last_name":
                        account.setLastName(accountJSON.getString(key));
                        break;
                    case "surname":
                        account.setSurname(accountJSON.getString(key));
                        break;
                    case "variableCommission":
                        account.setVariableCommission(accountJSON.getDouble(key));
                        break;
                    case "email":
                        account.setEmail(accountJSON.getString(key));
                        break;
                    case "active":
                        account.setActive(accountJSON.getBoolean(key));
                        break;
                    case "rfc":
                        account.setRfc(accountJSON.getString(key));
                        break;
                    case "create_time":
                        account.setCreateTime(accountJSON.getString(key));
                        break;
                    case "avatar":
                        account.setAvatar(accountJSON.getString(key));
                        break;
                    case "address":
                        JSONArray addressArray = accountJSON.getJSONArray(key);
                        if (addressArray.length() == 0) {
                            account.setAddresses(null);
                        }
                        for (int addressIndex = 0; addressIndex < addressArray.length(); addressIndex++) {
                            Address address = new Address();
                            JSONObject addressJSON = addressArray.getJSONObject(addressIndex);
                            address.setStreet(addressJSON.getString("street"));
                            address.setTown(addressJSON.getString("town"));
                            address.setZipCode(addressJSON.getString("zip_code"));
                            address.setCity(addressJSON.getString("city"));
                            account.getAddresses().add(address);
                        }
                        break;
                    case "phones":
                        JSONArray phonesArray = accountJSON.getJSONArray(key);
                        if (phonesArray.length() == 0) {
                            account.setPhones(null);
                        }
                        for (int phonesIndex = 0; phonesIndex < phonesArray.length(); phonesIndex++) {
                            JSONObject phoneJSON = phonesArray.getJSONObject(phonesIndex);
                            Pair<?, ?> phone = new Pair<>(phoneJSON.getString("label"), phoneJSON.getString("number"));
                            account.getPhones().add(phone);
                        }
                        break;
                    case "ecommerce":
                        account.setEcommerce(accountJSON.getBoolean(key));
                        break;

                    case "birthday":
                        account.setBirthday(accountJSON.getString(key));

                    case "clabe":
                        account.setClabe(accountJSON.getString(key));

                        break;
                    case "fixedCommission":
                        account.setCommission(accountJSON.getDouble(key));
                        break;
                    case "company":
                        JSONObject companyJSON = accountJSON.getJSONObject(key);
                        Company company = new Company();
                        company.setBusinessId(companyJSON.getInt("bussines_id"));
                        company.setName(companyJSON.getString("name"));
                        company.setRfc(companyJSON.getString("rfc"));
                        company.setWeb(companyJSON.has("web") ? companyJSON.getString("web") : "");
                        account.setCompany(company);
                        break;

                    case "thirdparty_accounts":
                        account.setThirdPartyAccounts(accountJSON.getBoolean(key));
                        break;

                    case "automatic_withdrawal":
                        account.setAutomaticWithdrawal(accountJSON.getInt(key));
                        break;
                }
            }
            if (account.permissions == null)
                account.permissions = new SPPermissions();
            JSONObject jsonPermissions = accountJSON.getJSONObject("permissions");
            if (jsonPermissions.has("/card"))
                account.permissions.setCard(true);
            if (jsonPermissions.has("/payment/convenience-store"))
                account.permissions.setConvenienceStore(true);
            if (jsonPermissions.has("/payment/card"))
                account.permissions.setPaymentCard(true);
            if (jsonPermissions.has("/balance"))
                account.permissions.setBalance(true);
            if (jsonPermissions.has("/withdrawal"))
                account.permissions.setWithdrawal(true);
            if (jsonPermissions.has("/transfer"))
                account.permissions.setTransfer(true);
            if (jsonPermissions.has("/operations"))
                account.permissions.setOperations(true);
            if (jsonPermissions.has("/reader"))
                account.permissions.setReader(true);
            if (jsonPermissions.has("/notifications"))
                account.permissions.setNotifications(true);
            if (jsonPermissions.has("/operators"))
                account.permissions.setOperators(true);
            if (jsonPermissions.has("/bank-accounts"))
                account.permissions.setBankAccounts(true);
            if (jsonPermissions.has("/invoice"))
                account.permissions.setInvoice(true);
            if (jsonPermissions.has("/payment/ecommerce"))
                account.permissions.setEcommerce(true);
            if (jsonPermissions.has("/account/avatar")) {
                JSONArray jsonAvavatar = jsonPermissions.getJSONArray("/account/avatar");
                for (int index = 0; index < jsonAvavatar.length(); index++) {
                    if (jsonAvavatar.getString(index).equals("edit")) {
                        account.permissions.setAvatar_edit(true);
                    }
                }
                account.permissions.setAvatar(true);
                account.permissions.setAvatar_show(true);
            } else {
                account.permissions.setAvatar(false);
                account.permissions.setAvatar_show(false);
                account.permissions.setAvatar_edit(false);
            }
            response.getItems().add(account);
//            
        } else {
            throw new JSONException("Error reading information from the server / Error code: 2");
        }*/
    }

    @SuppressWarnings("unchecked")
    private static void parsePayment(final SPResponse response, Context context, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        response.setStatus(jsonObject.getBoolean("success"));

        if (response.getStatus()) {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            Global.setStringKey(context, Definitions.KEY_TRANSACTION_TOKEN, jsonResult.getString("token"));

            if (jsonResult.has("transaction")) {
                Global.setStringKey(context, Definitions.KEY_EXT_TRANSACTION, jsonResult.getString("transaction"));
            }

            SrPagoTransaction operation = new SrPagoTransaction();
            operation.setToken(jsonResult.getString("token"));
            response.getItems().add(operation);
        } else {
            parseError(jsonObject.getJSONObject("error"), response);
            //Payment
        }
    }

    @SuppressWarnings("unchecked")
    private static void parsePaymentCard(final SPResponse response, Context context, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));

        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONObject jsonResult = jsonObject.getJSONObject("result");

            Global.setStringKey(context, Definitions.KEY_TRANSACTION_TOKEN, jsonResult.getString("token"));
            Global.setStringKey(context, Definitions.KEY_EXT_TRANSACTION, jsonResult.getJSONObject("recipe").getString("transaction"));
            Global.getInstance().holder = jsonResult.getJSONObject("recipe").getJSONObject("card").getString("label");
            if (jsonResult.has("emv_response")) {
                Global.setStringKey(context, Definitions.KEY_EMV, jsonResult.getString("emv_response"));
            }
            SrPagoTransaction operation = new SrPagoTransaction();
            operation.setToken(jsonResult.getString("token"));
            operation.setMethod(jsonResult.getString("method"));
//            operation.setAuthorizationCode(jsonResult.getString("autorization_code"));
            operation.setCryptogramType(jsonResult.has("cryptogram_type") ? jsonResult.getString("cryptogram_type") : null);
            operation.setCryptogramValue(jsonResult.has("cryptogram_type") ? jsonResult.getString("cryptogram_type") : null);
            operation.setAuthorizationCode(jsonResult.has("autorization_code") ? jsonResult.getString("autorization_code") : null);
            //operation.setCryptogramValue(jsonResult.getString("cryptogram_value"));
            JSONObject jsonRecipe = jsonResult.getJSONObject("recipe");
            operation.setCardNumber(jsonRecipe.getJSONObject("card").getString("number"));
            operation.setTransactionId(jsonRecipe.getString("transaction"));

            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonRecipe.getString("timestamp"));
                operation.setTimestamp(date);
                /*operation.setHour(new SimpleDateFormat("HH:mm:ss", Locale.US).format(date));
                operation.setTimestamp(jsonRecipe.getString(""));*/

            } catch (Exception ex) {
            }
            operation.setPaymentMethod(jsonRecipe.getString("payment_method"));
            operation.setStatus(jsonRecipe.getString("status"));
            operation.setReference(jsonRecipe.getJSONObject("reference").getString("description"));
            operation.setCardHolderName(jsonRecipe.getJSONObject("card").getString("holder_name"));
            operation.setCardLabel(jsonRecipe.getJSONObject("card").getString("label"));
            operation.setAmount(Double.parseDouble(jsonRecipe.getJSONObject("total").getString("amount")));
            if (jsonRecipe.has("tip")) {
                operation.setTip(Double.parseDouble(jsonRecipe.getJSONObject("tip").getString("amount")));
            }
            operation.setCurrency(jsonRecipe.getJSONObject("total").getString("currency"));
            operation.setAffiliation(jsonRecipe.getString("affiliation"));
//            operation.setArqc(jsonRecipe.getString("ARQC"));
//            operation.setAid(jsonRecipe.getString("AID"));


            if (jsonResult.getJSONObject("recipe").has("ARQC"))
                operation.setArqc(jsonResult.getJSONObject("recipe").getString("ARQC"));
            if (jsonResult.getJSONObject("recipe").has("AID"))
                operation.setAid(jsonResult.getJSONObject("recipe").getString("AID"));
            operation.setTransactionType(jsonRecipe.getString("transaction_type"));
            operation.setHasDevolution(Boolean.valueOf(jsonRecipe.getString("hasDevolution")));
            operation.setCardType(jsonResult.getString("card_type"));
            response.getItems().add(operation);

        }
        if (response != null)
            response.setRaw(json);
    }

    @SuppressWarnings("unchecked")
    private static void parsePaymentSignature(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        response.setStatus(jsonObject.getBoolean("success"));

        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void tailSignature(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));

        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void registerDevice(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));

        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void versionCheck(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            VersionCheckResult result = new VersionCheckResult();
            result.needsUpdate = jsonResult.getBoolean("needsUpdate");
            response.getItems().add(result);
        }
    }

    @SuppressWarnings("unchecked")
    private static void defaultBankAccount(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void getColonias(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONArray jsonResult = jsonObject.getJSONArray("result");
            for (int index = 0; index < jsonResult.length(); index++) {
                ZipCodeResult zipcode = new ZipCodeResult();
                zipcode.town = jsonResult.getJSONObject(index).getString("town");
                zipcode.setName(jsonResult.getJSONObject(index).getString("town"));
                zipcode.typeTown = jsonResult.getJSONObject(index).getString("typeTown");
                zipcode.zip_code = jsonResult.getJSONObject(index).getString("zip_code");
                zipcode.city = jsonResult.getJSONObject(index).getString("city");
                zipcode.country = jsonResult.getJSONObject(index).getString("country");
                zipcode.state = jsonResult.getJSONObject(index).getString("state");
                response.getItems().add(zipcode);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void getBusinessCatalog(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONArray jsonResult = jsonObject.getJSONArray("result");
            for (int index = 0; index < jsonResult.length(); index++) {
                BusinessCatalogResult businessCatalogResult = new BusinessCatalogResult();
                businessCatalogResult.setId(jsonResult.getJSONObject(index).getInt("id"));
                businessCatalogResult.key = jsonResult.getJSONObject(index).getString("key");
                businessCatalogResult.setName(jsonResult.getJSONObject(index).getString("name"));
                response.getItems().add(businessCatalogResult);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseStatus(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            RegisterStatusResult result = new RegisterStatusResult();
            result.email = jsonResult.getBoolean("email");
            result.facebook = jsonResult.getBoolean("facebook");
            result.operator = jsonResult.getBoolean("operator");
            response.getItems().add(result);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseRegister(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            RegisterResult result = new RegisterResult();
            result.token = jsonResult.getString("token");
            result.expires = jsonResult.getString("expires");
            response.getItems().add(result);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseRegisterLinkFbAccount(final SPResponse response, Context context, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            if (jsonObject.has("connection") && jsonObject.getJSONObject("connection").has("token")) {
                Global.setStringKey(context, Definitions.KEY_REGISTER_TOKEN(), jsonObject.getJSONObject("connection").getString("token"));
                Global.setStringKey(context, Definitions.KEY_EXPIRATION_TOKEN(), jsonObject.getJSONObject("connection").getString("expires"));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseLoginFacebook(final SPResponse response, Context context, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            if (jsonObject.has("connection") && jsonObject.getJSONObject("connection").has("token")) {
                Global.setStringKey(context, Definitions.KEY_REGISTER_TOKEN(), jsonObject.getJSONObject("connection").getString("token"));
                Global.setStringKey(context, Definitions.KEY_EXPIRATION_TOKEN(), jsonObject.getJSONObject("connection").getString("expires"));
            }
            if (jsonObject.has("result")) {
                Global.setStringKey(context, Definitions.KEY_EMAIL_ADMIN(), jsonObject.getJSONObject("result").getString("email"));
                Global.setStringKey(context, Definitions.KEY_PHONE_ADMIN(), jsonObject.getJSONObject("result").getString("phone"));
            }

            if (jsonObject.has("result") && jsonObject.getJSONObject("result").has("verifiedPhone")) {
                boolean verifiedPhone = jsonObject.getJSONObject("result").getBoolean("verifiedPhone");
                Global.setBooleanPreference(context, Definitions.IS_VERIFIED_PHONE, verifiedPhone);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseValidationPhone(final SPResponse response, Context context, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseValidationPhoneSend(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            ValidationPhoneSendResult result = new ValidationPhoneSendResult();
            result.maxUserAttempts = jsonResult.getString("maxUserAttempts");
            result.currentUserAttempt = jsonResult.getString("currentUserAttempt");
            result.maxAttempts = jsonResult.getString("maxAttempts");
            result.currentAttempt = jsonResult.getString("currentAttempt");
            result.secondsToNextAttempt = jsonResult.getString("secondsToNextAttempt");
            response.getItems().add(result);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseValidationPhoneEdit(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            ValidationPhoneSendResult result = new ValidationPhoneSendResult();
            result.maxUserAttempts = jsonResult.getString("maxUserAttempts");
            result.currentUserAttempt = jsonResult.getString("currentUserAttempt");
            result.maxAttempts = jsonResult.getString("maxAttempts");
            result.currentAttempt = jsonResult.getString("currentAttempt");
            result.secondsToNextAttempt = jsonResult.getString("secondsToNextAttempt");
            response.getItems().add(result);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseRecoveryPassword(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseValidateDocumentsPayment(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            JSONArray jsonResult = jsonObject.getJSONArray("result");
            if (jsonResult != null) {
                for (int index = 0; index < jsonResult.length(); index++) {
                    SPTransactionDocument transactionDocument = new SPTransactionDocument();
                    transactionDocument.setName(jsonResult.getJSONObject(index).getString("name"));
                    transactionDocument.setType(jsonResult.getJSONObject(index).getInt("type"));
                    transactionDocument.setDocumentDescription(jsonResult.getJSONObject(index).getString("description"));
                    response.getItems().add(transactionDocument);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseUploadDocumentsPayment(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        }
    }

    @SuppressWarnings("unchecked")
    private static void responseGetServerTime(final SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (!response.getStatus()) {
            parseError(jsonObject.getJSONObject("error"), response);
        } else {
            PixzelleClass time = new PixzelleClass();
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            time.setName(jsonResult.getString("currentDate"));
            response.getItems().add(time);
        }
    }
}
