package com.randstad.nasaapp.network.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.randstad.nasaapp.R;
import com.randstad.nasaapp.model.NasaImage;
import com.randstad.nasaapp.utils.Constants;

import java.util.List;

public class NasaListAdapter  extends RecyclerView.Adapter<NasaListAdapter.ViewHolder> {
    private static final String TAG = "NASALIST_ADPT";
    private List<NasaImage> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public NasaListAdapter(Context context, List<NasaImage> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NasaImage image = mData.get(position);
        holder.captionTextView.setText(image.caption);
        holder.idTextView.setText("ID: " + image.id);
        holder.versionTextView.setText("Ver. " + image.version);

        Glide.with(context)
                .load(Constants.NETWORK.IMAGE_URL + image.image + ".png?api_key=" + Constants.NETWORK.API_KEY)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().override(1080, 1080))
                .into(holder.imageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView idTextView;
        TextView captionTextView;
        TextView versionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            idTextView = itemView.findViewById(R.id.idTextView);
            captionTextView = itemView.findViewById(R.id.captionTextView);
            versionTextView = itemView.findViewById(R.id.versionTextView);
        }
    }
}
