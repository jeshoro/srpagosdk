package sr.pago.sdk.model;

import sr.pago.sdk.object.PixzelleClass;

/**
 * Created by Rodolfo on 20/10/2015.
 */
public class Store extends PixzelleClass {

    public String nameStore;
    public String description;
    public String shortName;
    public String url;
    public String minimo;
    public String maximo;

    public String getPositionMethod() {
        return positionMethod;
    }

    public void setPositionMethod(String positionMethod) {
        this.positionMethod = positionMethod;
    }

    public String positionMethod;

    public String getMinimo() {
        return minimo;
    }

    public void setMinimo(String minimo) {
        this.minimo = minimo;
    }

    public String getMaximo() {
        return maximo;
    }

    public void setMaximo(String maximo) {
        this.maximo = maximo;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
