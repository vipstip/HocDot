package com.billi.hocdot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.hocdot.Adapter.AdapterPosts;
import com.billi.hocdot.Interface.ListenerPosts;
import com.billi.hocdot.Interface.ListennerTerms;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentBook extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param2";
    private String urlGetData = "https://hocdot.com/apiAndroid.php";
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private static String lop;
    private static String mon;
    private static String tensach;

    private List<String> lstTerms;
    private List<String> lstPosts;

    private MainActivity main;

    private AdapterPosts adapterPosts;

    private LinearLayout layoutSpinerTemrs;
    private TextView txtNotify;
    private ListView listViewBai;

    public FragmentBook() {
        // Required empty public constructor
    }

    public static FragmentBook newInstance(String lop1, String mon1,String tenSach1) {
        FragmentBook fragment = new FragmentBook();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, lop1);
        args.putString(ARG_PARAM2, mon1);
        args.putString(ARG_PARAM3, tenSach1);
        lop = lop1;
        mon = mon1;
        tensach = tenSach1;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main = (MainActivity)getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_book,container,false);
        final Spinner spinnerTerms = (Spinner) view.findViewById(R.id.spinnerTerms);

        layoutSpinerTemrs = view.findViewById(R.id.layoutspinnerTerms);
        txtNotify = view.findViewById(R.id.notifyBook);
        listViewBai = view.findViewById(R.id.lstBaiBook);
        lstTerms = new ArrayList<>();
        lstPosts = new ArrayList<>();
        getTerms(view.getContext(), new ListennerTerms() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse() {
                if (lstTerms.size() != 0) {
                    ArrayAdapter<String> adapterTerms = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item, lstTerms);
                    adapterTerms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapterTerms.notifyDataSetChanged();
                    spinnerTerms.setAdapter(adapterTerms);
                    spinnerTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, final View view, final int i, long l) {
                            lstPosts.clear();
                            getPosts(view.getContext(), lstTerms.get(i), new ListenerPosts() {
                                @Override
                                public void onError(String message) {

                                }

                                @Override
                                public void onResponse() {
                                    adapterPosts = new AdapterPosts(view.getContext(),R.layout.post_row,lstPosts,lop,mon,tensach,lstTerms.get(i),false,main);
                                    adapterPosts.notifyDataSetChanged();
                                    listViewBai.setAdapter(adapterPosts);
                                    if (lstPosts.size() == 0){
                                        txtNotify.setVisibility(View.VISIBLE);
                                        txtNotify.setText("Nội dung hiện đang chờ được học sinh cập nhật...");
                                    }else txtNotify.setVisibility(View.GONE);
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    layoutSpinerTemrs.setVisibility(View.GONE);
                    txtNotify.setVisibility(View.VISIBLE);
                    txtNotify.setText("Nội dung hiện đang chờ được học sinh cập nhật...");

                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    private void getTerms(Context context, final ListennerTerms listennerTerms){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arrayTerms = new JSONArray(response);
                            for (int i = 0 ; i < arrayTerms.length(); i ++){
                                JSONObject Terms = arrayTerms.getJSONObject(i);
                                lstTerms.add(Terms.getString("tenchuong"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listennerTerms.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listennerTerms.onError(error.toString());
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("lopbcb",lop);
                params.put("monbcb",mon);
                params.put("booksbcb",tensach);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void getPosts(Context context, final String terms, final ListenerPosts listenerPosts){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arrayPosts = new JSONArray(response);

                            for (int i = 0 ; i < arrayPosts.length(); i ++){
                                JSONObject Terms = arrayPosts.getJSONObject(i);
                                lstPosts.add(Terms.getString("tenbai"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listenerPosts.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listenerPosts.onError(error.toString());
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("lopbcb",lop);
                params.put("monbcb",mon);
                params.put("booksbcb",tensach);
                params.put("termsbcb",terms);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
