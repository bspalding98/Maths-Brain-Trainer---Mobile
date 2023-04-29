package au.edu.jcu.braintrainermaths;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    public static long TIMER = 50000L;

    private float x;
    private float y;
    private float z;
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private SensorEventListener sensorEventListener;

    TextView timerView;
    TextView stageView;
    TextView equationView;
    TextView optionOneView;
    TextView optionTwoView;
    TextView optionThreeView;
    TextView optionFourView;
    TextView totalScoreView;
    TextView nameRecordView;
    Button homeButton;
    Button continueButton;
    Button startGameButton;
    Button playAgain;
    Button submitButton;
    CountDownTimer timer;
    int incorrectAnswer;
    int chooser;
    int answer = 0;
    int stages;
    int questions = 0;
    int a = 0;
    int b = 0;
    int choice = 0;
    boolean gameState = false;
    Random random = new Random();
    TextView[] answerOptions = new TextView[4];
    ArrayList<Integer> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialiseVariables();
        createTimer();
        createSensorListener();

        hideGame();
        homeButton.setVisibility(View.VISIBLE);
    }

    //PUBLIC

    public void startGame(View view) {
        if (!gameState) {
            showGame();
            createExpression();
            timer.start();
            sensorManager.registerListener(sensorEventListener, sensors.get(0), 500000000);
            homeButton.setVisibility(View.INVISIBLE);
        }
    }

    public void playAgain(View view) {
        resetVisuals();
        startGame(view);
        sensorManager.registerListener(sensorEventListener, sensors.get(0), 500000000);
    }

    public void openMainMenu(View view) {
        Intent newGameIntent = new Intent(this, MainActivity.class);
        startActivity(newGameIntent);
    }


    //PRIVATE

    private void resetVisuals() {
        questions = 0;
        stages = 0;
        playAgain.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
    }

    private void createExpression() {
        stages += 1;
        stageView.setText("Stage: " + stages);
        answers.clear();
        a = random.nextInt(70) + 30;
        b = random.nextInt(70) + 30;
        chooser = random.nextInt(4);
        equationView.setText(String.format("%s + %s", a, b));
        answer = a + b;

        for (int i = 0; i < 4; i++) {
            if (i == chooser) {
                answers.add(a + b);
            } else {
                incorrectAnswer = random.nextInt((a + b)) + 30;
                while (incorrectAnswer == (a + b)) {
                    incorrectAnswer = random.nextInt((a + b)) + 30;
                }
                answers.add(incorrectAnswer);
            }
            answerOptions[i].setText(String.valueOf(answers.get(i)));
        }
    }

    private void showGame() {
        startGameButton.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.INVISIBLE);
        nameRecordView.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);
        timerView.setVisibility(View.VISIBLE);
        stageView.setVisibility(View.VISIBLE);
        equationView.setVisibility(View.VISIBLE);
        optionOneView.setVisibility(View.VISIBLE);
        optionTwoView.setVisibility(View.VISIBLE);
        optionThreeView.setVisibility(View.VISIBLE);
        optionFourView.setVisibility(View.VISIBLE);
    }

    private void hideGame() {
        startGameButton.setVisibility(View.VISIBLE);
        continueButton.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);
        nameRecordView.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        timerView.setVisibility(View.INVISIBLE);
        stageView.setVisibility(View.INVISIBLE);
        equationView.setVisibility(View.INVISIBLE);
        optionOneView.setVisibility(View.INVISIBLE);
        optionTwoView.setVisibility(View.INVISIBLE);
        optionThreeView.setVisibility(View.INVISIBLE);
        optionFourView.setVisibility(View.INVISIBLE);
        totalScoreView.setVisibility(View.INVISIBLE);
    }

    private void createSensorListener() {
        sensorEventListener = new SensorEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] values = sensorEvent.values;
                x = values[0];
                y = values[1];
                z = values[2];
                checkAnswers();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(sensorEventListener, sensors.get(0), 500000000);
    }

    private void createTimer() {
        timer = new CountDownTimer(TIMER + 100, 1000) {    // change time; changed for testing
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                sensorManager.unregisterListener(sensorEventListener);
                hideGame();
                if (choice == answer) {
                    continueButton.setVisibility(View.VISIBLE);
                } else {
                    startGameButton.setVisibility(View.INVISIBLE);
                    if ((stages-1 > (Integer.parseInt(String.valueOf(Person.personArrayList.get(Person.personArrayList.size() - 1).getScore()))))) {
                        totalScoreView.setText("Congratulations you got through " + stages + " stages!");
                        totalScoreView.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.VISIBLE);
                        getUserInput();
                    } else {
                        Toast.makeText(GameActivity.this, getString(R.string.mockText), Toast.LENGTH_LONG).show();
                        playAgain.setVisibility(View.VISIBLE);
                        homeButton.setVisibility(View.VISIBLE);
                    }
                    gameState = false;
                }
            }
        };
    }

    private void getUserInput() {
        nameRecordView.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Please enter in a name to submit to high scores", Toast.LENGTH_LONG).show();
    }

    public void recordScore(View view) {
        if (nameRecordView.getText().toString().equals("")) {
            getUserInput();
        } else {
            String name = nameRecordView.getText().toString();
            Person person = new Person(name, Integer.toString(stages-1));
            MainActivity.appDatabase.addScoreToDatabase(person);
            nameRecordView.setText("");
            nameRecordView.setVisibility(View.INVISIBLE);
            totalScoreView.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.INVISIBLE);
            playAgain.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswers() {
        choice = 0;
        getInput();

        if (choice != 0)
            timer.onFinish();
    }

    private void getInput() {
        if (x > 1) choice = Integer.parseInt(optionTwoView.getText().toString());
        else if (x < -1) choice = Integer.parseInt(optionThreeView.getText().toString());
        else if (z > 2) choice = Integer.parseInt(optionFourView.getText().toString());
        else if (z < -2) choice = Integer.parseInt(optionOneView.getText().toString());
    }

    private void initialiseVariables() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);  // rate of change respect to time

        // TextViews:
        timerView = findViewById(R.id.timerView);
        stageView = findViewById(R.id.stageView);
        equationView = findViewById(R.id.equationView);
        optionOneView = findViewById(R.id.optionOneView);
        optionTwoView = findViewById(R.id.optionTwoView);
        optionThreeView = findViewById(R.id.optionThreeView);
        optionFourView = findViewById(R.id.optionFourView);
        totalScoreView = findViewById(R.id.totalScoreView);
        nameRecordView = findViewById(R.id.nameRecordView);
        answerOptions[0] = optionOneView;
        answerOptions[1] = optionTwoView;
        answerOptions[2] = optionThreeView;
        answerOptions[3] = optionFourView;

        //Buttons:
        homeButton = findViewById(R.id.homeButton);
        startGameButton = findViewById(R.id.startGameButton);
        playAgain = findViewById(R.id.playAgain);
        continueButton = findViewById(R.id.continueButton);
        submitButton = findViewById(R.id.submitButton);
    }
}