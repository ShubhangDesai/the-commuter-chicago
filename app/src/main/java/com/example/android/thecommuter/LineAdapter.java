package com.example.android.thecommuter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class LineAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] lines;

    public LineAdapter(Context context, int[] images, String[] lines) {
        super(context, R.layout.card_list_item, lines);
        this.context = context;
        this.images = images;
        this.lines = lines;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.card_list_item, null);
        }

        CardView tv = (CardView) rowView.findViewById(R.id.card);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tv.getLayoutParams();
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());

        if (position == 0) {
            params.setMargins(px, px, px, px); //substitute parameters for left, top, right, bottom
            tv.setLayoutParams(params);
        } else {
            params.setMargins(px, 0, px, px);
            tv.setLayoutParams(params);
        }

        ImageView image = (ImageView) rowView.findViewById(R.id.line_img);
        TextView line = (TextView) rowView.findViewById(R.id.line_name);

        if (position == 0) { image.setColorFilter(Color.argb(89, 198, 12, 48)); }
        else if (position == 1) { image.setColorFilter(Color.argb(89, 0, 161, 222)); }
        else if (position == 2) { image.setColorFilter(Color.argb(170, 98, 54, 27)); }
        else if (position == 3) { image.setColorFilter(Color.argb(89, 0, 155, 58)); }
        else if (position == 4) { image.setColorFilter(Color.argb(117, 249, 70, 28)); }
        else if (position == 5) { image.setColorFilter(Color.argb(137, 82, 35, 152)); }
        else if (position == 6) { image.setColorFilter(Color.argb(150, 226, 126, 166)); }
        else if (position == 7) { image.setColorFilter(Color.argb(89, 249, 227, 0)); }
        else { image.setColorFilter(Color.argb(0, 255, 255, 255)); }
        image.setImageResource(images[position]);

        line.setText(lines[position]);
        line.setTextColor(Color.BLACK);

        return rowView;
    }
}
