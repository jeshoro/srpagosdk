package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.Abm;

/**
 * Created by Rodolfo on 30/10/2015.
 */
public interface AbmListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Abm> abms);
}
