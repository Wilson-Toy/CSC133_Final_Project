package Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
        private Particle[] particles;
        private int x, y;
        private int xrange, yrange;
        private int maxlife;
        private String spriteTags[];
        private Sprite spr[];
        
        public ParticleSystem(int numParticles, int x, int y, int xrange, int yrange, int minlife, int maxlife, int xmove, int ymove, int mindelay, int maxdelay, Sprite[] spr){
                this.xrange = xrange;
                this.yrange = yrange;
                this.x = x;
                this.y = y;
                this.maxlife = maxlife;
                particles = new Particle[numParticles];
                this.spriteTags = spriteTags;
                this.spr = spr;
                initParticles(xmove, ymove, mindelay, maxdelay, minlife);
        }
        
        private void initParticles(int xmove, int ymove, int mindelay, int maxdelay, int _minlife) {
                for(int i = 0 ; i < particles.length ; i++) {
                        //int n = spriteTags.length;
                        int n = spr.length;
                        int index = Particle.getRandomInt(0, n-1);
                        particles[i] = new Particle(x, (x+xrange), y, (y+yrange), spr[index], _minlife, maxlife, xmove, ymove, mindelay, maxdelay);
                }
                //Age through at least one life cycle
                boolean isDone = false;
                while(isDone == false) {
                        isDone = true;
                        for(int i = 0 ; i < particles.length ; i++) {
                                particles[i].simulateAge();
                                if(particles[i].hasBeenReset() == false) isDone = false;
                        }
                }
                //All particles passed this point should be aged
        }
        
        // access the Particle array
        public Particle[] getParticleArray() {
                return particles;
        }
        
        // Retrieve iterator with all current particles
        public Iterator<Frame> getParticles(){
                List<Frame> parts = new ArrayList<>();
                for(int i = 0 ; i < particles.length ; i++) {
                        Frame tmp = particles[i].getCurrentFrame();
                        parts.add(tmp);
                }
                return parts.iterator();
        }
}
