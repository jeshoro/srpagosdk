package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.NotificationListener;
import sr.pago.sdk.interfaces.NotificationPostListener;
import sr.pago.sdk.model.Notification;
import sr.pago.sdk.model.SPResponse;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public class NotificationService {

    public void getNotifications(Context context, final NotificationListener notificationListener){
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.NOTIFICATIONS, new WebServiceListener<Notification>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Notification> response, int webService) {
                notificationListener.onSuccess(response.getItems());
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                notificationListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public void updateNotification(Context context, final NotificationPostListener notificationPostListener, String name, boolean email){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data =new Object[2];
        data[0] = name;
        data[1] = email;
        String[] nameParams = {name};
        serviceCore.executeService(Definitions.NOTIFICATION_POST, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                notificationPostListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                notificationPostListener.onError(response.getMessage(), response.getError());
            }
        }, data, nameParams);
    }
}
