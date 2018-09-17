package sr.pago.sdk.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import sr.pago.sdk.R;
import sr.pago.sdk.object.PixzelleClass;

@Deprecated
public class ListViewAdapter extends PixzelleAdapter {
    public ListViewAdapter(Context context){
        super(context);
    }

    @Override
    public int getResourceId() {
        return R.layout.layout_adapter;
    }

    @Override
    public void handleView(int i, View view, View view1) {
        TextView lblName=(TextView)view.findViewById(R.id.txtAdapter);

        PixzelleClass item = (PixzelleClass)this.getCollection().get(i);
        lblName.setText(item.getName());
    }
}
