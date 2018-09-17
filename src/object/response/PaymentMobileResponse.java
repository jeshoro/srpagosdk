package sr.pago.sdk.object.response;

import org.json.JSONException;
import org.json.JSONObject;

import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.model.SPResponse;

/**
 * Created by Pixzelle on 08/07/16.
 */
public class PaymentMobileResponse {
    public static void parseTransactions(SPResponse response, String json) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getBoolean("success"));
            JSONObject jsonResul = jsonObject.getJSONObject("result");
            response.setStatus(jsonObject.getBoolean("success"));
            SrPagoTransaction operation = new SrPagoTransaction();
            if (jsonResul.has("transaction"))
                operation.setToken(jsonResul.getString("transaction"));
            response.getItems().add(operation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
