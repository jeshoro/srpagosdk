package sr.pago.sdk.object.response;

import org.json.JSONException;
import org.json.JSONObject;

import sr.pago.sdk.api.parsers.Parser;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.Fund;
import sr.pago.sdk.model.responses.srpago.SrPagoCardRS;
import sr.pago.sdk.model.responses.srpago.WithdrawalRS;


/**
 * Created by Reynaldo on 06/10/2015.
 */
public class OperationsResponse {
    //    public static Response parseOperations(String json) throws JSONException {
//        
//        OperationsFounds operationsFounds=new OperationsFounds();
//        Response response = new Response();
//        JSONObject jsonObject=new  JSONObject(json);
//        response.setStatus(jsonObject.getBoolean("success"));
//        JSONObject jsonResult = jsonObject.getJSONObject("result");
//
//         for (int indexOne=0;indexOne<jsonResult.length();indexOne++) {
//            operationsFounds.setTotalFounds(jsonResult.getJSONObject(String.valueOf(indexOne)).getDouble("Total"));
//            operationsFounds.setAvailableFounds(jsonResult.getJSONObject(String.valueOf(indexOne)).getString("available_founds"));
//            operationsFounds.setCurrency(jsonResult.getJSONObject(String.valueOf(indexOne)).getString("currency"));
//            operationsFounds.setTimesTamp(jsonResult.getJSONObject(String.valueOf(indexOne)).getString("timestamp"));
//            response.getOperationsFounds().add(operationsFounds);
//        }
//
//        return response;
//    }

    @SuppressWarnings("unchecked")
    public static void parseTicketsFunds(SPResponse response, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        Fund founds = new Fund();
        founds.setTimestamp(jsonObject.getJSONObject("result").getString("total_founds"));
        founds.setTotalFounds(jsonObject.getJSONObject("result").getDouble("total_founds"));
        founds.setAvailableFounds(jsonObject.getJSONObject("result").getString("available_founds"));
        founds.setCurrency(jsonObject.getJSONObject("result").getString("currency"));
        founds.setTimestamp(jsonObject.getJSONObject("result").getString("timestamp"));
        response.getItems().add(founds);
    }

    public static void parseWithdrawals(SPResponse response, String json) throws JSONException {
        try {
            WithdrawalRS response1 = Parser.parse(json, WithdrawalRS.class);
            response.getItems().addAll(response1.getResult().getOperations());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
