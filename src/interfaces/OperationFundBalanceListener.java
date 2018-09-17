package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.OperationFundBalance;

/**
 * Created by Rodolfo on 27/10/2015.
 */
public interface OperationFundBalanceListener extends SrPagoWebServiceListener{
    void onSuccess(ArrayList<OperationFundBalance> operationFounts);
}
