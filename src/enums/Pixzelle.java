package sr.pago.sdk.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Rodolfo on 04/09/2015.
 */
public abstract class Pixzelle {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({OK, CREATED, NO_CONTENT, BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, UNPROCESSABLE_ENTITY, SERVER_ERROR,
            MAINTENANCE, NO_INTERNET, TIME_OUT, UNKNOWN_CODE})
    public @interface SERVER_CODES {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PLAY_STORE, MAIL_APP})
    public @interface ACTIVITY_CODES {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ASPECT_FREE, ASPECT_SQUARE, ASPECT_16_9, ASPECT_4_3, ASPECT_5_3, ASPECT_8_5, ASPECT_3_2})
    public @interface ASPECTS {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOGIN_TRADITIONAL, LOGIN_FB, LOGIN_TWITTER, LOGIN_GOOGLE})
    public @interface LOGIN_TYPES {
    }


    public final static int PLAY_STORE = 128;
    public final static int MAIL_APP = 129;

    public final static int OK = 200;
    public final static int CREATED = 201;
    public final static int NO_CONTENT = 204;
    public final static int BAD_REQUEST = 400;
    public final static int UNAUTHORIZED = 401;
    public final static int FORBIDDEN = 403;
    public final static int NOT_FOUND = 404;
    public final static int UNPROCESSABLE_ENTITY = 422;
    public final static int SERVER_ERROR = 500;
    public final static int MAINTENANCE = 503;
    public final static int NO_INTERNET = 69;
    public final static int TIME_OUT = 70;
    public final static int UNKNOWN_CODE = 71;

    public final static int ASPECT_FREE = 0;
    public final static int ASPECT_SQUARE = 1;
    public final static int ASPECT_16_9 = 2;
    public final static int ASPECT_4_3 = 3;
    public final static int ASPECT_5_3 = 4;
    public final static int ASPECT_8_5 = 5;
    public final static int ASPECT_3_2 = 6;

    public static final int LOGIN_TRADITIONAL = 1;
    public static final int LOGIN_FB = 2;
    public static final int LOGIN_TWITTER = 3;
    public static final int LOGIN_GOOGLE = 4;

    public abstract void setServerError(@SERVER_CODES int code);

    @SERVER_CODES
    public abstract int getServerError();

    public abstract void setActivityCode(@ACTIVITY_CODES int code);

    @ACTIVITY_CODES
    public abstract int getActivityCode();

    public abstract void setAspectRatio(@ASPECTS int code);

    @ASPECTS
    public abstract int getAspectRatio();

    @LOGIN_TYPES
    public abstract int getLoginType();
    public abstract void setLoginType(@LOGIN_TYPES int loginTypes);
}
