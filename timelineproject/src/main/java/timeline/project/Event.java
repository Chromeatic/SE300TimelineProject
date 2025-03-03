package timeline.project;

public class Event {

    private String name;
    private String time;
    private String info;
    
    public Event(String name, String time, String info) 
    {

        this.name = name;
        this.time = time;
        this.info = info;

    }

    public String getName() {
        return name;
    }

    public String getTime()
    {
        return time;
    }

    public String getInfo()
    {
        return info;
    }

}
