package org.example.pacman;


/**
 * This class should contain information about a single GoldCoin.
 * such as x and y coordinates (int) and whether or not the goldcoin
 * has been taken (boolean)
 */

            public class GoldCoin {
            private int coinx, coiny;
            private boolean isPickedUp;

            public GoldCoin(int x, int y, boolean isPickedUp) {
                this.coinx = x;
                this.coiny = y;
                this.isPickedUp = isPickedUp;
            }


        public int getCoinX() {
            return coinx;
        }
        public int getCoinY() { return coiny; }
        public boolean IsPickedUp() {
            return isPickedUp;
        }
        public void setIsPickedUp (boolean isPickedUp) {
            this.isPickedUp = isPickedUp;
        }

       }

