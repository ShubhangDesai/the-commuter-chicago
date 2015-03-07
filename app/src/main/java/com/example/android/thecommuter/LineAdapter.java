package com.example.android.thecommuter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class LineAdapter extends ArrayAdapter<String> {
    Context context;
    int[] icons;
    String[]lines;

    public LineAdapter(Context context, int[] icons, String[] lines) {
        super(context, R.layout.list_item, lines);
        this.context = context;
        this.icons = icons;
        this.lines = lines;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, null);
        }
        View icon = rowView.findViewById(R.id.line_icon);
        TextView line = (TextView) rowView.findViewById(R.id.line_name);

        icon.setBackgroundResource(icons[position]);
        line.setText(lines[position]);

        return rowView;
    }
}
