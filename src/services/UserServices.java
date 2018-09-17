package sr.pago.sdk.services;

import android.content.Context;

import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.WebServiceListener;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.interfaces.AuthLogoutListener;
import sr.pago.sdk.interfaces.AvatarListener;
import sr.pago.sdk.interfaces.LoginListener;
import sr.pago.sdk.interfaces.OnLoginFacebookListener;
import sr.pago.sdk.interfaces.OnPreferencesEditListener;
import sr.pago.sdk.interfaces.OnRecoveryPasswordListener;
import sr.pago.sdk.interfaces.OnRegisterLinkFbAccountListener;
import sr.pago.sdk.interfaces.OnRegisterStatusListener;
import sr.pago.sdk.interfaces.OnRegisterUserService;
import sr.pago.sdk.interfaces.OnValidatePromotionService;
import sr.pago.sdk.interfaces.ResetPasswordListener;
import sr.pago.sdk.model.Avatar;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.model.responses.srpago.ValidatePromotionRS;
import sr.pago.sdk.object.PaymentPreferences;
import sr.pago.sdk.object.response.RegisterResult;
import sr.pago.sdk.object.response.RegisterStatusResult;


/**
 * Created by Rodolfo on 29/10/2015.
 */
public class UserServices {
    public static void callAvatar(Context context, final AvatarListener avatarListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.AVATAR, new WebServiceListener<Avatar>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<Avatar> response, int webService) {
                 avatarListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                avatarListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void recoverPassword(Context context, final OnRecoveryPasswordListener onRecoveryPasswordListener, String email) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] requestData = new String[1];
        requestData[0] = email;
        serviceCore.executeService(Definitions.RECOVERY_PASSWORD, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRecoveryPasswordListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRecoveryPasswordListener.onError(response.getMessage(), response.getError());
            }
        }, requestData, null);
    }

    public static void uploadAvatar(Context context, WebServiceListener listener, String img) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[1];
        data[0] = img;
        serviceCore.executeService(Definitions.UPLOAD_AVATAR, listener, data, null);
    }

    public static void getPreferences(Context context, final OnPreferencesEditListener onPreferencesEditListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.GET_USER_SETTINGS, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPreferencesEditListener.onSuccess((PaymentPreferences) response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPreferencesEditListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void login(Context context, final LoginListener loginListener, String user, String pwd, String operator) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[3];
        data[0] = user;
        data[1] = pwd;
        data[2] = operator;
        serviceCore.executeService(Definitions.LOGIN, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                loginListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                loginListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void loginFacebook(Context context, final OnLoginFacebookListener onLoginFacebookListener, String tokenFacebook) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[1];
        data[0] = tokenFacebook;
        serviceCore.executeService(Definitions.LOGIN_FB, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onLoginFacebookListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onLoginFacebookListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void logout(Context context, final AuthLogoutListener authLogoutListener) {
        ServiceCore serviceCore = new ServiceCore(context);

        serviceCore.executeService(Definitions.AUTH_LOGOUT, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                authLogoutListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                authLogoutListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void updatePreferences(final Context context, final OnPreferencesEditListener onPreferencesEditListener) {
        ServiceCore serviceCore = new ServiceCore(context);
        serviceCore.executeService(Definitions.UPDATE_SETTINGS, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPreferencesEditListener.onSuccess(null);
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onPreferencesEditListener.onError(response.getMessage(), response.getError());
            }
        }, null, null);
    }

    public static void registerLinkAccount(Context context, final OnRegisterLinkFbAccountListener onRegisterLinkFbAccountListener, String email, String facebookId, String facebookToken, String password) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data = new Object[4];
        data[0] = email;
        data[1] = facebookId;
        data[2] = facebookToken;
        data[3] = password;
        serviceCore.executeService(Definitions.LINK_FB_ACCOUNT, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRegisterLinkFbAccountListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRegisterLinkFbAccountListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }

    public static void registerUser(Context context, final OnRegisterUserService onRegisterUserService, String firstName, String lastName, String middleName,
                             String area, String city, String state, String street, String zipcode, String numext, String numint, int bussines_id,
                             String bussinesname, String email, String password, String phonenumber, String ip, String tokenFB) {
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] requestData = new Object[17];
        requestData[0] = firstName;
        requestData[1] = lastName;
        requestData[2] = middleName;
        requestData[3] = area;
        requestData[4] = city;
        requestData[5] = state;
        requestData[6] = street;
        requestData[7] = zipcode;
        requestData[8] = numext;
        requestData[9] = numint;
        requestData[10] = bussines_id;
        requestData[11] = bussinesname;
        requestData[12] = email;
        requestData[13] = password;
        requestData[14] = phonenumber;
        requestData[15] = ip;
        requestData[16] = tokenFB;
        serviceCore.executeService(Definitions.REGISTER, new WebServiceListener<RegisterResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<RegisterResult> response, int webService) {
                onRegisterUserService.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRegisterUserService.onError(response.getMessage(), response.getError());
            }
        }, requestData, null);
    }

    public static void getRegisterStatus(Context context, final OnRegisterStatusListener onRegisterStatusListener, String facebookId, String email){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] requestData = new Object[2];
        requestData[0] = email;
        requestData[1] = facebookId;
        serviceCore.executeService(Definitions.REGISTER_STATUS, new WebServiceListener<RegisterStatusResult>() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse<RegisterStatusResult> response, int webService) {
                onRegisterStatusListener.onSuccess(response.getItems().get(0));
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onRegisterStatusListener.onError(response.getMessage(), response.getError());
            }
        }, requestData, null);
    }

    public static void validatePromotion(Context context, final OnValidatePromotionService onValidatePromotionService, String code){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] requestData = new Object[1];
        requestData[0] = code;
        serviceCore.executeService(Definitions.VALIDATE_PROMOTION, new WebServiceListener<ValidatePromotionRS>() {
            @Override
            public void onSuccess(int code, SPResponse<ValidatePromotionRS> response, int webService) {
                onValidatePromotionService.onSuccess(response.getItems().get(0));
            }
            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                onValidatePromotionService.onError(response.getMessage(), response.getError());
            }
        }, requestData, null);
    }

    public static void resetPassword(Context context, final ResetPasswordListener resetPasswordListener, String usuario){
        ServiceCore serviceCore = new ServiceCore(context);
        Object[] data =new Object[2];
        data[0]=usuario;
        data[1]=usuario;
        serviceCore.executeService(Definitions.RESET_PASSWORD, new WebServiceListener() {
            @Override
            public void onSuccess(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                resetPasswordListener.onSuccess();
            }

            @Override
            public void onError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
                resetPasswordListener.onError(response.getMessage(), response.getError());
            }
        }, data, null);
    }
}
