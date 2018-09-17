package sr.pago.sdk;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import sr.pago.sdk.activities.SDKBaseActivity;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.fragments.SignatureFragment;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.response.Response;


public class SignatureActivity extends SDKBaseActivity {

    private LinearLayout container;
    private String operation;
    private SrPagoTransaction sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_finish);
        Global.setBooleanPreference(this, Definitions.CAN_SHOW_SMS_TICKET, true);
        container = (LinearLayout) findViewById(R.id.container);
        if(getIntent() != null){
            operation = getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT);
        }
        Bundle extras = new Bundle();
        getIntent().putExtras(extras);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SignatureFragment.newInstance(operation,0))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment_finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {

    }
}
