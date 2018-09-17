package sr.pago.sdk.api.parsers;


import android.content.Context;

import org.json.JSONException;

import sr.pago.sdk.model.SPResponse;

/**
 * Created by Rodolfo on 30/07/2015 for GronJobb.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */
public class PixzelleParserResponseFactory {
    public SPResponse parseResponse(Context context, int webService, String json) throws JSONException{
        return null;
    }

    public SPResponse parseError(String json) throws JSONException{
        return null;
    }
}
