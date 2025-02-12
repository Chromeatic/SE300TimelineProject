public class Event {

    private String name;
    private int time;
    private String info;
    
    public Event(String name, int time, String info) 
    {

        this.name = name;
        this.time = time;
        this.info = info;

    }

    public String getName() {
        return name;
    }

    public int getTime()
    {
        return time;
    }

    public String getInfo()
    {
        return info;
    }

}
