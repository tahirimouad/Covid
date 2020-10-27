package com.example.covtest.entities;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("id")
    private String icon;
    @SerializedName("question")
    private String nQuestion;

    private String details;

    public Question(String icon, String nQuestion, String details) {
        this.icon = icon;
        this.nQuestion = nQuestion;
        this.details = details;
    }

    public String getIcon() {
        return icon;
    }

    public String getnQuestion() {
        return nQuestion;
    }

    public String getDetails() {
        return details;
    }
}
