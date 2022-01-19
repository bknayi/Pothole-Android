package com.potholedetection.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.potholedetection.R;
import com.potholedetection.URL;

import java.util.List;

public class CustomPotholeAdapter extends BaseAdapter {
    private Context activity;
    private LayoutInflater inflater;
    private List<PotholeData> notiData;
    Boolean isImageFitToScreen;

    public CustomPotholeAdapter(Context activity, List<PotholeData> en) {
        this.activity = activity;
        this.notiData = en;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        //Log.d("CustomListAdapter",position+"");
        return 0;
    }

    @Override
    public int getCount() {
        return notiData.size();
    }

    @Override
    public Object getItem(int location) {
        return notiData.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getting team data for the row
        final PotholeData m = notiData.get(position);
        int pos = getItemViewType(position);
        //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            //layout
            Log.d("CustomNotifAdapter"," not null\nnot null\n");
            convertView = inflater.inflate(R.layout.list_pothole, null);
        }

        TextView ad =  convertView.findViewById(R.id.addd);
        //TextView status =  convertView.findViewById(R.id.status);
        TextView timestamp =  convertView.findViewById(R.id.times);
        final ImageView col = convertView.findViewById(R.id.imageView);

        final Button mo = convertView.findViewById(R.id.mmore);
        final Button direction = convertView.findViewById(R.id.mdirection);

        ad.setText(m.getaddr());

        isImageFitToScreen = false;
        //timestamp.setText(m.getTime());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(activity).load(new URL().url+"uploads/" +m.getURL())
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .apply(requestOptions)
                .into(col);

        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,More.class);
                i.putExtra("url",new URL().url+"uploads/" +m.getURL());
                i.putExtra("addr",m.getaddr());
                i.putExtra("id",m.getID());

                activity.startActivity(i);
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+m.getLat()+","+m.getLon()));
                activity.startActivity(intent);
            }
        });

        col.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,ShowImage.class);
                i.putExtra("url",new URL().url+"uploads/" +m.getURL());
                activity.startActivity(i);
            }
        });


        return convertView;
    }

}
