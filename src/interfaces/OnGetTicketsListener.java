package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.Ticket;

/**
 * Created by David Morales on 9/29/17.
 */

public interface OnGetTicketsListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Ticket> tickets);

}