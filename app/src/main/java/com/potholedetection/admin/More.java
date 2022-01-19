package com.potholedetection.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.potholedetection.AppController;
import com.potholedetection.Login;
import com.potholedetection.R;
import com.potholedetection.URL;

import java.util.HashMap;
import java.util.Map;

public class More extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String url,id,adr;

    ImageView ig;
    TextView ad;
    Spinner sp;

    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Intent i = getIntent();
        url = i.getStringExtra("url");
        id = i.getStringExtra("id");
        adr = i.getStringExtra("addr");

        ig = findViewById(R.id.imgView);
        ad = findViewById(R.id.addrr);
        sp = findViewById(R.id.spinner);
        update = findViewById(R.id.button2);

        ad.setText(adr);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(getApplicationContext()).load(url)
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .apply(requestOptions)
                .into(ig);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.stat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = sp.getSelectedItem().toString().toLowerCase();
                updateStat();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),item,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void updateStat(){
        StringRequest s = new StringRequest(Request.Method.POST, new URL().url+"update.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(), Admin.class);
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
                params.put("id", id);
                params.put("stat",sp.getSelectedItem().toString());
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }

}
