package com.billi.hocdot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.billi.hocdot.ActiviAnswer;
import com.billi.hocdot.Contans.Questions;
import com.billi.hocdot.R;

import io.github.kexanie.library.MathView;

public class AdapterQuestions extends BaseAdapter {
    private Questions questions;
    private Context context;
    private int layout;

    public AdapterQuestions(Context context,int layout,Questions questions) {
        this.context = context;
        this.layout = layout;
        this.questions = questions;

    }

    @Override
    public int getCount() {
        return questions.getLstTitle().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtTitleQuestion = view.findViewById(R.id.titleQues);
            viewHolder.txtContentQuestion = view.findViewById(R.id.txtContentQues);
            viewHolder.btnAnswer = view.findViewById(R.id.btnAnswer);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtTitleQuestion.setText(questions.getLstTitle().get(i));

        viewHolder.txtContentQuestion.config(
                "MathJax.Hub.Config({\n"+
                        "  CommonHTML: { linebreaks: { automatic: true } },\n"+
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n"+
                        "         SVG: { linebreaks: { automatic: true } }\n"+
                        "});");
        final Spanned convertHTMLQuest = Html.fromHtml(questions.getQuestion(questions.getLstTitle().get(i)));
        viewHolder.txtContentQuestion.setInitialScale(1);
        viewHolder.txtContentQuestion.getSettings().setLoadWithOverviewMode(true);
        viewHolder.txtContentQuestion.getSettings().setUseWideViewPort(true);
        viewHolder.txtContentQuestion.getSettings().setSupportZoom(true);
        viewHolder.txtContentQuestion.getSettings().setBuiltInZoomControls(true);
        viewHolder.txtContentQuestion.getSettings().setDisplayZoomControls(false);
        viewHolder.txtContentQuestion.getSettings().setJavaScriptEnabled(true);

        viewHolder.txtContentQuestion.setText("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes\">" +convertHTMLQuest);

        final Intent intent = new Intent(view.getContext(), ActiviAnswer.class);

        viewHolder.btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("answer",questions.getAnswer(questions.getLstTitle().get(i)));
                intent.putExtra("title",questions.getLstTitle().get(i));
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView txtTitleQuestion;
        MathView txtContentQuestion;
        Button btnAnswer;
    }
}
