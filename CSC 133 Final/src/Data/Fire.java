package Data;

import java.awt.image.BufferedImage;

public class Fire {
        private ParticleSystem parts;
        private String[] spriteTags;
        private Sprite spr[];
        private int xspeed = 0, yspeed = -5, xpos, ypos, xrange, yrange, incrementXPos, incrementYPos, subImageIndex;
        private Interpreter si_i;
        private BufferedImage spritesheet;
        
        public Fire(Interpreter si_i, BufferedImage spritesheet, int xpos, int ypos, int xrange, int yrange, int minlife, int maxlife, int numparticles) {
                this.si_i = si_i;
                this.spritesheet = spritesheet;
                /*this.xpos = xpos;
                this.ypos = ypos;
                this.xrange = xrange;
                this.yrange = yrange;*/
                
                spr = new Sprite[5];
                si_i.setIndex(51);
                spr[4] = new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                si_i.setIndex(54);
                spr[3] = new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                si_i.setIndex(57);
                spr[2] = new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                si_i.setIndex(60);
                spr[1] = new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                si_i.setIndex(63);
                spr[0] = new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                
                parts = new ParticleSystem(numparticles, xpos, ypos, xrange, yrange, minlife, maxlife, xspeed, yspeed, 20, 40, spr);
        }
        
        private void updateParticleSprites() {
                Particle[] pa = parts.getParticleArray();
                for(int i = 0 ; i < pa.length ; i++) {
                        int stages = spr.length;
                        int life = pa[i].getLifeCycle();
                        int range = life / stages;
                        int age = pa[i].getAge();
                        for(int j = 0 ; j < stages ; j++) {
                                if(age >= (range*j) && age < (range*(j+1))) {
                                        if(pa[i].getAge() == 0) {  // if a particle respawns, the x and y movement will slightly change
                                                pa[i].changexMove(Particle.getRandomInt(-1, 4));
                                                pa[i].changeyMove(Particle.getRandomInt(-4, -6));
                                        }
                                        pa[i].changeSprite(spr[j]);
                                        //pa[i].changeSprite(spriteTags[j]);
                                        //pa[i].changexMove(Particle.getRandomInt(-10, 10));  // added changexMove and changeyMove to make particles sway horizontally
                                        //pa[i].changeyMove(Particle.getRandomInt(-3, -8));
                                        break;
                                }
                        }
                }
        }
        
        public ParticleSystem getParticleSystem() {
                updateParticleSprites();
                return parts;
        }
        
        public void setInterpreter(Interpreter i) {
                si_i = i;
        }
        
        public void setSubImageIndex(int i) {
                subImageIndex = i;
        }
        
        public void setBufferedImage(BufferedImage i) {
                spritesheet = i;
        }
        
        public void setXPos(int x) {
                xpos = x;
        }
        
        public void setYPos(int y) {
                ypos = y;
        }
        
        public void setXRange(int xRange) {
                xrange= xRange;
        }
        
        public void setYRange(int yRange) {
                yrange = yRange;
        }
        
        public int getIncrementXPos() {
                return incrementXPos;
        }
        
        public int getIncrementYPos() {
                return incrementYPos;
        }
        public void setIncrementXPos(int i) {
                incrementXPos= i;
        }
        
        public void setIncrementYPos(int i) {
                incrementYPos = i;
        }
}
