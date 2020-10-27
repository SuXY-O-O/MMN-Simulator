public class People implements Comparable<People> {
    private int id;
    private int arriveTime;
    private int needServerTime;
    private int beginServeTime;
    private boolean isServed;

    public People(int id, int arrive, int need) {
        this.id = id;
        this.arriveTime = arrive;
        this.needServerTime = need;
        isServed = false;
        beginServeTime = -1;
    }

    public String arrive() {
        String for_return = "";
        for_return += "Time: " + arriveTime + "\t People: " + this.id + "\t arrive";
        return for_return;
    }

    public String beginWait() {
        String for_return = "";
        for_return += "Time: " + arriveTime + "\t People: " + this.id + "\t begin waiting in queue";
        return for_return;
    }

    public String leaveWithoutServe() {
        String for_return = "";
        for_return += "Time: " + arriveTime + "\t People: " + this.id + "\t leave because waiting queue is full";
        return for_return;
    }

    public String beginServe(int beginTime, int serverId) {
        this.beginServeTime = beginTime;
        this.isServed = true;
        String for_return = "";
        for_return += "Time: " + String.format("%02d", beginTime) + "\t Server: " + String.format("%02d", serverId) + "\t begin serving People: " + this.id;
        return for_return;
    }

    public String endServe(int serverId) {
        String for_return = "";
        for_return += "Time: " + String.format("%02d", getEndTime()) + "\t Server: " + String.format("%02d", serverId) + "\t end    serving People: " + this.id;
        return for_return;
    }

    public int getEndTime() {
        if (!isServed)
            return -1;
        else
            return beginServeTime + needServerTime;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public int getServeTime() {
        return needServerTime;
    }

    public int getWaitingTime() {
        if (!isServed)
            return -1;
        else
            return beginServeTime - arriveTime;
    }

    public int getTotalTime() {
        if (!isServed)
            return -1;
        else
            return (beginServeTime - arriveTime) + needServerTime;
    }

    @Override
    public String toString() {
        return "People " + String.format("%05d", id) + " will arrive in " + String.format("%03d", arriveTime)
                + " min and need to be served " + String.format("%03d", needServerTime) + " min";
    }

    @Override
    public int compareTo(People p) {
        return this.arriveTime - p.arriveTime;
    }
}
