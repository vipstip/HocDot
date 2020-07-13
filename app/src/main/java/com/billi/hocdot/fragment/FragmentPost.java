package com.billi.hocdot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
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
import com.billi.hocdot.Adapter.AdapterQuestions;
import com.billi.hocdot.Contans.Questions;
import com.billi.hocdot.Interface.ListenerPosts;
import com.billi.hocdot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentPost extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;

    private String urlGetData = "https://hocdot.com/apiAndroid.php";
    private WebView webviewContent;
    private Spanned convertHTMLPost;
    private TextView titlePost;

    private static String lop;
    private static String mon;
    private static String tenSach;
    private static String tenChuong;
    private static String tenBai;
    private static Questions questions;

    private AdapterQuestions adapterQuestions;

    public FragmentPost() {
        // Required empty public constructor
    }

    public static FragmentPost newInstance(String lop1, String mon1,String tenSach1,String tenChuong1,String tenBai1) {
        FragmentPost fragment = new FragmentPost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, lop1);
        args.putString(ARG_PARAM2, mon1);
        args.putString(ARG_PARAM3, tenSach1);
        args.putString(ARG_PARAM4, tenChuong1);
        args.putString(ARG_PARAM5, tenBai1);

        lop = lop1;
        mon = mon1;
        tenSach = tenSach1;
        tenChuong = tenChuong1;
        tenBai = tenBai1;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_post, container, false);
        questions = new Questions();
        webviewContent = view.findViewById(R.id.webviewContent);
        titlePost = view.findViewById(R.id.titlePost);

        String title = "Sách "+ tenSach + "\n" +tenChuong;
        titlePost.setText(title);

        getQuestion(view.getContext(), new ListenerPosts() {
            @Override
            public void onError(String message) {


            }

            @Override
            public void onResponse() {
                adapterQuestions = new AdapterQuestions(view.getContext(),R.layout.question_row,questions);
                adapterQuestions.notifyDataSetChanged();

                LinearLayout layout = view.findViewById(R.id.layoutLine);
                for (int i = 0; i < adapterQuestions.getCount();i++){
                    View view1 = adapterQuestions.getView(i,null,null);
                    layout.addView(view1);
                }
            }
        });
        return view;
    }

    private void getQuestion(Context context, final ListenerPosts listenerPosts){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ContentPost = new JSONObject(response);

                            JSONArray arrContentPost = ContentPost.getJSONArray("mangContentPost");
                            for (int i = 0; i < arrContentPost.length(); i++){
                                JSONObject contentQuestion = arrContentPost.getJSONObject(i);

                                if (!contentQuestion.getString("contentpost").equals("")){
                                    convertHTMLPost = Html.fromHtml(contentQuestion.getString("contentpost"));
                                    webviewContent.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + convertHTMLPost, "text/html", "UTF-8", null);
                                }
                            }

                            JSONArray arrQuestion = ContentPost.getJSONArray("mangQuestion");
                            for (int i = 0; i< arrQuestion.length(); i++){
                                JSONObject Question = arrQuestion.getJSONObject(i);
                                String titleQuestion = Question.getString("titleQuest");
                                String contentQuestion = Question.getString("contentQuest");
                                String answerQuestion = Question.getString("answerQuest");
                                questions.setQuestions(titleQuestion,contentQuestion,answerQuestion);
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
                params.put("loppost",lop);
                params.put("monpost",mon);
                params.put("bookspost",tenSach);
                params.put("termspost",tenChuong);
                params.put("postspost",tenBai);

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
}
