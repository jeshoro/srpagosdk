package sr.pago.sdk.object.response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.parsers.Parser;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.model.Abm;
import sr.pago.sdk.model.Avatar;
import sr.pago.sdk.model.Balance;
import sr.pago.sdk.model.BankAccount;
import sr.pago.sdk.model.Notification;
import sr.pago.sdk.model.OperationFundBalance;
import sr.pago.sdk.model.Operator;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.Service;
import sr.pago.sdk.model.Store;
import sr.pago.sdk.model.StoresMethod;
import sr.pago.sdk.model.Transaction;
import sr.pago.sdk.model.responses.srpago.SrPagoCardRS;
import sr.pago.sdk.object.Card;
import sr.pago.sdk.object.Operation;
import sr.pago.sdk.object.SPPaymentType;
import sr.pago.sdk.utils.Logger;


/**
 * Created by Reynaldo on 28/09/2015.
 */
public class OperatorResponse {
    @SuppressWarnings("unchecked")
    public static void parseSrPagoCard(SPResponse response, String json) {

        try {
            SrPagoCardRS response1 = Parser.parse(json, SrPagoCardRS.class);
            response.getItems().add(response1.getResult().get(0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResult = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResult.length(); index++) {
                SrPagoCard card = new SrPagoCard();
                card.setHolderName(jsonResult.getJSONObject(index).getString("holder_name"));
                card.setType(jsonResult.getJSONObject(index).getString("type"));
                card.setNumber(jsonResult.getJSONObject(index).getString("number"));

                card.setActive(jsonResult.getJSONObject(index).getBoolean("active"));
                if (jsonResult.getJSONObject(index).has("cancelStatus")) {
                    card.setCancelStatus(jsonResult.getJSONObject(index).getString("cancelStatus"));
                } else {
                    card.setCancelStatus("");
                }


                if (jsonResult.getJSONObject(index).has("beneficiary")) {

                    JSONObject jsonBeneficiary = null;
                    SrPagoCard.Beneficiary beneficiary = card.new Beneficiary();

                    try {
                        jsonBeneficiary = jsonResult.getJSONObject(index).getJSONObject("beneficiary");

                    } catch (JSONException ex) {

                    }

                    if (jsonBeneficiary != null) {

                        if (jsonBeneficiary.has("name"))
                            beneficiary.setName(jsonBeneficiary.getString("name"));
                        if (jsonBeneficiary.has("address"))
                            beneficiary.setAddress(jsonBeneficiary.getString("address"));
                        if (jsonBeneficiary.has("relation"))
                            beneficiary.setRelation(jsonBeneficiary.getInt("relation"));

                        try {
                            beneficiary.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonBeneficiary.getString("birth_date")));
                        } catch (Exception e) {
                            beneficiary.setDate(new Date());
                        }
                        card.setBeneficiary(beneficiary);
                    }
                }

                if (jsonResult.getJSONObject(index).has("birth_date")) {
                    try {
                        card.setBirthDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonResult.getJSONObject(index).getString("birth_date")));
                    } catch (Exception e) {
                        card.setBirthDate(null);
                    }
                }

                if (jsonResult.getJSONObject(index).has("nationality")) {
                    card.setNationality(jsonResult.getJSONObject(index).getString("nationality"));
                }

                if (jsonResult.getJSONObject(index).has("curp")) {
                    card.setCurp(jsonResult.getJSONObject(index).getString("curp"));
                }

                if (jsonResult.getJSONObject(index).has("rfc")) {
                    card.setRfc(jsonResult.getJSONObject(index).getString("rfc"));
                }

                if (jsonResult.getJSONObject(index).has("income")) {
                    String income = jsonResult.getJSONObject(index).getString("income");
                    if (!income.isEmpty())
                        card.setIncome(income);
                }

                if (jsonResult.getJSONObject(index).has("balance")) {
                    if (!jsonResult.getJSONObject(index).getString("balance").isEmpty())
                        card.setBalance(jsonResult.getJSONObject(index).getString("balance"));
                }

                if (jsonResult.getJSONObject(index).has("transactions")) {
                    card.setTransactions(jsonResult.getJSONObject(index).getInt("transactions"));
                }

                response.getItems().add(card);
            }
        }*/
    }

    @SuppressWarnings("unchecked")
    public static void parseBalance(final SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResult = jsonObject.getJSONArray("result");
            JSONObject array = jsonResult.getJSONObject(0).getJSONObject("balance");

            Balance balance = new Balance();
            balance.setTimesStamp(array.getString("timestamp"));
            balance.setTotalFunds(array.getDouble("total_founds"));
            balance.setCurrency(array.getString("currency"));

            response.getItems().add(balance);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseSPCardTransactions(final SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            if (response.getStatus()) {

                JSONArray jsonResult = jsonObject.getJSONArray("result");

                JSONObject jsonCard = jsonResult.getJSONObject(0).getJSONObject("card");

                Transaction transactionsUno = new Transaction();
                transactionsUno.setTypeCatd(jsonCard.getString("type"));
                transactionsUno.setNumber(jsonCard.getInt("number"));
                transactionsUno.setClient(jsonCard.getInt("client"));

                //response.getItems().add(transactionsUno);
                JSONArray jsonTransactions = jsonResult.getJSONObject(0).getJSONArray("transactions");

                for (int index = 0; index < jsonResult.getJSONObject(0).getJSONArray("transactions").length(); index++) {
                    Transaction transactions = new Transaction();

                    String stamp = jsonTransactions.getJSONObject(index).getString("timestamp");
                    try {
                        SimpleDateFormat format = new SimpleDateFormat(Definitions.TIMESTAMP(), Locale.US);
                        transactions.setTimestamp(format.parse(stamp));
                    } catch (Exception ex) {
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            transactions.setTimestamp(format.parse(stamp));
                        } catch (ParseException e) {
                            Logger.logError(e);
                        }
                    }

                    JSONObject jsunReference = jsonTransactions.getJSONObject(index).getJSONObject("reference");

                    transactions.setDescriptionTrans(jsunReference.getString("description"));

                    JSONObject jsunTotal = jsonTransactions.getJSONObject(index).getJSONObject("total");

                    transactions.setAmount(jsunTotal.getDouble("amount"));
                    transactions.setCurrency(jsunTotal.getString("currency"));

                    transactions.setType(jsonTransactions.getJSONObject(index).getString("type"));

                    response.getItems().add(transactions);
                }

            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseOperators(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResul = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResul.length(); index++) {
                Operator operator = new Operator();
                operator.setOperator(jsonResul.getJSONObject(index).getBoolean("operator"));
                operator.setClave(jsonResul.getJSONObject(index).getInt("id"));
                operator.setName(jsonResul.getJSONObject(index).getString("name"));
                operator.setEmail(jsonResul.getJSONObject(index).getString("email"));
                operator.setActive(jsonResul.getJSONObject(index).getBoolean("active"));

                response.getItems().add(operator);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static void parseService(final SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResult = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResult.length(); index++) {
                Service services = new Service();

                services.setCompany(jsonResult.getJSONObject(index).getString("company"));
                services.setUrl(jsonResult.getJSONObject(index).getString("url"));

                JSONArray jsonProductos = jsonResult.getJSONObject(index).getJSONArray("products");
                services.setServices(new ArrayList<Service>());
                for (int inde = 0; inde < jsonProductos.length(); inde++) {
                    Service services1 = new Service();

                    services1.setType(jsonProductos.getJSONObject(inde).getString("type"));
                    services1.setSku(jsonProductos.getJSONObject(inde).getString("sku"));
                    services1.setAmount(jsonProductos.getJSONObject(inde).getDouble("amount"));
                    services1.setDescription(jsonProductos.getJSONObject(inde).getString("description"));
                    services.getServices().add(services1);
                }
                response.getItems().add(services);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseStore(final SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResul = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResul.length(); index++) {
                StoresMethod stores = new StoresMethod();
                try {
                    JSONArray jsonMethods = jsonResul.getJSONObject(index).getJSONArray("options");
                    stores.setMethod(jsonResul.getJSONObject(index).getString("method"));
                    stores.setStoreName(jsonResul.getJSONObject(index).getString("name"));
                    for (int j = 0; j < jsonMethods.length(); j++) {
                        Store store = new Store();

                        store.setNameStore(jsonMethods.getJSONObject(j).getString("name"));
                        store.setDescription(jsonMethods.getJSONObject(j).getString("description"));
                        store.setShortName(jsonMethods.getJSONObject(j).getString("shortName"));
                        store.setUrl(jsonMethods.getJSONObject(j).getString("url"));
                        store.setMinimo(jsonMethods.getJSONObject(j).getString("min_operation"));
                        store.setMaximo(jsonMethods.getJSONObject(j).getString("max_operation"));
                        store.setPositionMethod(index + "");
                        stores.getStores().add(store);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                response.getItems().add(stores);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseAuthLogout(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
        } catch (JSONException e) {
            response.setStatus(false);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseBankAccount(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResult = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResult.length(); index++) {
                //TODO: add viewType to each account
                BankAccount bankAccount = new BankAccount();
                bankAccount.setClave(jsonResult.getJSONObject(index).getInt("id"));
                bankAccount.setOrigin(jsonResult.getJSONObject(index).has("origin") ? jsonResult.getJSONObject(index).getInt("origin") : 0);
                bankAccount.setBankName(jsonResult.getJSONObject(index).getString("bank_name"));
                bankAccount.setType(jsonResult.getJSONObject(index).getString("type"));
                bankAccount.setAlias(jsonResult.getJSONObject(index).getString("alias"));
                bankAccount.setAccountNumberSuffix(jsonResult.getJSONObject(index).getString("account_number_suffix"));
                bankAccount.setActive(jsonResult.getJSONObject(index).getBoolean("active"));
                bankAccount.setAuthorized(jsonResult.getJSONObject(index).getString("authorized"));
                bankAccount.setDefault(jsonResult.getJSONObject(index).getBoolean("default"));
                response.getItems().add(bankAccount);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseNotification(SPResponse response, String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResult = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResult.length(); index++) {
                Notification notification = new Notification();

                notification.setName(jsonResult.getJSONObject(index).getString("name"));
                notification.setDescription(jsonResult.getJSONObject(index).getString("description"));
                notification.setEmail(jsonResult.getJSONObject(index).getBoolean("email"));
                response.getItems().add(notification);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void parseOperator(SPResponse response, String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONObject jsonResult = jsonObject.getJSONObject("result");


            Operator operator = new Operator();

            operator.setName(jsonResult.getString("name"));
            operator.setEmail(jsonResult.getString("email"));
            response.getItems().add(operator);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseOperationFountBalance(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONObject jsonResult = jsonObject.getJSONObject("result");

            OperationFundBalance notification = new OperationFundBalance();
            notification.setTotalFounds(jsonResult.getDouble("total_founds"));
            notification.setAvailableFounds(jsonResult.getDouble("available_founds"));
            notification.setPendingFunds(jsonResult.getDouble("pending_funds"));
            notification.setTotalRedrawal(jsonResult.getDouble("total_redrawal"));
            JSONObject jsonArray = jsonResult.getJSONObject("commission");

            notification.setTotalRedrawal(jsonArray.getDouble("amount"));
            notification.setTotalRedrawal(jsonArray.getDouble("iva"));
            notification.setTotalRedrawal(jsonResult.getDouble("total_redrawal"));
            notification.setCurrency(jsonResult.getString("currency"));
            notification.setTimestamp(jsonResult.getString("timestamp"));
            response.getItems().add(notification);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void parsePaymentStore(final SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            response.setStatus(jsonObject.getBoolean("success"));
            SrPagoTransaction operation = new SrPagoTransaction();
            if (jsonResult.has("token"))
                operation.setToken(jsonResult.getString("token"));
            response.getItems().add(operation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static Response parseAccountImg(String json) {

        Response response = new Response();
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    @SuppressWarnings("unchecked")
    public static void parseAvatar(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));

            Avatar avatar = new Avatar();
            if (jsonObject.has("result")) {
                avatar.setAvatar(jsonObject.getString("result").replace("data:image/jpg;base64, ", ""));
                response.getItems().add(avatar);
            } else {

            }

        } catch (JSONException e) {
            Logger.logError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseAbm(final SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONArray jsonResult = jsonObject.getJSONArray("result");

            for (int index = 0; index < jsonResult.length(); index++) {
                Abm abm = new Abm();
                abm.setName(jsonResult.getJSONObject(index).getString("name"));
                abm.setNumber(jsonResult.getJSONObject(index).getString("number"));
                abm.setShortName(jsonResult.getJSONObject(index).getString("short_name"));
                response.getItems().add(abm);
            }
        } catch (JSONException e) {
            Logger.logError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void parseOperation(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONObject jsonResul = jsonObject.getJSONObject("result");

            Operation operation = new Operation(null);
            operation.setTransactionType(jsonResul.getString("transaction"));

            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(jsonResul.getString("timestamp"));
                operation.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date));
                operation.setHour(new SimpleDateFormat("HH:mm:ss", Locale.US).format(date));
            } catch (Exception ex) {
            }

            operation.setAuthNumber(jsonResul.getString("authorization_code"));
            operation.setCommerce(" ");
            JSONObject object = jsonResul.optJSONObject("reference");
            operation.setDescription(object.getString("description"));
            JSONObject objectCard = jsonResul.optJSONObject("card");

            operation.setCard(new Card(null));
            operation.getCard().setCardHolderName(objectCard.getString("holder_name"));
            operation.getCard().setType(objectCard.getString("type"));
            operation.getCard().setCardNumber(objectCard.getString("number"));
            operation.setApplicationLabel(objectCard.has("label") ? objectCard.getString("label") : "");
            JSONObject objectTotal = jsonResul.optJSONObject("total");
            operation.setAmountF(objectTotal.getDouble("amount"));
            operation.setCurrency(objectTotal.getString("currency"));

            operation.setFolio(jsonResul.has("affiliation") ? jsonResul.getString("affiliation") : "");
            if (jsonResul.has("ARQC")) {
                operation.setCryptogramType(jsonResul.getString("ARQC"));
            } else
                operation.setCryptogramType("");
            //operation.setCryptogramValue(jsonResul.has("affiliation") ? jsonResul.getString("ARQC") : "");
            operation.setCryptogramType(jsonResul.has("cryptogram_type") ? jsonResul.getString("cryptogram_type") : "");
            operation.setApplicationIdentifier(jsonResul.has("AID") ? jsonResul.getString("AID") : "");
            operation.setTransactionType(jsonResul.has("transaction_type") ? jsonResul.getString("transaction_type") : "");

            operation.setHasDevolution(jsonResul.getBoolean("hasDevolution"));
            response.getItems().add(operation);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void monthsInterestResponse(SPResponse response, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONObject jsonResul = jsonObject.getJSONObject("result");

            if (jsonResul.has("promotions")) {
                JSONArray jsonMonths = jsonResul.getJSONArray("promotions");
                for (int index = 0; index < jsonMonths.length(); index++) {
                    SPPaymentType month = new SPPaymentType();
                    month.setMonths(jsonMonths.getJSONObject(index).getInt("months"));
                    month.setCommission(jsonMonths.getJSONObject(index).getDouble("rate"));
                    month.setPerMonth(jsonMonths.getJSONObject(index).getDouble("amount"));
                    response.getItems().add(month);
                }
            }

            if (jsonResul.has("cash")) {
                SPPaymentType cash = new SPPaymentType();
                cash.setMonths(0);
                cash.setCommission(jsonResul.getJSONObject("cash").getDouble("variable"));
                response.getItems().add(cash);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
