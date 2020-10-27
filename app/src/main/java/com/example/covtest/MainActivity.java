package com.example.covtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.covtest.entities.TestReport;
import com.example.covtest.network.ApiClient;
import com.example.covtest.network.ApiService;
import com.example.covtest.network.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start_test(View view){
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
        finish();
    }

    public void skip(View view){

        Retrofit instance = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = instance.create(ApiService.class);
        TestReport testReport = new TestReport(1,0,0,1,0,0,1,1,0,1);
        Call<TestResponse> testing = apiService.checkResult(testReport);
        testing.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Result : "+response.body().getResult(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Req not success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
