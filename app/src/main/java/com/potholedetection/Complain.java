package com.potholedetection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Complain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Complain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Complain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    private static Uri fileUri;

    //imageview
    //ImageView mImageView;

    String imageName = "";

    String latitude ="",longitude="";

    // Progress dialog
    private ProgressDialog pDialog;

    Button comp,loc,upload;

    EditText addrr;



    public static final int MEDIA_TYPE_IMAGE = 1;

    public Complain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Complain.
     */
    // TODO: Rename and change types and number of parameters
    public static Complain newInstance(String param1, String param2) {
        Complain fragment = new Complain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complain, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upload = view.findViewById(R.id.uploadImg);
        loc = view.findViewById(R.id.mlocation);
        comp = view.findViewById(R.id.raisecomp);
        addrr = view.findViewById(R.id.addre);
        //mImageView = view.findViewById(R.id.imageView);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int flag=-1;
                if(imageName.equals("")){
                    Toast.makeText(getContext(),"Image is not uploaded",Toast.LENGTH_SHORT).show();
                    //return;
                } else if (longitude.equals("") && latitude.equals("")){
                    Toast.makeText(getContext(),"Location is not selected",Toast.LENGTH_SHORT).show();
                    //return;
                }else if(addrr.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Address field is empty",Toast.LENGTH_SHORT).show();
                } else {
                    makeRequest();
                }
            }
        });
    }



    void getLocation(){
        Intent i = new Intent(getContext(), MapsActivityCurrentPlace.class);
        startActivityForResult(i, 101);
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                launchUploadActivity(true);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getActivity(),"User cancelled image capture", Toast.LENGTH_SHORT).show();

            } else {
                // failed to capture image
                Toast.makeText(getActivity(),"Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }

        }else if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                latitude=data.getStringExtra("lat");
                longitude=data.getStringExtra("log");
                //Toast.makeText(getActivity(),"got the coordinates",Toast.LENGTH_SHORT).show();
                loc.setText("Got the location");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
         final String IMAGE_DIRECTORY_NAME = "Android File Upload";
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator+ "IMG_" + timeStamp + ".jpg");

        imageName = "IMG_" + timeStamp + ".jpg";

        Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();


        return mediaFile;
    }

    private void launchUploadActivity(boolean isImage){
        Intent i = new Intent(getActivity(), UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("imageName",imageName);

        AsyncTask<Void, Integer, String> n  = new UploadFileToServer(new UploadFileToServer.AsyncResponse(){

            @Override
            public void processFinish(String result){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.
                if (result.trim().equalsIgnoreCase("done")){
                    //Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                    upload.setText("Image uploaded");
                }else {
                    Toast.makeText(getActivity(),"Upload failed",Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
        //startActivity(i);
    }

    private static class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        // you may separate this or combined to caller class.
        public interface AsyncResponse {
            void processFinish(String output);
        }

        public AsyncResponse delegate = null;

        public UploadFileToServer(AsyncResponse delegate){
            this.delegate = delegate;
        }

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            //progressBar.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(new URL().url+"complain.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                //publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(fileUri.getPath());

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                //totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }catch (Exception e){
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            delegate.processFinish(result);


            super.onPostExecute(result);
        }

    }

    void makeRequest(){
        showpDialog();
        UserData userf = new UserData();
        final String username = userf.getUsername(getActivity());

        //Toast.makeText(getActivity(),username+"",Toast.LENGTH_SHORT).show();
        URL u = new URL();
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"registercomplain.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                Log.e("dammm",response);
                if(response.trim().equals("done")){
                    //Intent intent=new Intent(Signup3.this,MainActivity.class);
                    //startActivity(intent);
                    //hidepDialog();
                    Toast.makeText(getActivity(),"Your complain is registered",Toast.LENGTH_SHORT).show();
                    imageName = "";
                    latitude = "";
                    longitude = "";
                    Intent i = new Intent(getContext(),Home.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getActivity(),"Try again",Toast.LENGTH_SHORT).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", username);
                params.put("imgname",imageName);
                params.put("lat",latitude);
                params.put("log", longitude);
                params.put("addrr", addrr.getText().toString());

                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(s);
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
