package Data;

import java.awt.Color;

import Sound.Sound;
import logic.Control;

public class RectSystem {
        
        private Control ctrl;
        
        private SaveData saveData;
        private Interpreter rect_interpreter, BG_interpreter, addBG_i;
        private boolean newGame = false, loadGame = false;
        private DialogueSystem ds;
        private Sound sound;
        private int xMouse, yMouse, dropShadow, background;
        
        public RectSystem(Interpreter rect_interpreter, Interpreter BG_interpreter, Interpreter addBG_i, DialogueSystem ds, Sound sound, int xMouse, int yMouse, int dropShadow, int background) {
                this.rect_interpreter = rect_interpreter;
                this.BG_interpreter = BG_interpreter;
                this.addBG_i = addBG_i;
                this.ds = ds;
                this.sound = sound;
                this.xMouse = xMouse;
                this.yMouse = yMouse;
                this.dropShadow = dropShadow;
                this.background = background;
        }
        
        public void displayRect(int i) {
                rect_interpreter.setDialogueSystem(ds);
                saveData.setDialogueSystem(ds);
                // new game
                if(rect_interpreter.getRECT(i).getSubImageIndex() == 129) {
                        ctrl.addSpriteToFrontBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
                        if(rect_interpreter.getRECT(i).isCollision(xMouse, yMouse)) {
                                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(i).getGraphicalHover().getX(), rect_interpreter.getRECT(i).getGraphicalHover().getY(), rect_interpreter.getRECT(i).getGraphicalHover().getSprite());
                                ctrl.drawHudString(xMouse, (yMouse-2), BG_interpreter.getLeftArrowLabel(), Color.black);
                                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, BG_interpreter.getLeftArrowLabel(), Color.magenta);
                        }
                        if(Control.getMouseInput() != null) {
                                if(rect_interpreter.getRECT(i).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                                        sound.playWAV();
                                        addBG_i.setIndex(1);
                                        ds.setBackgroundTag(addBG_i.getTag());
                                        background = 0;
                                        newGame = true;
                                }
                        }
                }
                else if(rect_interpreter.getRECT(i).getSubImageIndex() == 120) {  // load data
                        ctrl.addSpriteToFrontBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
                        if(rect_interpreter.getRECT(i).isCollision(xMouse, yMouse)) {
                                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(i).getGraphicalHover().getX(), rect_interpreter.getRECT(i).getGraphicalHover().getY(), rect_interpreter.getRECT(i).getGraphicalHover().getSprite());
                                ctrl.drawHudString(xMouse, (yMouse-2), BG_interpreter.getLeftArrowLabel(), Color.black);
                                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, BG_interpreter.getLeftArrowLabel(), Color.magenta);
                        }
                        if(Control.getMouseInput() != null) {
                                if(rect_interpreter.getRECT(i).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                                        sound.playWAV();
                                        addBG_i.setIndex(1);
                                        //saveData.loadData();
                                        ds.setDialogueFlagsArray(saveData.getDialogueFlags());
                                        ds.setItemTakenArray(saveData.getItemTaken());
                                        ds.setBackgroundTag(saveData.getBackgroundTag());
                                        if(ds.getBackgroundTag().equals("front_room")) {
                                                System.out.println("PEANUTS");
                                                background = 1;
                                        }
                                        if(ds.getBackgroundTag().equals("dungeon")) {
                                                background = 2;
                                        }
                                        if(ds.getBackgroundTag().equals("study_room")) {
                                                background = 3;
                                        }
                                        
                                        //loadDialogueFlags = saveData.getDialogueFlags();
                                        //System.out.println(Arrays.toString(ds.getDialogueFlagsArray()));
                                        //System.out.println(Arrays.toString(ds.getItemTakenArray()));
                                        //System.out.println(saveData.getBackgroundTag());
                                        loadGame = true;
                                }
                        }
                }
                // arrow navigation
                else if(rect_interpreter.getRECT(i).getSubImageIndex() == 108 || rect_interpreter.getRECT(i).getSubImageIndex() == 135) {
                        ctrl.addSpriteToFrontBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
                        BG_interpreter.setIndex(background);
                        rect_interpreter.getRECT(i).setLabel(BG_interpreter.getLeftArrowLabel());
                        if(rect_interpreter.getRECT(7).isCollision(xMouse, yMouse)) {
                                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(7).getGraphicalHover().getX(), rect_interpreter.getRECT(7).getGraphicalHover().getY(), rect_interpreter.getRECT(7).getGraphicalHover().getSprite());
                                ctrl.drawHudString(xMouse, (yMouse-2), BG_interpreter.getLeftArrowLabel(), Color.black);
                                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, BG_interpreter.getLeftArrowLabel(), Color.magenta);
                        }
                        else if(rect_interpreter.getRECT(8).isCollision(xMouse, yMouse)) {
                                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(8).getGraphicalHover().getX(), rect_interpreter.getRECT(8).getGraphicalHover().getY(), rect_interpreter.getRECT(8).getGraphicalHover().getSprite());
                                ctrl.drawHudString(xMouse, (yMouse-2), BG_interpreter.getRightArrowLabel(), Color.black);
                                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, BG_interpreter.getRightArrowLabel(), Color.magenta);
                        }
                        if(Control.getMouseInput() != null) {
                                if(rect_interpreter.getRECT(7).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                                        sound.playWAV();
                                        background = BG_interpreter.getLeftBG();
                                }
                                else if(rect_interpreter.getRECT(8).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                                        sound.playWAV();
                                        background = BG_interpreter.getRightBG();
                                }
                        }
                }
                else if(rect_interpreter.getRECT(i).getSubImageIndex() == 159) {  // save data
                        ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
                        if(rect_interpreter.getRECT(39).isCollision(xMouse, yMouse)) {
                                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(i).getGraphicalHover().getX(), rect_interpreter.getRECT(i).getGraphicalHover().getY(), rect_interpreter.getRECT(i).getGraphicalHover().getSprite());
                                ctrl.drawHudString(xMouse, (yMouse-2), rect_interpreter.getRECT(i).getHoverLabel(), Color.black);
                                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, rect_interpreter.getRECT(i).getHoverLabel(), Color.magenta);
                        }
                        if(Control.getMouseInput() != null) {
                                if(rect_interpreter.getRECT(39).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                                        saveData.saveData();
                                }
                        }
                }
                else if(rect_interpreter.getRECT(i).getSubImageIndex() == 84) {  // HUD has no collision or click function on purpose
                        ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
                }
                // everything else
                else {
                        ctrl.addSpriteToFrontBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
                        if(rect_interpreter.getRECT(i).isCollision(xMouse, yMouse)) {
                                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(i).getGraphicalHover().getX(), rect_interpreter.getRECT(i).getGraphicalHover().getY(), rect_interpreter.getRECT(i).getGraphicalHover().getSprite());
                                ctrl.drawHudString(xMouse, (yMouse-2), rect_interpreter.getRECT(i).getHoverLabel(), Color.black);
                                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, rect_interpreter.getRECT(i).getHoverLabel(), Color.magenta);
                        }
                        if(Control.getMouseInput() != null) {
                                if(rect_interpreter.getRECT(i).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 66) {  // front door
                                                if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 18) && !ds.getDialogueFlags(19)){
                                                        ds.setDialogueFlag(19);  // ends the game
                                                }
                                                else if(ds.getDialogueFlags(1)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(1);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 192) {  // vault
                                                if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 16) && !ds.getDialogueFlags(17)){
                                                        ds.setDialogueFlag(17);  // checks the vault after deciphering the clock
                                                        ds.setItemTaken(3, true);
                                                }
                                                else if(ds.getDialogueFlags(2)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(2);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 87) {  // justine in front room
                                                if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 18) && !ds.getDialogueFlags(24)){
                                                        ds.setDialogueFlag(24);  // justine tells you to check the left cell
                                                }
                                                else if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 17) && !ds.getDialogueFlagsRange(23, 24)){
                                                        ds.setDialogueFlag(23);  // justine at vault, then tells you to check the left cell
                                                }
                                                else if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 16) && !ds.getDialogueFlagsRange(22, 24)){
                                                        ds.setDialogueFlag(22);  // justine at clock, then tells you to check the vault
                                                }
                                                else if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 15) && !ds.getDialogueFlagsRange(21, 24)){
                                                        ds.setDialogueFlag(21);  // justine at at the desk, then tells you to check the clock
                                                }
                                                else if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlags(14)){
                                                        ds.setDialogueFlag(20);  // justine at front room, then tells you to check the desks
                                                }
                                                else if(ds.getDialogueFlagsRange(0, 11) && !ds.getDialogueFlags(14)){
                                                        ds.setDialogueFlag(14);  // justine will tell you her theory
                                                }
                                                else if(ds.getDialogueFlags(3)) {
                                                        ds.setDialogueFlag(13);  // justine will tell you to check everything, then talk to her for review
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(3);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 114) {  // left cell
                                                if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 17) && !ds.getDialogueFlags(18)){
                                                        ds.setDialogueFlag(18);  // check left cell after opening the vault
                                                        ds.setItemTaken(4, true);
                                                }
                                                else if(ds.getDialogueFlags(4)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(4);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 141) {  // right cell
                                                if(ds.getDialogueFlags(5)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(5);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 186) {  // torch holder
                                                if(ds.getDialogueFlags(6)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                        ds.setItemTaken(0, true);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(6);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 3) {  // bookshelf
                                                if(ds.getDialogueFlags(7)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(7);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 21) {  // clock
                                                
                                                if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlagsRange(14, 15) && !ds.getDialogueFlags(16)){  // test Justine's theory
                                                        ds.setDialogueFlag(16);
                                                }
                                                else if(ds.getDialogueFlags(8)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(8);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 162) {  // storage and collect saltpeter
                                                if(ds.getDialogueFlags(9)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                        ds.setItemTaken(1, true);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(9);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 27) {  // cup
                                                if(ds.getDialogueFlags(10)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                        ds.setItemTaken(2, true);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(10);
                                                }
                                        }
                                        if(rect_interpreter.getRECT(i).getSubImageIndex() == 39) {  // desk
                                                if(ds.getDialogueFlagsRange(0, 11) && ds.getDialogueFlags(14) && !ds.getDialogueFlags(15)){  // test Justine's theory
                                                        ds.setDialogueFlag(15);
                                                }
                                                else if(ds.getDialogueFlags(11)) {  // already checked
                                                        ds.setDialogueFlag(12);
                                                }
                                                else {  // never checked
                                                        ds.setDialogueFlag(11);
                                                }
                                        }
                                        sound.playWAV();
                                }
                        }
                }
        }
        /*ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(7).getX(), rect_interpreter.getRECT(7).getY(), rect_interpreter.getRECT(7).getTag());
        ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(8).getX(), rect_interpreter.getRECT(8).getY(), rect_interpreter.getRECT(8).getTag());
        BG_interpreter.setIndex(background);
        rect_interpreter.getRECT(7).setLabel(BG_interpreter.getLeftArrowLabel());
        rect_interpreter.getRECT(8).setLabel(BG_interpreter.getRightArrowLabel());
        if(rect_interpreter.getRECT(7).isCollision(xMouse, yMouse)) {
                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(7).getGraphicalHover().getX(), rect_interpreter.getRECT(7).getGraphicalHover().getY(), rect_interpreter.getRECT(7).getGraphicalHover().getSpriteTag());
                ctrl.drawHudString(xMouse, (yMouse-2), BG_interpreter.getLeftArrowLabel(), Color.black);
                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, BG_interpreter.getLeftArrowLabel(), Color.magenta);
        }
        else if(rect_interpreter.getRECT(8).isCollision(xMouse, yMouse)) {
                ctrl.addSpriteToHudBuffer(rect_interpreter.getRECT(8).getGraphicalHover().getX(), rect_interpreter.getRECT(8).getGraphicalHover().getY(), rect_interpreter.getRECT(8).getGraphicalHover().getSpriteTag());
                ctrl.drawHudString(xMouse, (yMouse-2), BG_interpreter.getRightArrowLabel(), Color.black);
                ctrl.drawHudString(xMouse-dropShadow, (yMouse-dropShadow)-2, BG_interpreter.getRightArrowLabel(), Color.magenta);
        }
        if(Control.getMouseInput() != null) {
                if(rect_interpreter.getRECT(7).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                        background = BG_interpreter.getLeftBG();
                }
                else if(rect_interpreter.getRECT(8).isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
                        clickSound.playWAV();
                        background = BG_interpreter.getRightBG();
                }
        }*/
        
        public void displayRange(int start, int end) {
                for(int i = start ; i <= end ; i++) {
                        displayRect(i);
                }
        }
        
        public void displaySprite(int i) {
                ctrl.addSpriteToFrontBuffer(rect_interpreter.getRECT(i).getX(), rect_interpreter.getRECT(i).getY(), rect_interpreter.getRECT(i).getSprite());
        }
        
        public void displaySpriteRange(int start, int end) {
                for(int i = start ; i <= end ; i++) {
                        displaySprite(i);
                }
        }
        
        public int getBackground() {
                return background;
        }
        
        public boolean getNewGame() {
                return newGame;
        }
        
        public boolean getLoadGame() {
                return loadGame;
        }
        
        public void setBackground(int bg) {
                background = bg;
        }
        
        public void setControl(Control c) {
                ctrl = c;
        }
        
        public void setXMouse(int x) {
                xMouse = x;
        }
        
        public void setYMouse(int y) {
                yMouse = y;
        }
        
        public void setXYMouse(int x, int y) {
                setXMouse(x);
                setYMouse(y);
        }
        
        public Interpreter getRectInterpreter() {
                return rect_interpreter;
        }
        
        public void setSaveData(SaveData s) {
                saveData = s;
        }
}
