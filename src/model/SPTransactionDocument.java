package sr.pago.sdk.model;

import java.io.Serializable;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by dherrera on 2/15/17.
 */

public class SPTransactionDocument extends PixzelleClass implements Serializable{

    private String documentDescription;
    private String image;
    private int type;
    private int process;

    public String getDocumentDescription() {
        return documentDescription;
    }

    public int getType() {
        return type;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}
