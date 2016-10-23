package com.example.mario.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar barra;

    TextView tiempo;

    Boolean counterIsActive = false;

    Button inicio;

    CountDownTimer countDownTimer;

    public void resetTimer() {
        tiempo.setText("01:00");
        barra.setProgress(60);
        countDownTimer.cancel();
        inicio.setText("INICIAR");
        barra.setEnabled(true);
        counterIsActive = false;
    }


    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft % 60;

        String secondsString = Integer.toString(seconds);
        if (secondsString.length() < 2) {
            secondsString = "0" + secondsString;
        }

        String minutesString = Integer.toString(minutes);
        if (minutesString.length() < 2) {
            minutesString = "0" + minutesString;
        }

        tiempo.setText(minutesString + ":" + secondsString);
    }

    public void controlTimer(View view) {

        if (!counterIsActive) {

            counterIsActive = true;
            barra.setEnabled(false);
            inicio.setText("DETENER");

            countDownTimer = new CountDownTimer(barra.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    tiempo.setText("00:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    resetTimer();

                }
            }.start();

        } else {

            resetTimer();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barra = (SeekBar)findViewById(R.id.controlador);
        tiempo = (TextView)findViewById(R.id.cuenta);
        inicio = (Button)findViewById(R.id.iniciador);

        barra.setMax(600);
        barra.setProgress(60);

        tiempo.setEnabled(false);

        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
