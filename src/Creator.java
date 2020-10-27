import javax.swing.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Creator {
    private JTextArea output;

    public Creator(JTextArea j) {
        output = j;
    }

    public LinkedList<People> createPeople(int averageGapTime, int averageNeedTime, int total) {
        LinkedList<People> peoples = new LinkedList<>();
        Random random = new Random(System.currentTimeMillis());
        int time = 0;
        for (int i = 0; i < total; i++) {
            int thisGapTime = random.nextInt() % (2 * averageGapTime);
            if (thisGapTime < 0) {
                thisGapTime = - thisGapTime;
            }
            int thisNeedTime = random.nextInt() % (2 * averageNeedTime);
            if (thisNeedTime < 0) {
                thisNeedTime = -thisNeedTime;
            } else if (thisNeedTime == 0) {
                thisNeedTime = 1;
            }
            time += thisGapTime;
            People thisPeople = new People(i, time, thisNeedTime);
            output.append(thisPeople.toString() + "\n");
            peoples.add(thisPeople);
        }
        Collections.sort(peoples);
        return peoples;
    }

    public LinkedList<Server> createServer(int number) {
        LinkedList<Server> servers = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            Server thisServer = new Server(i);
            servers.add(thisServer);
        }
        output.append("Create " + number + " servers.\n");
        return servers;
    }
}
