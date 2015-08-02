package com.recursionlabs.thecommuter.adapters;

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

import com.recursionlabs.thecommuter.R;
import com.recursionlabs.thecommuter.managers.FavoritesManager;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {
    Context context;
    int[] images;
    String[] lines;
    RecyclerView recyclerView;
    int lineId;
    FavoritesManager favoritesManager;
    boolean favorite = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public ImageView mStar;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.line_name);
            mImageView = (ImageView) v.findViewById(R.id.line_img);
            mStar = (ImageView) v.findViewById(R.id.star);
        }
    }

    public StopAdapter(Context context, int[] images, String[] lines, RecyclerView recyclerView, int lineId) {
        this.context = context;
        this.images = images;
        this.lines = lines;
        this.recyclerView = recyclerView;
        this.lineId = lineId;
        favoritesManager = new FavoritesManager(context);
        if (lineId == -1) {
            favorite = true;
        }
    }

    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_card_item, parent, false);
        v.setOnClickListener(new StopOnClickListener(context, recyclerView, lineId, lines, images));
        ImageView star = (ImageView) v.findViewById(R.id.star);

        star.setOnClickListener(new FavoriteOnClickListener(context, recyclerView, lineId, lines, images));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.mTextView.setText(lines[position]);
        holder.mTextView.setTextColor(Color.WHITE);
        holder.mImageView.setImageResource(images[position]);
        if (favorite) {
            int lineId = favoritesManager.getLines().get(position);
            if (lineId == 0) { holder.mImageView.setColorFilter(Color.argb(89, 198, 12, 48)); }
            else if (lineId == 1) { holder.mImageView.setColorFilter(Color.argb(89, 0, 161, 222)); }
            else if (lineId == 2) { holder.mImageView.setColorFilter(Color.argb(170, 98, 54, 27)); }
            else if (lineId == 3) { holder.mImageView.setColorFilter(Color.argb(89, 0, 155, 58)); }
            else if (lineId == 4) { holder.mImageView.setColorFilter(Color.argb(117, 249, 70, 28)); }
            else if (lineId == 5) { holder.mImageView.setColorFilter(Color.argb(137, 82, 35, 152)); }
            else if (lineId == 6) { holder.mImageView.setColorFilter(Color.argb(150, 226, 126, 166)); }
            else if (lineId == 7) { holder.mImageView.setColorFilter(Color.argb(89, 249, 227, 0)); }
            else { holder.mImageView.setColorFilter(Color.argb(0, 255, 255, 255)); }
        }
        if (favoritesManager.isFavorite(lineId, position) || favorite) {
            holder.mStar.setImageResource(R.drawable.ic_star_gold);
        } else {
            holder.mStar.setImageResource(R.drawable.ic_star);
        }

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
