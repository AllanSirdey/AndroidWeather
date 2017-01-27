package com.example.allan.androidweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Custom Adapter class for the Recycle view
 */

public class VilleAdapter extends RecyclerView.Adapter<VilleAdapter.ViewHolder> {
    private List<Ville> mDataset;
    private WeakReference<VilleListener> mDelegate;
    public static View myView;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final WeakReference<VilleListener> mDelegate;

        public View mRootView;
        public TextView mName;
        public TextView mTemperature;
        public ImageView mIcone;

        public ViewHolder(View v, WeakReference<VilleListener> delegate) {
            super(v);
            mDelegate = delegate;

            mRootView = v;
            myView = v;

            mName = (TextView) v.findViewById(R.id.ville_name);
            mTemperature = (TextView) v.findViewById(R.id.ville_temperature);
            mIcone = (ImageView) v.findViewById(R.id.img);

            new DownloadImageTask((ImageView) v.findViewById(R.id.img))
                    .execute("http://openweathermap.org/img/w/10n.png");


            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(mDelegate.get() != null) {
                        mDelegate.get().villeOnclick(position);
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public VilleAdapter(List<Ville> myDataset, VilleListener delegate) {
        mDataset = myDataset;
        mDelegate = new WeakReference<>(delegate);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VilleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ville_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v, mDelegate);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mName.setText(mDataset.get(position).getNom());
        holder.mTemperature.setText(String.valueOf(mDataset.get(position).getMeteo().getTemperatureActuelle())+" °C");
        String iconeCode =  mDataset.get(position).getMeteo().getIcone();
        new DownloadImageTask((ImageView) myView.findViewById(R.id.img)).execute("http://openweathermap.org/img/w/"+iconeCode+".png");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

