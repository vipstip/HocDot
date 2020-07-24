package com.billi.hocdot.BangTinhTrucTuyen.giaiPT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.billi.hocdot.R;

import io.github.kexanie.library.MathView;

public class GiaiHePT2An extends AppCompatActivity {
    int a1, b1, c1, a2, b2, c2;
    float D, Dx, Dy, x, y;
    String kqua,kquaphanso;
    EditText a11,a22,b11,b22,c11,c22;
    MathView txtkqua;
    Button btnTinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giai_he_p_t2_an);
        Init();
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
                getData();
                giaiHePT();
                Spanned spanned = Html.fromHtml(kqua);
                txtkqua.setInitialScale(1);
                txtkqua.getSettings().setLoadWithOverviewMode(true);
                txtkqua.getSettings().setUseWideViewPort(true);
                txtkqua.getSettings().setSupportZoom(true);
                txtkqua.getSettings().setBuiltInZoomControls(true);
                txtkqua.getSettings().setDisplayZoomControls(false);
                txtkqua.getSettings().setJavaScriptEnabled(true);
                txtkqua.setText( "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes\">" + spanned);
                hideKeyboard();
            }
        });
        findViewById(R.id.main_giaiPT2An).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return true;
            }
        });
    }

    public void Init(){
        a11 = findViewById(R.id.nhap_a1);
        b11 = findViewById(R.id.nhap_b1);
        c11 = findViewById(R.id.nhap_c1);
        a22 = findViewById(R.id.nhap_a2);
        b22 = findViewById(R.id.nhap_b2);
        c22 = findViewById(R.id.nhap_c2);
        txtkqua = findViewById(R.id.txtKquaGiaiHePT2An);
        btnTinh = findViewById(R.id.btnGiaiHePT2An);

    }

    public void getData(){
        if (!a11.getText().toString().equals("")){
            a1 = Integer.parseInt(a11.getText().toString());
        } else a1 = 0;

        if (!b11.getText().toString().equals("")){
            b1 = Integer.parseInt(b11.getText().toString());
        } else b1 = 0;

        if (!c11.getText().toString().equals("")){
            c1 = Integer.parseInt(c11.getText().toString());
        } else c1 = 0;

        if (!a22.getText().toString().equals("")){
            a2 = Integer.parseInt(a22.getText().toString());
        } else a2 = 0;

        if (!b22.getText().toString().equals("")){
            b2 = Integer.parseInt(b22.getText().toString());
        } else b2 = 0;

        if (!c22.getText().toString().equals("")){
            c2 = Integer.parseInt(c22.getText().toString());
        } else c2 = 0;

    }

    public void giaiHePT(){
        D = a1 * b2 - a2 * b1;
        Dx = c1 * b2 - c2 * b1;
        Dy = a1 * c2 - a2 * c1;
        if (D == 0) {
            if (Dx + Dy == 0)
                 kqua = "Hệ phương trình có vô số nghiệm";
            else
                kqua = "Hệ phương trình vô nghiệm";
        }
        else {
            x = Dx / D;
            y = Dy / D;
            if (Math.abs(Dx) > D && Dx % D == 0){
                int x1 = Math.round(x);
                int y1 = Math.round(y);
                kqua = "Hệ phương trình có nghiệm (x , y) = (" + x1 + " , " + y1 + ")";
            } else {
                rutgonPS(Dx,D);
                kqua = "Hệ phương trình có nghiệm &lt;p&gt; (x , y) = (" + x + " , " + y + ") &lt;p&gt; Hoặc: (x , y) = (" + kquaphanso ;
                rutgonPS(Dy,D);
                kqua = kqua + " , " + kquaphanso + ")" ;
            }

        }
    }
    public int UCLN(int a,int b){
        if (a%b!=0)
            return UCLN(b,a%b);
        else
            return b;
    }

    public void rutgonPS(float tu1, float mau1){
        int tu = Math.round(tu1);
        int mau = Math.round(mau1);
        int ucln = UCLN(tu,mau);
        tu = tu/ucln;
        mau = mau/ucln;
        if (mau!=1)
        {
            if (mau<0){
                mau=mau*-1;
                tu=tu*-1;
            }
            kquaphanso = "\\(\\frac{"+tu+"}{"+mau+"}\\)";
        }
    }
    public void clearData(){
        a1 = 0;
        b1 = 0;
        c1 = 0;
        a2 = 0;
        b2 = 0;
        c2 = 0;
        kqua = "";
    }
    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
