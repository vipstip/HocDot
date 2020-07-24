package com.billi.hocdot.BangTinhTrucTuyen.giaiPT;

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

public class GiaiPTBac1 extends AppCompatActivity {
    Button btnGiai;
    EditText nhapA,nhapB,nhapC;
    MathView ketqua;
    float a,b,c;
    float kq;
    String kquasothapphan,kquaphanso;
    String textkq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giai_p_t_bac1);
        Init();

        btnGiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                getData();
                textLoiGiai();
                giaiPT();
                Spanned spanned = Html.fromHtml(textkq);
                ketqua.setText(String.valueOf(spanned));
                hideKeyboard();
            }
        });

        findViewById(R.id.main_gpt1An).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return true;
            }
        });
        findViewById(R.id.main_gpt1An).clearFocus();
    }

    public void giaiPT(){
        textkq = textkq + "Phương trình có nghiệm là: \n";
        if (a!=0){
            kq = (c-b)/(a);
            if ((Math.abs(c - b) >= a && (c - b)% a == 0)|| c - b == 0 || kq == 1){
                textkq = textkq + "x = " + Math.round(kq);
            } else {
                textkq = textkq + "x = " + kq + " hoặc ";
                rutgonPS(c - b,a);
                textkq = textkq + kquaphanso;
            }
        } else textkq = textkq + "x = " + 0;
    }

    public void Init(){
        btnGiai = findViewById(R.id.btnGiai);
        nhapA = findViewById(R.id.gpt1AnNhapA);
        nhapB = findViewById(R.id.gpt1AnNhapB);
        nhapC = findViewById(R.id.gpt1AnNhapC);
        ketqua = findViewById(R.id.ket_qua);
    }

    public void getData(){
        if (!nhapA.getText().toString().equals("")){
            a = Integer.parseInt(nhapA.getText().toString());
        } else a = 0;

        if (!nhapB.getText().toString().equals("")){
            b = Integer.parseInt(nhapB.getText().toString());
        } else b = 0;

        if (!nhapC.getText().toString().equals("")){
            c = Integer.parseInt(nhapC.getText().toString());
        } else c = 0;
    }

    public void textLoiGiai(){
        int a1 = Math.round(a);
        int b1 = Math.round(b);
        int c1 = Math.round(c);
        float tinh1 = -b1 + c1;
        float tinh2 = tinh1/a1;
        textkq = " Phương trình có dạng ax + b = c " +
                "&lt;p&gt; Trong đó: a # 0 " +
                "&lt;p&gt; Cách giải:" +
                "&lt;p&gt; &nbsp;&nbsp;&nbsp; - Bước 1: Chuyển vế ax = -b + c " +
                "&lt;p&gt;" + "\\(\\Rightarrow\\)" + a1 + "x =" + -b1 + "+" + c1 +
                "&lt;p&gt;" + "\\(\\Rightarrow\\)" + a1 + "x =" + Math.round(tinh1) +
                "&lt;p&gt; &nbsp;&nbsp;&nbsp; - Bước 2: Chia hai vế cho a ta được: x = - b/a." +
                "&lt;p&gt; " + "\\(\\Rightarrow\\) (" + a1 + "/" + a1 + ")x =" + "(" + Math.round(tinh1) + "/" + a1 + ")" +
                "&lt;p&gt; " + "\\(\\Rightarrow\\) x =" + Math.round(tinh2) +
                "&lt;p&gt; &nbsp;&nbsp;&nbsp; - Bước 3: Kết nghiệm: &lt;p&gt;"
        ;
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
            kquaphanso ="\\(\\frac{"+tu+"}{"+mau+"}\\)";
        }
    }
    public void clear(){
        a = 0;
        b = 0;
        c = 0;
        kq = 0f;
        kquasothapphan = "";
        kquaphanso = "";
        textkq = "";
    }
    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
