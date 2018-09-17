package sr.pago.sdk.services;

import android.content.Context;

import com.newrelic.agent.android.NewRelic;

/**
 * Created by desarrolladorandroid on 11/01/17.
 */

public class NewRelicReports {

    private static String usernameInternal;

    public static void init(String tokenNewRelic, Context appContext){
        NewRelic.withApplicationToken(tokenNewRelic).start(appContext);
    }

    public static void initUserName(String username){
        usernameInternal = username;
    }

    public static void reportPaymentNewRelic(String mensaje){
        if(usernameInternal != null){
            NewRelic.setAttribute("Username", usernameInternal);
        }
        NewRelic.setAttribute("makePaymentFailError",mensaje);
    }

    public void reportAttribute(String attribute, String value){
        NewRelic.setAttribute(attribute, value);
    }

    public void reportAttribute(String attribute, float value){
        NewRelic.setAttribute(attribute, value);
    }

    public void reportAttribute(String attribute, boolean value){
        NewRelic.setAttribute(attribute, value);
    }
}
