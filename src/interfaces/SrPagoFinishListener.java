package sr.pago.sdk.interfaces;

/**
 * Created by Rodolfo on 10/08/2015.
 */
public interface SrPagoFinishListener {
    void onSuccess();
    void onError(int code, String message);
}
