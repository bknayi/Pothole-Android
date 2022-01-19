package com.potholedetection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AutoComplain {

    void makeRequest(final Double lat, final Double lon, Activity cc){
        //showpDialog();
        UserData userf = new UserData();
//        final String username = userf.getUsername(MainApplication);
//        SharedPreferences sharedPref2 = cc.getPreferences(Context.MODE_PRIVATE);
//        String name =sharedPref2.getString("name","no");
        final String name = userf.getDefaults("name",cc);
        //Toast.makeText(getActivity(),username+"",Toast.LENGTH_SHORT).show();
        URL u = new URL();
        final String finalName = name;
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"registercomplain.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                Log.e("dammm",response);
                if(response.trim().equals("done")){
                    //Intent intent=new Intent(Signup3.this,MainActivity.class);
                    //startActivity(intent);
                    //hidepDialog();
                    Log.e("AutoComplain",response+" <- response");
                }else{
                    Log.e("AutoComplain",response+" <- Noresponse" +name);
                }
                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AutoComplain","Error"+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", finalName);
                params.put("lat", String.valueOf(lat));
                params.put("log", String.valueOf(lon));
                params.put("addrr", "Auto detected");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }
}