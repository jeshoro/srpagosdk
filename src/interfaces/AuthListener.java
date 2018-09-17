package sr.pago.sdk.interfaces;

/**
 * <h1>AuthListener</h1>
 * Interface for hearing success or errors for authenticating. The onError is called in SrPagoWebServiceListener
 * <p/>
 * <h4>Version 1.0</h4>
 * <ul>
 *     <li>onAuthAccess()</li>
 *     <li>Date: 24/09/2015</li>
 * </ul>
 *
 * @author Rodolfo Peña - * Sr. Pago All rights reserved.
 * @version 1.0
 * @see SrPagoWebServiceListener
 * @since 24/09/2015
 */
public interface AuthListener extends SrPagoWebServiceListener {
    /**
     * Receives the message that the authentication was succesful.
     * @since Version 1.0
     */
    void onAuthSuccess();
}
