package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.Operator;

/**
 * Created by Rodolfo on 15/10/2015.
 */
public interface OperatorsListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Operator> operators);
}
