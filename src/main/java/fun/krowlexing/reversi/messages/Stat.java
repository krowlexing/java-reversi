package fun.krowlexing.reversi.messages;


import java.io.IOException;

public class Stat {
    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public int getCells() {
        var size =  (fieldWidth * fieldHeight) ;

        if (size % 2 == 0) {
            return size;
        } else {
            return size - 1;
        }
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getPairTried() {
        return pairTried;
    }

    public String getUsername() {
        return username;
    }

    public void setPairTried(int pairTried) {
        this.pairTried = pairTried;
    }

    public int getTotalPairs() {
        return totalPairs;
    }

    public void setTotalPairs(int totalPairs) {
        this.totalPairs = totalPairs;
    }


    public double getAverageTime() {
        var avgTime = ((double) timeUsed) / (double) getCells();
        return ((double) Math.round(  avgTime * 100)) / 100;
    }

    public String username;

    public int fieldWidth;

    public int fieldHeight;
    public int timeUsed;

    public int totalTime;
    public int pairTried;

    public int totalPairs;

    public Stat(String username,int fieldWidth, int fieldHeight, int timeUsed, int totalTime, int pairTried, int totalPairs) {
        this.username = username;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.timeUsed = timeUsed;
        this.totalTime = totalTime;
        this.pairTried = pairTried;
        this.totalPairs = totalPairs;
    }

    public static Stat read(SocketReader in) throws IOException {
        return new Stat(
            in.readString(),
            in.readInt(),
            in.readInt(),
            in.readInt(),
            in.readInt(),
            in.readInt(),
            in.readInt()
        );
    }

    public void write(SocketWriter out) throws IOException {
        out.writeString(username);
        out.write(fieldWidth);
        out.write(fieldHeight);
        out.write(timeUsed);
        out.write(totalTime);
        out.write(pairTried);
        out.write(totalPairs);
    }
}
