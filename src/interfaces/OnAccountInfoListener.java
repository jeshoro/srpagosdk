package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.responses.srpago.AccountInfo;
import sr.pago.sdk.object.Account;

/**
 * Created by Rodolfo on 23/10/2015.
 */
public interface OnAccountInfoListener extends SrPagoWebServiceListener {
    void onSuccess(AccountInfo account);
}
