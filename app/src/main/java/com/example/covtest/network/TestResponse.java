package com.example.covtest.network;

import com.google.gson.annotations.SerializedName;

public class TestResponse {
    @SerializedName("inf_proba")
    private float result;
    public float getResult() {
        return result;
    }
}
