package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.Transaction;

/**
 * Created by Rodolfo on 14/10/2015.
 */
public interface TransactionsListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Transaction> transactions);
}
