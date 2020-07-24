package com.billi.hocdot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.kexanie.library.MathView;

public class ActiviAnswer extends AppCompatActivity {
    TextView txtTitle;
    MathView mathViewAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activi_answer);
        txtTitle = findViewById(R.id.titleAnswer);
        mathViewAnswer = findViewById(R.id.mathViewAnswer);

        Intent intent = getIntent();
        Bundle content = intent.getExtras();
        if (content != null)
        {
            txtTitle.setText(content.getString("title"));
            Spanned convertHTMLAnswer = Html.fromHtml(content.getString("answer"));

            mathViewAnswer.setInitialScale(1);
            mathViewAnswer.getSettings().setLoadWithOverviewMode(true);
            mathViewAnswer.getSettings().setUseWideViewPort(true);
            mathViewAnswer.getSettings().setSupportZoom(true);
            mathViewAnswer.getSettings().setBuiltInZoomControls(true);
            mathViewAnswer.getSettings().setDisplayZoomControls(false);
            mathViewAnswer.getSettings().setJavaScriptEnabled(true);
            mathViewAnswer.setText("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes\">" + convertHTMLAnswer);
        }
    }
}
