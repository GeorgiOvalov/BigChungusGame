package org.example.pacman;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;


        public class MainActivity extends AppCompatActivity {
        //reference to the main view
        GameView gameView;
        //reference to the game class.
        Game game;
        private Timer Timer;
        private Timer countdownTimer;
        private int counter;
        private int countdown = 60;
        private TextView countdownView;


        // setting down methods for timer and countdown
        private void TimerMethod() {
            this.runOnUiThread(Timer_Tick);
        }
        private void CountdownTimerMethod() {
            this.runOnUiThread(Countdown_Tick);
        }
        private Runnable Countdown_Tick = new Runnable() {
            public void run() {

            if (game.GetRunning() && game.getCountdown() > 0) {
                game.SetCountdown(game.getCountdown ()-1);
                countdownView.setText("Countdown: " + game.getCountdown()+ " seconds");
            }

            if (game.GetRunning() && game.getCountdown() == 1) {
                countdownView.setText("Countdown: " + game.getCountdown() + " seconds");
            }

            if (game.getCountdown() == 0 && game.getCoins().size() > game.getPoints()) {
                Toast.makeText(getApplicationContext(), "You lost! Try again", Toast.LENGTH_LONG).show();
                game.SetRunning(false);
            }
            }
        };


          private Runnable Timer_Tick = new Runnable() {
                public void run() {
                    if (game.GetRunning()) {
                        counter++;
                        game.movePacman();
                        for (Enemy enemy : game.getEnemies()) {
                            game.moveEnemy(enemy);
                        }
                    }
                }
            };

          // displaying all the properties on top for points, values, timer and etc for each new game
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //saying we want the game to run in one mode only
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_main);

            gameView = findViewById(R.id.gameView);
            TextView textView = findViewById(R.id.points);
            TextView levelView = findViewById(R.id.level);
            countdownView = findViewById(R.id.countdown);

            game = new Game(this, textView, levelView);
            game.setGameView(gameView);
            gameView.setGame(game);

            Timer = new Timer();
            countdownTimer = new Timer();
            game.SetRunning(true);
            Timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                }
            }, 0, 10);
            countdownTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    CountdownTimerMethod();
                }
            }, 0, 1000);

            game.newGame();

             // the swiping gestures
            gameView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
                @Override
                public void onSwipeTop() {
                    game.SetDirection(2);
                }
                @Override
                public void onSwipeRight() {game.SetDirection(0); }
                @Override
                public void onSwipeBottom() {
                    game.SetDirection(3);
                }
                @Override
                public void onSwipeLeft() {
                    game.SetDirection(1);
                }
            });
        }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();
                if (id == R.id.action_settings) {
                    Toast.makeText(this, "Settings clicked", Toast.LENGTH_LONG).show();
                    return true;
                } else if (id == R.id.action_continueGame) {
                    game.SetRunning(true);
                    Toast.makeText(this, "Resume Game", Toast.LENGTH_LONG).show();
                    return true;
                } else if (id == R.id.action_pauseGame) {
                    game.SetRunning(false);
                    Toast.makeText(this, "Pause Game", Toast.LENGTH_LONG).show();
                    return true;
                } else if (id == R.id.action_newGame) {
                    game.newGame();
                    game.SetRunning(true);
                    countdown = 60;
                    Toast.makeText(this, "New Game", Toast.LENGTH_LONG).show();
                    counter = 0;
                    return true;
                }
                return super.onOptionsItemSelected(item);
            }
         }



