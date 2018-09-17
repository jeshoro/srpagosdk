package sr.pago.sdk.connection;

import android.annotation.SuppressLint;

import java.io.InputStream;

import sr.pago.sdk.model.SPResponse;


/**
 * Created by Rodolfo on 21/07/2016 for Test it.
 * Pixzelle Studio S. de R.L. All rights reserved.
 */
@SuppressLint("ParcelCreator")
class ResponseRaw extends SPResponse {
    private InputStream inputStream;
    private Integer responseCode;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
