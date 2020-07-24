package com.billi.hocdot.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.billi.hocdot.BangTinhTrucTuyen.giaiPT.GiaiHePT2An;
import com.billi.hocdot.BangTinhTrucTuyen.giaiPT.GiaiPTBac1;
import com.billi.hocdot.BangTinhTrucTuyen.giaiPT.GiaiPTBac2;
import com.billi.hocdot.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBangTinh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBangTinh extends Fragment {

    Button btnPtBac1;
    Button btnPtBac2;
    Button btnHePT;

    public FragmentBangTinh() {

    }

    // TODO: Rename and change types and number of parameters
    public static FragmentBangTinh newInstance() {
        FragmentBangTinh fragment = new FragmentBangTinh();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_bang_tinh, container, false);
        btnPtBac1 = view.findViewById(R.id.btnPtrinhBac1);
        btnPtBac2 = view.findViewById(R.id.btnPtrinhBac2);
        btnHePT = view.findViewById(R.id.btnHePT);

        btnPtBac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GiaiPTBac1.class);
                startActivity(i);
            }
        });

        btnPtBac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GiaiPTBac2.class);
                startActivity(i);
            }
        });

        btnHePT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GiaiHePT2An.class);
                startActivity(i);
            }
        });
        return view;
    }

}
