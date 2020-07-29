package com.billi.hocdot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.billi.hocdot.Adapter.AdapterSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivitySearch extends AppCompatActivity {
    AutoCompleteTextView editSearch;
    ImageButton btnSearch;
    List<String> suggestSearch;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

        sharedPreferences = getApplicationContext().getSharedPreferences("saveHistorySearch",Context.MODE_PRIVATE);
        Set<String> setHistory  = sharedPreferences.getStringSet("HistorySearch",null);
        if (setHistory!=null){
            suggestSearch.addAll(setHistory);
        }

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (suggestSearch!=null){
                    List<String> suggestSearch1 = suggestSearch;
                    Collections.reverse(suggestSearch1);
                    AdapterSearch adapter = new AdapterSearch(ActivitySearch.this,suggestSearch1);
                    adapter.notifyDataSetChanged();
                    editSearch.setAdapter(adapter);
                    adapter.getFilter().filter(editable);
                }
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    editSearch.clearFocus();
                    addSuggest(editSearch.getText().toString());
                    putHistorySearch();
                    handled = true;
                }
                return handled;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                addSuggest(editSearch.getText().toString());
                editSearch.clearFocus();
                putHistorySearch();
            }
        });
        hideKeyboard();
    }

    public void init(){
        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        suggestSearch = new ArrayList<>();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            addSuggest(editSearch.getText().toString());
            putHistorySearch();
        }
    }

    public void putHistorySearch(){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>(suggestSearch);
        editor.putStringSet("HistorySearch",set);
        editor.apply();
    }
    public void addSuggest(String historySearch){
        suggestSearch.add(historySearch);
    }

}
