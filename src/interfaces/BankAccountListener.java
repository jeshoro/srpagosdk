package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.BankAccount;

/**
 * Created by Rodolfo on 24/10/2015.
 */
public interface BankAccountListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<BankAccount> bankAccounts);
}
