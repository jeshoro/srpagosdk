package sr.pago.sdk.interfaces;

import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Rodolfo on 22/08/2017.
 */

public interface OnSignatureCompleteListener extends Serializable{

    void onSignatureComplete(Intent intent);
}
