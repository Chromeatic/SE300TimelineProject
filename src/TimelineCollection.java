import java.util.ArrayList;

public class TimelineCollection 
{
    
    private ArrayList<Timeline> Timelines = new ArrayList<Timeline>();

    public void addTimeline(Timeline timeline) {
        Timelines.add(timeline);
    }

    public Timeline getTimeline(int index) {
        return Timelines.get(index);
    }

    public int getSize() {
        return Timelines.size();
    }


}
