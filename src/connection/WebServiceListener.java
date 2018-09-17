package sr.pago.sdk.connection;

import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.model.SPResponse;

/**
 * Created by Rodolfo on 08/12/2015 for Jyb.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */
public interface WebServiceListener<T>{
    void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<T> response, int webService);
    void onError(@Pixzelle.SERVER_CODES int code, SPResponse<T> response, int webService);
}
