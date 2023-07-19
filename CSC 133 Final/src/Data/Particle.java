package Data;

import timer.stopWatchX;

public class Particle {
        private int x, y, lifecycle, age, xMove, yMove;
        private String particleSpriteTag;
        private stopWatchX timer;
        private Sprite spr;
        
        //to preserve for resetting
        private int rootX, rootY;
        private boolean isReset;
        
        public Particle(int minX, int maxX, int minY, int maxY, Sprite spr, int minLife, int maxLife, int xMove, int yMove, int mindelay, int maxdelay) {
                this.spr = spr;
                this.x = getRandomInt(minX, maxX);
                this.y = getRandomInt(minY, maxY);
                lifecycle = getRandomInt(minLife, maxLife);
                this.xMove = xMove;
                this.yMove = yMove;
                int delay = getRandomInt(mindelay, maxdelay);
                timer = new stopWatchX(delay);
                rootX = x;
                rootY = y;
        }
        
        /*implemented set methods for xMove and yMove for ease of use*/
        public void changexMove(int newxMove) {
                xMove = newxMove;
        }
        public void changeyMove(int newyMove) {
                yMove = newyMove;
        }
        /*****/
        
        public boolean hasBeenReset() {
                return isReset;
        }
        public void changeX(int newX) {
                x = newX;
        }
        public int getX() {
                return x;
        }
        public int getLifeCycle() {
                return lifecycle;
        }
        public int getAge() {
                return age;
        }
        public void changeSprite(String newSpriteTag) {
                particleSpriteTag = newSpriteTag;
        }
        public void changeSprite(Sprite s) {
                spr = s;
        }
        
        public boolean isParticleDead() {
                if(age >= lifecycle) return true;
                if(y > 720 || x > 1279) return true;
                return false;
        }
        public void simulateAge() {
                age++;
                x += xMove;
                y += yMove;
                if(isParticleDead()) {
                        x = rootX;
                        y = rootY;
                        age = 0;
                        isReset = true;
                }
        }
        public Frame getCurrentFrame() {
                if(timer.isTimeUp()) {
                        age++;
                        x += xMove;
                        y += yMove;
                        if(isParticleDead()) {
                                x = rootX;
                                y = rootY;
                                age = 0;
                                isReset = true;
                        }
                        timer.resetWatch();
                }
                //return new Frame(x, y, particleSpriteTag);
                return new Frame(x, y, spr);
        }
        public static int getRandomInt(int first, int last) {
                int diff = last - first;
                double num = Math.random() * diff;
                int intNum = (int)num;
                return first + intNum;
        }
        public static int rollDie(int dieSides) {
                double result = Math.random() * dieSides;
                int res = (int) result;
                res++;
                return res;
        }
}
