package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.object.Operation;

/**
 * Created by Rodolfo on 04/11/2015.
 */
public interface OperationListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Operation> operations);

}
