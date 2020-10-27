package com.example.covtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covtest.entities.Question;
import com.example.covtest.entities.TestReport;
import com.example.covtest.network.ApiClient;
import com.example.covtest.network.ApiService;
import com.example.covtest.network.TestResponse;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    private List<Question> questionList;
    public SeekBar seekBar;
    public TextView txt_question;
    public TextView txt_question_details;
    public String[][] data;
    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[][] items =
                {
                        {"@drawable/tchow", "Question 1 : ", "Avez-vous une fatigue inhabituelle ces derniers jours ?"},
                        {"@drawable/chaleur", "Question 2 : ", " Avez-vous une toux ou votre toux habituelle s’est-elle modifiée ces derniers jours ?"},
                        {"@drawable/covid", "Question 3 : ", "Avez-vous de la diarrhée ces dernières 24 heures (au moins 3 selles molles) ?"},
                        {"@drawable/tchow", "Question 4 : ", " Ces derniers jours, avez-vous une toux ou votre toux habituelle s’est-elle modifiée ? "},
                        {"@drawable/tchow", "Question 5 : ", "  Ces derniers jours, avez-vous une fatigue inhabituelle ? "},
                        {"@drawable/tchow", "Question 3 : ", "  Ces derniers jours, avez-vous eu un mal de gorge et/ou des douleurs musculaires et/ou des courbatures inhabituelles et/ou des maux de tête inhabituels ? "},
                        {"@drawable/tchow", "Question 3 : ", " Ces dernières 24 heures, avez-vous de la diarrhée ?\nAvec au moins 3 selles molles."},
                        {"@drawable/tchow", "Question 3 : ", " Ces dernières 24 heures, avez-vous noté un manque de souffle inhabituel lorsque vous parlez ou faites un petit effort ?"},
                };

        /*{"@drawable/tchow", "Question 1 : ", "Avez-vous une fatigue inhabituelle ces derniers jours ?"},
        {"@drawable/chaleur", "Question 2 : ", " Avez-vous une toux ou votre toux habituelle s’est-elle modifiée ces derniers jours ?"},
        {"@drawable/covid", "Question 3 : ", "Avez-vous de la diarrhée ces dernières 24 heures (au moins 3 selles molles) ?"},
        {"@drawable/toux", "Question 4 : ", " Ces derniers jours, avez-vous une toux ou votre toux habituelle s’est-elle modifiée ? "},
        {"@drawable/fatigue", "Question 5 : ", "  Ces derniers jours, avez-vous une fatigue inhabituelle ? "},
        {"@drawable/gorge", "Question 3 : ", "  Ces derniers jours, avez-vous eu un mal de gorge et/ou des douleurs musculaires et/ou des courbatures inhabituelles et/ou des maux de tête inhabituels ? "},
        {"@drawable/diarrhea", "Question 3 : ", " Ces dernières 24 heures, avez-vous de la diarrhée ?\nAvec au moins 3 selles molles."},
        {"@drawable/breathing", "Question 3 : ", " Ces dernières 24 heures, avez-vous noté un manque de souffle inhabituel lorsque vous parlez ou faites un petit effort ?"},*/
        data = items;


        questionList = new ArrayList<>();
        questionList = setAllQuestions();

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(items.length - 1);
        txt_question = findViewById(R.id.txt_question);
        txt_question_details = findViewById(R.id.txt_question_details);
        loadQuestion();
    }

    public void preview(View view) {
        if (i > 0) {
            i--;
            loadQuestion();
        }
    }

    public void next(View view) {
        if (i < data.length - 1) {
            i++;
            loadQuestion();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Show Result").setIcon(R.drawable.start_icon)
                    .setMessage("Are you sure you want to show result of your test ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final DecimalFormat df = new DecimalFormat("##.###");

                            ApiService apiService = ApiClient.getInstance().create(ApiService.class);
                            TestReport testReport = new TestReport(1,0,0,1,0,0,1,1,0,1);
                            Call<TestResponse> testing = apiService.checkResult(testReport);
                            testing.enqueue(new Callback<TestResponse>() {
                                @Override
                                public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(TestActivity.this,"Result : "+response.body().getResult(),Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
                                        intent.putExtra("result",""+df.format(response.body().getResult()));
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(TestActivity.this,"Req not success",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<TestResponse> call, Throwable t) {
                                    Toast.makeText(TestActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });

                            /*ApiService apiService = ApiClient.getInstance().create(ApiService.class);
                            apiService.setParam(0, 1, 0, 1, 1,
                                    0, 1, 0, 0, 1,
                                    new Callback<TestResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<TestResponse> call,@NonNull retrofit2.Response<TestResponse> response) {
                                            String prediction = "" + response.body().getResult();

                                            Intent intent = new Intent(TestActivity.this, ResultActivity.class);
                                            intent.putExtra("result",prediction);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<TestResponse> call, @NonNull Throwable t) {

                                        }
                                    });*/

                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApiService apiService = ApiClient.getInstance().create(ApiService.class);
                            TestReport testReport = new TestReport(1,0,0,1,0,0,1,1,0,1);
                            Call<TestResponse> testing = apiService.checkResult(testReport);
                            testing.enqueue(new Callback<TestResponse>() {
                                @Override
                                public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(TestActivity.this,"Result : "+response.body().getResult(),Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(TestActivity.this,"Req not success",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<TestResponse> call, Throwable t) {
                                    Toast.makeText(TestActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });


                        }
                    })
                    .show();
        }
    }

    private ArrayList<Question> setAllQuestions(){
        ArrayList<Question> list = new ArrayList<>();

        list.add(new Question("@drawable/tchow", "Question 1 : ", "Avez-vous une fatigue inhabituelle ces derniers jours ?"));
        list.add(new Question("@drawable/chaleur", "Question 2 : ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        list.add(new Question("@drawable/covid", "Question 3 : ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
        return list;
    }
    public void loadQuestion() {
        String[] item = data[i];

        // image
        ImageView q_image = findViewById(R.id.Q_image);
        int imageResource = getResources().getIdentifier(item[0], null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        q_image.setImageDrawable(res);
        // question title
        txt_question.setText(item[1] + " : " + i);
        // question description
        txt_question_details.setText(item[2]);

        seekBar.setProgress(i);
    }
}