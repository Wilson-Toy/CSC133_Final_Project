package Data;

public class Frame {
        private int x;
        private int y;
        private Sprite spr;
        private String spriteTag;
        
        public Frame(int x, int y, String spriteTag) {
                this.x = x;
                this.y = y;
                this.spriteTag = spriteTag;
        }
        
        public Frame(int x, int y, Sprite spr) {
                this.x = x;
                this.y = y;
                this.spr = spr;
        }
        
        public int getX() {
                return x;
        }
        
        public int getY() {
                return y;
        }
        
        public Sprite getSprite() {
                return spr;
        }
        
        public String getSpriteTag() {
                return spriteTag;
        }
}
