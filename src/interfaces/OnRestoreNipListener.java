package sr.pago.sdk.interfaces;

/**
 * Created by David Morales on 10/18/17.
 */

public interface OnRestoreNipListener extends SrPagoWebServiceListener{
    void onSuccess( String body);
}
