package com.potholedetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    Button reg;
    EditText nam,email,pno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg = findViewById(R.id.reg);
        nam = findViewById(R.id.editText2);
        pno = findViewById(R.id.editText3);
        email = findViewById(R.id.editText4);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makerequest(pno.getText().toString(),nam.getText().toString(),email.getText().toString());
            }
        });

    }

    void makerequest(final String num,final String namAS,final String mail){

        StringRequest s = new StringRequest(Request.Method.POST, new URL().url+"register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"registered",Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Try again after some time",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nam.getText().toString());
                params.put("number", pno.getText().toString());
                params.put("email", email.getText().toString());
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }
}
