package com.example.covtest.entities;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("id")
    private String id;
    @SerializedName("question")
    private String question;

    private String icon;

    public Question(String id, String nQuestion, String icon) {
        this.id = icon;
        this.question = nQuestion;
        this.icon = icon;
    }

    public String getIcon() {
        return id;
    }

    public String getnQuestion() {
        return question;
    }

    public String getDetails() {
        return icon;
    }
}
