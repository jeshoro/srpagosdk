package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.Service;

/**
 * Created by Rodolfo on 19/10/2015.
 */
public interface ServicesListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Service> services);
}
