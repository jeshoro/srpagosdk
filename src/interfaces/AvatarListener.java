package sr.pago.sdk.interfaces;

import sr.pago.sdk.model.Avatar;

/**
 * Created by Rodolfo on 29/10/2015.
 */
public interface AvatarListener extends SrPagoWebServiceListener {

    void onSuccess(Avatar avatar);
}
