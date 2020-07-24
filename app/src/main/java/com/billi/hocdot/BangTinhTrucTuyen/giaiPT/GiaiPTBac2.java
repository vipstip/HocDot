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

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;

public class GiaiPTBac2 extends AppCompatActivity {
    Button btnGiai;
    EditText nhapA,nhapB,nhapC;
    MathView ketqua;
    float a,b,c,x1,x2,delta;
    int trongCanBac2,ngoaiCanBac2;
    float tu,mau;
    String kquaphanso;
    String textkq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giai_p_t_bac2);
        Init();
        btnGiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                getData();
                tinhDelta();
                khaiCanBac2(Math.round(delta));
                giaiPT();
                Spanned spanned = Html.fromHtml(textkq);
                ketqua.setInitialScale(1);
                ketqua.getSettings().setLoadWithOverviewMode(true);
                ketqua.getSettings().setUseWideViewPort(true);
                ketqua.getSettings().setSupportZoom(true);
                ketqua.getSettings().setBuiltInZoomControls(true);
                ketqua.getSettings().setDisplayZoomControls(false);
                ketqua.getSettings().setJavaScriptEnabled(true);
                ketqua.setText( "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes\">" + spanned);
                hideKeyboard();
            }
        });
        findViewById(R.id.main_gptBac2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return true;
            }
        });
    }

    public void giaiPT(){
            if (a!=0){
                if (delta > 0){
                    textkq = "- Tính \\(\\Delta\\) ta có: &lt;p&gt; \\(\\Delta = b^2 - 4ac \\Rightarrow \\Delta = "+ Math.round(b)+"^2"+ "-" + 4 + "*" + Math.round(a) + "*" + Math.round(c) + " = " + Math.round(delta) + "> 0 \\)" + "&lt;p&gt;" +
                            "Có " + "\\(\\Delta > 0\\)" + "nên phương trình có 2 nghiệm phân biệt: &lt;p&gt;" +
                            "\\( x1 = \\frac{-b + \\sqrt{\\Delta}}{2*a} \\) , \\( x2 = \\frac{-b - \\sqrt{\\Delta}}{2*a} \\) &lt;p&gt;"

                    ;
                    double sqrtDelta = Math.sqrt(delta) ;
            x1 = ( -b + Float.parseFloat(String.valueOf(sqrtDelta)))/ (2*a);
            x2 = ( -b - Float.parseFloat(String.valueOf(sqrtDelta)))/ (2*a);
            rutgonPS(-b + Float.parseFloat(String.valueOf(sqrtDelta)),(2*a));
            if ((Math.abs(tu) > mau && tu % mau == 0)|| tu == 0 || tu/mau == 1){
                textkq = textkq + "\\(\\Rightarrow x1 = " + Math.round(x1) + "\\)" + " , ";
            } else {
                textkq = textkq + "\\(\\Rightarrow x1 = " + x1 +"\\)  hoặc" +  kquaphanso + " , ";
            }

            rutgonPS(-b - Float.parseFloat(String.valueOf(sqrtDelta)),(2*a));
            if ((Math.abs(tu) > mau && tu % mau == 0)|| tu == 0 || tu/mau == 1){
                textkq = textkq + "\\( x2 = " + Math.round(x2) + "\\)";
            } else {
                textkq = textkq + " \\( x2 = " + x2 +"\\) hoặc " +  kquaphanso;
            }
        }
        else if (delta == 0){
            textkq = "\\(\\Delta = b^2 - 4AC\\) = 0 \\(\\Rightarrow \\Delta = \\) " + Math.round(delta) + " nên phương trình có nghiệm kép:&lt;p&gt;";
            x1 = -b/(2*a);
            rutgonPS(-b,2*a);
            if ((Math.abs(tu) > mau && tu % mau == 0)|| tu == 0 || tu/mau == 1 || tu/mau == -1){
                textkq = textkq + "\\(\\Rightarrow x = " + Math.round(x1) +"\\)";
            } else {
                textkq = textkq + "\\(\\Rightarrow x = \\)  " + x1 +" hoặc " +  kquaphanso;
                }
            }
        else if (delta < 0){
            textkq = "\\(\\Delta = b^2 - 4AC\\) < 0" + "PT vô nghiệm";
        }

        } else {
        textkq = "a phải khác 0";
    }

    }

    public void rutgonPS(float tu1, float mau1){
        this.tu = tu1;
        this.mau = mau1;
        int Tu = Math.round(tu1);
        int Mau = Math.round(mau1);
        if ((Tu - tu1) == 0){
            int ucln = UCLN(Tu,Mau);
            Tu = Tu/ucln;
            Mau = Mau/ucln;
            if (Mau!=1)
            {
                if (Mau<0){
                    Mau=Mau*-1;
                    Tu=Tu*-1;
                }
                kquaphanso = "\\(\\frac{"+Tu+"}{"+Mau+"}\\)";
            }
        }
        else {
            List<Integer>tachTu = new ArrayList<>();
            List<Integer>tachMau = new ArrayList<>();
            int chon = 0 ;
            int soChung = 0;
            if (b < ngoaiCanBac2){
                chon = (int) b;
            } else {
                chon = ngoaiCanBac2;
            }
            if (ngoaiCanBac2 != 1){
                for (int i = chon; i >= 2 ; i--){
                    if (b%i == 0 && ngoaiCanBac2 %i == 0){
                        tachTu.add(i);
                        break;
                    }
                }
            }
            if (mau1 != 0){
                for (int i = Mau; i >= 2; i--){
                    if (Mau%i == 0){
                        tachMau.add(i);
                        break;
                    }
                }
            }

            for (Integer soChungTu : tachTu){
                for (Integer soChungMau : tachMau){
                    if (soChungTu.equals(soChungMau)) {
                        soChung = soChungTu;
                        break;
                    }
                }
            }
            if (soChung == 0){
                if (ngoaiCanBac2 == 1){
                    kquaphanso = "\\(\\frac{" + -Math.round(b) + "+" + "\\sqrt{"+trongCanBac2+"}}{"+Mau+"}\\)";
                } else {
                    kquaphanso = "\\(\\frac{" + -Math.round(b) + " + " +ngoaiCanBac2+"\\sqrt{"+trongCanBac2+"}}{"+Mau+"}\\)";
                }
            }
            else {
                if (ngoaiCanBac2 == 1){
                    kquaphanso = "\\(\\frac{" + -Math.round(b) + "+" + "\\sqrt{"+trongCanBac2+"}}{"+Mau+"}\\)";
                } else {
                    int tinhBTu,tinhDeltaTu,tinhMau;
                    tinhBTu = Math.round(b)/soChung;
                    tinhDeltaTu = ngoaiCanBac2/soChung;
                    tinhMau = Mau/soChung;
                    String strTu = "",strMau = "";

                    if (tinhBTu != 1){
                        strTu = String.valueOf(-tinhBTu);
                    }
                    if (tinhDeltaTu != 1){
                        strTu = strTu + "\\(\\sqrt{"+trongCanBac2+"}\\)";
                    } else strTu = strTu + tinhDeltaTu +"\\(\\sqrt{"+trongCanBac2+"}\\)";

                    if (tinhMau != 1){
                        kquaphanso = "\\(\\frac{"+strTu+"}{"+strMau+"}\\)";
                    } else kquaphanso = strTu;

                }
            }

        }
    }

    public void tinhDelta(){
        delta = (b * b) - 4 * a * c;
    }

    public void Init(){
        btnGiai = findViewById(R.id.btnGiai);
        nhapA = findViewById(R.id.gptBac2NhapA);
        nhapB = findViewById(R.id.gptBac2NhapB);
        nhapC = findViewById(R.id.gptBac2NhapC);
        ketqua = findViewById(R.id.ket_qua);
    }

    public void getData(){
        if (!nhapA.getText().toString().equals("")){
        a = Float.parseFloat(nhapA.getText().toString());
    } else a = 0f;

        if (!nhapB.getText().toString().equals("")){
            b = Float.parseFloat(nhapB.getText().toString());
        } else b = 0f;

        if (!nhapC.getText().toString().equals("")){
            c = Float.parseFloat(nhapC.getText().toString());
        } else c = 0f;
    }
    public void clear(){
        a = 0f;
        b = 0f;
        c = 0f;
        x1 = 0f;
        x2 = 0f;
        tu = 0;
        mau = 0;
        kquaphanso = "";
        textkq = "";
    }
    public int UCLN(int a,int b){
        if (a%b!=0)
            return UCLN(b,a%b);
        else
            return b;
    }
    public void khaiCanBac2(int soKhaiCan){
        int i = soKhaiCan;
        List<Integer> LstTachSoKhaiCan;
        LstTachSoKhaiCan = new ArrayList<>();
        for (int j = soKhaiCan; j >= 2 ; j--){
            if ((soKhaiCan % j) == 0 ){
                LstTachSoKhaiCan.add(j);
            }
        }
        for (Integer x : LstTachSoKhaiCan){
            ngoaiCanBac2 = 1;
            float tinh = (float) Math.sqrt(x);
            if ((tinh-Math.round(tinh)) == 0 ){
                ngoaiCanBac2 = Math.round(tinh);
                trongCanBac2 = soKhaiCan/x;
                break;
            }else trongCanBac2 = soKhaiCan;
        }

    }
    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
