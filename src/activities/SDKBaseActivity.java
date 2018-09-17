package sr.pago.sdk.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sr.pago.sdk.R;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 24/07/2017.
 */

public class SDKBaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //super.onCreate(savedInstanceState);

        Logger.logDebug("Activity", this.getClass().toString());
        Logger.logDebug("DPIs", getString(R.string.dpi));
        if(getIntent().getIntExtra("signature", -100) == -100) {
            if (getResources().getBoolean(R.bool.tablet)) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        super.onCreate(savedInstanceState);
    }
}
