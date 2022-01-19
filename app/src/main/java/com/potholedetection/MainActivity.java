package com.potholedetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makerequest();
    }

    void makerequest(){
        StringRequest s = new StringRequest(Request.Method.POST, new URL().url+"check.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("ok")){
                    //Toast.makeText(getApplicationContext(),"goo",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(),"Cannot connect to server",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(s);
    }
}
