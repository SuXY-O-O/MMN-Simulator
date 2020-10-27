import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Center {
    private int maxWaitingNumber;
    private int time;
    private int queueUseCount;
    private LinkedList<Server> servers;

    private JTextArea output;

    private LinkedList<People> arriveList;      // People do not arrived
    private LinkedList<People> waitingList;     // People waiting for server
    private ArrayList<People> servedList;       // People have been served
    private ArrayList<People> leftList;         // People left because the waiting queue is full

    public Center(int averageGap, int averageNeed, int totalPeople, int serverNumber, int maxQueueLong, JTextArea output) {
        output.append("=============================================================\n");
        output.append("Begin Create:\n");
        maxWaitingNumber = maxQueueLong;
        time = 0;
        queueUseCount = 0;
        Creator creator = new Creator(output);
        servers = creator.createServer(serverNumber);
        arriveList = creator.createPeople(averageGap, averageNeed, totalPeople);
        waitingList = new LinkedList<>();
        servedList = new ArrayList<>();
        leftList = new ArrayList<>();
        this.output = output;
        output.append("=============================================================\n");
    }

    private void print(String s) {
        output.append(s + "\n");
    }

    /* return:
    *      0: next is arriving
    *      1: next is a people end serving and leave
    *      2: all ended
    */
    private int getNextEvent() {
        Collections.sort(servers);
        if (servers.getFirst().getThisEndTime() == -1) {
            if (arriveList.isEmpty()) {
                return 2;
            }
            else {
                return 0;
            }
        }
        else if (arriveList.isEmpty()) {
            return 1;
        }
        else {
            if (servers.getFirst().getThisEndTime() < arriveList.getFirst().getArriveTime())
                return 1;
            else
                return 0;
        }
    }

    private void peopleArrive() {
        People arrive = arriveList.pollFirst();
        if (arrive == null)
            throw new NullPointerException();
        time = arrive.getArriveTime();
        print(arrive.arrive());
        if (waitingList.size() >= maxWaitingNumber) {
            print(arrive.leaveWithoutServe());
            leftList.add(arrive);
        }
        else {
            for (Server server : servers) {
                if (!server.isServing()) {
                    print(server.beginServePeople(arrive, time));
                    servedList.add(arrive);
                    return;
                }
            }
            waitingList.add(arrive);
            print(arrive.beginWait());
        }
    }

    private void peopleEndServe() {
        time = servers.getFirst().getThisEndTime();
        print(servers.getFirst().endServePeople());
        if (!waitingList.isEmpty()) {
            People waiting = waitingList.pollFirst();
            print(servers.getFirst().beginServePeople(waiting, time));
            servedList.add(waiting);
        }
    }

    public void beginServe() {
        output.append("=============================================================\n");
        output.append("Begin Simulation:\n");
        int eventType = getNextEvent();
        int pastTime = time = 0;
        while (eventType != 2) {
            queueUseCount += waitingList.size() * (time - pastTime);
            pastTime = time;
            if (eventType == 0) {
                peopleArrive();
            }
            else if (eventType == 1) {
                peopleEndServe();
            }
            eventType = getNextEvent();
        }
        print("All service end");
        output.append("=============================================================\n");
    }

    public float getAverageWaitingNumber() {
        return queueUseCount / ((float) time);
    }

    public float getAverageWaitingTime() {
        int totalTime = 0;
        for (People p : servedList) {
            totalTime += p.getWaitingTime();
        }
        return ((float) totalTime) / servedList.size();
    }

    public float getAverageTotalTime() {
        int totalTime = 0;
        for (People p : servedList) {
            totalTime += p.getTotalTime();
        }
        return ((float) totalTime) / servedList.size();
    }

    public HashMap<Integer, Float> getServerUtilizationRate() {
        HashMap<Integer, Float> idToRate = new HashMap<>();
        for (Server s: servers) {
            idToRate.put(s.getId(), s.getUtilizationTime() / time);
        }
        return idToRate;
    }

    public int getLeftNumber() {
        return leftList.size();
    }
}
