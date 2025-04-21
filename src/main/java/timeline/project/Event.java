package timeline.project;

public class Event {

    private String name;
    private String time;
    private String description;
    
    public Event(String name, String time, String description) 
    {

        this.name = name;
        this.time = time;
        this.description = description;

    }

    //Getters
    public String getName() {
        return name;
    }

    public String getTime()
    {
        return time;
    }

    public String getDescription()
    {
        return description;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
