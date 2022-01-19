package com.potholedetection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.potholedetection.admin.Admin;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button login,register;
    EditText num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        num =  findViewById(R.id.number);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makerequest(num.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });
    }

    void makerequest(final String num){

        StringRequest s = new StringRequest(Request.Method.POST, new URL().url+"login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                //Log.e("LOOOOO",response.trim());
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"logging in...",Toast.LENGTH_SHORT).show();

                    UserData u = new UserData();
                    u.saveUserData(getApplicationContext(),num);

                    u.setDefaults("name",num,getApplicationContext());
//                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString("name", num);
//                    editor.commit();
//
//                    SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
//                    String name =sharedPref2.getString("name","no");
//                    Toast.makeText(getApplicationContext(),"logging in..."+name,Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                }else if(response.trim().equals("admin")){
                    UserData u = new UserData();
                    u.saveUserData(getApplicationContext(),num);

                    SharedPreferences.Editor editor2 = getSharedPreferences("us", MODE_PRIVATE).edit();
                    editor2.putString("name", num);
                    editor2.apply();

                    Intent intent=new Intent(getApplicationContext(), Admin.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
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
                params.put("number", num);
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }
}
