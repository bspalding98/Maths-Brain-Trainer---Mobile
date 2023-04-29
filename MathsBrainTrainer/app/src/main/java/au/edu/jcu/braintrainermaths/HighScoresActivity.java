package au.edu.jcu.braintrainermaths;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScoresActivity extends AppCompatActivity {

    private ListView highScoreList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        highScoreList = findViewById(R.id.highscoreList);

        setPersonAdapter();
    }

    public void openMainMenu(View view) {
        Intent newGameIntent = new Intent(this, MainActivity.class);
        startActivity(newGameIntent);
    }

    private void setPersonAdapter() {
        PersonListAdapter personListAdapter = new PersonListAdapter(getApplicationContext(), Person.personArrayList);
        highScoreList.setAdapter(personListAdapter);
    }
}