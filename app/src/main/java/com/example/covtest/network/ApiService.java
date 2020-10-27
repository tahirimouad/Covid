package com.example.covtest.network;

import com.example.covtest.entities.TestReport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/corona_test")
    void setParam(@Field("Fever") int fever, @Field("Tiredness") int tiredness, @Field("Dry-Cough") int dry_cough, @Field("Difficulty-in-Breathing") int breathing,
                  @Field("Sore-Throat") int soreThroat, @Field("None-Symton") int noneSymton, @Field("Pains") int pains, @Field("Nasal-Congestion") int nasalCongestion,
                  @Field("Runny-Nose") int runnyNose, @Field("Diarrhea") int diarrhea, Callback<TestResponse> callback);

    @POST("api/corona_test")
    Call<TestResponse> checkResult(@Body TestReport testReport);
}
