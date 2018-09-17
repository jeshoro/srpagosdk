/*
 * Copyright (c) 2014. Pixzelle S.R.L de C.V
 * @author Rodolfo Peña
 * @version 1.0 1/7/14 4:04 PM
 */

package sr.pago.sdk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import sr.pago.sdk.object.PixzelleClass;

@Deprecated
/**
 * The adapter that Pixzelle uses in the development.
 *
 * @author  Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 2.0
 * @since   2014-07-14
 */
public abstract class PixzelleAdapter extends BaseAdapter{
    protected Context context;
    protected ArrayList<PixzelleClass> collection;

    public PixzelleAdapter(){
        super();
    }

    public PixzelleAdapter(Context context){
        super();
        this.context = context;
        this.collection = new ArrayList<>();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<PixzelleClass> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<PixzelleClass> collection) {
        this.collection = collection;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(getResourceId(), null);
        } else {
            view = convertView;
        }

        handleView(position, view, parent);

        return view;
    }

    @Override
    public long getItemId(int position) {
        return this.collection.get(position).getId();
    }

    @Override
    public Object getItem(int position) {
        return this.collection.get(position);
    }

    @Override
    public int getCount() {
        return this.collection.size();
    }

    /**
     * Controls the resource id for use in the adapter.
     * @return Resource id.
     */
    public abstract int getResourceId();

    /**
     * Abstract method for handling the view of the adapter, with getResourceId, they're the only method that the developer has to use.
     */
    public abstract void handleView(int position, View view, View parent);
}