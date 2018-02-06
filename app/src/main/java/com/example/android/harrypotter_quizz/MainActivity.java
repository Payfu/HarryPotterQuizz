package com.example.android.harrypotter_quizz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * Answers
     */
    private static final String[] MY_ARRAY = {"q1_r3", "q2_r3", "Dobby", "q4_r2", "q5_r1", "q6_r4", "q7_r2", "q7_r4", "q8_r2", "q9_r2", "q10_r1"};

    int score = 0;
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * On récupère les réponses dans une boucle et on vérifie si ce sont les bonne réponse checkées
     * @param view
     */
    public void onValidForm(View view){

        score = 0;
        check = 0;

        if( areAllQuestionsAnswered() ){
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_error);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            return;
        }

        for(int i=0; i<11; i++) {
            int resId = getResources().getIdentifier(MY_ARRAY[i], "id", getPackageName());

            if(i == 2) {
                EditText nameEditText = (EditText) findViewById(R.id.q3_r1);
                String strResult = nameEditText.getText().toString().replaceAll(" ", "");
                if (strResult.equals(MY_ARRAY[2])){  score++;  }
            }
            else if(i == 6 || i == 7) {
                CheckBox checkBox = (CheckBox) findViewById(resId);

                if(checkBox.isChecked()) {   check++;   }

                if(check == 2){score++;}
            }
            else {
                RadioButton question = (RadioButton) findViewById(resId);
                if(question.isChecked()){  score++;  }
            }
        }

        displayToast(score * 10);
    }


    private boolean areAllQuestionsAnswered()
    {
        int nbCheck = 0;

        for(int i=1; i<=10; i++) {

            if(i == 3){
                EditText nameEditText = (EditText) findViewById(R.id.q3_r1);
                String strResult = nameEditText.getText().toString().replaceAll(" ", "");

                if (!TextUtils.isEmpty(strResult)){  nbCheck++;  }
            }
            else if(i == 7){
                for (int ii = 1; ii <= 4; ii++) {

                    int resId = getResources().getIdentifier("q" + i + "_r" + ii, "id", getPackageName());
                    CheckBox checkBox = (CheckBox) findViewById(resId);

                    if(checkBox.isChecked()){ nbCheck++; ii = 4; }
                }
            }

            if(i != 3 && i != 7) {
                for (int ii = 1; ii <= 4; ii++) {
                    int resId = getResources().getIdentifier("q" + i + "_r" + ii, "id", getPackageName());
                    RadioButton question = (RadioButton) findViewById(resId);

                    if(question.isChecked()){ nbCheck++; }
                }
            }

        }

        if(nbCheck != 10){    return true;      }

        return false;
    }



    private void displayToast(int score){
        Context context = getApplicationContext();

        int comment = getResources().getIdentifier("score_" + score, "string", getPackageName());

        CharSequence text = getString(R.string.your_score) + score + "/100 \n" + getString(comment);
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
