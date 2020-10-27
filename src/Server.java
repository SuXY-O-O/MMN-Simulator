public class Server implements Comparable<Server> {
    private int id;
    private int inServingTime;
    private boolean isServing;
    private People servingPeople;
    private int thisEndTime;

    public Server(int id) {
        this.id = id;
        this.inServingTime = 0;
        this.isServing = false;
        servingPeople = null;
        thisEndTime = -1;
    }

    public int getId() {
        return id;
    }

    public boolean isServing() {
        return isServing;
    }

    public int getThisEndTime() {
        if (!isServing)
            return -1;
        else
            return thisEndTime;
    }

    public String beginServePeople(People p, int time) {
        String for_return = p.beginServe(time, this.id);
        servingPeople = p;
        thisEndTime = p.getEndTime();
        isServing = true;
        return for_return;
    }

    public String endServePeople() {
        if (!isServing)
            return null;
        else
        {
            inServingTime += servingPeople.getServeTime();
            isServing = false;
            thisEndTime = -1;
            return servingPeople.endServe(this.id);
        }
    }

    public float getUtilizationTime() {
        return (float) inServingTime;
    }

    @Override
    public int compareTo(Server server) {
        if (this.isServing && server.isServing) {
            return this.thisEndTime - server.thisEndTime;
        }
        else if (this.isServing) {
            return -1;
        }
        else if (server.isServing) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
