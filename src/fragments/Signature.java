package sr.pago.sdk.fragments;

import io.realm.RealmObject;

/**
 * Created by Pixzelle on 09/05/16.
 */
public class Signature extends RealmObject {
    public String user;
    public String data;
    public String guid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String token;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
