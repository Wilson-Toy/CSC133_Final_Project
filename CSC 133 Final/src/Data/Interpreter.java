package Data;

import java.awt.Color;
import java.awt.image.BufferedImage;

import FileIO.EZFileRead;
import logic.Control;

public class Interpreter {
        
        private String dialogue, dialogue_name = "", tag, gTag = "", hoverLabel = "", leftArrowLabel = "", rightArrowLabel = "";
        private boolean finishEarly = false;
        private Color color, nameColor;
        private int x, y, x2, y2, width, height, subImageIndex, gHoverSubImageIndex, start, end, eventStart, eventEnd;
        private int[] itemIndex = new int[5], itemIndex2 = new int[5];
        private Frame gHover;
        private Command c = new Command("");
        private RECT r;
        private int index = 0, background = 0, leftBG, rightBG;
        private Control ctrl;
        //private AText atext = new AText("This is a test of the aText class.", 20);
        private EZFileRead ezr;
        private DialogueSystem ds;
        private RectSystem rs;
        private Sprite spr, spr2;
        private BufferedImage bi;
        private Interpreter si_i;
        
        public Interpreter(String fileName) {
                ezr = new EZFileRead(fileName);
        }
        
        public Interpreter(String fileName, BufferedImage bi, Interpreter interpreter) {
                ezr = new EZFileRead(fileName);
                this.bi = bi;
                this.si_i = interpreter;
        }
        
        public Interpreter(String fileName, DialogueSystem ds, RectSystem rs) {
                ezr = new EZFileRead(fileName);
                this.ds = ds;
                this.rs = rs;
        }
        
        public void interpreting() {
                if(!endOfFile()) {
                        String raw = ezr.getLine(index);
                        //String raw = ezr.getNextLine();
                        raw = raw.trim();
                        if(!raw.equals("") && raw.charAt(0) != '#') {
                                c = new Command(raw);
                                if(c.isCommand("dialogue")) {
                                        dialogue_name = c.getParmByIndex(0);
                                        dialogue = c.getParmByIndex(1);
                                        if(c.getNumParms() == 3) {
                                                subImageIndex = Integer.parseInt(c.getParmByIndex(2));
                                                si_i.setIndex(subImageIndex);
                                                spr = new Sprite(x, y, bi.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                                        }
                                        if(dialogue_name.equals("Narrator")) {
                                                color = Color.green;
                                        }
                                        else {
                                                if(dialogue_name.equals("Claude")) {
                                                        nameColor = Color.cyan;
                                                }
                                                else if(dialogue_name.equals("Justine")) {
                                                        nameColor = Color.pink;
                                                }
                                                else if(dialogue_name.equals("Arn")) {
                                                        nameColor = Color.red;
                                                }
                                                color = Color.white;
                                        }
                                }
                                else if(c.isCommand("sprite")) {
                                        x = Integer.parseInt(c.getParmByIndex(0));
                                        y = Integer.parseInt(c.getParmByIndex(1));
                                        tag = c.getParmByIndex(2);
                                }
                                else if(c.isCommand("rect")) {  // rect
                                        x = Integer.parseInt(c.getParmByIndex(0));
                                        y = Integer.parseInt(c.getParmByIndex(1));
                                        x2 = Integer.parseInt(c.getParmByIndex(2));
                                        y2 = Integer.parseInt(c.getParmByIndex(3));
                                        subImageIndex = Integer.parseInt(c.getParmByIndex(4));
                                        si_i.setIndex(subImageIndex);
                                        spr = new Sprite(x, y, bi.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                                        hoverLabel = c.getParmByIndex(5);
                                        gHoverSubImageIndex = Integer.parseInt(c.getParmByIndex(6));
                                        si_i.setIndex(gHoverSubImageIndex);
                                        spr2 = new Sprite(x, y, bi.getSubimage(si_i.getX(), si_i.getY(), si_i.getWidth(), si_i.getHeight()), "spritesheet");
                                        gHover = new Frame(x, y, spr2);
                                        r = new RECT(x, y, x2, y2, subImageIndex, spr, hoverLabel, gHover);
                                }
                                else if(c.isCommand("subImage")) {
                                        tag = c.getParmByIndex(0);
                                        x = Integer.parseInt(c.getParmByIndex(1));
                                        y = Integer.parseInt(c.getParmByIndex(2));
                                        width = Integer.parseInt(c.getParmByIndex(3));
                                        height = Integer.parseInt(c.getParmByIndex(4));
                                }
                                else if(c.isCommand("background")) {
                                        background = Integer.parseInt(c.getParmByIndex(0));
                                        leftBG = Integer.parseInt(c.getParmByIndex(1));
                                        rightBG = Integer.parseInt(c.getParmByIndex(2));
                                        leftArrowLabel = c.getParmByIndex(3);
                                        rightArrowLabel = c.getParmByIndex(4);
                                }
                        }
                }
        }
        
        //dialogue methods
        public String getDialogue(int i) {
                index = i;
                interpreting();
                return dialogue;
        }
        public String getDialogue(int i, BufferedImage b) {
                index = i;
                bi = b;
                interpreting();
                return dialogue;
        }
        public String getDialogueName() {
                interpreting();
                return dialogue_name;
        }
        public Color getColor() {
                interpreting();
                return color;
        }
        public Color getNameColor() {
                interpreting();
                return nameColor;
        }
        
        // coordinate methods
        public int getX() {
                interpreting();
                return x;
        }
        public int getY() {
                interpreting();
                return y;
        } 
        public int getX2() {
                interpreting();
                return x2;
        }
        public int getY2() {
                interpreting();
                return y2;
        }
        public int getWidth() {
                interpreting();
                return width;
        }
        public int getHeight() {
                interpreting();
                return height;
        }
        public int getSubImageIndex() {
                interpreting();
                return subImageIndex;
        }
        public int getGHoverSubImageIndex() {
                return gHoverSubImageIndex;
        }
        public void setBufferedImage(BufferedImage b) {
                bi = b;
        }
        public Sprite getSprite() {
                return spr;
        }
        public Sprite getSprite2() {
                return spr2;
        }
        
        // Other
        public String getTag() {
                interpreting();
                return tag;
        }
        
        public String getGraphicalTag() {
                interpreting();
                return gTag;
        }
        
        public RECT getRECT(int i) {
                index = i;
                interpreting();
                return r;
        }
        public void setControl(Control c) {
                ctrl = c;
        }
        
        // background and arrow methods
        public int getBG() {
                interpreting();
                return background;
        }
        public int getLeftBG() {
                interpreting();
                return leftBG;
        }
        public int getRightBG() {
                interpreting();
                return rightBG;
        }
        public String getLeftArrowLabel() {
                interpreting();
                return leftArrowLabel;
        }
        public String getRightArrowLabel() {
                interpreting();
                return rightArrowLabel;
        }
        
        // event start and end
        public int getEventStart() {
                interpreting();
                return eventStart;
        }
        public int getEventEnd() {
                interpreting();
                return eventEnd;
        }
        
        // index methods
        public void setIndex(int i) {
                index = i;
        }
        public void incrementIndex(int i) {
                index += i;
        }
        
        // EZFileRead methods
        public int getNumLines() {
                return ezr.getNumLines();
        }
        public boolean endOfFile() {
                return index >= ezr.getNumLines();
        }
        public boolean finishEarly() {
                return finishEarly;
        }
        public void setDialogueSystem(DialogueSystem d) {
                ds = d;
        }
}
