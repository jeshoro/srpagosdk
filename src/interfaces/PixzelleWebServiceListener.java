/*
 * Copyright (c) 2014. Pixzelle S.R.L de C.V
 * @author Rodolfo Peña
 * @version 1.0 4/1/14 6:28 PM
 */

package sr.pago.sdk.interfaces;

import java.util.List;
import java.util.Map;

import sr.pago.sdk.enums.Pixzelle;

/**
 * Interface for handling the responses for the web services
 *
 * @author  Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since   2014-01-04
 */
public interface PixzelleWebServiceListener {

    @Deprecated
    void result(String result, int webService);
    /**
     * It goes where you want to handle the response.
     * @see sr.pago.sdk.enums.PixzelleErrors
     * @param code The code of the status of the response.
     * @param result Data read from the web service.
     * @param webService Number of the web service that called the Internet, the developer must establish this.
     */
    void result(Pixzelle.SERVER_CODES code, String result, Map<String, List<String>> headers, int webService, Object[] params, String[] urlParams);
}
