package sr.pago.sdk.connection;

import sr.pago.sdk.SrPagoDefinitions;

/**
 * Created by Rodolfo on 26/10/2015.
 */
public class WSErrorHandler {

//    public static String errorHandling(String exception){
//        String message = "";
//
//        switch (exception){
//            case "email is required format": message = "El correo electrónico no es válido";
//                break;
//
//            default: message = "Ha ocurrido un error innesperado";
//                break;
//        }
//
//        return message;
//    }

    public static String errorHandling(SrPagoDefinitions.Error error) {
        String message;

        switch (error) {
            case EMAIL_INVALID:
                message = "El correo electrónico no es válido";
                break;
            case ERROR_CARD:
                message = "No se pudo leer a tarjeta correctamente";
                break;
            case SERVICE_UNAVAILABLE:
                message = "El servicio no está disponible por el momento";
                break;
            case DEVICE_NOT_PRESENT:
                message = "Conecte un dispositivo por favor";
                break;
            case SERVER_ERROR:
                message = "No se ha podido establecer la conexión con el servidor, favor de revisar su conexión a internet e intente nuevamente.";
                break;
            case BAD_REQUEST:
                message = "Formato de correo electrónico inválido";
                break;
            case AMOUNT_GREATER:
                message = "El monto es mayor al permitido, favor de comunicarte con nosotros al 01 800 710 2127 para incrementar su límite.";
                break;
            case CARD_INFO_NOT_FOUND:
                message = "No se encuentra la información de la tarjeta";
                break;
            case INVALID_ACCOUNT:
                message = "Usuario no válido";
                break;
            case USERNAME_REQUIRES:
                message = "username not required format";
                break;
            case ALIAS_INVALID:
                message = "El alias debe de tener una longitud de entre 8 y 80 caracteres.";
                break;
            case BANK_ACCOUNT:
                message = "Error al eliminar.";
                break;
            case TOTAL_MUST:
                message = "El minimo es de $1.00.";
                break;
            case DATA:
                message="Seleccione una imagen";
                break;
            case CANCEL_CARD:
                message="El servicio no esta disponible";
                break;
            case SERVICE_CURRENTLY:
                message="El servidor no está disponible";
                break;
            case READER_UNKNOWN: case CARD_REMOVED: case TRANSACTION_INTERRUPTED:
                message="Transacción interrumpida";
                break;
            case READER_BUSY: case READER_TIMEOUT:
                message = "Lector ocupado";
                break;
            case CARD_WITH_CHIP:
                message = "Esta tarjeta tiene chip, debe de ingresarla en el lector de chip.";
                break;
            case NO_CARD_DETECTED:
                message = "Inserte alguna tarjeta para poder hacer alguna transacción";
                break;
            case SQPOS_NO_DETECTED:
                    message = "No se pudo conectar con el lector";
                break;
            case MSR_NOT_ALLOWED:
                message = "La operación con banda no está permitida en esta cuenta";
                break;
            default:
                message = "Ha ocurrido un error inesperado";
                break;
        }

        return message;
    }

    public static SrPagoDefinitions.Error errorHandling(String exception, String message) {
        SrPagoDefinitions.Error error;

        switch (message) {
            case "email is required format":
                error = SrPagoDefinitions.Error.EMAIL_INVALID;
                break;

            case "READER_UNKNOWN":
                error = SrPagoDefinitions.Error.ERROR_CARD;
                break;
            case "The service is temporarily unavailable":
                error = SrPagoDefinitions.Error.SERVICE_UNAVAILABLE;
                break;
            case "Bank account not found 0":
                error = SrPagoDefinitions.Error.SERVER_ERROR;
                break;
            case "At least email or phone is required":
                error = SrPagoDefinitions.Error.BAD_REQUEST;
                break;
            case "The amount is greater than allowed":
                error = SrPagoDefinitions.Error.AMOUNT_GREATER;
                break;
            case "Card information not found":
                error = SrPagoDefinitions.Error.CARD_INFO_NOT_FOUND;
                break;
            case "Invalid account":
                error = SrPagoDefinitions.Error.INVALID_ACCOUNT;
                break;
            case "username not required format":
                error = SrPagoDefinitions.Error.USERNAME_REQUIRES;
                break;
            case "Alias parameter is invalid, length should be between 8 and 80 characters":
                error = SrPagoDefinitions.Error.ALIAS_INVALID;
                break;
            case "Bank Account \\\"3938\\\" is already deleted":
                error = SrPagoDefinitions.Error.BANK_ACCOUNT;
                break;
            case "total total must be greater than 1.00 ":
            case "total total must be greater than 1.00":
                error = SrPagoDefinitions.Error.TOTAL_MUST;
                break;
            case "There is no data to validate":
                error= SrPagoDefinitions.Error.DATA;
                break;
            case "Service not available":
                error= SrPagoDefinitions.Error.CANCEL_CARD;
                break;
            case "The server is currently unavailable":
                error= SrPagoDefinitions.Error.SERVICE_CURRENTLY;
                break;
            case "No se detectó una conexión a Internet.":
                error = SrPagoDefinitions.Error.NO_INTERNET;
                break;
            case "No se puedo conectar con el lector":
                error = SrPagoDefinitions.Error.SQPOS_NO_DETECTED;
                break;
            case  "Esta tarjeta tiene chip, debe de ingresarla en el lector de chip.":
                error = SrPagoDefinitions.Error.SERVER_ERROR;
                break;
            case "No se ha podido establecer la conexión con el servidor, favor de revisar su conexión a internet e intente nuevamente.":
                error = SrPagoDefinitions.Error.SERVER_ERROR;
                break;
            default:
                error = SrPagoDefinitions.Error.S500;
                switch (exception) {
                    case "CardInformationNotFoundException":
                        error = SrPagoDefinitions.Error.CARD_INFO_NOT_FOUND;
                        break;
                }
                break;
        }

        return error;
    }
}
