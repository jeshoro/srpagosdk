package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.Notification;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public interface NotificationListener extends SrPagoWebServiceListener {
    void onSuccess(ArrayList<Notification> notifications);

}
