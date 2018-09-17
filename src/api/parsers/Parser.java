package sr.pago.sdk.api.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Created by David Morales on 16/11/17.
 */

public class Parser {

    public static <T> T parse(String json, Class<T> clazz) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> T parse(String json, Class<T> clazz, String dateFormat) throws JsonSyntaxException {
        Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
        return gson.fromJson(json, clazz);
    }
}
