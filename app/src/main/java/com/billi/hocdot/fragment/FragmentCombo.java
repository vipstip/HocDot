package com.billi.hocdot.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
import com.billi.hocdot.Interface.ListennerBooks;
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

public class FragmentCombo extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private String urlGetData = "https://hocdot.com/apiAndroid.php";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    private static String lop;
    private static String mon;
    private static String combo;
    private static String book;
    private static String terms;
    private SharedPreferences sharedPreferences;

    private int posBook;
    private int posTerm;

    private List<String> lstBooks;
    private List<String> lstTerms;
    private List<String> lstPosts;

    private MainActivity main;

    private AdapterPosts adapterPosts;
    private ListView lstviewBai;

    public FragmentCombo() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main = (MainActivity)getActivity();
    }

    public static FragmentCombo newInstance(String lop1, String mon1, String tenCombo1) {
        FragmentCombo fragment = new FragmentCombo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, lop1);
        args.putString(ARG_PARAM2, mon1);
        args.putString(ARG_PARAM3, tenCombo1);
        lop = lop1;
        mon = mon1;
        combo = tenCombo1;

        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_combo, container, false);
        sharedPreferences = view.getContext().getSharedPreferences("savePosCombo",Context.MODE_PRIVATE);
        posBook = sharedPreferences.getInt("posBook",0);
        posTerm = sharedPreferences.getInt("posTerm",0);
        lstBooks = new ArrayList<>();
        lstTerms = new ArrayList<>();
        lstPosts = new ArrayList<>();

        lstviewBai = view.findViewById(R.id.lstBai);

        // Ad Type Spinner set up
        getBooks(view.getContext(), new ListennerBooks() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse() {
                final Spinner spinnerBooks = (Spinner) view.findViewById(R.id.spinnerBooks);
                final Spinner spinnerTerms = (Spinner) view.findViewById(R.id.spinnerTerms);
                book = sharedPreferences.getString("nameBook","");

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                        android.R.layout.simple_spinner_item,lstBooks);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinnerBooks.setAdapter(adapter);

                if (adapter.getCount() > posBook){
                    spinnerBooks.setSelection(posBook);
                }
                spinnerBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, final View view, final int pos, long l) {

                            posBook = pos;
                            if (!book.equals(lstBooks.get(pos)))
                            {
                                posTerm = 0;
                                book = lstBooks.get(pos);
                            }

                            lstTerms.clear();
                        getTerms(view.getContext(), book, new ListennerTerms() {
                                @Override
                                public void onError(String message) {

                                }

                                @Override
                                public void onResponse() {
                                    if (lstTerms.size() != 0){
                                        ArrayAdapter<String> adapterTerms = new ArrayAdapter<String>(view.getContext(),
                                                android.R.layout.simple_spinner_item,lstTerms);
                                        adapterTerms.notifyDataSetChanged();
                                        adapterTerms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinnerTerms.setAdapter(adapterTerms);
                                        spinnerTerms.setSelection(posTerm);
                                        spinnerTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, final long l) {
                                                lstPosts.clear();
                                                posTerm = i;
                                                terms = lstTerms.get(i);
                                                getPosts(view.getContext(), book, terms, new ListenerPosts() {
                                                    @Override
                                                    public void onError(String message) {
                                                    }

                                                    @Override
                                                    public void onResponse() {
                                                        adapterPosts = new AdapterPosts(view.getContext(),R.layout.post_row,lstPosts,lop,mon,book,terms,true,main);
                                                        adapterPosts.notifyDataSetChanged();
                                                        lstviewBai.setAdapter(adapterPosts);
                                                    }
                                                });
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                }
                            });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

            }
        });
        return view;
    }

    private void getBooks(Context context, final ListennerBooks listennerBooks){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray arrayBooks = new JSONArray(response);

                            for (int i = 0 ; i < arrayBooks.length(); i ++){
                                JSONObject books = arrayBooks.getJSONObject(i);
                                lstBooks.add(books.getString("tensach"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listennerBooks.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listennerBooks.onError(error.toString());
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("lopbcbb",lop);
                params.put("monbcbb",mon);
                params.put("combobcbb",combo);

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
    private void getTerms(Context context, final String book, final ListennerTerms listennerTerms){
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
                params.put("lopbcbt",lop);
                params.put("monbcbt",mon);
                params.put("combobcbt",combo);
                params.put("booksbcbt",book);

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

    private void getPosts(Context context, final String book, final String terms, final ListenerPosts listenerPosts){
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
                params.put("booksbcb",book);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("posBook",posBook);
        editor.putInt("posTerm",posTerm);
        editor.apply();
    }
}
