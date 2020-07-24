package com.billi.hocdot.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questions {
    private List<String> lstTitle;
    private Map<String,String> question;
    private Map<String,String> answer;

    public Questions() {
        lstTitle = new ArrayList<>();
        question = new HashMap<>();
        answer = new HashMap<>();
    }

    public void setQuestions(String title, String question, String answer) {
        lstTitle.add(title);
        this.question.put(title,question);
        this.answer.put(title+"answer",answer);
    }

    public List<String> getLstTitle() {
        return lstTitle;
    }

    public String getQuestion(String title) {
        return question.get(title);
    }

    public String getAnswer(String title) {
        return answer.get(title+"answer");
    }
}
