package com.example.covtest.network;

import com.google.gson.annotations.SerializedName;

public class TestResponse {
    @SerializedName("int-proba")
    private int result;

    public int getResult() {
        return result;
    }
}
