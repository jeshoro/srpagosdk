package sr.pago.sdk.model;

import java.util.ArrayList;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Pixzelle on 05/07/16.
 */
public class StoresMethod extends PixzelleClass{
    public ArrayList<Store> stores;
    public String method;

    public StoresMethod(){
        stores = new ArrayList<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String storeName;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }


}
