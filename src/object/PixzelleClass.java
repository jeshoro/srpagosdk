package sr.pago.sdk.object;


import java.io.Serializable;

/**
 * The superclass for working with Pixzelle team
 *
 * @author  Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since   2014-01-07
 */
public class PixzelleClass implements Serializable{

    private int id;
    private String guid;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
