package com.billi.hocdot.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billi.hocdot.Adapter.AdapterMonHoc;
import com.billi.hocdot.Contans.MonHoc;
import com.billi.hocdot.Interface.ListenerMonHoc;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClass extends Fragment {
    private static final String Lop = "content";
    private String urlGetData = "https://hocdot.com/apiAndroid.php";
    private static String lop;
    private AdapterMonHoc gridviewAdapterMonHoc;
    private MainActivity main;
    private MonHoc monHoc;
    private SharedPreferences sharedPreferences;
    public FragmentClass() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main = (MainActivity) getActivity();
    }

    public static FragmentClass newInstance(String param1) {
        FragmentClass fragment = new FragmentClass();
        Bundle args = new Bundle();
        args.putString(Lop, param1);
        fragment.setArguments(args);
        lop = param1;

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
        final View view = inflater.inflate(R.layout.fragment_class, container, false);
        sharedPreferences = view.getContext().getSharedPreferences("savelop",Context.MODE_PRIVATE);
        if (lop.equals("0")){
            lop = sharedPreferences.getString("lop","");
        }
        monHoc = new MonHoc();

        TextView txtTitle = view.findViewById(R.id.title);
        txtTitle.setText(lop);



        getData(view.getContext(), new ListenerMonHoc() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse() {


                gridviewAdapterMonHoc = new AdapterMonHoc(view.getContext(),R.layout.grid_mon_hoc_row,monHoc,lop,main);
                gridviewAdapterMonHoc.notifyDataSetChanged();

                LinearLayout col1 = view.findViewById(R.id.lineCol1);
                LinearLayout col2 = view.findViewById(R.id.lineCol2);
                LinearLayout col3 = view.findViewById(R.id.lineCol3);
                LinearLayout col4 = view.findViewById(R.id.lineCol4);

                if (gridviewAdapterMonHoc.getCount() != 0){
                    int dem = 0;
                    for (int i = 1 ; i <= gridviewAdapterMonHoc.getCount(); i++){
                        dem++;
                        View viewContent = gridviewAdapterMonHoc.getView(i-1,null,null);

                        if(dem == 1){
                            col1.addView(viewContent);
                        }else if (dem == 2){
                            col2.addView(viewContent);
                        } else if (dem == 3){
                            col3.addView(viewContent);
                        } else if (dem == 4){
                            col4.addView(viewContent);
                            dem = 0;
                        }

                    }
                }

            }
        });


        return view;
    }


    private void getData(Context context, final ListenerMonHoc listenerMonHoc){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arraySelectMon = new JSONArray(response);

                            for (int i = 0; i < arraySelectMon.length(); i++){
                                JSONObject jsonObject = arraySelectMon.getJSONObject(i);
                                monHoc.setTenSach(jsonObject.getString("loaisach"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listenerMonHoc.onResponse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listenerMonHoc.onError(error.toString());
                        Log.e("Err",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("selectmon",lop);

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
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lop",lop);
        editor.apply();
    }
}
