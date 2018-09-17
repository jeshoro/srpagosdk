package sr.pago.sdk.interfaces;

import java.util.ArrayList;

import sr.pago.sdk.model.OperatorAux;

/**
 * Created by Rodolfo on 25/09/2015 for SrPagoSDK.
 * Sr. Pago All rights reserved.
 */
public interface OperatorListener extends SrPagoWebServiceListener{
    void onSuccess(ArrayList<OperatorAux> operators);



}
