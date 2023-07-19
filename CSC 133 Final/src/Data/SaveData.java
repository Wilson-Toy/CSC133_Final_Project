package Data;

import java.util.StringTokenizer;

import FileIO.EZFileRead;
import FileIO.EZFileWrite;

public class SaveData {
        
        private DialogueSystem ds;
        private boolean[] loadDialogueFlags = new boolean[25];
        private boolean[] itemTaken = new boolean[5];
        private String backgroundTag = "";
        
        
        public SaveData() {
                
        }
        public void saveData() {
                String out = "";
                for(int i = 0 ; i < ds.getDialogueFlagsArray().length; i++) {
                        out += ds.getDialogueFlags(i) + "*";
                }
                for(int i = 0 ; i < ds.getItemTakenArray().length ; i++) {
                        out += ds.getItemTaken(i) + "*";
                }
                out += ds.getBackgroundTag() + "*";
                out = out.substring(0, out.length()-1);
                EZFileWrite ezw = new EZFileWrite("save.txt");
                ezw.writeLine(out);
                ezw.saveFile();
        }
        
        public void loadData() {
                EZFileRead ezr = new EZFileRead("save.txt");
                String raw = ezr.getLine(0);
                StringTokenizer st = new StringTokenizer(raw, "*");
                if(st.countTokens() != ds.getDialogueFlagsArray().length + ds.getItemTakenArray().length+1) return;
                for(int i = 0 ; i < ds.getDialogueFlagsArray().length ; i++) {
                        String value = st.nextToken();
                        if(i < ds.getDialogueFlagsArray().length) {
                                if(value.equals("true")) {
                                        //System.out.println(value);
                                        ds.setDialogueFlags(i, true);
                                        loadDialogueFlags[i] = true;
                                }
                                else if(value.equals("false")){
                                        ds.setDialogueFlags(i, false);
                                        loadDialogueFlags[i] = false;
                                }
                        }
                }
               for(int i = 0 ; i < ds.getItemTakenArray().length ; i++) {
                       String value = st.nextToken();
                       if(i < ds.getItemTakenArray().length) {
                               if(value.equals("true")) {
                                       //System.out.println(value);
                                       ds.setItemTaken(i, true);
                                       itemTaken[i] = true;
                               }
                               else if(value.equals("false")){
                                       ds.setItemTaken(i, false);
                                       itemTaken[i] = false;
                               }
                       }
               }
               String value = st.nextToken();
               ds.setBackgroundTag(value);
                        //int val = Integer.parseInt(value);//buffer[i] = val;
        }
        
        public void setDialogueSystem(DialogueSystem d) {
                ds = d;
        }
        
        public boolean[] getDialogueFlags() {
                loadData();
                return loadDialogueFlags;
        }
        
        public boolean[] getItemTaken() {
                loadData();
                return itemTaken;
        }
        
        public String getBackgroundTag() {
                loadData();
                return backgroundTag;
        }
}
