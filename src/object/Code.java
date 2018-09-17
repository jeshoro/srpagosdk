package sr.pago.sdk.object;

import java.util.Locale;

/**
 * Created by Rodolfo on 12/07/2017.
 */

public class Code extends PixzelleClass {
    private String username;
    private int type;
    private double percentage;
    private boolean active;
    private String image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        if(image == null || image.equals("")){
            return image;
        }

        return String.format(Locale.getDefault(), "https://d2mp3bnti41r8r.cloudfront.net/codes/%s", image);
    }

    public void setImage(String image) {
        this.image = image;
    }
}
