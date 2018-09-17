package sr.pago.sdk.interfaces;

import sr.pago.sdk.SrPagoDefinitions;

/**
 * <h1>SrPagoWebServiceListener</h1>
 * Interface for hearing errors for any call to Sr.Pago method. The success is not implemented here, because for every call to a different service the onPaymentSuccess changes.
 * <h4>Version 1.0</h4>
 * <ul>
 * <li>onError(SrPagoDefinitions.Error error)</li>
 * <li>Date: 24/09/2015</li>
 * </ul>
 *
 * @author Rodolfo Peña - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since 24/09/2015
 */
public interface SrPagoWebServiceListener{
    /**
     * Receives the error message of any service called by the sdk.
     *
     * @see sr.pago.sdk.SrPagoDefinitions.Error
     * @since Version 1.0
     */
    
    void onError(SrPagoDefinitions.Error error);
    void onError(String error, SrPagoDefinitions.Error errorCode);
}
