package sr.pago.sdk.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import sr.pago.sdk.R;
import sr.pago.sdk.object.PixzelleClass;

@Deprecated
public class DevicesAdapter extends PixzelleAdapter {
    public DevicesAdapter(Context context) {
        super(context);
    }

    private boolean isConnecting;

    @Override
    public int getResourceId() {
        return R.layout.qpos_adapter_v2;
    }

    @Override
    public void handleView(int i, View view, View view1) {
        TextView lblName = (TextView) view.findViewById(R.id.lbl_qpos_reader_name);
        TextView lblTitle = (TextView) view.findViewById(R.id.lbl_qpos_reader_title);
        ProgressBar pgrConnecting = (ProgressBar) view.findViewById(R.id.pgr_connecting_device);

        lblName.setTypeface(Typeface.createFromAsset(context.getAssets(), "gotham_light.ttf"));
        lblTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "gotham-bold.ttf"));

        PixzelleClass item = this.getCollection().get(i);
        lblName.setText(String.format(Locale.getDefault(), "SN: ••••%s", item.getName().substring(4, item.getName().length())));
        if(item.getId() == 1){
            pgrConnecting.setVisibility(View.VISIBLE);
        }else{
            pgrConnecting.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        if(isConnecting)
            return false;

        return super.isEnabled(position);
    }

    public boolean isConnecting() {
        return isConnecting;
    }

    public void setConnecting(boolean connecting) {
        isConnecting = connecting;
    }
}