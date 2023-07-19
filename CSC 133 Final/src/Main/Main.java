package Main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import Data.AText;
import Data.Animation;
import Data.DialogueSystem;
import Data.Fire;
import Data.Frame;
import Data.Interpreter;
import Data.ParticleSystem;
import Data.RectSystem;
import Data.SaveData;
import Data.Sprite;
import Input.Mouse;
import Sound.Sound;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
        public static Sprite[] rat;
        public static Fire fire;
        
        public static stopWatchX dialogueTimer = new stopWatchX(100);
        public static AText atext = new AText("This is a test of the aText class.", 20);
        public static Sound song = new Sound("Sound/Claude_Theme.wav");
        public static Sound sfx = new Sound("Sound/funny_death.wav");
        public static Sound clickSound = new Sound("Sound/click_sound.wav");
        public static Sound dialogueSound = new Sound("Sound/dialogue_sound.wav");
        public static BufferedImage spritesheet;
        
        // Interpreters
        public static Interpreter si_i = new Interpreter("Script/subImage_coordinates.txt");
        public static Interpreter dialogue_interpreter = new Interpreter("Script/dialogue.txt", spritesheet, si_i);
        public static Interpreter fire_interpreter = new Interpreter("Script/subImage_coordinates.txt");
        public static Interpreter bg_i = new Interpreter("Script/BG_arrow.txt");
        public static Interpreter addBG_i = new Interpreter("Script/add_BG.txt");
        public static Interpreter r_i = new Interpreter("Script/rects.txt", spritesheet, si_i);
        
        public static Point p;
        public static int xMouse, yMouse;
        public static int dropShadow = 2;
        public static int background = 0;  // 0 = title, 1 = frontroom, 2 = lab, 3 = dungeon
        public static boolean gameplayMode = false, dialogueMode = false, item1, item2, item3, item4, item5;
        
        // Dialogue and Rect Systems
        public static DialogueSystem ds = new DialogueSystem(dialogue_interpreter, si_i, spritesheet, atext, dialogueTimer, dialogueSound);
        public static RectSystem rs = new RectSystem(r_i, bg_i, addBG_i, ds, clickSound, xMouse, yMouse, dropShadow, background);
        
        public static Animation anim;
        public static SaveData saveData = new SaveData();
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(Control ctrl){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
	        
	        //Format: Rain(int xpos, int ypos, int xrange, int yrange, int minlife, int maxlife, int numparticles)
	        spritesheet = ctrl.getSpriteFromBackBuffer("spritesheet").getSprite();
	        fire = new Fire(si_i, spritesheet, 1100, 100, 50, 10, 10, 70, 100);  // new instance of Fire using ParticleSystem;
	        ctrl.hideDefaultCursor();  // hides the default cursor
	        song.setLoop();  // playing my original music
	        
	        // animation
	        anim = new Animation(80, true);
                int subImageIndex = 0;
                for(int x = 1280 ; x > -128 ; x -= 12) {
                        si_i.setIndex(198 + subImageIndex);
                        anim.addFrame(new Frame(x, 490, new Sprite(x, 488, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet")));
                        subImageIndex += 3;
                        if(subImageIndex > 12) subImageIndex = 0;
                }
	}
	
	public static void rat(Control ctrl) {
	        Frame curFrame = anim.getCurrentFrame();
                if(curFrame != null) {
                        ctrl.addSpriteToHudBuffer(curFrame.getX(), curFrame.getY(), curFrame.getSprite());
                }
	}
	
	public static void fire(Control ctrl) {
                ParticleSystem pm2 = fire.getParticleSystem();
                Iterator<Frame> it2 = pm2.getParticles();
                while(it2.hasNext()) {
                        Frame par2 = it2.next();
                        si_i.setIndex(51);
                        ctrl.addSpriteToHudBuffer(par2.getX() - fire.getIncrementXPos(), par2.getY() + fire.getIncrementYPos(), par2.getSprite());
                }
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
	        p = Mouse.getMouseCoords();
	        xMouse = (int)p.getX();
	        yMouse = (int)p.getY();
	        //saveData.setDialogueSystem(ds);
	        rs.setSaveData(saveData);
	        ds.setControl(ctrl);
	        rs.setControl(ctrl);
	        rs.setXYMouse(xMouse, yMouse);
	        ds.setBufferedImage(spritesheet);
	        r_i.setBufferedImage(spritesheet);
	        si_i.setBufferedImage(spritesheet);
	        fire.setInterpreter(si_i);
	        fire.setBufferedImage(spritesheet);
	        
	        si_i.setIndex(126);
	        ctrl.addSpriteToOverlayBuffer(new Sprite(xMouse, yMouse, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet"));
	        if(ds.getGameEnded()) {  // if last scene is fully played
	                System.exit(0);
	        }
	        if(ds.getDialogueMode()) {  // dialogue mode
	                ds.setGameplayMode(false);
	        }
	        else if(ds.getGameplayMode()) {  // gameplay mode
	                ds.setDialogueMode(false);
	                rs.displayRange(7, 8);
	                rs.displayRect(39);  // save icon
	                rs.displayRect(42);  // HUD
	                if(ds.getItemTaken(0)) {
                                rs.displayRect(11);
                                fire.setIncrementXPos(775);
                                fire.setIncrementYPos(450);
                                fire(ctrl);
                        }
                        if(ds.getItemTaken(1)) {
                                rs.displayRect(12);
                        }
                        if(ds.getItemTaken(2)) {
                                rs.displayRect(13);
                        }
                        if(ds.getItemTaken(3)) {
                                rs.displayRect(14);
                        }
                        if(ds.getItemTaken(4)) {
                                rs.displayRect(15);
                        }
                        
	        }
                if(rs.getBackground() == 0) {  // title screen/castle
                        addBG_i.setIndex(0);
                        ds.setBackgroundTag(addBG_i.getTag());
                        ctrl.addSpriteToBackgroundBuffer(addBG_i.getX(), addBG_i.getY(), addBG_i.getTag());
                        si_i.setIndex(168);
                        ctrl.addSpriteToHudBuffer(0, 10, new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet"));
                        si_i.setIndex(171);
                        ctrl.addSpriteToHudBuffer(320, 35, new Sprite(0, 0, spritesheet.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet"));
                        rs.displayRect(3);  // new game button
                        rs.displayRect(4);  // load game button
                        if(rs.getNewGame()) {
                                rs.setBackground(1);
                                ds.setDialogueMode(true);
                                ds.setDialogueFlag(0);
                                ds.setGameplayMode(false);
                        }
                        else if(rs.getLoadGame()) {
                                ds.setGameplayMode(true);
                                rs.setBackground(1);
                        }
                        
                }
                else if(rs.getBackground() == 1) {  // front room
                        addBG_i.setIndex(1);
                        ds.setBackgroundTag(addBG_i.getTag());
                        ctrl.addSpriteToBackgroundBuffer(addBG_i.getX(), addBG_i.getY(), addBG_i.getTag());
                        if(ds.getDialogueMode()) {
                                rs.displaySpriteRange(19, 20);
                        }
                        else {
                                rs.displayRange(18, 20);
                        }
                }
                else if(rs.getBackground() == 2) {  // dungeon
                        addBG_i.setIndex(2);
                        ds.setBackgroundTag(addBG_i.getTag());
                        ctrl.addSpriteToBackgroundBuffer(addBG_i.getX(), addBG_i.getY(), addBG_i.getTag());
                        if(ds.getDialogueMode()) {
                                rs.displaySpriteRange(24, 25);
                                if(!ds.getDialogueFlags(6)) {
                                        rs.displaySprite(26);
                                        fire(ctrl);
                                }
                        }
                        else {
                                rat(ctrl);
                                rs.displayRange(24, 25);
                                if(!ds.getDialogueFlags(6)) {
                                        rs.displayRect(26);
                                        fire(ctrl);
                                }     
                        }
                }
                else if(rs.getBackground() == 3) {  // study room
                        addBG_i.setIndex(3);
                        ds.setBackgroundTag(addBG_i.getTag());
                        ctrl.addSpriteToBackgroundBuffer(addBG_i.getX(), addBG_i.getY(), addBG_i.getTag());
                        if(ds.getDialogueMode()) {
                                rs.displaySpriteRange(31, 34);
                                if(!ds.getDialogueFlags(10)) {
                                        rs.displaySprite(36);
                                }
                        }
                        else {
                                rs.displayRange(31, 34);
                                if(!ds.getDialogueFlags(10)) {
                                        rs.displayRect(36);
                                }
                        }
                }
                
                // dialogue flags
                if(ds.getDialogueFlag() == 0) {  // new game
                        ds.playRange(1, 35);
                }
                else if(ds.getDialogueFlag() == 1) {  // front door
                        ds.playRange(38, 42);
                }
                else if(ds.getDialogueFlag() == 2) {  // vault
                        ds.playRange(44, 50);
                }
                else if(ds.getDialogueFlag() == 3) {  // justine in front room
                        ds.playRange(52, 59);
                }
                else if(ds.getDialogueFlag() == 4) {  // left cell
                        ds.playRange(61, 69);
                }
                else if(ds.getDialogueFlag() == 5) {  // right cell
                        ds.playRange(71, 78);
                }
                else if(ds.getDialogueFlag() == 6) {  // acquire torch
                        ds.playRange(80, 93);
                        ds.setItemTaken(0, true);
                }
                else if(ds.getDialogueFlag() == 7) {  // bookshelf
                        ds.playRange(95, 129);
                        //ds.playRange(1, 3);
                }
                else if(ds.getDialogueFlag() == 8) {  // clock
                        ds.playRange(131, 153);
                        //ds.playRange(1, 3);
                }
                else if(ds.getDialogueFlag() == 9) {  // acquire saltpeter
                        ds.playRange(155, 174);
                        //ds.playRange(1, 3);
                        ds.setItemTaken(1, true);
                }
                else if(ds.getDialogueFlag() == 10) {  // acquire cup
                        ds.playRange(176, 184);
                        ds.setItemTaken(2, true);
                }
                else if(ds.getDialogueFlag() == 11) {  // select desk
                        ds.playRange(186, 190);
                }
                else if(ds.getDialogueFlag() == 12) {  // already checked
                        ds.playRange(192, 194);
                }
                else if(ds.getDialogueFlag() == 13) {  // select justine after talking to her once already
                        ds.playRange(196, 197);
                }
                else if(ds.getDialogueFlag() == 14) {  // select justine after checking everything
                        ds.playRange(199, 219);
                }
                else if(ds.getDialogueFlag() == 15) {  // select desk after reviewing with justine
                        ds.playRange(221, 231);
                }
                else if(ds.getDialogueFlag() == 16) {  // select clock after finding message
                        ds.playRange(233, 238);
                }
                else if(ds.getDialogueFlag() == 17) {  // select vault after checking clock
                        ds.playRange(240, 246);
                        ds.setItemTaken(3, true);
                }
                else if(ds.getDialogueFlag() == 18) {  // select the left cell door to get front door key
                        ds.playRange(248, 304);
                        ds.setItemTaken(4, true);
                }
                else if(ds.getDialogueFlag() == 19) {  // select front door to end the game
                        ds.playRange(306, 317);  // should end the game
                }
                else if(ds.getDialogueFlag() == 20) {  // select justine after she says her theory
                        ds.playRange(319, 321);
                }
                else if(ds.getDialogueFlag() == 21) {  // select justine after working at the desk
                        ds.playRange(323, 325);
                }
                else if(ds.getDialogueFlag() == 22) {  // select justine after checking the clock for 4:20
                        ds.playRange(327, 332);
                }
                else if(ds.getDialogueFlag() == 23) {  // select justine after opening the vault for the cell door key
                        ds.playRange(334, 336);
                }
                else if(ds.getDialogueFlag() == 24) {  // select justine after getting the front door key
                        ds.playRange(338, 341);
                }
	}
	// Additional Static methods below...(if needed)
}
