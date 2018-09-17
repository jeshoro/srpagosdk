package sr.pago.sdk.model.responses.srpago;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("company")
    @Expose
    private List<Object> company = null;

    @SerializedName("properties")
    @Expose
    private Properties properties;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Object> getCompany() {
        return company;
    }

    public void setCompany(List<Object> company) {
        this.company = company;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}