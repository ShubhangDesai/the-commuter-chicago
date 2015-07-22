package com.example.android.thecommuter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.thecommuter.widgets.StopOnClickListener;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {
    Context context;
    int[] images;
    String[] lines;
    RecyclerView recyclerView;
    int lineId;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.line_name);
            mImageView = (ImageView) v.findViewById(R.id.line_img);
        }
    }

    public StopAdapter(Context context, int[] images, String[] lines, RecyclerView recyclerView, int lineId) {
        this.context = context;
        this.images = images;
        this.lines = lines;
        this.recyclerView = recyclerView;
        this.lineId = lineId;
    }

    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_card_item, parent, false);
        v.setOnClickListener(new StopOnClickListener(context, recyclerView, lineId, lines));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(lines[position]);
        holder.mImageView.setImageResource(images[position]);

        View cv = (View) holder.mImageView.getParent().getParent();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cv.getLayoutParams();
        Resources r = context.getResources();
        int px16 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
        int px8 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
        int left;
        int right;

        if (position % 2 == 0) {
            left = px16;
            right = px8;
        } else {
            left = px8;
            right = px16;
        }

        if (position == 0 || position == 1) {
            params.setMargins(left, px16, right, px16); //substitute parameters for left, top, right, bottom
            cv.setLayoutParams(params);
        } else {
            params.setMargins(left, 0, right, px16);
            cv.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return lines.length;
    }
}
