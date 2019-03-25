package org.example.pacman;

        public class Enemy {
            private int enemyx, enemyy;

            public Enemy (int x, int y) {
                this.enemyx = x;
                this.enemyy = y;
            }

            public int getEnemyX() {
                return enemyx;
            }
            public void setEnemyX(int x) {
                this.enemyx = x;
            }
            public int getEnemyY() {
                return enemyy;
            }
            public void setEnemyY(int y) {
                this.enemyy = y;
            }
        }
