package sr.pago.sdk.object.response;

import java.io.Serializable;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by desarrolladorandroid on 06/12/16.
 */

public class VersionCheckResult extends PixzelleClass implements Serializable{

    private static final long serialVersionUID = -415235623889625746L;
    public boolean needsUpdate;
}
