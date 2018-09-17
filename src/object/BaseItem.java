package sr.pago.sdk.object;

import android.content.Context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import sr.pago.sdk.SrPagoDefinitions;
import sr.pago.sdk.connection.PixzelleNetworkHandler;
import sr.pago.sdk.interfaces.SrPagoWebServiceListener;
import sr.pago.sdk.api.parsers.ParserResponseFactory;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.utils.Logger;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.object.response.Response;

/**
 * Created by Rodolfo on 21/09/2015.
 */
public class BaseItem implements Serializable{
    private int id;
    private String guid;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
