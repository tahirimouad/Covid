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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covtest.entities.Question;
import com.example.covtest.entities.TestReport;
import com.example.covtest.network.ApiClient;
import com.example.covtest.network.ApiService;
import com.example.covtest.network.TestResponse;
import com.example.covtest.utils.Constants;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    private List<Question> questionList;
    private RadioButton radioButtonYes;
    private RadioButton radioButtonNo;
    public SeekBar seekBar;
    public TextView txt_question;
    public TextView txt_question_details;
    public String[][] data;
    private int[] resultArray = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[][] items =
                {
                        {"@drawable/fatigue", "Question 1 : ", "Avez-vous une fatigue inhabituelle ces derniers jours ?"},
                        {"@drawable/taux", "Question 2 : ", " Avez-vous une toux ou votre toux habituelle s’est-elle modifiée ces derniers jours ?"},
                        {"@drawable/diarrhea", "Question 3 : ", "Avez-vous de la diarrhée ces dernières 24 heures (au moins 3 selles molles) ?"},
                        {"@drawable/gorge", "Question 4: ", "  Ces derniers jours, avez-vous eu un mal de gorge et/ou des douleurs musculaires et/ou des courbatures inhabituelles et/ou des maux de tête inhabituels ? "},
                        {"@drawable/breathing", "Question 5 : ", " Ces dernières 24 heures, avez-vous noté un manque de souffle inhabituel lorsque vous parlez ou faites un petit effort ?"},
                        {"@drawable/fatigue", "Question 6 : ", "Avez-vous une fatigue inhabituelle ces derniers jours ?"},
                        {"@drawable/taux", "Question 7 : ", " Avez-vous une toux ou votre toux habituelle s’est-elle modifiée ces derniers jours ?"},
                        {"@drawable/diarrhea", "Question 8 : ", "Avez-vous de la diarrhée ces dernières 24 heures (au moins 3 selles molles) ?"},
                        {"@drawable/gorge", "Question 9 : ", "  Ces derniers jours, avez-vous eu un mal de gorge et/ou des douleurs musculaires et/ou des courbatures inhabituelles et/ou des maux de tête inhabituels ? "},
                        {"@drawable/breathing", "Question 10 : ", " Ces dernières 24 heures, avez-vous noté un manque de souffle inhabituel lorsque vous parlez ou faites un petit effort ?"}
                };
        data = items;

        questionList = new ArrayList<>();
        questionList = setAllQuestions();
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(items.length - 1);
        txt_question = findViewById(R.id.txt_question);
        txt_question_details = findViewById(R.id.txt_question_details);
        radioButtonYes = findViewById(R.id.rb_yes);
        radioButtonNo = findViewById(R.id.rb_no);
        loadQuestion();
    }

    public void preview(View view) {
        if (i > 0) {
            i--;
            loadQuestion();
        }

    }

    /*
    * validate function use for checking if the radioButtons are checked
    * */
    private boolean validate(){
        if (radioButtonYes.isChecked() || radioButtonNo.isChecked())
            return true;
        return false;
    }
    private void setUncheckedRadioButton(){
        radioButtonNo.setChecked(false);
        radioButtonYes.setChecked(false);
    }

    public void next(View view) {
        if (i < data.length - 1) {
            if (validate()){
                if (radioButtonYes.isChecked()){
                    resultArray[i] = Constants.YES;
                }
                if (radioButtonNo.isChecked()){
                    resultArray[i] = Constants.NO;
                }
                //unchecked the both radio button before the next question
                setUncheckedRadioButton();
                i++;
                loadQuestion();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Show Result").setIcon(R.drawable.start_icon)
                    .setMessage("Are you sure you want to show result of your test ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (radioButtonYes.isChecked()){
                                resultArray[i] = Constants.YES;
                            }else if (radioButtonNo.isChecked()){
                                resultArray[i] = Constants.NO;
                            }
                            else{
                                resultArray[i] = Constants.NO;
                            }

                            final DecimalFormat df = new DecimalFormat("##.###");

                            ApiService apiService = ApiClient.getInstance().create(ApiService.class);
                            TestReport testReport = new TestReport(resultArray[0],resultArray[1],resultArray[2],resultArray[3],
                                    resultArray[4],resultArray[5],resultArray[6],resultArray[7],resultArray[8],resultArray[9]);
                            Call<TestResponse> testing = apiService.checkResult(testReport);
                            testing.enqueue(new Callback<TestResponse>() {
                                @Override
                                public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                                    if(response.isSuccessful()){
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

                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String msg =resultArray[0]+"\n"+resultArray[1]+"\n"+resultArray[2]+"\n"+resultArray[3]+"\n"+
                                    resultArray[4]+"\n"+resultArray[5]+"\n"+resultArray[6]+"\n"+resultArray[7]+"\n"+resultArray[8]+"\n"+resultArray[9];
                            Toast.makeText(TestActivity.this,msg,Toast.LENGTH_LONG).show();


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
        switch (resultArray[i]){
            case 0:
                if(radioButtonYes.isActivated()){
                    radioButtonYes.setChecked(false);
                }
                radioButtonNo.setChecked(true);
                break;
            case 1:
                if(radioButtonNo.isActivated()){
                    radioButtonNo.setChecked(false);
                }
                radioButtonYes.setChecked(true);
                break;
        }
        String[] item = data[i];
        // image
        ImageView q_image = findViewById(R.id.Q_image);
        int imageResource = getResources().getIdentifier(item[0], null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        q_image.setImageDrawable(res);
        // question title
        //txt_question.setText(item[1] + " : " + i);
        txt_question.setText(item[1]);
        // question description
        txt_question_details.setText(item[2]);

        seekBar.setProgress(i);
    }
}