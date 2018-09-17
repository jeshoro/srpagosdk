package sr.pago.sdk.object.response;

import java.io.Serializable;

import sr.pago.sdk.object.BaseItem;
import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by desarrolladorandroid on 24/01/17.
 */

public class ZipCodeResult extends BaseItem implements Serializable {

    private static final long serialVersionUID = -9198805305548093910L;
    public String town;
    public String typeTown;
    public String zip_code;
    public String city;
    public String country;
    public String state;
}
