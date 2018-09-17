package sr.pago.sdk.object.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pixzelle on 29/06/16.
 */
public class CardRequest {
    public static String parseReplacementCard( String numberCard) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number",numberCard);
        return jsonObject.toString();
    }

}
