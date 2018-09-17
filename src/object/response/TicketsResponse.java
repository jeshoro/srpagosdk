package sr.pago.sdk.object.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.parsers.Parser;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.Ticket;
import sr.pago.sdk.model.responses.srpago.Fee;
import sr.pago.sdk.model.responses.srpago.FeeDetail;
import sr.pago.sdk.object.Transference;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Reynaldo on 25/09/2015 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public class TicketsResponse {

    public static void parseOperations(SPResponse response, String json) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            if (response.getStatus()) {
                JSONObject jsonResult = jsonObject.getJSONObject("result");
                JSONArray JsonOperations = jsonResult.getJSONArray("operations");

                for (int index = 0; index < JsonOperations.length(); index++) {
                    JSONObject jsonOperation = JsonOperations.getJSONObject(index);

                    SrPagoTransaction srPagoTransaction = new SrPagoTransaction();
                    srPagoTransaction.setTransactionId(jsonOperation.getString("transaction"));

                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonOperation.getString("timestamp"));
                        srPagoTransaction.setTimestamp(date);
                    } catch (Exception ex) {
                        Logger.logError(ex);
                    }

                    srPagoTransaction.setPaymentMethod(jsonOperation.getString("payment_method"));
                    srPagoTransaction.setAuthorizationCode(jsonOperation.getString("authorization_code"));
                    srPagoTransaction.setHasDevolution(jsonOperation.getBoolean("hasDevolution"));

                    if (JsonOperations.getJSONObject(index).has("type")) {
                        srPagoTransaction.setTransactionType(jsonOperation.getString("type"));
                    }

                    if (JsonOperations.getJSONObject(index).has("transaction_type")) {
                        srPagoTransaction.setTransactionType(jsonOperation.getString("transaction_type"));
                    }

                    if (JsonOperations.getJSONObject(index).has("commission")) {
                        srPagoTransaction.setCommission(jsonOperation.getJSONObject("commission").getDouble("amount"));
                    }

                    if (JsonOperations.getJSONObject(index).has("operator")) {
                        srPagoTransaction.setOperator(jsonOperation.getJSONObject("operator").getString("name"));
                    } else {
                        srPagoTransaction.setOperator(null);
                    }

                    if (JsonOperations.getJSONObject(index).has("monthly_installments")) {
                        srPagoTransaction.setMonthlyInstallments(jsonOperation.getInt("monthly_installments"));
                    } else {
                        srPagoTransaction.setMonthlyInstallments(0);
                    }

                    JSONArray jsonFeeDetails = jsonOperation.getJSONArray("fee_details");
                    List<FeeDetail> feeDetails = new ArrayList<>();
                    for (int indexFee = 0; indexFee < jsonFeeDetails.length(); indexFee++) {
                        JSONObject jsonFeeDetail = jsonFeeDetails.getJSONObject(indexFee);
                        FeeDetail feeDetail = Parser.parse(jsonFeeDetail.toString(), FeeDetail.class);
                        feeDetails.add(feeDetail);
                    }
                    srPagoTransaction.setFeeDetails(feeDetails);

                    /*JSONObject jsonFeeDetails = JsonOperations.getJSONObject(index).getJSONObject("fee");
                    srPagoTransaction.setReference(jsonFeeDetails.getString("description"));*/

                    JSONObject jsonReference = JsonOperations.getJSONObject(index).getJSONObject("reference");
                    srPagoTransaction.setReference(jsonReference.getString("description"));

                    JSONObject jsonCard = JsonOperations.getJSONObject(index).getJSONObject("card");
                    srPagoTransaction.setCardType(jsonCard.getString("type"));
                    srPagoTransaction.setCardNumber(jsonCard.getString("number"));

                    if (jsonCard.has("holder_name")) {
                        srPagoTransaction.setCardHolderName(jsonCard.getString("holder_name"));
                    }

                    JSONObject jsonTotal = jsonOperation.getJSONObject("total");

                    srPagoTransaction.setAmount(Double.parseDouble(jsonTotal.getString("amount")));

                    srPagoTransaction.setCurrency(jsonTotal.getString("currency"));

                    JSONObject jsonOrigin = JsonOperations.getJSONObject(index).getJSONObject("origin");
                    JSONObject jsonlocation = jsonOrigin.getJSONObject("location");


                    srPagoTransaction.setLatitude(jsonlocation.getDouble("latitude"));
                    srPagoTransaction.setLongitude(jsonlocation.getDouble("longitude"));

                    JSONObject jsonTip = JsonOperations.getJSONObject(index).getJSONObject("tip");

                    srPagoTransaction.setTip(jsonTip.getDouble("amount"));
                    //srPagoTransaction.setAmount(jsonResult.getInt("total"));

                    if (srPagoTransaction.getPaymentMethod().equals("TRA")) {
                        Transference transference = new Transference();
                        parseTransferencePerson(JsonOperations.getJSONObject(index).getJSONObject("transference").getJSONObject("source"), transference.getSource());
                        parseTransferencePerson(JsonOperations.getJSONObject(index).getJSONObject("transference").getJSONObject("destination"), transference.getDestination());

                        srPagoTransaction.setTransference(transference);
                    }

                    response.getItems().add(srPagoTransaction);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseTickets(SPResponse response, String json) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            if (response.getStatus()) {
                JSONObject jsonResult = jsonObject.getJSONObject("result");
                JSONArray JsonOperations = jsonResult.getJSONArray("operations");

                for (int index = 0; index < JsonOperations.length(); index++) {
                    JSONObject jsonOperation = JsonOperations.getJSONObject(index);

                    Ticket ticket = new Ticket();
                    ticket.setTransactionId(jsonOperation.getString("transaction"));

                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonOperation.getString("timestamp"));
                        ticket.setTimestamp(date);
                    } catch (Exception ex) {
                        Logger.logError(ex);
                    }

//                    ticket.setStore(jsonOperation.getJSONObject("store").getString("name"),jsonOperation.getJSONObject("store").getString("short_name"));
                    ticket.setName(jsonOperation.getJSONObject("store").getString("name"));
                    ticket.setShortName(jsonOperation.getJSONObject("store").getString("short_name"));


                    ticket.setBankAccountNumber(jsonOperation.getString("bank_account_number"));
                    ticket.setBankName(jsonOperation.getString("bank_name"));
                    ticket.setPaymentId(jsonOperation.getString("payment_id"));
                    ticket.setShortId(jsonOperation.getString("short_id"));

                    ticket.setReference(jsonOperation.getJSONObject("reference").getString("description"));

//                    ticket.setTotal(jsonOperation.getJSONObject("total").getDouble("amount"),jsonOperation.getJSONObject("total").getString("currency"));
                    ticket.setAmount(jsonOperation.getJSONObject("total").getDouble("amount"));
                    ticket.setCurrency(jsonOperation.getJSONObject("total").getString("currency"));

                    ticket.setUrl(jsonOperation.getString("url"));

                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonOperation.getString("expiration_date"));
                        ticket.setExpirationDate(date);
                    } catch (Exception ex) {
                        Logger.logError(ex);
                    }


                    ticket.setStatus(jsonOperation.getString("status"));
                    ticket.setStatusCode(jsonOperation.getString("status_code"));
                    if (JsonOperations.getJSONObject(index).has("phone")) {
//                        ticket.setPhone(jsonOperation.getJSONObject("phone").getString("number"));
                        ticket.setNumber(jsonOperation.getJSONObject("phone").getString("number"));
                    }

                    if (JsonOperations.getJSONObject(index).has("email")) {
//                        ticket.setPhone(jsonOperation.getJSONObject("phone").getString("number"));
                        ticket.setNumber(jsonOperation.getString("email"));
                    }


                    response.getItems().add(ticket);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void parseTransferencePerson(JSONObject root, Transference.Person person) throws JSONException {
        person.setId(root.getInt("id"));
        person.setName(root.getString("first_name"));
        person.setLastName(root.getString("last_name"));
        person.setSurname(root.getString("surname"));
        person.setEmail(root.getString("email"));
        person.setUsername(root.getString("username"));
        person.setActive(root.getBoolean("active"));
        person.setFullProfile(root.getBoolean("fullProfile"));
        person.setEmailSent(root.getBoolean("emailSent"));
        person.setHoursReversal(root.getInt("hoursReversal"));
    }

    public static Response parseTicketsId(String json) throws JSONException {
        Response response = new Response();
        JSONObject jsonObject = new JSONObject(json);
        response.setStatus(jsonObject.getBoolean("success"));
        if (response.getStatus()) {
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            jsonResult.getString("transaction");
            jsonResult.getString("timestamp");
            jsonResult.getString("payment_method");
            jsonResult.getString("authorization_code");
            jsonResult.getString("status");

            JSONObject jsonReference = jsonResult.getJSONObject("reference");
            jsonReference.getString("description");

            JSONObject jsonCard = jsonResult.getJSONObject("card");

            jsonCard.getString("holder_name");
            jsonCard.getString("type");
            jsonCard.getString("number");

            JSONObject jsonTotal = jsonResult.getJSONObject("total");

            jsonTotal.getString("amount");
            jsonTotal.getString("currency");

            jsonResult.getJSONObject("operations").getString("type");
            jsonResult.getJSONObject("operations").getString("related");
            jsonResult.getJSONObject("operations").getString("url");

        } else {
        }

        return response;
    }

    public static SrPagoTransaction parseTransaction(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.getBoolean("success")) {
            JSONObject resultJSON = jsonObject.getJSONObject("result");

            SrPagoTransaction srPagoTransaction = new SrPagoTransaction();
            srPagoTransaction.setToken(resultJSON.has("token") ? resultJSON.getString("token") : null);
            srPagoTransaction.setMethod(resultJSON.has("method") ? resultJSON.getString("method") : null);
            srPagoTransaction.setAuthorizationCode(resultJSON.has("autorization_code") ? resultJSON.getString("autorization_code") : null);
            srPagoTransaction.setCryptogramType(resultJSON.has("cryptogram_type") ? resultJSON.getString("cryptogram_type") : null);
            srPagoTransaction.setCryptogramValue(resultJSON.has("ca") ? resultJSON.getString("cryptogram_value") : null);
            srPagoTransaction.setCardNumber(resultJSON.getString("card") + "XXXXXXXX" + resultJSON.getJSONObject("recipe").getJSONObject("card").getString("number"));
            srPagoTransaction.setTransactionId(resultJSON.getJSONObject("recipe").getString("transaction"));

            try {
                srPagoTransaction.setTimestamp(new SimpleDateFormat(Definitions.TIMESTAMP(), Locale.US).parse(resultJSON.getJSONObject("recipe").getString("timestamp")));
            } catch (Exception ex) {
            }

            if (resultJSON.has("commission")) {
                srPagoTransaction.setCommission(resultJSON.getJSONObject("commission").getDouble("amount"));
            }

            if (resultJSON.has("tip")) {
                srPagoTransaction.setTip(resultJSON.getJSONObject("tip").getDouble("amount"));
            }

            srPagoTransaction.setPaymentMethod(resultJSON.getJSONObject("recipe").getString("payment_method"));
            srPagoTransaction.setLatitude(Double.parseDouble(resultJSON.getJSONObject("recipe").getJSONObject("origin").getJSONObject("location").getString("latitude")));
            srPagoTransaction.setLongitude(Double.parseDouble(resultJSON.getJSONObject("recipe").getJSONObject("origin").getJSONObject("location").getString("longitude")));
            srPagoTransaction.setStatus(resultJSON.getJSONObject("recipe").getString("status"));
            srPagoTransaction.setReference(resultJSON.getJSONObject("recipe").getJSONObject("reference").getString("description"));
            srPagoTransaction.setCardHolderName(resultJSON.getJSONObject("recipe").getJSONObject("card").getString("holder_name"));
            srPagoTransaction.setCardLabel(resultJSON.getJSONObject("recipe").getJSONObject("card").getString("label"));
            srPagoTransaction.setAmount(Double.parseDouble(resultJSON.getJSONObject("recipe").getJSONObject("total").getString("amount")));
            srPagoTransaction.setTip(Double.parseDouble(resultJSON.getJSONObject("recipe").getJSONObject("tip").getString("amount")));
            try {
                srPagoTransaction.setMonthlyInstallments(!resultJSON.getJSONObject("recipe").has("monthly_installments") ? 0 : Integer.parseInt(resultJSON.getJSONObject("recipe").getString("monthly_installments")));
            } catch (Exception ex) {
                srPagoTransaction.setMonthlyInstallments(0);
            }
            srPagoTransaction.setCurrency(resultJSON.getJSONObject("recipe").getJSONObject("total").getString("currency"));
            srPagoTransaction.setAffiliation(resultJSON.getJSONObject("recipe").getString("affiliation"));
            if (resultJSON.getJSONObject("recipe").has("ARQC"))
                srPagoTransaction.setArqc(resultJSON.getJSONObject("recipe").getString("ARQC"));
            if (resultJSON.getJSONObject("recipe").has("AID"))
                srPagoTransaction.setAid(resultJSON.getJSONObject("recipe").getString("AID"));
            srPagoTransaction.setTransactionType(resultJSON.getJSONObject("recipe").getString("transaction_type"));
            srPagoTransaction.setUrl(resultJSON.getJSONObject("recipe").getString("url"));
            srPagoTransaction.setHasDevolution(resultJSON.getJSONObject("recipe").getBoolean("hasDevolution"));
            srPagoTransaction.setCardType(resultJSON.getString("card_type"));

            return srPagoTransaction;
        }

        return null;
    }

    public static String parseIdTicket(String email, String phone) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", "");
        jsonObject.put("email", email);
        jsonObject.put("phone", phone);

        return jsonObject.toString();
    }
}
