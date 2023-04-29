package au.edu.jcu.braintrainermaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private EditText timerValue;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        timerValue = findViewById(R.id.timerEditText);
    }

    public void updateTimer(View view) {
        try {
            value = Integer.parseInt(timerValue.getText().toString());
        } catch (Exception e) {
            value = 0;
        }
        if (timerValue.getText().toString().equals("") || value < 3 || value > 15) {
            Toast.makeText(this, "Please enter in an appropriate value between 3 and 15", Toast.LENGTH_LONG).show();
        } else {
            GameActivity.TIMER = (value * 1000);
            Toast.makeText(this, "Congrats! Timer has been updated to: " + value, Toast.LENGTH_SHORT).show();
        }
    }

    public void openMainMenu(View view) {
        Intent newGameIntent = new Intent(this, MainActivity.class);
        startActivity(newGameIntent);
    }
}