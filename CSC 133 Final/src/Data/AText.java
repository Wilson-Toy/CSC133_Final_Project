package Data;

import timer.stopWatchX;

public class AText {
        private String srcStr;  // original string
        private String destStr;  // currently displayed string
        private stopWatchX timer;  // text speed
        private boolean isFinished;  // flag for end of animation
        private int cursor;  // track index into srcStr
        
        public AText(String srcStr, int delay) {
                this.srcStr = srcStr;
                timer = new stopWatchX(delay);
                destStr = "";
                isFinished = false;
                cursor = 0;
        }
        
        public void setStr(String s) {
                srcStr = s;
        }
        public String getCurrentStr() {
                if(isFinished) {
                        return destStr;
                }
                if(timer.isTimeUp()) {
                        if(cursor < srcStr.length())
                                destStr += srcStr.charAt(cursor++);
                        if(cursor >= srcStr.length())
                                isFinished = true;
                        timer.resetWatch();
                 }
                return destStr;
        }
        public boolean isAnimationDone() {
                return isFinished;
        }
        public void resetAnimation() {
                isFinished = false;
                destStr = "";
                cursor = 0;
                timer.resetWatch();
        }
        public String getSrcStr() {
                return srcStr;
        }
        public void setCurrentStr(String s) {
                destStr = s;
                cursor = srcStr.length();
        }
}
