package sr.pago.sdk.interfaces;

import sr.pago.sdk.model.responses.srpago.Card;

public interface ActivatePrevivaleListener extends SrPagoWebServiceListener {

    void onPrevivaleSuccess(Card card);
}
