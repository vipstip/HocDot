package com.billi.hocdot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.hocdot.Adapter.GridViewAdapterSachVaCombo;
import com.billi.hocdot.Models.SachVaCombo;
import com.billi.hocdot.Interface.ListenerComboAndBooks;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChild#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChild extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String Lop = "Lớp";
    private static final String Mon = "Môn";

    private static String lop = "Lớp";
    private static String mon = "Môn";

    private String urlGetData = "https://hocdot.com/apiAndroid.php";
    private SachVaCombo sachVaCombo;
    private MainActivity main;
    private GridViewAdapterSachVaCombo gridViewAdapterSachVaCombo;
    private GridView grvSachVaCombo;
    private TextView txtNotify;
    public FragmentChild() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main = (MainActivity)getActivity();
    }

    public static FragmentChild newInstance(String lop1, String mon1) {
        FragmentChild fragment = new FragmentChild();
        Bundle args = new Bundle();
        args.putString(Lop, lop1);
        args.putString(Mon, mon1);
        lop = lop1;
        mon = mon1;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_child,container,false);
        sachVaCombo = new SachVaCombo();
        txtNotify = view.findViewById(R.id.txtNotify) ;



        getData(view.getContext(), new ListenerComboAndBooks() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse() {
                if (sachVaCombo.getListTenSach().size() == 0){
                    txtNotify.setText("Nội dung đang chờ được cập nhật...");
                } else
                {
                    String title = lop + " " + mon;
                    txtNotify.setText(title);
                }
                grvSachVaCombo = view.findViewById(R.id.grvSachVaCombo);
                gridViewAdapterSachVaCombo = new GridViewAdapterSachVaCombo(view.getContext(),R.layout.sach_row,sachVaCombo,lop,mon,main);
                grvSachVaCombo.setAdapter(gridViewAdapterSachVaCombo);
            }
        });


        return view;
    }

    private void getData(Context context, final ListenerComboAndBooks listenerComboAndBooks){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray arrSach = jsonObject.getJSONArray("Sach");

                            JSONArray arrCombo = jsonObject.getJSONArray("Combo");

                            for (int i = 0 ; i < arrSach.length(); i ++){
                                JSONObject Sach = arrSach.getJSONObject(i);
                                sachVaCombo.setSach(Sach.getString("tensach"),Sach.getString("hinhanh"));
                            }
                            for (int i = 0 ; i < arrCombo.length(); i ++){
                                JSONObject Combo = arrCombo.getJSONObject(i);
                                sachVaCombo.setCombo(Combo.getString("tencombo"),Combo.getString("hinhanh"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listenerComboAndBooks.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listenerComboAndBooks.onError(error.toString());
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("lopChild",lop);
                params.put("monChild",mon);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
