package sr.pago.sdk.interfaces;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by dherrera on 2/27/17.
 */

public interface ServerTimeListener extends SrPagoWebServiceListener {

    void onSuccess (Object time);
}
