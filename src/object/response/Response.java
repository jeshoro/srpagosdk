package sr.pago.sdk.object.response;

import android.annotation.SuppressLint;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sr.pago.sdk.object.PixzelleClass;


/**
 * Created by Rodolfo on 07/09/2015 for GronJobb.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */
@SuppressLint("ParcelCreator")
public class Response extends PixzelleClass {
    private boolean status;
    private Map<String, List<String>> header;
    private ArrayList<Object> items;
    private String raw;
    private String code;
    private String message;
    private String url;
    private String body;
    private String method;
    private Exception exception;

    private Pair<?, ?> errorDetail;
    private String errorCode;

    public Response(){
        super();
        setStatus(true);
        items = new ArrayList<>();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Map<String, List<String>> getHeader() {
        return header;
    }

    public void setHeader(Map<String, List<String>> header) {
        this.header = header;
    }

    public ArrayList<Object> getItems() {
        return items;
    }

    public void setItems(ArrayList<Object> items) {
        this.items = items;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public Pair<?, ?> getErrorDetail() {
        return errorDetail;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorDetail(Pair<?, ?> errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
