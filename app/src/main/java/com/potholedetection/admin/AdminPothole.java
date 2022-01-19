package com.potholedetection.admin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.potholedetection.AppController;
import com.potholedetection.CustomeNotificationAdaoter;
import com.potholedetection.NotificationData;
import com.potholedetection.R;
import com.potholedetection.URL;
import com.potholedetection.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminPothole extends AppCompatActivity {

    //Custom list
    private List<PotholeData> pot = new ArrayList<PotholeData>();

    //customlist adapter
    CustomPotholeAdapter adapter;

    TextView nopot;

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pothole);

        ListView listView =  findViewById(R.id.potholelist);
        nopot = findViewById(R.id.nopothole);

        pDialog = new ProgressDialog(getApplication());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        adapter = new CustomPotholeAdapter(getApplicationContext(), pot);
        listView.setAdapter(adapter);

        makeJsonArrayRequest();
    }

    private void makeJsonArrayRequest() {

        //showpDialog();

        String url = new URL().url;
        url+="getPothole.php";

        UserData userf = new UserData();
        final String username = userf.getUsername(getApplicationContext());

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("AdminPothole", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    int flag = -1;
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject com = (JSONObject) response.get(i);

                        String id = com.getString("id").trim();
                        String photo = com.getString("photo").trim();
                        String latitude = com.getString("latitude").trim();
                        String longitude = com.getString("longitude").trim();
                        String ad = com.getString("addr").trim();
                        String timestamp = com.getString("timestamp").trim();
                        if (id.equals("-1") && photo.equals("-1") && latitude.equals("-1")){
                            flag=0;
                        }

                        PotholeData t = new PotholeData(id,photo,latitude,longitude,timestamp,ad);
                        pot.add(t);

                    }
                    if (flag==-1){
                        adapter.notifyDataSetChanged();
                    }else{
                        nopot.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("PotActivity", "Error: " + error.getMessage());
                Log.e("abc",error.getMessage());
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"F's available right now" + error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
