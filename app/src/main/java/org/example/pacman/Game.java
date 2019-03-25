package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 *
 * This class should contain all your game logic
 */

        public class Game {
            //context is a reference to the activity
            private Context context;
            private int points = 0; //how points do we have


            //bitmap of the pacman
            private Bitmap pacBitmap;

            //bitmap of the coins/pizzas
            private Bitmap coinBitmap;

            //bitmap of the enemy
            private Bitmap enemyBitmap;

            //textview reference to points
            private TextView pointsView;

            //textview reference to level
            private TextView levelView;

            private int pacx, pacy;

    //the list of enemies
    public ArrayList<Enemy> enemies = new ArrayList<>();

    //the list of goldcoins - initially empty
    public ArrayList<GoldCoin> coins = new ArrayList<>();

      //a reference to the gameview
            private GameView gameView;
            private int direction;
            private int pacmanpixels = 3;
            private int enemypixels = 1;
            private boolean running;
            private int level;
            private int countdown = 60;
            private int h, w; //height and width of screen


         public Game(Context context, TextView view, TextView levelview) {
              this.context = context;
              this.pointsView = view;
              this.levelView = levelview;
              pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigchungus);
              coinBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.carrot);
              enemyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ugandan_knuckles);
    }

        public void setGameView(GameView view) {
            this.gameView = view;
        }



        //initializing new game
        public void newGame() {
            pacx = 40;
            pacy = 400; //just some starting coordinates
            //reset the points
            points = 0;
            level = 1;
            countdown = 30;
            pointsView.setText(context.getResources().getString(R.string.points) + " " + points);
            levelView.setText(context.getResources().getString(R.string.level)+ " " +level);
            coins.clear();
            enemies.clear();
            gameView.invalidate(); //redraw screen
            coins.add(new GoldCoin(120, 500, false));
            coins.add(new GoldCoin(200, 410, false));
            coins.add(new GoldCoin(330, 820, false));
            coins.add(new GoldCoin(439, 140, false));


            enemies.add(new Enemy(200, 200));
        }

        //movements of pacman
         public void movePacman() {

            switch (direction) {
                case 0:
                    //right
                    if (pacx + pacmanpixels + pacBitmap.getWidth() < w) {
                        pacx = pacx + pacmanpixels;
                        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigchungus);
                    }
                    break;

                case 1:
                    //left
                    if (pacx > 0) {
                        pacx = pacx - pacmanpixels;
                        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigchungusleft);
                    }
                    break;

                case 2:
                    //up
                    if (pacy - pacmanpixels > 0) {
                        pacy = pacy - pacmanpixels;
                        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigchungus);
                    }
                    break;

                case 3:
                    //down
                    if (pacy + pacmanpixels + pacBitmap.getHeight() < h) {
                        pacy = pacy + pacmanpixels ;
                        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigchungus);
                    }
                    break;
            }
            doCollisionCheck();
            gameView.invalidate();
        }

        // movements of enemy
            public void moveEnemy(Enemy enemy) {
            if (enemy.getEnemyX() + enemypixels + enemyBitmap.getWidth() < w && enemy.getEnemyX() < pacx) {
                enemy.setEnemyX(enemy.getEnemyX()+enemypixels); //right
            }

            else if (enemy.getEnemyX() > 0 && enemy.getEnemyX() > pacx) {
                enemy.setEnemyX(enemy.getEnemyX() - enemypixels); //left
            }

            else if (enemy.getEnemyY() - enemypixels > 0 && enemy.getEnemyY()> pacy) {
                enemy.setEnemyY(enemy.getEnemyY() - enemypixels); //up
            }

            else if (enemy.getEnemyY()+ enemypixels + enemyBitmap.getHeight() < h && enemy.getEnemyY() < pacy) {
                enemy.setEnemyY(enemy.getEnemyY() + enemypixels); //down
            }
        }

        public void setSize(int h, int w) {
            this.h = h;
            this.w = w;
        }

        public boolean CheckIfCollectedAll() {
            for (GoldCoin coin : coins) {
                if (!coin.IsPickedUp()) {
                    return false;
                }
            }
            return true;
        }

            //TODO check if the pacman touches a gold coin
            //and if yes, then update the neccesseary data
            //for the gold coins and the points
            //so you need to go through the arraylist of goldcoins and
            //check each of them for a collision with the pacman
            public void doCollisionCheck() {
                for (GoldCoin gcoin : coins) {

            int centerpacx = pacx + pacBitmap.getWidth() / 2;
            int centerpacy = pacy + pacBitmap.getHeight() / 2;

            int centercoinx = gcoin.getCoinX() + coinBitmap.getWidth() / 2;
            int centercoiny = gcoin.getCoinY() + coinBitmap.getHeight() / 2;

            double distance = Math.sqrt((centerpacy - centercoiny) * (centerpacy - centercoiny) + (centerpacx - centercoinx) * (centerpacx - centercoinx));
            if (distance < 50 && !gcoin.IsPickedUp()) {
                gcoin.setIsPickedUp(true);
                points++;
                pointsView.setText("Points: " + " " + points);
            }
        }

            for (
                    Enemy enemy : enemies) {
                int centerpacx = pacx + pacBitmap.getWidth() / 2;
                int centerpacy = pacy + pacBitmap.getHeight() / 2;

                int centerenemyx = enemy.getEnemyX() + enemyBitmap.getWidth() / 2;
                int centerenemyy = enemy.getEnemyY() + enemyBitmap.getHeight() / 2;

                double distance = Math.sqrt((Math.pow((centerpacx - centerenemyx), 2)) + Math.pow((centerpacy - centerenemyy), 2));
                if (distance < 50) {
                    Toast.makeText(context, "You lost Try again!", Toast.LENGTH_LONG).show();
                    running = false;
                }
            }

            // checking if coins are picked up and which levels it so it can go to next level. I know it can be implemented in another way too
            if (CheckIfCollectedAll() && level == 1) {
                Toast.makeText(context, "You win! Next level.", Toast.LENGTH_LONG).show();
                nextLevel2();
            }

            if (CheckIfCollectedAll() && level == 2) {
                Toast.makeText(context, "You win! Next level.", Toast.LENGTH_LONG).show();
                nextLevel3();
            }

            if (CheckIfCollectedAll() && level == 3) {
                Toast.makeText(context, "You won the game", Toast.LENGTH_LONG).show();
                newGame();
                running = false;
            }
        }

            // second level
            public void nextLevel2() {
                     pacx = 40;
                     pacy = 400; //just some starting coordinates
                     //reset the points
                     points = 0;
                     pointsView.setText(context.getResources().getString(R.string.points) + " " + points);
                     coins.clear();
                     enemies.clear();
                     level = 2;
                     countdown = 55;
                     levelView.setText(context.getResources().getString(R.string.level)+ " " +level);
                     gameView.invalidate(); //redraw screen
                     coins.add(new GoldCoin(220, 200, false));
                     coins.add(new GoldCoin(450, 750, false));
                     coins.add(new GoldCoin(290, 320, false));
                     coins.add(new GoldCoin(169, 500, false));
                     coins.add(new GoldCoin(470, 650, false));
                     coins.add(new GoldCoin(410, 267, false));
                     coins.add(new GoldCoin(342, 570, false));
                     coins.add(new GoldCoin(292, 410, false));

                     enemies.add(new Enemy(300, 200));
                     enemies.add(new Enemy(100, 700));
             }
             // third level
              public void nextLevel3() {
                    pacx = 40;
                    pacy = 400; //just some starting coordinates
                    //reset the points
                    points = 0;
                    pointsView.setText(context.getResources().getString(R.string.points) + " " + points);
                    coins.clear();
                    enemies.clear();
                    level = 3;
                    countdown = 85;
                    levelView.setText(context.getResources().getString(R.string.level)+ " " +level);
                    gameView.invalidate(); //redraw screen
                    coins.add(new GoldCoin(120, 200, false));
                    coins.add(new GoldCoin(250, 910, false));
                    coins.add(new GoldCoin(390, 320, false));
                    coins.add(new GoldCoin(420, 500, false));
                    coins.add(new GoldCoin(300, 600, false));
                    coins.add(new GoldCoin(290, 320, false));
                    coins.add(new GoldCoin(329, 100, false));
                    coins.add(new GoldCoin(230, 670, false));
                    coins.add(new GoldCoin(320, 287, false));
                    coins.add(new GoldCoin(192, 210, false));
                    coins.add(new GoldCoin(92, 410, false));


                    enemies.add(new Enemy(700, 200));
                    enemies.add(new Enemy(900, 700));
                    enemies.add(new Enemy(500, 400));
                }

                public int getPacx() { return pacx; }
                public int getPacy() { return pacy; }
                public int getPoints() { return points; }
                public ArrayList<GoldCoin> getCoins() { return coins; }
                public ArrayList<Enemy> getEnemies() { return enemies; }
                public Bitmap getPacBitmap() { return pacBitmap; }
                public Bitmap getCoinBitmap() { return coinBitmap; }
                public Bitmap getEnemyBitmap() { return enemyBitmap; }
                public void SetDirection (int direction) { this.direction = direction; }
                public boolean GetRunning() { return running; }
                public void SetRunning (boolean running) { this.running = running; }
                public void SetCountdown (int countdown) {this.countdown = countdown;}
                public int getCountdown() {return countdown;}
                {
                }
            }
