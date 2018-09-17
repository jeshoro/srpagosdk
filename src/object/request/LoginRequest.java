package sr.pago.sdk.object.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Reynaldo on 25/09/2015 for SrPagoSDK.
 *
 */
public class LoginRequest {
    public static String parse( String username, String password,String operator) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username",username);
        jsonObject.put("operator",operator);
        jsonObject.put("password",password);

        return jsonObject.toString();
    }


    public static String parseRecharge( String number, String sku) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("phone",number);
        jsonObject.put("sku",sku);

        return jsonObject.toString();
    }


    public static String parseResetPassword( String username, String usernameOne) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username",username);
        //jsonObject.put("operator",usernameOne);

        return jsonObject.toString();
    }

    public static String parseOperatorAdd( String email, String name,String pwd) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("email",email);
        jsonObject.put("name",name);
        jsonObject.put("password",pwd);

        return jsonObject.toString();
    }



    public static String parseNotificationPost ( String name, boolean email) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("email", email);
        //jsonObject.put("name",name);

        return jsonObject.toString();
    }

    public static String parsePeymentStore( String email,String total, String store,String phone,String token ) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (!email.equals("")){
            jsonObject.put("email",email.equals("") ? null : email);
        }else{
            jsonObject.put("phone",phone.equals("") ? null : phone);
        }
        jsonObject.put("total",total);
        jsonObject.put("store",store);
        jsonObject.put("token", token);

        return jsonObject.toString();
    }


    public static String parsePaymentMethod(String phone, String token) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone.equals("") ? null : phone);
        jsonObject.put("token", token);
        return jsonObject.toString();
    }



    public static String parseMethodConfirmation(String otp, String token) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("otp", otp.equals("") ? null : otp);
        jsonObject.put("token", token);
        return jsonObject.toString();
    }


    public static String parseAccountImg( String img ) throws JSONException {
        JSONObject jsonObject = new JSONObject();
//        Log.e("email 1", "" + img);
        jsonObject.put("avatar", img);

        return jsonObject.toString();
    }



    public static String parseBankAccountPost( String alias,String clabe,String number,String img , String imgIfe , String imgIfeBack, boolean isDefaultAccount, int origin) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("alias", alias);
        jsonObject.put("type", clabe);
        jsonObject.put("number", number);
        jsonObject.put("img", img);
        jsonObject.put("ife_front", imgIfe);
        jsonObject.put("ife_back", imgIfeBack);
        jsonObject.put("default", isDefaultAccount);
        jsonObject.put("origin", origin);
        return jsonObject.toString();
    }

    public static String parseSendTicket( String id,String email,String phone) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", id);
        if(email != null && !email.equalsIgnoreCase("")) {
            jsonObject.put("email", email);
        }else{
            jsonObject.put("phone",phone);
        }


        return jsonObject.toString();
    }

    public static String parseTransfer(String id) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("bank-account", id);


        return jsonObject.toString();
    }

    public static String parseTransfer(String id, double amount, String description) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("bank-account", id);
        jsonObject.put("amount", amount);
        jsonObject.put("description", description);

        return jsonObject.toString();
    }

    public static String parseAddOperator( String name,String pwd) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        jsonObject.put("password", pwd);


        return jsonObject.toString();
    }

    public static String parseCancel( String number, String reason,String obs) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("number", number);
        jsonObject.put("reason", reason);
        jsonObject.put("obs", obs);
        return jsonObject.toString();
    }


}
