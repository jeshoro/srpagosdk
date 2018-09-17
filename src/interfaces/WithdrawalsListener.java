package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.responses.srpago.Withdrawal;

/**
 * Created by Rodolfo on 27/10/2015.
 */
public interface WithdrawalsListener extends SrPagoWebServiceListener{
    void onSuccess(ArrayList<Withdrawal> withdrawals);
}
