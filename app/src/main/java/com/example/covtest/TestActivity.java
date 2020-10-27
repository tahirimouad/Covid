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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covtest.entities.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    final String url = "http://192.168.1.13:5000/api/";
    private RequestQueue queue;
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
                        {"@drawable/tchow", "Question 1 : ", "Avez-vous une fatigue inhabituelle ces derniers jours ?,"},
                        {"@drawable/chaleur", "Question 2 : ", " Avez-vous une toux ou votre toux habituelle s’est-elle modifiée ces derniers jours ?"},
                        {"@drawable/covid", "Question 3 : ", "Avez-vous de la diarrhée ces dernières 24 heures (au moins 3 selles molles) ?"},
                };
        data = items;

        queue = Volley.newRequestQueue(this);

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

                            JSONObject postparams = null;
                            // Fill the jsonObject with user inputs
                            String json = "";
                            System.out.println(json);
                            try {
                                postparams = new JSONObject(json);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Send a post request and fetch the results from the flaskAPI
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray data = response.getJSONArray("data");
                                        if (data.length() == 0) {
                                            Toast.makeText(getApplicationContext(), "No result !", Toast.LENGTH_LONG).show();
                                        } else {
                                            JSONObject obj = data.getJSONObject(0);
                                            String prediction = "" + obj.getString("prediction");

                                            Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                                            intent.putExtra("result",prediction);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            queue.add(jsonObjectRequest);

                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    private ArrayList<Question> setAllQuestions(){
        ArrayList<Question> list = new ArrayList<>();
        list.add(new Question("@drawable/tchow", "Question 1 : ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry."));
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