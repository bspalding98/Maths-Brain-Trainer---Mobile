package au.edu.jcu.braintrainermaths;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

//    public static ConstraintLayout backgroundAnimation;
    private VideoView tutorial;
    private MediaController mediaController;
    private ImageView play;
    private ListView highScoreList;
    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseVariables();
//        startDynamicBackground();
        createVideoPlayer();


        loadHighScoreFromDatabase();

        setPersonAdapter();

    }

    // PUBLIC

    public void viewHighScores(View view) {
        Intent newGameIntent = new Intent(this, HighScoresActivity.class);
        startActivity(newGameIntent);
    }

    public void playTutorial(View view) {
        play.setVisibility(View.INVISIBLE);
        tutorial.setVisibility(RelativeLayout.VISIBLE);
        tutorial.start();  // show video
    }

    public void startGame(View view) {
        Intent newGameIntent = new Intent(this, GameActivity.class);
        startActivity(newGameIntent);
    }

//    public static void startDynamicBackground() {
//        AnimationDrawable animationDrawable = (AnimationDrawable) backgroundAnimation.getBackground();
//        animationDrawable.setEnterFadeDuration(2500);
//        animationDrawable.setExitFadeDuration(5000);
//        animationDrawable.start();
//    }

    public void openSettings(View view) {
        Intent newGameIntent = new Intent(this, Settings.class);
        startActivity(newGameIntent);
    }


    // PRIVATE

    private void setPersonAdapter() {
        PersonListAdapter personListAdapter = new PersonListAdapter(getApplicationContext(), Person.personArrayList);
        highScoreList.setAdapter(personListAdapter);
    }

    private void loadHighScoreFromDatabase() {
        appDatabase = AppDatabase.instanceOfDatabase(this);
        appDatabase.populateNoteListArray();
    }

    private void createVideoPlayer() {
        tutorial.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.venom_video);
        mediaController.setAnchorView(tutorial);
        tutorial.setMediaController(mediaController); // Media controller
        tutorial.setOnCompletionListener(mp -> {
            tutorial.stopPlayback(); // Stop playback loop
            play.setVisibility(View.VISIBLE);
            tutorial.setVisibility(RelativeLayout.GONE);
        });
        tutorial.setVisibility(RelativeLayout.GONE);
    }

    private void initialiseVariables() {

//        backgroundAnimation = findViewById(R.id.mainLayout);
        tutorial = findViewById(R.id.tutorialVideo);
        play = findViewById(R.id.playVideoButton);

        mediaController = new MediaController(this);
        highScoreList = findViewById(R.id.highscoreList);
    }
}