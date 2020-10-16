package com.example.covtest;

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

public class TestActivity extends AppCompatActivity {

    public SeekBar seekBar;
    public TextView txt_question;
    public TextView txt_question_detals;
    public String[][] data;
    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[][] items =
                {
                        {"@drawable/tchow", "Question 1 : ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"},
                        {"@drawable/chaleur", "Question 2 : ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"},
                        {"@drawable/covid", "Question 3 : ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"},
                };
        data = items;

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(items.length - 1);
        txt_question = findViewById(R.id.txt_question);
        txt_question_detals = findViewById(R.id.txt_question_details);
        loadQuestion();
    }

    public void preview(View view){
        if( i>0 ){
            i--;
            loadQuestion();
        }
    }

    public void next(View view){
        if( i<data.length -1){
            i++;
            loadQuestion();
        }else{
            new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Show Result").setIcon(R.drawable.start_icon)
                .setMessage("Are you sure you want to show result of your test ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
        }
    }

    public void loadQuestion(){
        String[] item = data[i];

        // image
        ImageView q_image = findViewById(R.id.Q_image);
        int imageResource = getResources().getIdentifier(item[0], null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        q_image.setImageDrawable(res);
        // qestion title
        txt_question.setText(item[1]+" : "+i) ;
        // qestion description
        txt_question_detals.setText(item[2]);

        seekBar.setProgress(i);
    }
}