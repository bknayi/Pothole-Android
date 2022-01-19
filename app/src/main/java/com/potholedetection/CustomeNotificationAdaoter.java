package com.potholedetection;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomeNotificationAdaoter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NotificationData> notiData;

    public CustomeNotificationAdaoter(Activity activity, List<NotificationData> en) {
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
        final NotificationData m = notiData.get(position);
        int pos = getItemViewType(position);
        //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            //layout
            Log.d("CustomNotifAdapter"," not null\nnot null\n");
            convertView = inflater.inflate(R.layout.list_notification, null);
        }

        TextView ad =  convertView.findViewById(R.id.addd);
        TextView status =  convertView.findViewById(R.id.status);
        TextView timestamp =  convertView.findViewById(R.id.timestamp);
        ImageView col = convertView.findViewById(R.id.imageView);

        ad.setText(m.getAdd());
        status.setText(m.getStatus());
        timestamp.setText(m.getTT());

        if (m.getStatus().equals("pending")){
            col.setImageResource(R.drawable.blue);
        }else if(m.getStatus().equals("done")){
            col.setImageResource(R.drawable.green);
        }else if(m.getStatus().equals("rejected")){
            col.setImageResource(R.drawable.red);
        }else {
            col.setImageResource(R.drawable.blue);
            //Log.e("noooo",sta)
        }

        col.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(activity,ShowEventDetails.class);
//                i.putExtra("ename",m.getEname());
//                i.putExtra("des",m.getEdes());
//                i.putExtra("ll",m.getElink());
//                activity.startActivity(i);
            }
        });


        return convertView;
    }

}
