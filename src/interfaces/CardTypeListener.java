package sr.pago.sdk.interfaces;

import sr.pago.sdk.model.responses.srpago.CardType;

public interface CardTypeListener extends SrPagoWebServiceListener {

    void onCardTypeSuccess(CardType cardType);
}
