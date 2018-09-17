package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.responses.srpago.SrPagoCard;

/**
 * Created by Rodolfo on 25/07/2017.
 */

public interface OnSrPagoCardListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<SrPagoCard> cards);
}
