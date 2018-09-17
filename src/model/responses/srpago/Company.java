package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company implements Serializable {

    @SerializedName("bussines_id")
    @Expose
    private int bussinesId;

    @SerializedName("rfc")
    @Expose
    private String rfc;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("web")
    @Expose
    private String web;

    @SerializedName("monthlySales")
    @Expose
    private String monthlySales;

    @SerializedName("averagePrice")
    @Expose
    private String averagePrice;

    public int getBussinesId() {
        return bussinesId;
    }

    public void setBussinesId(int bussinesId) {
        this.bussinesId = bussinesId;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(String monthlySales) {
        this.monthlySales = monthlySales;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

}