package timeline.project;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Timeline 
{

    private ArrayList<Event> events = new ArrayList<Event>();

    public Timeline(String csvFilePath) throws IOException {
        events = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
        if (lines.size() < 3) {
            throw new IllegalArgumentException("CSV file must contain at least 3 lines");
        }
        String[] names = lines.get(0).split(",");
        String[] times = lines.get(1).split(",");
        String[] descriptions = lines.get(2).split(",");
        int len = Math.min(names.length, Math.min(times.length, descriptions.length));
        for (int i = 0; i < len; i++) {
            events.add(new Event(names[i].trim(), times[i].trim(), descriptions[i].trim()));
        }
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void displayTimeline()
    {
        for (int i = 0; i < events.size(); i++) 
        {
            System.out.println(events.get(i).getName() + " " + events.get(i).getTime() + " " + events.get(i).getDescription());
        }
    }
}