package Data;

import java.awt.image.BufferedImage;

import Sound.Sound;
import logic.Control;
import timer.stopWatchX;

public class DialogueSystem {
        
        private int rangeIndex = 1, dialogueFlag = -1;
        
        private AText atext;
        private Interpreter interpreter, si_i;
        private BufferedImage bi;
        private stopWatchX timer;
        private Sound sound;
        private boolean dialogueMode = false, gameplayMode = false, usingRange, gameEnded = false;
        private boolean[] dialogueFlags = new boolean[25];
        private boolean[] itemTaken = new boolean[5];
        private String backgroundTag = "castle";
        private Control ctrl;
        
        public DialogueSystem(Interpreter interpreter, Interpreter si_i, BufferedImage bi, AText atext, stopWatchX timer, Sound sound) {
                this.interpreter = interpreter;
                this.si_i = si_i;
                this.bi = bi;
                this.atext = atext;
                this.timer = timer;
                this.sound = sound;
        }
        
        public void playLine(int i) {
                setDialogueMode(true);
                setGameplayMode(false);
                if(!interpreter.getDialogueName().equals("Narrator")) {
                        //ctrl.addSpriteToDialogueBuffer(450, 100);
                        if(interpreter.getSprite() != null) {
                                ctrl.addSpriteToDialogueBuffer(450, 100, interpreter.getSprite());
                        }
                        ctrl.drawDialogueString(40, 570, interpreter.getDialogueName(), interpreter.getNameColor());
                }
                si_i.setIndex(45);
                ctrl.addSpriteToDialogueBuffer(new Sprite(30, 550, bi.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet"));
                si_i.setIndex(48);
                ctrl.addSpriteToDialogueBuffer(new Sprite(0, 575, bi.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet"));
                atext.setStr(interpreter.getDialogue(i, bi));
                
                if(!atext.isAnimationDone() && timer.isTimeUp()) {
                        timer.resetWatch();
                        sound.playWAV();
                }
                if(Control.getMouseInput() != null) {
                        if(Control.getMouseInput().getButton() == 3) {  // right click to skip text animation
                                if(!atext.isAnimationDone()) {
                                        atext.setCurrentStr(atext.getSrcStr());
                                }
                                else{
                                        atext.resetAnimation();
                                        if(usingRange) {
                                                rangeIndex++;
                                        }
                                        else {
                                                setDialogueMode(false);
                                                dialogueFlags[dialogueFlag] = true;
                                                dialogueFlag = -1;
                                        }
                                }
                        }
                        else if(Control.getMouseInput().getButton() == 1) {  // left click to progress text after animation is done
                                if(!atext.isAnimationDone()) {}
                                else{
                                        atext.resetAnimation();
                                        if(usingRange) {
                                                rangeIndex++;
                                        }
                                        else {
                                                setDialogueMode(false);
                                                dialogueFlags[dialogueFlag] = true;
                                                dialogueFlag = -1;
                                        }
                                }
                        }
                }
                ctrl.drawDialogueString(40, 605, atext.getCurrentStr(), interpreter.getColor());  //should only keep this line after dialogueSystem implementation
        }
        
        public void playRange(int start, int end) {
                if(usingRange == false) {
                        rangeIndex = start;
                        usingRange = true;
                }
                if(usingRange = true) {
                        playLine(rangeIndex);
                        if(rangeIndex >= end) {
                                usingRange = false;
                                setDialogueMode(false);
                                setGameplayMode(true);
                                dialogueFlags[dialogueFlag] = true;
                                if(dialogueFlag == 19) {
                                        gameEnded = true;
                                }
                                dialogueFlag = -1;
                        }
                }
        }
        
        public void setControl(Control c) {
                ctrl = c;
        }
        
        public boolean getDialogueMode() {
                return dialogueMode;
        }
        
        public boolean getGameplayMode() {
                return gameplayMode;
        }
        
        public int getDialogueFlag() {
                return dialogueFlag;
        }
        
        public boolean getDialogueFlags(int i) {
                return dialogueFlags[i];
        }
        
        public boolean getDialogueFlagsRange(int start, int end) {
                for(int i = start ; i <= end ; i++) {
                        if(!dialogueFlags[i]) {
                                return false;
                        }
                }
                return true;
        }
        
        public String getBackgroundTag() {
                return backgroundTag;
        }
        
        public boolean[] getDialogueFlagsArray() {
                return dialogueFlags;
        }
        
        public boolean[] getItemTakenArray() {
                return itemTaken;
        }
        
        public boolean getItemTaken(int i) {
                return itemTaken[i];
        }
        
        public boolean getGameEnded() {
                return gameEnded;
        }
        
        public void setBackgroundTag(String s) {
                backgroundTag = s;
        }
        
        public void setItemTaken(int i, boolean b) {
                itemTaken[i] = b;
        }
        
        public void setDialogueFlagsArray(boolean[] b) {
                dialogueFlags = b;
        }
        
        public void setItemTakenArray(boolean[] b) {
                itemTaken = b;
        }
        
        public void setDialogueFlag(int i) {
                dialogueFlag = i;
        }
        
        public void setDialogueFlags(int i, boolean b) {
                dialogueFlags[i] = b;
        }
        
        public void setDialogueMode(boolean b) {
                dialogueMode = b;
        }
        
        public void setBufferedImage(BufferedImage b) {
                bi = b;
        }
        
        public void setRangeIndex(int i) {
                rangeIndex = i;
        }
        
        public void setGameplayMode(boolean b) {
                gameplayMode = b;
        }
        
        /*ds.playLine(1);  // ideally, this will do all of the below
                        atext.setStr(dialogue_interpreter.getDialogue(1));
                        if(!atext.isAnimationDone() && dialogueTimer.isTimeUp()) {
                                dialogueTimer.resetWatch();
                                dialogueSound.playWAV();
                        }
                        if(Control.getMouseInput() != null && Control.getMouseInput().getButton() == 1) {
                                if(!atext.isAnimationDone()) {
                                        atext.setCurrentStr(atext.getSrcStr());
                                }
                                else{
                                        atext.resetAnimation();
                                        //dialogue_interpreter.incrementIndex(1);
                                }
                        }*/
        
}
