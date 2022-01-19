package com.potholedetection;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String TAG = HomeFragment.class.getSimpleName();
    private ProgressDialog pDialog;

    TextView twoone,twotwo,twothe,theone,thetwo,thethe,forone,fortwo,forthe,fivone,fivtwo,fivthe;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
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

        twoone = view.findViewById(R.id.twoone);
        twotwo = view.findViewById(R.id.twotwo);
        twothe = view.findViewById(R.id.twothe);

        theone = view.findViewById(R.id.theone);
        thetwo = view.findViewById(R.id.thetwo);
        thethe = view.findViewById(R.id.thethe);

        forone = view.findViewById(R.id.forone);
        fortwo = view.findViewById(R.id.fortwo);
        forthe = view.findViewById(R.id.forthe);

        fivone = view.findViewById(R.id.fivone);
        fivtwo = view.findViewById(R.id.fivtwo);
        fivthe = view.findViewById(R.id.fivthe);

        pDialog = new ProgressDialog(getContext());

        fetchreport();
    }

    private void fetchreport() {

        pDialog.setMessage("Loading Events...");
        //pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(new URL().url+"report.php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                JSONObject url = object.getJSONObject("complain");
                                twoone.setText(url.getString(String.valueOf(1)));
                                twotwo.setText(url.getString(String.valueOf(2)));
                                twothe.setText(url.getString(String.valueOf(3)));

                                JSONObject url2 = object.getJSONObject("resolved");
                                theone.setText(url2.getString(String.valueOf(1)));
                                thetwo.setText(url2.getString(String.valueOf(2)));
                                thethe.setText(url2.getString(String.valueOf(3)));

                                JSONObject url3 = object.getJSONObject("pending");
                                forone.setText(url3.getString(String.valueOf(1)));
                                fortwo.setText(url3.getString(String.valueOf(2)));
                                forthe.setText(url3.getString(String.valueOf(3)));

                                JSONObject url4 = object.getJSONObject("reject");
                                fivone.setText(url4.getString(String.valueOf(1)));
                                fivtwo.setText(url4.getString(String.valueOf(2)));
                                fivthe.setText(url4.getString(String.valueOf(3)));

//                                image.setSmall(url.getString("small"));
//                                image.setMedium(url.getString("medium"));
//                                image.setLarge(url.getString("large"));
                                //image.setTimestamp(object.getString("name"));


                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
}
