package com.appchallengers.appchallengers.helpers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appchallengers.appchallengers.R;

/**
 * Created by jir on 21.2.2018.
 */

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private boolean isGrid;
    public CustomAdapter(Context context, boolean isGrid) {
        layoutInflater = LayoutInflater.from(context);
        this.isGrid = isGrid;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            if (isGrid) {
                view = layoutInflater.inflate(R.layout.fragment_contry_select, parent, false);

            }
            viewHolder = new ViewHolder();
            viewHolder.editText = (EditText) view.findViewById(R.id.country_select_fragment_edittext);
            view.setTag(viewHolder);
        }

        return view;

    }
    static class ViewHolder {
        EditText editText;

    }
}
