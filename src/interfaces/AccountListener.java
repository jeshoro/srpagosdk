package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.AccountCard;

/**
 * Created by Rodolfo on 15/10/2015.
 */
public interface AccountListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<AccountCard> accountses);
}
